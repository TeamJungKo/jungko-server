package com.jungko.jungko_server.product;

import com.jungko.jungko_server.auth.domain.Oauth2Type;
import com.jungko.jungko_server.auth.jwt.JwtTokenProvider;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.product.domain.Product;
import com.jungko.jungko_server.product.domain.ProductKeyword;
import com.jungko.jungko_server.product.service.ProductService;
import com.jungko.jungko_server.utils.test.E2EMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("/api/v1/products")
public class ProductControllerTest extends E2EMvcTest {

    private final String URL_PREFIX = "/api/v1/products";
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
    @DisplayName("GET /api/v1/products/{productId}")
    class GetProductDetail {

        private final String url = URL_PREFIX;
        private Member loginMember;
        private Product sample;

        private Set<ProductKeyword> productKeywords;

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

            sample = Product.createProduct(
                    1L,
                    "아이유 검정바지요",
                    "아이유 검정바지 싸게 팔고 있어요.",
                    5000L,
                    "ON_SALE",
                    LocalDateTime.now(),    // CreadtedAt
                    "http://example.com",
                    "중고나라",
                    "1234",
                    LocalDateTime.now(),    // UpdatedAt
                    null,   // productCategory
                    productKeywords,
                    null    // area
            );
        }

        @Test
        @DisplayName("성공 - 특정 상품의 상제 정보 조회")
        void 성공_getProductDetail() throws Exception {
            // given
            Long productId = sample.getId();

            // when
            MockHttpServletRequestBuilder requestBuilder = get(url + "/" + productId)
                    .header(AUTHORIZE_VALUE, BEARER + token);

            // then
            mockMvc.perform(requestBuilder)
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/products/categories")
    class GetProductCategories {

        private final String url = URL_PREFIX;
        private Member loginMember;

        private ProductService productService;

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
        @DisplayName("성공 - 모든 카테고리 조회")
        void getAllProductCategories() throws Exception {
            MockHttpServletRequestBuilder requestBuilder = get(url + "/categories")
                    .header(AUTHORIZE_VALUE, BEARER + token);

            mockMvc.perform(requestBuilder)
                    .andDo(print())
                    .andExpect(status().isOk());
            Mockito.verify(productService).getAllCategories();
        }
    }
}
