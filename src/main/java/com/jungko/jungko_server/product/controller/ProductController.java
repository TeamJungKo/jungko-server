package com.jungko.jungko_server.product.controller;

import com.jungko.jungko_server.area.dto.response.AreaListResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductCategoryListResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductDetailResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductListResponseDto;
import com.jungko.jungko_server.product.dto.response.RelatedQueryListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        @Operation(summary = "검색어를 통해 상품 목록을 조회", description = "상세 검색 페이지, 제목과 카테고리, 지역, 가격범위를 기반으로하여 상품을 조회합니다. 제목과 일치하는 결과를 제공합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "ok"),
        })
        @GetMapping(value = "/search")
        public ProductListResponseDto searchProducts(
                        @RequestParam String query,
                        @RequestParam Integer page,
                        @RequestParam Integer size,
                        @RequestParam String sort,
                        @RequestParam Order order) {
                log.info("Called searchProducts query: {}, page: {}, size: {}, sort: {}, order{}", query, page, size,
                                sort,
                                order);
                return ProductListResponseDto.builder().build();
        }

        @Operation(summary = "검색 결과에 대한 연관 검색어 조회", description = "현재 검색 결과의 연관 검색어를 제공합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "ok"),
        })
        @GetMapping(value = "/search/related")
        public RelatedQueryListResponseDto searchByRelatedKeyword(
                        @RequestParam String query) {
                log.info("Called searchByRelatedKeyword query: {}", query);
                return RelatedQueryListResponseDto.builder().build();
        }

        @Operation(summary = "선택한 상품들 비교", description = "선택한 상품들을 비교합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "ok"),
        })
        @GetMapping(value = "/compare")
        public ProductListResponseDto compareProducts(
                        @RequestParam Integer page,
                        @RequestParam Integer size,
                        @RequestParam String sort,
                        @RequestParam Order order) {
                log.info("Called compareProducts page: {}, size: {}, sort: {}, order: {}", page, size, sort, order);
                return ProductListResponseDto.builder().build();
        }

        @Operation(summary = "특정 상품 상세 정보 조회", description = "특정 상품의 상세 정보를 조회합니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "ok"),
        })
        @GetMapping(value = "/{productId}")
        public ProductDetailResponseDto getProductDetail(
                        @PathVariable("productId") Long productId) {
                log.info("Called getProductDetail productId: {}", productId);
                return ProductDetailResponseDto.builder().build();
        }

        @Operation(summary = "전체 카테고리 목록 조회", description = "전체 카테고리 목록을 조회합니다. 리소스 수가 많지 않으므로 페이지네이션을 지원하지 않습니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "ok"),
        })
        @GetMapping(value = "/categories")
        public ProductCategoryListResponseDto getAllCategories() {
                log.info("Called getAllCategories");
                return ProductCategoryListResponseDto.builder().build();
        }

        @Operation(summary = "전체 지역 목록 조회", description = "전체 지역 목록을 조회합니다. 리소스 수가 많지 않으므로 페이지네이션을 지원하지 않습니다.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "ok"),
        })
        @GetMapping(value = "/areas")
        public AreaListResponseDto getAllAreas() {
                log.info("Called getAllAreas");
                return AreaListResponseDto.builder().build();
        }
}
