package com.jungko.jungko_server.keyword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KeywordController {

	@GetMapping("/")
	@ResponseBody
	public String index() {
		return "Hello World!";
	}
}


