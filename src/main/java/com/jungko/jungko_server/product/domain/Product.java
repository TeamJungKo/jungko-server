package com.jungko.jungko_server.product.domain;

import com.jungko.jungko_server.area.domain.EmdArea;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	@OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	private ProductCategory productCategory;

	@OneToMany(mappedBy = "product")
	private Set<ProductKeyword> productKeywords;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id", unique = true)
	private EmdArea area;

}
