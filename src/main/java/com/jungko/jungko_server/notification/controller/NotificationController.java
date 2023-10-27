package com.jungko.jungko_server.notification.controller;

import com.jungko.jungko_server.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/notices", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {

    private final NotificationService notificationService;

}
