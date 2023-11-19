package com.jungko.jungko_server.mapper;


import com.jungko.jungko_server.product.domain.Product;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.dto.ProductDetailDto;
import com.jungko.jungko_server.product.dto.SpecificProductCategoryDto;
import com.jungko.jungko_server.product.dto.response.ProductDetailResponseDto;
import java.util.HashSet;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


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


	/**
	 * 특정 ProductCategory의 부모 요소를 포함하여 SpecificProductCategoryDto로 변환한다.
	 *
	 * @param category 변환할 ProductCategory
	 * @return 변환된 SpecificProductCategoryDto
	 */
	default SpecificProductCategoryDto convertToSpecificProductCategoryDtoRecursive(
			ProductCategory category) {
		return convertToSpecificProductCategoryDtoRecursive(category, new HashSet<>());
	}

	default SpecificProductCategoryDto convertToSpecificProductCategoryDtoRecursive(
			ProductCategory category, Set<Long> visitedIds) {
		SpecificProductCategoryDto dto = SpecificProductCategoryDto.builder()
				.categoryId(category.getId())
				.name(category.getName())
				.level(category.getLevel())
				.subCategory(null)
				.build();

		if (visitedIds.contains(category.getId())) {
			return dto;
		}
		visitedIds.add(category.getId());

		if (category.getParentCategory() != null) {
			SpecificProductCategoryDto parentDto = convertToSpecificProductCategoryDtoRecursive(
					category.getParentCategory(), visitedIds);
			parentDto.setSubCategory(dto);
			return parentDto;
		} else {
			return dto;
		}
	}
}
