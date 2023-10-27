package com.jungko.jungko_server.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String profileImageUrl;

    @NotNull
    @Size(max = 255)
    private String nickname;

    @NotNull
    private Boolean notificationAgreement;

    @NotNull
    @Size(max = 255)
    private String oauthType;

    @NotNull
    @Size(max = 255)
    private String oauthId;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

}
