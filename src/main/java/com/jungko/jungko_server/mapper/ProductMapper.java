package com.jungko.jungko_server.mapper;


import com.jungko.jungko_server.product.domain.Product;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.dto.ProductCategoryDto;
import com.jungko.jungko_server.product.dto.ProductDetailDto;
import com.jungko.jungko_server.product.dto.response.ProductCategoryListResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductDetailResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productCategory", target = "productCategory")
    @Mapping(source = "product.area", target = "area")
    @Mapping(source = "product.imageUrl", target = "productImageUrl")
    ProductDetailDto toProductDetailDto(Product product, String imageUrl);

    @Mapping(source = "productDetailDto", target = "productDetail")
    ProductDetailResponseDto toProductDetailResponseDto(ProductDetailDto productDetailDto);

    @Mapping(source = "productCategory.id", target = "categoryId")
    @Mapping(source = "productCategory.childCategories", target = "subCategory")
    ProductCategoryDto toProductCategoryDto(ProductCategory productCategory);
}
