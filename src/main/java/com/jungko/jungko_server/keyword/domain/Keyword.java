package com.jungko.jungko_server.keyword.domain;

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
import org.hibernate.annotations.OnDelete;

public class Keyword {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String keyword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Member member;
    public static Keyword createKeyword(String keyword) {
        Keyword keywordd = new Keyword();
        keywordd.keyword = keyword;
        return keywordd;
    }
    public void setOwner(Member member) {
        this.member = member;
    }
    public boolean isOwner(Member loginMember) {
        return this.member.equals(loginMember);
    }
}