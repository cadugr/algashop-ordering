package com.anonymous.algashop.ordering.domain.model.repository;

import com.anonymous.algashop.ordering.domain.model.entity.Customer;
import com.anonymous.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.anonymous.algashop.ordering.domain.model.valueobject.Email;
import com.anonymous.algashop.ordering.domain.model.valueobject.FullName;
import com.anonymous.algashop.ordering.domain.model.valueobject.Phone;
import com.anonymous.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.anonymous.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.anonymous.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.anonymous.algashop.ordering.infrastructure.persistence.provider.CustomersPersistenceProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class
})
class CustomersIT {
    private final Customers customers;

    @Autowired
    public CustomersIT(Customers customers) {
        this.customers = customers;
    }

    @Test
    void shouldPersistAndFind() {
        Customer originalCustomer = CustomerTestDataBuilder.brandNewCustomer().build();
        CustomerId customerId = originalCustomer.id();

        customers.add(originalCustomer);
        Optional<Customer> customerOptional = customers.ofId(customerId);

        assertThat(customerOptional).isPresent();

        Customer savedCustomer = customerOptional.get();

        assertThat(savedCustomer).satisfies(
                sc -> assertThat(sc.id()).isEqualTo(customerId),
                sc -> assertThat(sc.fullName()).isEqualTo(originalCustomer.fullName()),
                sc -> assertThat(sc.birthDate()).isEqualTo(originalCustomer.birthDate()),
                sc -> assertThat(sc.email()).isEqualTo(originalCustomer.email()),
                sc -> assertThat(sc.phone()).isEqualTo(originalCustomer.phone()),
                sc -> assertThat(sc.document()).isEqualTo(originalCustomer.document()),

                sc -> assertThat(sc.isPromotionNotificationsAllowed()).isTrue(),
                sc -> assertThat(sc.isArchived()).isFalse(),
                sc -> assertThat(sc.registeredAt()).isNotNull(),
                sc -> assertThat(sc.archivedAt()).isNull(),
                sc -> assertThat(sc.loyaltyPoints().value()).isZero()
        );
    }

    @Test
    void shouldUpdateExistingCustomer() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        CustomerId customerId = customer.id();
        FullName updateFullName = new FullName("Leonardo", "DiCaprio");

        customers.add(customer);

        customer = customers.ofId(customerId).orElseThrow();

        customer.changeName(updateFullName);

        customers.add(customer);

        customer = customers.ofId(customerId).orElseThrow();

        assertThat(customer.fullName()).isEqualTo(updateFullName);
    }

    @Test
    void shouldNotAllowStaleUpdates() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        CustomerId customerId = customer.id();
        Phone originPhone = customer.phone();

        customers.add(customer);

        Customer customerT1 = customers.ofId(customerId).orElseThrow();
        Customer customerT2 = customers.ofId(customerId).orElseThrow();

        Email newEmail = new Email("other-email@example.com");
        customerT1.changeEmail(newEmail);
        customers.add(customerT1);

        Phone newPhone = new Phone("456-789-1234");
        customerT2.changePhone(newPhone);

        assertThatThrownBy(() -> customers.add(customerT2)).isInstanceOf(ObjectOptimisticLockingFailureException.class);

        Customer savedCustomer = customers.ofId(customerId).orElseThrow();

        assertThat(savedCustomer).satisfies(
                sc -> assertThat(sc.email()).isEqualTo(newEmail),
                sc -> assertThat(sc.phone()).isEqualTo(originPhone)
        );
    }

    @Test
    void shouldCountExistingCustomers() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();

        assertThat(customers.count()).isZero();

        customers.add(customer);

        assertThat(customers.count()).isEqualTo(1L);
    }

    @Test
    void shouldReturnIfCustomerExists() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();

        assertThat(customers.exists(customer.id())).isFalse();

        customers.add(customer);

        assertThat(customers.exists(customer.id())).isTrue();
        assertThat(customers.exists(new CustomerId())).isFalse();
    }
}
