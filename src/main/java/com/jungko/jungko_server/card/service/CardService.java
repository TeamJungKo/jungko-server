package com.jungko.jungko_server.card.service;

import com.jungko.jungko_server.area.domain.EmdArea;
import com.jungko.jungko_server.area.infrastructure.EmdAreaRepository;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.dto.CardDTO;
import com.jungko.jungko_server.card.infrastructure.CardRepository;
import com.jungko.jungko_server.member.domain.Member;
import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.product.domain.ProductCategory;
import com.jungko.jungko_server.product.infrastructure.ProductCategoryRepository;
import com.jungko.jungko_server.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CardService {

    private final CardRepository cardRepository;
    private final MemberRepository memberRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final EmdAreaRepository emdAreaRepository;

    public CardService(final CardRepository cardRepository, final MemberRepository memberRepository,
            final ProductCategoryRepository productCategoryRepository,
            final EmdAreaRepository emdAreaRepository) {
        this.cardRepository = cardRepository;
        this.memberRepository = memberRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.emdAreaRepository = emdAreaRepository;
    }

    public List<CardDTO> findAll() {
        final List<Card> cards = cardRepository.findAll(Sort.by("id"));
        return cards.stream()
                .map(card -> mapToDTO(card, new CardDTO()))
                .toList();
    }

    public CardDTO get(final Long id) {
        return cardRepository.findById(id)
                .map(card -> mapToDTO(card, new CardDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CardDTO cardDTO) {
        final Card card = new Card();
        mapToEntity(cardDTO, card);
        return cardRepository.save(card).getId();
    }

    public void update(final Long id, final CardDTO cardDTO) {
        final Card card = cardRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cardDTO, card);
        cardRepository.save(card);
    }

    public void delete(final Long id) {
        cardRepository.deleteById(id);
    }

    private CardDTO mapToDTO(final Card card, final CardDTO cardDTO) {
        cardDTO.setId(card.getId());
        cardDTO.setTitle(card.getTitle());
        cardDTO.setKeyword(card.getKeyword());
        cardDTO.setMinPrice(card.getMinPrice());
        cardDTO.setMaxPrice(card.getMaxPrice());
        cardDTO.setScope(card.getScope());
        cardDTO.setCreatedAt(card.getCreatedAt());
        cardDTO.setMember(card.getMember() == null ? null : card.getMember().getId());
        cardDTO.setProductCategory(card.getProductCategory() == null ? null : card.getProductCategory().getId());
        cardDTO.setArea(card.getArea() == null ? null : card.getArea().getId());
        return cardDTO;
    }

    private Card mapToEntity(final CardDTO cardDTO, final Card card) {
        card.setTitle(cardDTO.getTitle());
        card.setKeyword(cardDTO.getKeyword());
        card.setMinPrice(cardDTO.getMinPrice());
        card.setMaxPrice(cardDTO.getMaxPrice());
        card.setScope(cardDTO.getScope());
        card.setCreatedAt(cardDTO.getCreatedAt());
        final Member member = cardDTO.getMember() == null ? null : memberRepository.findById(cardDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        card.setMember(member);
        final ProductCategory productCategory = cardDTO.getProductCategory() == null ? null : productCategoryRepository.findById(cardDTO.getProductCategory())
                .orElseThrow(() -> new NotFoundException("productCategory not found"));
        card.setProductCategory(productCategory);
        final EmdArea area = cardDTO.getArea() == null ? null : emdAreaRepository.findById(cardDTO.getArea())
                .orElseThrow(() -> new NotFoundException("area not found"));
        card.setArea(area);
        return card;
    }

}
