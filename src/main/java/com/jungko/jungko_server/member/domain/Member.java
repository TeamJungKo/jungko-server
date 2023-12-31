package com.jungko.jungko_server.member.domain;

import com.jungko.jungko_server.auth.domain.Oauth2Type;
import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.domain.InterestedCard;
import com.jungko.jungko_server.keyword.domain.Keyword;
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
import java.util.List;
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

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private Boolean notificationAgreement;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Oauth2Type oauthType;

	@Column(nullable = false)
	private String oauthId;

	@Column(nullable = true)
	private String deviceToken;

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
	private List<Keyword> interestedKeywords;

	public static Member createMember(String email, String profileImageUrl, String nickname,
			boolean notificationAgreement, Oauth2Type oauth2Type, String oauthId,
			LocalDateTime now) {
		Member member = new Member();
		member.email = email;
		member.profileImageUrl = profileImageUrl;
		member.nickname = nickname;
		member.notificationAgreement = notificationAgreement;
		member.oauthType = oauth2Type;
		member.oauthId = oauthId;
		member.createdAt = now;
		return member;
	}

	public void updateProfile(String nickname, String email, String profileImageUrl) {
		if (nickname != null && !nickname.isEmpty()) {
			this.nickname = nickname;
		}
		if (email != null && !email.isEmpty()) {
			this.email = email;
		}
		if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
			this.profileImageUrl = profileImageUrl;
		}
	}

	public void setNotificationAgreement(boolean notificationAgreement) {
		this.notificationAgreement = notificationAgreement;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
}
