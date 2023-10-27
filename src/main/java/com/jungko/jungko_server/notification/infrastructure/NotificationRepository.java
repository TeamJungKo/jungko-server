package com.jungko.jungko_server.notification.infrastructure;

import com.jungko.jungko_server.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
