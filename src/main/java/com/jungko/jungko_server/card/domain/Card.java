package com.jungko.jungko_server.card.domain;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.product.domain.ProductCategory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;


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
	@Enumerated(EnumType.STRING)
	private CardScope scope;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "card")
	private Set<InterestedCard> interestedCards;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_category_id")
	@OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	private ProductCategory productCategory;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "area_id")
	@OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	private EmdArea area;

	public static Card createCard(String title, String keyword, Integer minPrice, Integer maxPrice,
			CardScope scope, LocalDateTime now) {
		Card card = new Card();
		card.title = title;
		card.keyword = keyword;
		card.minPrice = minPrice;
		card.maxPrice = maxPrice;
		card.scope = scope;
		card.createdAt = now;
		return card;
	}

	public void setOwner(Member member) {
		this.member = member;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public void setArea(EmdArea area) {
		this.area = area;
	}

	public boolean isOwner(Member loginMember) {
		return this.member.equals(loginMember);
	}

	public void update(String title, String keyword, Integer minPrice, Integer maxPrice,
			CardScope scope) {
		if (title != null && !title.isEmpty()) {
			this.title = title;
		}
		if (keyword != null && !keyword.isEmpty()) {
			this.keyword = keyword;
		}
		if (minPrice != null) {
			this.minPrice = minPrice;
		}
		if (maxPrice != null) {
			this.maxPrice = maxPrice;
		}
		if (scope != null) {
			this.scope = scope;
		}
	}
}
