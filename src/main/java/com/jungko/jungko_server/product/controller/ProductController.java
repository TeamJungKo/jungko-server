package com.jungko.jungko_server.product.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jungko.jungko_server.area.dto.AreaDto;
import com.jungko.jungko_server.area.dto.response.AreaListResponseDto;
import com.jungko.jungko_server.auth.annotation.LoginMemberInfo;
import com.jungko.jungko_server.auth.domain.MemberRole;
import com.jungko.jungko_server.auth.dto.MemberSessionDto;
import com.jungko.jungko_server.mapper.ProductMapper;
import com.jungko.jungko_server.product.dto.ProductDetailDto;
import com.jungko.jungko_server.product.dto.request.ProductCompareRequestDto;
import com.jungko.jungko_server.product.dto.response.ProductCategoryListResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductDetailResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductListResponseDto;
import com.jungko.jungko_server.product.dto.response.RelatedQueryListResponseDto;
import com.jungko.jungko_server.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort.Order;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "상품", description = "상품 관련 API")
public class ProductController {

	private final ProductService productService;
	private final ProductMapper productMapper;

	@Operation(summary = "검색어를 통해 상품 목록을 조회", description = "상세 검색 페이지, 제목과 카테고리, 지역, 가격범위를 기반으로하여 상품을 조회합니다. 제목과 일치하는 결과를 제공합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/search")
	@Secured(MemberRole.S_USER)
	public ProductListResponseDto searchProducts(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@RequestParam String query,
			@RequestParam Integer page,
			@RequestParam Integer size,
			@RequestParam String sort,
			@RequestParam Order order) {
		log.info(
				"Called searchProducts member: {}, query: {}, page: {}, size: {}, sort: {}, order: {}",
				memberSessionDto, query,
				page, size,
				sort,
				order);
		return ProductListResponseDto.builder().build();
	}

	@Operation(summary = "검색 결과에 대한 연관 검색어 조회", description = "현재 검색 결과의 연관 검색어를 제공합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/search/related")
	@Secured(MemberRole.S_USER)
	public RelatedQueryListResponseDto searchByRelatedKeyword(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@RequestParam String query) {
		log.info("Called searchByRelatedKeyword member: {}, query: {}", memberSessionDto, query);
		return RelatedQueryListResponseDto.builder().build();
	}

	@Operation(summary = "선택한 상품들 비교", description = "선택한 상품들을 비교합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/compare")
	@Secured(MemberRole.S_USER)
	public ProductListResponseDto compareProducts(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@Valid @RequestBody ProductCompareRequestDto productIds) {
		log.info(
				"Called compareProducts member: {}, productIds: {}",
				memberSessionDto, productIds);

		return productService.compareProduct(productIds);
	}

	@Operation(summary = "특정 상품 상세 정보 조회", description = "특정 상품의 상세 정보를 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ok"),
	})
	@GetMapping(value = "/{productId}")
	@Secured(MemberRole.S_USER)
	public ProductDetailResponseDto getProductDetail(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable("productId") Long productId) {
		log.info("Called getProductDetail member: {}, productId: {}", memberSessionDto, productId);

		ProductDetailDto productDetailDto = productService.getProductDetail(productId);
		return productMapper.toProductDetailResponseDto(productDetailDto);
	}

	@Operation(summary = "전체 카테고리 목록 조회", description = "전체 카테고리 목록을 조회합니다. 리소스 수가 많지 않으므로 페이지네이션을 지원하지 않습니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "ok",
					content = @io.swagger.v3.oas.annotations.media.Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ProductCategoryListResponseDto.class)
					)
			),
	})
	@GetMapping(value = "/categories")
	@Secured(MemberRole.S_USER)
	public ProductCategoryListResponseDto getAllCategories(
			@LoginMemberInfo MemberSessionDto memberSessionDto
	) {
		log.info("Called getAllCategories member: {}", memberSessionDto);

		return productService.getAllCategories();
	}

	@Operation(summary = "전체 지역 목록 조회", description = "전체 지역 목록을 조회합니다. 리소스 수가 많지 않으므로 페이지네이션을 지원하지 않습니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "ok",
					content = @io.swagger.v3.oas.annotations.media.Content(
							mediaType = "application/json",
							schema = @Schema(implementation = AreaListResponseDto.class)
					)
			),
	})
	@GetMapping(value = "/areas")
	@Secured(MemberRole.S_USER)
	public AreaListResponseDto getAllAreas(
			@LoginMemberInfo MemberSessionDto memberSessionDto
	) {
		log.info("Called getAllAreas member: {}", memberSessionDto);

		return productService.getAllAreas();
	}
}
