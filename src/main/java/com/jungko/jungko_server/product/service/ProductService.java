package com.jungko.jungko_server.product.service;

import com.jungko.jungko_server.area.domain.SidoArea;
import com.jungko.jungko_server.area.dto.response.AreaListResponseDto;
import com.jungko.jungko_server.area.infrastructure.SidoAreaRepository;
import com.jungko.jungko_server.mapper.AreaMapper;
import com.jungko.jungko_server.mapper.ProductMapper;
import com.jungko.jungko_server.product.domain.Product;
import com.jungko.jungko_server.product.dto.ProductCategoryDto;
import com.jungko.jungko_server.product.dto.ProductDetailDto;
import com.jungko.jungko_server.product.dto.ProductPreviewDto;
import com.jungko.jungko_server.product.dto.response.ProductCategoryListResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductListResponseDto;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import com.jungko.jungko_server.product.infrastructure.ProductRepository;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final ProductCategoryRepository productCategoryRepository;
	private final SidoAreaRepository sidoAreaRepository;
	private final AreaMapper areaMapper;

	public ProductListResponseDto searchProduct(String keyword, Integer minPrice, Integer maxPrice,
			Long categoryId, Long areaId, Integer page, Integer size, String sort,
			Direction order) {

		log.info(
				"Called searchProduct keyword: {}, minPrice: {}, maxPrice: {}, categoryId: {}, areaId: {}, page: {}, size: {}, sort: {}, order: {}",
				keyword, minPrice, maxPrice, categoryId, areaId, page, size, sort, order);

		Pageable pageable = PageRequest.of(page, size,
				Sort.by(Sort.Direction.fromString(order.name()), sort));

		Specification<Product> specification = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (keyword != null) {
				predicates.add(criteriaBuilder.like(root.get("title"), "%" + keyword + "%"));
			}
			if (minPrice != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
			}
			if (maxPrice != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
			}
			if (categoryId != null) {
				predicates.add(
						criteriaBuilder.equal(root.get("productCategory").get("id"), categoryId));
			}
			if (areaId != null) {
				predicates.add(criteriaBuilder.equal(root.get("area").get("id"), areaId));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};

		Page<Product> products = productRepository.findAll(specification, pageable);

		List<ProductPreviewDto> productPreviewDtos = products.stream()
				.map(productMapper::toProductPreviewDto)
				.collect(Collectors.toList());

		return productMapper.toProductListResponseDto(productPreviewDtos,
				products.getTotalElements());
	}


	public ProductListResponseDto compareProduct(List<Long> productIds) {
		log.info("Called compareProduct productIds: {}", productIds);

		List<Product> products = productRepository.findAllById(productIds);
		List<ProductPreviewDto> list = new ArrayList<ProductPreviewDto>();
		for (Product product : products) {
			list.add(productMapper.toProductPreviewDto(product));
		}

		return new ProductListResponseDto(list, list.size());
	}

	public ProductDetailDto getProductDetail(Long productId) {
		log.info("Called getProductDetail productId: {}", productId);

		Product product = productRepository.findById(productId).orElseThrow(
				() -> new HttpClientErrorException(
						HttpStatus.NOT_FOUND,
						"해당 상품이 존재하지 않습니다. id=" + productId));

		return productMapper.toProductDetailDto(product, product.getImageUrl());
	}


	public ProductCategoryListResponseDto getAllCategories() {
		log.info("Called getAllCategories");
		List<ProductCategoryDto> categories = productCategoryRepository.findAllByParentId()
				.stream()
				.map(ProductCategoryDto::of)
				.collect(Collectors.toList());
		return new ProductCategoryListResponseDto(categories);
	}

	public AreaListResponseDto getAllAreas() {
		log.info("Called getAllArea");
		List<SidoArea> areas = sidoAreaRepository.findAllWithSiggAndEmd();
		return areaMapper.toAreaListResponseDto(areas);
	}
}
