package com.jungko.jungko_server.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "특정 알림 삭제하는 DTO")
public class NoticeDeleteRequestDto {

  @Schema(description = "notice 고유 번호의 배열", type = "array", example = "[\"1\", \"2\", \"3\"]")
  private final List<Long> noticeIds;
}
