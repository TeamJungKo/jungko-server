package com.jungko.jungko_server.product.domain;

import com.jungko.jungko_server.card.domain.Card;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;


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

	@ToString.Exclude
	@OneToOne(
			mappedBy = "productCategory",
			fetch = FetchType.LAZY
	)
	private Card card;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	@JoinColumn(name = "parent_category_id")
	private ProductCategory parentCategory;

	@OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductCategory> childCategories = new ArrayList<>();

	@ToString.Exclude
	@OneToOne(
			mappedBy = "productCategory",
			fetch = FetchType.LAZY
	)
	private Product product;
}
