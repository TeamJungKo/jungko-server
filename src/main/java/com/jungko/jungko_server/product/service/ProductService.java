package com.jungko.jungko_server.product.service;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.domain.SidoArea;
import com.jungko.jungko_server.area.domain.SiggArea;
import com.jungko.jungko_server.area.dto.AreaDto;
import com.jungko.jungko_server.area.dto.SidoDto;
import com.jungko.jungko_server.area.dto.response.AreaListResponseDto;
import com.jungko.jungko_server.area.infrastructure.EmdAreaRepository;
import com.jungko.jungko_server.area.infrastructure.SidoAreaRepository;
import com.jungko.jungko_server.area.infrastructure.SiggAreaRepository;
import com.jungko.jungko_server.mapper.AreaMapper;
import com.jungko.jungko_server.mapper.ProductMapper;
import com.jungko.jungko_server.product.domain.Product;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.dto.ProductCategoryDto;
import com.jungko.jungko_server.product.dto.ProductDetailDto;
import com.jungko.jungko_server.product.dto.response.ProductCategoryListResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductDetailResponseDto;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import com.jungko.jungko_server.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.awt.geom.Area;
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

    private final EmdAreaRepository emdAreaRepository;
    private final SiggAreaRepository siggAreaRepository;
    private final SidoAreaRepository sidoAreaRepository;
    private final AreaMapper areaMapper;

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

        List<ProductCategory> categories = productCategoryRepository.findAllOrderByParentId();
        List<ProductCategoryDto> categoryDtos = new ArrayList<ProductCategoryDto>();

        for(ProductCategory category : categories) {
            categoryDtos.add(productMapper.toProductCategoryDto(category));
        }

        return new ProductCategoryListResponseDto(categoryDtos);
    }

    public AreaListResponseDto getAllAreas() {
        log.info("Called getAllArea");

        List<EmdArea> emdAreas = emdAreaRepository.findAll();
        List<SiggArea> siggAreas = siggAreaRepository.findAll();
        List<SidoArea> sidoAreas = sidoAreaRepository.findAll();

        List<AreaDto> areas = new ArrayList<AreaDto>();

        for (SidoArea sido : sidoAreas) {
//            for (SiggArea sigg : siggAreas) {
//                for (EmdArea emd : emdAreas) {
//                    areas.add(areaMapper.toEmdDto(emd));
//                }
//            }
            SidoDto sidoDto = areaMapper.toSidoDto(sido);
            areas.add(areaMapper.toAreaDto(sido));
        }

        return new AreaListResponseDto(areas);
    }
}
