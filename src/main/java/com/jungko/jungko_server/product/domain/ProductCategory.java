package com.jungko.jungko_server.product.domain;

import com.jungko.jungko_server.card.domain.Card;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer level;

    @Column
    private String imageUrl;

    @OneToOne(
            mappedBy = "productCategory",
            fetch = FetchType.LAZY
    )
    private Card card;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id", unique = true)
    private ProductCategory productCategory;

    @OneToOne(
            mappedBy = "productCategory",
            fetch = FetchType.LAZY
    )
    private ProductCategory productCategoryParent;

    @OneToOne(
            mappedBy = "productCategory",
            fetch = FetchType.LAZY
    )
    private Product product;

}
