package com.anonymous.algashop.ordering.infrastructure.persistence.provider;

import com.anonymous.algashop.ordering.domain.model.entity.Customer;
import com.anonymous.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.anonymous.algashop.ordering.domain.model.valueobject.Email;
import com.anonymous.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.anonymous.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.anonymous.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.anonymous.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.anonymous.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.anonymous.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DataJpaTest
@Import({
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class
})
class CustomersPersistenceProviderIT {
    private final CustomersPersistenceProvider customersPersistenceProvider;
    private final CustomerPersistenceEntityRepository customerPersistenceRepository;

    @Autowired
    CustomersPersistenceProviderIT(CustomersPersistenceProvider customersPersistenceProvider, CustomerPersistenceEntityRepository customerPersistenceRepository) {
        this.customersPersistenceProvider = customersPersistenceProvider;
        this.customerPersistenceRepository = customerPersistenceRepository;
    }

    @Test
    void shouldUpdateAndKeepPersistenceEntityState() {
        String originEmail = "user@email.com";
        Customer customer = CustomerTestDataBuilder.existingCustomer()
                .email(new Email(originEmail))
                .build();
        UUID customerId = customer.id().value();

        customersPersistenceProvider.add(customer);

        CustomerPersistenceEntity customerPersistence = customerPersistenceRepository.findById(customerId).orElseThrow();

        assertThat(customerPersistence).satisfies(
                cp -> assertThat(cp.getEmail()).isEqualTo(originEmail),

                cp -> assertThat(cp.getCreatedByUserId()).isNotNull(),
                cp -> assertThat(cp.getLastModifiedByUserId()).isNotNull(),
                cp -> assertThat(cp.getLastModifiedAt()).isNotNull()
        );

        String newEmail = "new@email.com";

        customer = customersPersistenceProvider.ofId(customer.id()).orElseThrow();
        customer.changeEmail(new Email(newEmail));
        customersPersistenceProvider.add(customer);

        customerPersistence = customerPersistenceRepository.findById(customerId).orElseThrow();

        assertThat(customerPersistence).satisfies(
                cp -> assertThat(cp.getEmail()).isEqualTo(newEmail),

                cp -> assertThat(cp.getCreatedByUserId()).isNotNull(),
                cp -> assertThat(cp.getLastModifiedByUserId()).isNotNull(),
                cp -> assertThat(cp.getLastModifiedAt()).isNotNull()
        );
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldAddAndFindNotFailWhenNoTransaction() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();

        customersPersistenceProvider.add(customer);

        assertThatNoException().isThrownBy(() -> customersPersistenceProvider.ofId(customer.id()).orElseThrow());
    }

    @Test
    void shouldCountCorrectly() {
        assertThat(customersPersistenceProvider.count()).isZero();

        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        customersPersistenceProvider.add(customer);

        assertThat(customersPersistenceProvider.count()).isEqualTo(1L);
    }

    @Test
    void shouldVerifyIfExists() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        CustomerId customerId = customer.id();

        assertThat(customersPersistenceProvider.exists(customerId)).isFalse();

        customersPersistenceProvider.add(customer);

        assertThat(customersPersistenceProvider.exists(customerId)).isTrue();
    }
}
