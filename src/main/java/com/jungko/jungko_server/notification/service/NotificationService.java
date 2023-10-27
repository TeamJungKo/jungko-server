package com.jungko.jungko_server.notification.service;

import com.jungko.jungko_server.member.infrastructure.MemberRepository;
import com.jungko.jungko_server.notification.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

}
