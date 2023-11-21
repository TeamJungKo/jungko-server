package com.jungko.jungko_server.product.service;

import com.jungko.jungko_server.area.domain.SidoArea;
import com.jungko.jungko_server.area.dto.SpecificAreaDto;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
			ProductCategoryDto productCategoryDto, SpecificAreaDto specificAreaDto,
			PageRequest pageRequest) {
		log.info("Called searchProduct");

		Page<Product> products = productRepository.searchProduct(keyword, minPrice,
				maxPrice, productCategoryDto.getCategoryId(),
				specificAreaDto.getSido().getSigg().getEmd().getCode(),
				pageRequest);

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
