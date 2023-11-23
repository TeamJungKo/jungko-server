package com.jungko.jungko_server.notification.infrastructure;

import com.jungko.jungko_server.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query("SELECT n FROM Notification n WHERE n.member.id = :memberId")
	Page<Notification> findAllByMemberId(Long memberId, Pageable pageable);
}
