package com.jungko.jungko_server.card;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

public class InterestedCardControllerTest extends E2EMvcTest {

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
	class LikeCard {

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
		@DisplayName("성공 - 관심 카드 등록 성공")
		void 성공_관심_카드_등록_성공() throws Exception {
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
			Card otherCard = Card.createCard(
					validTitle,
					validKeyword,
					validMinPrice,
					validMaxPrice,
					validScope,
					LocalDateTime.now()
			);
			otherCard.setArea(em.find(EmdArea.class, validAreaId));
			otherCard.setProductCategory(em.find(ProductCategory.class, validCategoryId));
			otherCard.setOwner(cardOwner);
			em.persist(otherCard);

			// when
			MockHttpServletRequestBuilder request = put(url + "/" + otherCard.getId() + "/like")
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andExpect(status().isOk());

			InterestedCard interestedCard = em.createQuery(
							"select ic from InterestedCard ic where ic.member.id = :memberId and ic.card.id = :cardId",
							InterestedCard.class)
					.setParameter("memberId", loginMember.getId())
					.setParameter("cardId", otherCard.getId())
					.getSingleResult();
			Assertions.assertNotNull(interestedCard);
		}

		@Test
		@DisplayName("실패 - 이미 관심 카드로 등록한 카드를 재등록")
		void 실패_이미_관심_카드로_등록한_카드를_재등록() throws Exception {
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
			InterestedCard interestedCard = InterestedCard.createInterestedCard(loginMember, card);
			em.persist(interestedCard);

			// when
			MockHttpServletRequestBuilder request = put(url + "/" + card.getId() + "/like")
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andExpect(status().isOk());
		}

		@Test
		@DisplayName("실패 - 존재하지 않는 카드를 관심 카드로 등록")
		void 실패_존재하지_않는_카드를_관심_카드로_등록() throws Exception {
			// given
			Long invalidCardId = 9999L;

			// when
			MockHttpServletRequestBuilder request = put(url + "/" + invalidCardId + "/like")
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("실패 - 자신이 만든 카드를 관심 카드로 등록")
		void 실패_자신이_만든_카드를_관심_카드로_등록() throws Exception {
			// given
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
			card.setOwner(loginMember);
			em.persist(card);

			// when
			MockHttpServletRequestBuilder request = put(url + "/" + card.getId() + "/like")
					.header(AUTHORIZE_VALUE, BEARER + token);

			// then
			mockMvc.perform(request)
					.andExpect(status().isForbidden());
		}
	}
}
