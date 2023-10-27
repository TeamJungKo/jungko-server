package com.jungko.jungko_server.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NotificationDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String content;

    @NotNull
    @Size(max = 255)
    private String noticeType;

    @NotNull
    @JsonProperty("isRead")
    private Boolean isRead;

    @NotNull
    private LocalDateTime createdAt;

    private Long member;

}
