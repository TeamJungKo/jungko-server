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
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

	@Nested
	@DisplayName("GET /api/v1/members/{memberId}/profile")
	class GetOtherProfile {

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
		@DisplayName("성공 - 정상적으로 특정 유저의 프로필을 조회한다.")
		void 성공_getOtherProfile() throws Exception {
			// given
			Member otherMember = Member.createMember(
					"otherMember@gmail.com",
					"http://example.com",
					"test",
					false,
					Oauth2Type.GOOGLE,
					"test",
					LocalDateTime.now()
			);
			em.persist(otherMember);

			// when
			MockHttpServletRequestBuilder requestBuilder = get(
					url + "/" + otherMember.getId() + "/profile")
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(requestBuilder)
					.andDo(print())
					.andExpect(status().isOk());
		}

		@Test
		@DisplayName("실패 - 존재하지 않는 유저의 프로필을 조회 시도")
		void 실패_getOtherProfile_not_exists() throws Exception {
			// given
			long notExistsMemberId = 99999L;

			// when
			MockHttpServletRequestBuilder requestBuilder = get(
					url + "/" + notExistsMemberId + "/profile")
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(requestBuilder)
					.andDo(print())
					.andExpect(status().isNotFound());
		}
	}

	@Nested
	@DisplayName("PATCH /api/v1/members/me/profile")
	class UpdateMyProfile {

		private final String url = URL_PREFIX + "/me/profile";
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
		@DisplayName("성공 - 정상적으로 내 프로필을 수정한다.")
		void 성공_updateMyProfile() throws Exception {
			// given
			MockMultipartFile profileImageData = new MockMultipartFile("imageData",
					"normalFile" + ".png", "image/png", "normalFile".getBytes());
			String validNickname = "newNickname";
			String validEmail = "example@gmail.com";

			// when
			MockHttpServletRequestBuilder requestBuilder = multipart(HttpMethod.PATCH, url)
					.file(profileImageData)
					.param("nickname", validNickname)
					.param("email", validEmail)
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(requestBuilder)
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.nickname").value(validNickname))
					.andExpect(jsonPath("$.email").value(validEmail));
		}

		@Test
		@DisplayName("실패 - 유효하지 않은 닉네임")
		void 실패_updateMyProfile_invalid_nickname() throws Exception {
			// given
			MockMultipartFile profileImageData = new MockMultipartFile("imageData",
					"normalFile" + ".png", "image/png", "normalFile".getBytes());
			String inValidNickname = "ånewNickname";
			String validEmail = "example@gmail.com";

			// when
			MockHttpServletRequestBuilder requestBuilder = multipart(HttpMethod.PATCH, url)
					.file(profileImageData)
					.param("nickname", inValidNickname)
					.param("email", validEmail)
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(requestBuilder)
					.andDo(print())
					.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("실패 - 닉네임 범위 초과")
		void 실패_updateMyProfile_exceed_nickname() throws Exception {
			// given
			MockMultipartFile profileImageData = new MockMultipartFile("imageData",
					"normalFile" + ".png", "image/png", "normalFile".getBytes());
			String exceedNickname = "newNickname1234567890123456789012345678901234567890123456789012345678901234567890";
			String validEmail = "example@gmail.com";

			// when
			MockHttpServletRequestBuilder requestBuilder = multipart(HttpMethod.PATCH, url)
					.file(profileImageData)
					.param("nickname", exceedNickname)
					.param("email", validEmail)
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(requestBuilder)
					.andDo(print())
					.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("실패 - 유효하지 않은 이메일")
		void 실패_updateMyProfile_invalid_email() throws Exception {
			// given
			MockMultipartFile profileImageData = new MockMultipartFile("imageData",
					"normalFile" + ".png", "image/png", "normalFile".getBytes());
			String validNickname = "newNickname";
			String inValidEmail = "strange.gmail.com";

			// when
			MockHttpServletRequestBuilder requestBuilder = multipart(HttpMethod.PATCH, url)
					.file(profileImageData)
					.param("nickname", validNickname)
					.param("email", inValidEmail)
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(requestBuilder)
					.andDo(print())
					.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("실패 - 이메일 범위 초과")
		void 실패_updateMyProfile_exceed_email() throws Exception {
			// given
			MockMultipartFile profileImageData = new MockMultipartFile("imageData",
					"normalFile" + ".png", "image/png", "normalFile".getBytes());
			String validNickname = "newNickname";
			String exceedEmail = "example1234567890123456789012345678901234567890123456789012345678901234567890@gmail.com";

			// when
			MockHttpServletRequestBuilder requestBuilder = multipart(HttpMethod.PATCH, url)
					.file(profileImageData)
					.param("nickname", validNickname)
					.param("email", exceedEmail)
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(requestBuilder)
					.andDo(print())
					.andExpect(status().isBadRequest());
		}
	}
}
