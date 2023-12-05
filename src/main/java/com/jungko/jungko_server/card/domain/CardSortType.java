package com.jungko.jungko_server.card.domain;

public enum CardSortType {

	title("title"),
	keyword("keyword"),
	minPrice("minPrice"),
	maxPrice("maxPrice"),
	createdAt("createdAt");

	private final String value;

	CardSortType(String value) {
		this.value = value;
	}
}
