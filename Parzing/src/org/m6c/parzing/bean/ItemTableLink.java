package org.m6c.parzing.bean;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class ItemTableLink extends AncestorItem {

	private static final long serialVersionUID = -8808637844153697383L;

	private boolean exclude = false;
	private String url = "";

	public ItemTableLink() {
		super();
	}

	/**
	 * @param url
	 */
	public ItemTableLink(String url) {
		super();
		this.url = url;
	}

	public boolean isExclude() {
		return exclude;
	}

	public String getUrl() {
		return url;
	}

	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getData() {
		return new String[] { " ", getUrl() };
	}
}