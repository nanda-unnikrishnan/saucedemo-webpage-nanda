package com.saucedemo.qa.base;

public enum PageTitle {

	PRODUCTS_PAGE_TITLE("PRODUCTS"), YOUR_CART_PAGE_TITLE("YOUR CART"),
	YOUR_INFO_PAGE_TITLE("CHECKOUT: YOUR INFORMATION"), CHECKOUT_OVERVIEW_PAGE_TITLE("CHECKOUT: OVERVIEW"),
	CHECKOUT_COMPLETE_PAGE_TITLE("CHECKOUT: COMPLETE!");

	private String titleText;

	PageTitle(String titleText) {
		this.titleText = titleText;
	}

	public String getTitleText() {
		return titleText;
	}

}
