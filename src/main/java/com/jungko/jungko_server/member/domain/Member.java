package com.jungko.jungko_server.member.domain;

import com.jungko.jungko_server.card.domain.Card;
import com.jungko.jungko_server.card.domain.InterestedCard;
import com.jungko.jungko_server.keyword.domain.InterestedKeyword;
import com.jungko.jungko_server.notification.domain.Notification;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private Boolean notificationAgreement;

    @Column(nullable = false)
    private String oauthType;

    @Column(nullable = false)
    private String oauthId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member")
    private Set<Notification> notifications;

    @OneToMany(mappedBy = "member")
    private Set<InterestedCard> interestedCards;

    @OneToMany(mappedBy = "member")
    private Set<Card> cards;

    @OneToMany(mappedBy = "member")
    private Set<InterestedKeyword> interestedKeywords;

}
