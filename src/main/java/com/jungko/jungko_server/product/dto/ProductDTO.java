package com.jungko.jungko_server.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 4095)
    private String content;

    private Long price;

    @NotNull
    @Size(max = 255)
    private String availability;

    @NotNull
    private LocalDateTime uploadedAt;

    @Size(max = 255)
    private String imageUrl;

    @NotNull
    @Size(max = 255)
    private String marketName;

    @NotNull
    @Size(max = 255)
    private String marketProductId;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Long productCategory;

    private Long area;

}
