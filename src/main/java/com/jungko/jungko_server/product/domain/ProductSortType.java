package com.jungko.jungko_server.product.domain;

public enum ProductSortType {

	title("title"),
	price("price"),
	uploadedAt("uploadedAt");

	private final String value;

	ProductSortType(String value) {
		this.value = value;
	}
}
