package com.jungko.jungko_server.keyword.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/keywords", produces = MediaType.APPLICATION_JSON_VALUE)
public class KeywordController {

	@GetMapping("/")
	@ResponseBody
	public String index() {
		return "Hello World!";
	}
}


