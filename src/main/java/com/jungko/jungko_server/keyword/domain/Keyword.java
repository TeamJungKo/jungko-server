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
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;

@Entity

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    public static List<Keyword> createKeyword(Member member, List<String> keywords) {

        List<Keyword> keywordList = new ArrayList<>();

        for (String keyword : keywords) {
            Keyword newKeyword = new Keyword();
            newKeyword.setKeyword(keyword);
            newKeyword.setOwner(member);
            keywordList.add(newKeyword);
        }
        return keywordList;

    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public void setOwner(Member member) {
        this.member = member;
    }
    public boolean isOwner(Member loginMember) {
        return this.member.equals(loginMember);
    }
}