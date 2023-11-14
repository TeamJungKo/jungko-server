package com.jungko.jungko_server.member;

import com.jungko.jungko_server.auth.domain.Oauth2Type;
import com.jungko.jungko_server.auth.jwt.JwtTokenProvider;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.utils.test.E2EMvcTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("/api/v1/members")
public class MemberControllerTest extends E2EMvcTest {

	private final String URL_PREFIX = "/api/v1/members";
	private final String BEARER = "Bearer ";
	private final String AUTHORIZE_VALUE = "Authorization";
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	private String token;


	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext) {
		super.setup(webApplicationContext);
	}

	@Nested
	@DisplayName("GET /api/v1/members/me/profile")
	class GetMyProfile {

		private final String url = URL_PREFIX;
		private Member loginMember;

		@BeforeEach
		void setUp() {
			loginMember = Member.createMember(
					"example@gmail.com",
					"http://example.com",
					"test",
					false,
					Oauth2Type.GOOGLE,
					"test",
					LocalDateTime.now()
			);
			em.persist(loginMember);
			
			token = jwtTokenProvider.createCommonAccessToken(
					loginMember.getId()).getTokenValue();
		}

		@Test
		@DisplayName("성공 - 정상적으로 내 프로필을 조회한다.")
		void 성공_getMyProfile() throws Exception {
			// given
			// when
			MockHttpServletRequestBuilder requestBuilder = get(url + "/me/profile")
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(requestBuilder)
					.andDo(print())
					.andExpect(status().isOk());
		}
	}
}
