package com.anonymous.algashop.ordering.domain.entity;

import com.anonymous.algashop.ordering.domain.valueobject.Money;
import com.anonymous.algashop.ordering.domain.valueobject.Product;
import com.anonymous.algashop.ordering.domain.valueobject.ProductName;
import com.anonymous.algashop.ordering.domain.valueobject.id.ProductId;

public class ProductTestDataBuilder {

    public static final ProductId DEFAULT_PRODUCT_ID = new ProductId();

    private ProductTestDataBuilder() {

    }

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .id(new ProductId())
                .inStock(true)
                .name(new ProductName("Notebook X11"))
                .price(new Money("3000"));
    }

    public static Product.ProductBuilder aProductUnavailable() {
        return Product.builder()
                .id(new ProductId())
                .inStock(false)
                .name(new ProductName("Desktop FX9000"))
                .price(new Money("5000"));
    }

    public static Product.ProductBuilder aProductAltRamMemory() {
        return Product.builder()
                .id(new ProductId())
                .inStock(true)
                .name(new ProductName("4GB RAM"))
                .price(new Money("200"));
    }

    public static Product.ProductBuilder aProductAltMousePad() {
        return Product.builder()
                .id(new ProductId())
                .inStock(true)
                .name(new ProductName("Mouse pad"))
                .price(new Money("100"));
    }

}
