package com.jungko.jungko_server.notification.infrastructure;

import com.jungko.jungko_server.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Page<Notification> findAllByMemberId(Long memberId, Pageable pageable);
}
