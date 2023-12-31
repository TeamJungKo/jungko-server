package com.jungko.jungko_server.mapper;


import com.jungko.jungko_server.area.dto.SpecificAreaDto;
import com.jungko.jungko_server.product.domain.Product;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.domain.ProductKeyword;
import com.jungko.jungko_server.product.dto.ProductDetailDto;
import com.jungko.jungko_server.product.dto.ProductKeywordDto;
import com.jungko.jungko_server.product.dto.ProductPreviewDto;
import com.jungko.jungko_server.product.dto.SpecificProductCategoryDto;
import com.jungko.jungko_server.product.dto.response.ProductDetailResponseDto;
import com.jungko.jungko_server.product.dto.response.ProductListResponseDto;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(ProductMapper.class);

	@Mapping(source = "product.id", target = "productId")
	@Mapping(source = "product.imageUrl", target = "productImageUrl")
	@Mapping(target = "area", source = "areaDto")
	@Mapping(target = "category", source = "categoryDto")
	@Mapping(target = "keywords", source = "keywords")
	ProductDetailDto toProductDetailDto(Product product, String imageUrl, SpecificAreaDto areaDto,
			SpecificProductCategoryDto categoryDto, List<ProductKeywordDto> keywords);

	@Mapping(source = "productDetailDto", target = "productDetail")
	ProductDetailResponseDto toProductDetailResponseDto(ProductDetailDto productDetailDto);

	@Mapping(source = "product.id", target = "productId")
	@Mapping(source = "product.imageUrl", target = "productImageUrl")
	@Mapping(target = "area", source = "areaDto")
	@Mapping(target = "category", source = "categoryDto")
	@Mapping(target = "keywords", source = "keywords")
	ProductPreviewDto toProductPreviewDto(Product product, SpecificAreaDto areaDto,
			SpecificProductCategoryDto categoryDto, List<ProductKeywordDto> keywords);

	@Mapping(source = "productPreviewDtos", target = "products")
	@Mapping(source = "totalElements", target = "totalResources")
	ProductListResponseDto toProductListResponseDto(List<ProductPreviewDto> productPreviewDtos,
			long totalElements);

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
				.imageUrl(category.getImageUrl())
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

	List<ProductKeywordDto> toProductKeywordDto(List<ProductKeyword> keywords);
}
