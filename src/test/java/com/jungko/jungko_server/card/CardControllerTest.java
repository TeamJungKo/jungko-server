package com.jungko.jungko_server.card;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.auth.domain.Oauth2Type;
import com.jungko.jungko_server.auth.jwt.JwtTokenProvider;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.domain.CardScope;
import com.jungko.jungko_server.card.domain.InterestedCard;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.utils.test.E2EMvcTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

@DisplayName("/api/v1/cards")
public class CardControllerTest extends E2EMvcTest {

	private final String URL_PREFIX = "/api/v1/cards";
	private final String BEARER = "Bearer ";
	private final String AUTHORIZE_VALUE = "Authorization";
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	private String token;

	/* start of given data */
	Long validCategoryId = 3L; // 여성의류 -> 아우터
	Long validAreaId = 5196L; // 서울특별시 중구 장충동2가
	String validTitle = "validTitle";
	String validKeyword = "validKeyword";
	Integer validMinPrice = 1000;
	Integer validMaxPrice = 10000;
	CardScope validScope = CardScope.PUBLIC;

	/* end of given data */

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext) {
		super.setup(webApplicationContext);
	}

	@Nested
	@DisplayName("POST /")
	class CreateCard {

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
		@DisplayName("성공 - 정상적으로 카드 생성에 성공한다.")
		@Disabled
		void 성공_createCard() throws Exception {
			// given

			// when
			MockHttpServletRequestBuilder request = multipart(url)
					.param("categoryId", validCategoryId.toString())
					.param("areaId", validAreaId.toString())
					.param("title", validTitle)
					.param("keyword", validKeyword)
					.param("minPrice", validMinPrice.toString())
					.param("maxPrice", validMaxPrice.toString())
					.param("scope", validScope.toString())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
//					.andDo(print())
					.andExpect(status().isCreated());
//					.andExpect(jsonPath("$.cardId").exists())
//					.andExpect(jsonPath("$.title").value(validTitle))
//					.andExpect(jsonPath("$.keyword").value(validKeyword))
//					.andExpect(jsonPath("$.minPrice").value(validMinPrice))
//					.andExpect(jsonPath("$.maxPrice").value(validMaxPrice))
//					.andExpect(jsonPath("$.scope").value(validScope.toString()))
//					.andExpect(jsonPath("$.createdAt").exists())
//					.andExpect(jsonPath("$.author").exists())
//					.andExpect(jsonPath("$.area").exists())
//					.andExpect(jsonPath("$.category").exists());
		}

		@Test
		@DisplayName("실패 - 존재하지 않는 카테고리")
		void 실패_createCard_invalidCategoryId() throws Exception {
			// given
			Integer invalidCategoryId = 99999999;

			// when
			MockHttpServletRequestBuilder request = multipart(url)
					.param("categoryId", invalidCategoryId.toString())
					.param("areaId", validAreaId.toString())
					.param("title", validTitle)
					.param("keyword", validKeyword)
					.param("minPrice", validMinPrice.toString())
					.param("maxPrice", validMaxPrice.toString())
					.param("scope", validScope.toString())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("실패 - 존재하지 않는 지역")
		void 실패_createCard_invalidAreaId() throws Exception {
			// given
			Integer invalidAreaId = 99999999;

			// when
			MockHttpServletRequestBuilder request = multipart(url)
					.param("categoryId", validCategoryId.toString())
					.param("areaId", invalidAreaId.toString())
					.param("title", validTitle)
					.param("keyword", validKeyword)
					.param("minPrice", validMinPrice.toString())
					.param("maxPrice", validMaxPrice.toString())
					.param("scope", validScope.toString())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("실패 - 유효하지 않은 제목")
		void 실패_createCard_invalidTitle() throws Exception {
			// given
			String invalidTitle = "invalidTitleinvalidTitleinvalidTitleinvalidTitleinvalidTitleinvalidTitle";

			// when
			MockHttpServletRequestBuilder request = multipart(url)
					.param("categoryId", validCategoryId.toString())
					.param("areaId", validAreaId.toString())
					.param("title", invalidTitle)
					.param("keyword", validKeyword)
					.param("minPrice", validMinPrice.toString())
					.param("maxPrice", validMaxPrice.toString())
					.param("scope", validScope.toString())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("실패 - 유효하지 않은 키워드")
		void 실패_createCard_invalidKeyword() throws Exception {
			// given
			String invalidKeyword = "invalidKeywordinvalidKeywordinvalidKeywordinvalidKeywordinvalidKeywordinvalidKeyword";

			// when
			MockHttpServletRequestBuilder request = multipart(url)
					.param("categoryId", validCategoryId.toString())
					.param("areaId", validAreaId.toString())
					.param("title", validTitle)
					.param("keyword", invalidKeyword)
					.param("minPrice", validMinPrice.toString())
					.param("maxPrice", validMaxPrice.toString())
					.param("scope", validScope.toString())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("실패 - 유효하지 않은 최소금액")
		void 실패_createCard_invalidMinPrice() throws Exception {
			// given
			Integer invalidMinPrice = -1;

			// when
			MockHttpServletRequestBuilder request = multipart(url)
					.param("categoryId", validCategoryId.toString())
					.param("areaId", validAreaId.toString())
					.param("title", validTitle)
					.param("keyword", validKeyword)
					.param("minPrice", invalidMinPrice.toString())
					.param("maxPrice", validMaxPrice.toString())
					.param("scope", validScope.toString())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("실패 - 유효하지 않은 최대금액")
		void 실패_createCard_invalidMaxPrice() throws Exception {
			// given
			Integer invalidMaxPrice = 1000000000;

			// when
			MockHttpServletRequestBuilder request = multipart(url)
					.param("categoryId", validCategoryId.toString())
					.param("areaId", validAreaId.toString())
					.param("title", validTitle)
					.param("keyword", validKeyword)
					.param("minPrice", validMinPrice.toString())
					.param("maxPrice", invalidMaxPrice.toString())
					.param("scope", validScope.toString())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isBadRequest());
		}
	}

	@Nested
	@DisplayName("DELETE /{cardId}")
	class DeleteCard {

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
		@DisplayName("성공 - 정상적으로 카드 삭제에 성공한다.")
		void 성공_deleteCard() throws Exception {
			// given
			Card card = Card.createCard(
					validTitle,
					validKeyword,
					validMinPrice,
					validMaxPrice,
					validScope,
					LocalDateTime.now()
			);
			card.setOwner(loginMember);
			card.setArea(em.find(EmdArea.class, validAreaId));
			card.setProductCategory(em.find(ProductCategory.class, validCategoryId));
			em.persist(card);

			// when
			MockHttpServletRequestBuilder request = delete(url + "/" + card.getId())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isNoContent());
		}

		@Test
		@DisplayName("실패 - 존재하지 않는 카드")
		void 실패_deleteCard_invalidCardId() throws Exception {
			// given
			Long invalidCardId = 99999999L;

			// when
			MockHttpServletRequestBuilder request = delete(url + "/" + invalidCardId)
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("실패 - 카드의 소유자가 아님")
		void 실패_deleteCard_notOwner() throws Exception {
			// given
			Member cardOwner = Member.createMember(
					"example2@gmail.com",
					"http://example.com",
					"test",
					false,
					Oauth2Type.GOOGLE,
					"test",
					LocalDateTime.now()
			);
			em.persist(cardOwner);
			Card card = Card.createCard(
					validTitle,
					validKeyword,
					validMinPrice,
					validMaxPrice,
					validScope,
					LocalDateTime.now()
			);
			card.setArea(em.find(EmdArea.class, validAreaId));
			card.setProductCategory(em.find(ProductCategory.class, validCategoryId));
			card.setOwner(cardOwner);
			em.persist(card);

			// when
			MockHttpServletRequestBuilder request = delete(url + "/" + card.getId())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isForbidden());
		}
	}

	@Nested
	@DisplayName("PATCH /{cardId}")
	class UpdateCard {

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
		@DisplayName("성공 - 정상적으로 카드 수정에 성공한다.")
		void 성공_updateCard() throws Exception {
			// given
			Card card = Card.createCard(
					validTitle,
					validKeyword,
					validMinPrice,
					validMaxPrice,
					validScope,
					LocalDateTime.now()
			);
			card.setOwner(loginMember);
			card.setArea(em.find(EmdArea.class, validAreaId));
			card.setProductCategory(em.find(ProductCategory.class, validCategoryId));
			em.persist(card);

			String newTitle = "newTitle";
			Integer newMinPrice = 5000;
			CardScope newScope = CardScope.PRIVATE;

			// when
			MockHttpServletRequestBuilder request = multipart(HttpMethod.PATCH,
					url + "/" + card.getId())
					.param("title", newTitle)
					.param("minPrice", newMinPrice.toString())
					.param("scope", newScope.toString())
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isOk());

			Card updatedCard = em.find(Card.class, card.getId());
			assertThat(updatedCard.getTitle()).isEqualTo(newTitle);
			assertThat(updatedCard.getKeyword()).isEqualTo(validKeyword);
			assertThat(updatedCard.getMinPrice()).isEqualTo(newMinPrice);
			assertThat(updatedCard.getMaxPrice()).isEqualTo(validMaxPrice);
			assertThat(updatedCard.getScope()).isEqualTo(newScope);
		}
	}

	@Nested
	@DisplayName("GET /popular")
	class GetPopularCards {

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
		@DisplayName("인기 카드 조회 순위가 올바른지 확인")
		void 인기_카드_조회() throws Exception {
			// given
			Card card1 = Card.createCard(
					validTitle,
					validKeyword,
					validMinPrice,
					validMaxPrice,
					validScope,
					LocalDateTime.now()
			);
			card1.setOwner(loginMember);
			card1.setArea(em.find(EmdArea.class, validAreaId));
			card1.setProductCategory(em.find(ProductCategory.class, validCategoryId));
			em.persist(card1);

			Card card2 = Card.createCard(
					validTitle,
					validKeyword,
					validMinPrice,
					validMaxPrice,
					validScope,
					LocalDateTime.now()
			);
			card2.setOwner(loginMember);
			card2.setArea(em.find(EmdArea.class, validAreaId));
			card2.setProductCategory(em.find(ProductCategory.class, validCategoryId));
			em.persist(card2);

			Card card3 = Card.createCard(
					validTitle,
					validKeyword,
					validMinPrice,
					validMaxPrice,
					validScope,
					LocalDateTime.now()
			);
			card3.setOwner(loginMember);
			card3.setArea(em.find(EmdArea.class, validAreaId));
			card3.setProductCategory(em.find(ProductCategory.class, validCategoryId));
			em.persist(card3);

			em.persist(InterestedCard.createInterestedCard(
					loginMember,
					card2
			));
			em.persist(InterestedCard.createInterestedCard(
					loginMember,
					card2
			));
			em.persist(InterestedCard.createInterestedCard(
					loginMember,
					card3
			));

			int page = 0;
			int size = 3;

			// when
			MockHttpServletRequestBuilder request = get(
					url + "/popular" + "?page=" + page + "&size=" + size)
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.cards.length()").value(3))
					.andExpect(jsonPath("$.cards[0].cardId").value(card2.getId()))
					.andExpect(jsonPath("$.cards[1].cardId").value(card3.getId()))
					.andExpect(jsonPath("$.cards[2].cardId").value(card1.getId()));
		}
	}
}
