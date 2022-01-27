package com.saucedemo.qa.utils;

public enum InventorySortOrder {
	BY_PRICE_HIGH_TO_LOW("Price (high to low)"),
	BY_PRICE_LOW_TO_HIGH("Price (low to high)"),
	BY_NAME_A_TO_Z("Name (A to Z)"),
	BY_NAME_Z_TO_A("Name (Z to A)"); 

	private String description;

	InventorySortOrder(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
