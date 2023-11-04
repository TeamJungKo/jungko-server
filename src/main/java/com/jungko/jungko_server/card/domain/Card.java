package com.jungko.jungko_server.card.domain;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.product.domain.ProductCategory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String keyword;

    @Column
    private Integer minPrice;

    @Column
    private Integer maxPrice;

    @Column(nullable = false)
    private String scope;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "card")
    private Set<InterestedCard> interestedCards;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id", unique = true)
    private ProductCategory productCategory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", unique = true)
    private EmdArea area;

}
