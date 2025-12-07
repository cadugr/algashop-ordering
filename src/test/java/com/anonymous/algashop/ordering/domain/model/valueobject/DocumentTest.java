package com.anonymous.algashop.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void shouldCreateDocument() {
        Document document = new Document("255-08-0578");
        Assertions.assertThat(document.value()).isNotNull();
        Assertions.assertThat(document.value()).isEqualTo("255-08-0578");
    }

    @Test
    void shouldGenerateExceptionWhenValueIsNull() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Document(null));
    }

    @Test
    void shouldGenerateExceptionWhenValueIsBlank() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Document(""));
    }

}