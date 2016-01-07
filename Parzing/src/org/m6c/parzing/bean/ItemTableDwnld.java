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

public class ItemTableDwnld extends AncestorItem {

	private static final long serialVersionUID = 6332019985882025211L;

	public static final String TYPE_TEXT = "Text";
	public static final String TYPE_IMAGE = "Image";
	public static final String TYPE_LINK = "Link";
	public static final String TYPE_BLOCK = "Block";
	
	private String textStart;
	private String textEnd;
	private String textPath;
	private String textType;
	private String textName;

	private boolean mandatory = false;

	/**
	 * 
	 */
	public ItemTableDwnld() {
		super();
	}

	/**
	 * @param textName
	 * @param textType "Text" Or "Image" Or "Link" Or "Block"
	 * @param textStart
	 * @param textEnd
	 * @param textPath
	 */
	public ItemTableDwnld(String textName, String textType, String textStart, String textEnd, String textPath) {
		this(textName, textType, textStart, textEnd, textPath, false);
	}

	/**
	 * @param textName
	 * @param textType "Text" Or "Image" Or "Link" Or "Block"
	 * @param textStart
	 * @param textEnd
	 * @param textPath
	 * @param mandatory
	 */
	public ItemTableDwnld(String textName, String textType, String textStart, String textEnd, String textPath, boolean mandatory) {
		super();
		this.textName = textName;
		this.textType = textType;
		this.textStart = textStart;
		this.textEnd = textEnd;
		this.textPath = textPath;
		this.mandatory = mandatory;
	}

	public String getTextStart() {
		return textStart;
	}

	public void setTextStart(String textStart) {
		this.textStart = textStart;
	}

	public void setTextEnd(String textEnd) {
		this.textEnd = textEnd;
	}

	public String getTextEnd() {
		return textEnd;
	}

	public String getTextPath() {
		return textPath;
	}

	public void setTextPath(String textPath) {
		this.textPath = textPath;
	}

	/**
	 * 
	 * @param textType     "Text" Or "Image" Or "Link" Or "Block"
	 */
	public void setTextType(String textType) {
		this.textType = textType;
	}

	/**
	 * 
	 * @return "Text" Or "Image" Or "Link" Or "Block"
	 */
	public String getTextType() {
		return textType;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	public String getTextName() {
		return textName;
	}

	public String[] getData() {
		String[] data = { getTextName(), getTextStart(), getTextEnd(),
				getTextType(), getTextPath(), "", "" };
		return data;
	}

	/**
	 * @return the mandatory
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
}