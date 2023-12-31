package com.jungko.jungko_server.card.domain;

import com.jungko.jungko_server.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;


@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestedCard {

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	private Member member;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_id")
	@OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	private Card card;

	public static InterestedCard createInterestedCard(Member member, Card card) {
		InterestedCard interestedCard = new InterestedCard();
		interestedCard.createdAt = LocalDateTime.now();
		interestedCard.member = member;
		interestedCard.card = card;
		return interestedCard;
	}
}
