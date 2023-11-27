package com.jungko.jungko_server.keyword;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.auth.domain.Oauth2Type;
import com.jungko.jungko_server.auth.jwt.JwtTokenProvider;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.keyword.domain.Keyword;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.utils.test.E2EMvcTest;
import com.jungko.jungko_server.member.domain.Member;

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


@DisplayName("/api/v1/keywords")
public class KeywordControllerTest extends E2EMvcTest {

	private final String URL_PREFIX = "/api/v1/keywords";
	private final String BEARER = "Bearer ";
	private final String AUTHORIZE_VALUE = "Authorization";
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	private String token;

	/* start of given data */
	Long validKeywordId = 3L;
	String validKeyword = "validKeyword";

	/* end of given data */

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext) {
		super.setup(webApplicationContext);
	}
	@Nested
	@DisplayName("PUT /")
	class CreateKeyword {

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
		@DisplayName("성공 - 정상적으로 키워드 생성에 성공한다.")
		void 성공_createKeyword() throws Exception {
			// given

			// when
			MockHttpServletRequestBuilder request = multipart(url)
					.param("keywordId", validKeywordId.toString())
					.param("keyword", validKeyword)
					.header(AUTHORIZE_VALUE, BEARER + token);
			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.keyword").value(validKeyword));
		}

		@Test
		@DisplayName("실패 - 유효하지 않은 이름")
		void 실패_createKeyword_invalidKeyword() throws Exception {
			// given
			String validKeyword = "invalidKeyword~~~~!!!!!!!!!!!!!!!!!!";

			// when
			MockHttpServletRequestBuilder request = multipart(url)
					.param("keywordId", validKeywordId.toString())
					.param("keyword", validKeyword)
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isBadRequest());
		}
	}

	@Nested
	@DisplayName("DELETE /")
	class DeleteKeyword {

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
		@DisplayName("성공 - 정상적으로 키워드 삭제에 성공한다.")
		void 성공_deleteKeyword() throws Exception {
			// given
			Keyword keyword = Keyword.createKeyword(
					validKeywordId
					validKeyword,
					LocalDateTime.now()
			);
			keyword.setOwner(loginMember);
			em.persist(keyword);

			// when
			MockHttpServletRequestBuilder request = delete(url + "/" + keyword.getId())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isNoContent());
		}

		@Test
		@DisplayName("실패 - 존재하지 않는 키워드")
		void 실패_deleteKeyword_invalidKeyworddId() throws Exception {
			// given
			Long invalidKeywordId = 123456;

			// when
			MockHttpServletRequestBuilder request = delete(url + "/" + invalidKeywordId)
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("실패 - 키워드의 소유자가 아님")
		void 실패_deleteKeywordd_notOwner() throws Exception {
			// given
			Member keywordOwner = Member.createMember(
					"example2@gmail.com",
					"http://example.com",
					"test",
					false,
					Oauth2Type.GOOGLE,
					"test",
					LocalDateTime.now()
			);

			em.persist(keywordOwner);
			Keyword keyword = Keyword.createKeyword(
					validKeywordId
					validKeyword,
					LocalDateTime.now()
			);
			keyword.setOwner(keywordOwner);
			em.persist(keyword);

			// when
			MockHttpServletRequestBuilder request = delete(url + "/" + keyword.getId())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isForbidden());
		}
	}



	@Nested
	@DisplayName("GET /api/v1/keywords/members/me")
	class GetMyKeywords {

		private final String url = URL_PREFIX;
		private Member loginMember;

		private Keyword myKeyword;

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
		@DisplayName("성공 - 정상적으로 내가 등록한 키워드 목록을 조회한다.")
		void 성공_getMyProfile() throws Exception {
			// given
			// when
			MockHttpServletRequestBuilder requestBuilder = get(url + "/members/me")
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
		@DisplayName("성공 - 정상적으로 특정 유저가 등록한 키워드 목록을 조회한다.")
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
		@DisplayName("실패 - 존재하지 않는 유저의 키워드 목록을 조회 시도")
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

}

