package com.anonymous.algashop.ordering.infrastructure.persistence.repository;

import com.anonymous.algashop.ordering.domain.model.utility.IdGenerator;
import com.anonymous.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.anonymous.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@DataJpaTest //This annotation is used to test JPA repositories. It configures in-memory database, Hibernate, Spring Data, and other relevant components.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // This annotation is used to control the configuration of the test database. By setting replace = AutoConfigureTestDatabase.Replace.NONE, it indicates that the test should use the actual database configuration defined in the application instead of replacing it with an in-memory database.
class OrderPersistenceEntityRepositoryIT {

    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;

    @Autowired
    OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderPersistenceEntityRepository) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
    }

    @Test
    void shouldPersist() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();

        orderPersistenceEntityRepository.saveAndFlush(entity);
        Assertions.assertThat(orderPersistenceEntityRepository.existsById(entity.getId())).isTrue();
    }

    @Test
    void shouldCount() {
        long ordersCount = orderPersistenceEntityRepository.count();
        Assertions.assertThat(ordersCount).isZero();
    }

}