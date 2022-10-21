package com.cp.phonebill.helper;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageBundle {

	private static MessageBundle mb = null;
	private ResourceBundle rb = null;
	private static final String RESOURCE_BUNDLE = "ErrorMessage";

	private MessageBundle() {
		loadBundle();
	}

	private void loadBundle() {
		try {
			// System.out.println("Inside Load bundle");

			rb = ResourceBundle.getBundle(RESOURCE_BUNDLE, new Locale("en"), this.getClass().getClassLoader());
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public String getMessage(String code) {
		return rb.getString(code);
	}

	public static MessageBundle getBundle() {
		if (null == mb) {
			mb = new MessageBundle();
		}
		// System.out.println(mb);
		return mb;
	}
}
