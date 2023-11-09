package com.jungko.jungko_server.auth.dto;

import com.jungko.jungko_server.auth.domain.MemberRole;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
/*
  로그인한 회원의 정보를 담는 클래스
 */
public class MemberSessionDto {

	private final Long memberId;
	private final List<MemberRole> roles;
}
