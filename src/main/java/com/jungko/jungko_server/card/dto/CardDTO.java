package com.jungko.jungko_server.card.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CardDTO {

    private Long id;

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String keyword;

    private Integer minPrice;

    private Integer maxPrice;

    @NotNull
    @Size(max = 255)
    private String scope;

    @NotNull
    private LocalDateTime createdAt;

    private Long member;

    private Long productCategory;

    private Long area;

}
