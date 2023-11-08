package com.jungko.jungko_server.member.domain;

import com.jungko.jungko_server.auth.domain.Oauth2Type;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.domain.InterestedCard;
import com.jungko.jungko_server.keyword.domain.InterestedKeyword;
import com.jungko.jungko_server.notification.domain.Notification;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column
	private String profileImageUrl;

	@Column(nullable = false, unique = true)
	private String nickname;

	@Column(nullable = false)
	private boolean notificationAgreement;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Oauth2Type oauthType;

	@Column(nullable = false)
	private String oauthId;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime deletedAt;

	@ToString.Exclude
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private Set<Notification> notifications;

	@ToString.Exclude
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private Set<InterestedCard> interestedCards;

	@ToString.Exclude
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private Set<Card> cards;

	@ToString.Exclude
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private Set<InterestedKeyword> interestedKeywords;

	public static Member createMember(String email, String profileImageUrl, String nickname,
			boolean notificationAgreement, Oauth2Type oauth2Type, String oauthId,
			LocalDateTime now) {
		Member member = new Member();
		member.email = email;
		member.profileImageUrl = profileImageUrl;
		member.nickname = nickname;
		member.notificationAgreement = notificationAgreement;
		member.oauthType = oauth2Type;
		System.out.println("oauthType = " + oauth2Type);
		member.oauthId = oauthId;
		member.createdAt = now;
		return member;
	}
}
