package com.jungko.jungko_server.product.service;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.domain.SidoArea;
import com.jungko.jungko_server.area.domain.SiggArea;
import com.jungko.jungko_server.area.dto.AreaDto;
import com.jungko.jungko_server.area.dto.EmdDto;
import com.jungko.jungko_server.area.dto.SidoDto;
import com.jungko.jungko_server.area.dto.SiggDto;
import com.jungko.jungko_server.area.dto.response.AreaListResponseDto;
import com.jungko.jungko_server.area.infrastructure.SidoAreaRepository;
import com.jungko.jungko_server.product.domain.Product;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.dto.ProductCategoryDto;
import com.jungko.jungko_server.product.dto.response.ProductCategoryListResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductDetailResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductListResponseDto;
import com.jungko.jungko_server.product.dto.response.RelatedQueryListResponseDto;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import com.jungko.jungko_server.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SidoAreaRepository sidoAreaRepository;
    public ProductListResponseDto getProductByKeyword(String query) {
        log.info("Called getProductByKeyword");

        List<Product> products = productRepository.findByTitle(query);

        return ProductListResponseDto.builder().build();
    }

    public RelatedQueryListResponseDto getRelatedKeyword(String query) {
        log.info("Called getRelatedKeyword");

        return RelatedQueryListResponseDto.builder().build();
    }

    public ProductDetailResponseDto getProductDetailById(Long productId) {
        log.info("Called getProductDetailById product: {}", productId);

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id=" + productId));
        return ProductDetailResponseDto.builder().build();
    }

    public ProductCategoryListResponseDto getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();

        List<ProductCategoryDto> productCategoryDtos = productCategories.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new ProductCategoryListResponseDto(productCategoryDtos);
    }

    private ProductCategoryDto mapToDto(ProductCategory category) {
        ProductCategoryDto subCategoryDto = category.getProductCategoryParent() != null ? mapToDto(category.getProductCategoryParent()) : null;

        return ProductCategoryDto.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .level(category.getLevel())
                .subCategory(subCategoryDto)
                .build();
    }

    public AreaListResponseDto getAllAreas() {
        log.info("Called getAllAreas");

        List<SidoArea> sidos = sidoAreaRepository.findAll();

        List<AreaDto> areaDtos = sidos.stream()
                .map(this::mapToAreaDto)
                .collect(Collectors.toList());

        return new AreaListResponseDto(areaDtos);
    }

    private AreaDto mapToAreaDto(SidoArea sido) {
        List<SiggDto> siggDtos = sido.getSiggAreas().stream()
                .map(this::mapToSiggDto)
                .collect(Collectors.toList());

        SidoDto sidoDto = SidoDto.builder()
                .code(sido.getAdmCode())
                .name(sido.getName())
                .sigg(siggDtos)
                .build();

        return AreaDto.builder()
                .sido(sidoDto)
                .build();
    }

    private SiggDto mapToSiggDto(SiggArea sigg) {
        List<EmdDto> emdDtos = sigg.getEmdAreas().stream()
                .map(this::mapToEmdDto)
                .collect(Collectors.toList());

        return SiggDto.builder()
                .code(sigg.getAdmCode())
                .name(sigg.getName())
                .emd(emdDtos)
                .build();
    }

    private EmdDto mapToEmdDto(EmdArea emd) {
        return EmdDto.builder()
                .code(emd.getAdmCode())
                .name(emd.getName())
                .build();
    }

}
