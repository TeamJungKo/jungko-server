package com.jungko.jungko_server.product.service;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.infrastructure.EmdAreaRepository;
import com.jungko.jungko_server.product.domain.Product;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.dto.ProductDTO;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import com.jungko.jungko_server.product.infrastructure.ProductRepository;
import com.jungko.jungko_server.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final EmdAreaRepository emdAreaRepository;

    public ProductService(final ProductRepository productRepository,
            final ProductCategoryRepository productCategoryRepository,
            final EmdAreaRepository emdAreaRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.emdAreaRepository = emdAreaRepository;
    }

    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public ProductDTO get(final Long id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getId();
    }

    public void update(final Long id, final ProductDTO productDTO) {
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    public void delete(final Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setContent(product.getContent());
        productDTO.setPrice(product.getPrice());
        productDTO.setAvailability(product.getAvailability());
        productDTO.setUploadedAt(product.getUploadedAt());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setMarketName(product.getMarketName());
        productDTO.setMarketProductId(product.getMarketProductId());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setProductCategory(product.getProductCategory() == null ? null : product.getProductCategory().getId());
        productDTO.setArea(product.getArea() == null ? null : product.getArea().getId());
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setTitle(productDTO.getTitle());
        product.setContent(productDTO.getContent());
        product.setPrice(productDTO.getPrice());
        product.setAvailability(productDTO.getAvailability());
        product.setUploadedAt(productDTO.getUploadedAt());
        product.setImageUrl(productDTO.getImageUrl());
        product.setMarketName(productDTO.getMarketName());
        product.setMarketProductId(productDTO.getMarketProductId());
        product.setCreatedAt(productDTO.getCreatedAt());
        final ProductCategory productCategory = productDTO.getProductCategory() == null ? null : productCategoryRepository.findById(productDTO.getProductCategory())
                .orElseThrow(() -> new NotFoundException("productCategory not found"));
        product.setProductCategory(productCategory);
        final EmdArea area = productDTO.getArea() == null ? null : emdAreaRepository.findById(productDTO.getArea())
                .orElseThrow(() -> new NotFoundException("area not found"));
        product.setArea(area);
        return product;
    }

}
