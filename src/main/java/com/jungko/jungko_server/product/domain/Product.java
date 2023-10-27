package com.jungko.jungko_server.product.domain;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.domain.ProductKeyword;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Product {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 4095)
    private String content;

    @Column
    private Long price;

    @Column(nullable = false)
    private String availability;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String marketName;

    @Column(nullable = false)
    private String marketProductId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id", nullable = false, unique = true)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    private Set<ProductKeyword> productKeywords;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", unique = true)
    private EmdArea area;

}
