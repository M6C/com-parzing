package org.m6c.parzing.bean;

public class ThreadParameter {
	private boolean clean = false;// true;
	private boolean indent = false;// true;
	private boolean subLink = false;
	private boolean correctHtml = false;// true;
	private boolean useIncludeList = false;
	private boolean useTimeOut = false;
	private boolean editorHtmlUpd = false;
	private boolean showPage = true;// false;
	private boolean parse = false;
	private boolean closeAtEnd = false;
	private boolean useIncludeListAsMask = true;
	private boolean overwriteIfFileExist = true;
	private int maxSubLevel = 1;
	private String encoding;
	private String outputPathXml;
	private String outputPathImage;

	private String proxyHost;//"webproxy-rgs.telintrans.fr";
	private String proxyUser;//"droca";
	private String proxyPwd;//"d230roca";
	private String proxyPort;//"3128";

	private String escapeAttrVal;

	/**
	 * @return the clean
	 */
	public boolean isClean() {
		return clean;
	}
	/**
	 * @param clean the clean to set
	 */
	public void setClean(boolean clean) {
		this.clean = clean;
	}
	/**
	 * @return the indent
	 */
	public boolean isIndent() {
		return indent;
	}
	/**
	 * @param indent the indent to set
	 */
	public void setIndent(boolean indent) {
		this.indent = indent;
	}
	/**
	 * @return the subLink
	 */
	public boolean isSubLink() {
		return subLink;
	}
	/**
	 * @param subLink the subLink to set
	 */
	public void setSubLink(boolean subLink) {
		this.subLink = subLink;
	}
	/**
	 * @return the correctHtml
	 */
	public boolean isCorrectHtml() {
		return correctHtml;
	}
	/**
	 * @param correctHtml the correctHtml to set
	 */
	public void setCorrectHtml(boolean correctHtml) {
		this.correctHtml = correctHtml;
	}
	/**
	 * @return the useIncludeList
	 */
	public boolean isUseIncludeList() {
		return useIncludeList;
	}
	/**
	 * @param useIncludeList the useIncludeList to set
	 */
	public void setUseIncludeList(boolean useIncludeList) {
		this.useIncludeList = useIncludeList;
	}
	/**
	 * @return the useTimeOut
	 */
	public boolean isUseTimeOut() {
		return useTimeOut;
	}
	/**
	 * @param useTimeOut the useTimeOut to set
	 */
	public void setUseTimeOut(boolean useTimeOut) {
		this.useTimeOut = useTimeOut;
	}
	/**
	 * @return the editorHtmlUpd
	 */
	public boolean isEditorHtmlUpd() {
		return editorHtmlUpd;
	}
	/**
	 * @param editorHtmlUpd the editorHtmlUpd to set
	 */
	public void setEditorHtmlUpd(boolean editorHtmlUpd) {
		this.editorHtmlUpd = editorHtmlUpd;
	}
	/**
	 * @return the showPage
	 */
	public boolean isShowPage() {
		return showPage;
	}
	/**
	 * @param showPage the showPage to set
	 */
	public void setShowPage(boolean showPage) {
		this.showPage = showPage;
	}
	/**
	 * @return the parse
	 */
	public boolean isParse() {
		return parse;
	}
	/**
	 * @param parse the parse to set
	 */
	public void setParse(boolean parse) {
		this.parse = parse;
	}
	/**
	 * @return the closeAtEnd
	 */
	public boolean isCloseAtEnd() {
		return closeAtEnd;
	}
	/**
	 * @param closeAtEnd the closeAtEnd to set
	 */
	public void setCloseAtEnd(boolean closeAtEnd) {
		this.closeAtEnd = closeAtEnd;
	}
	/**
	 * @return the useIncludeListAsMask
	 */
	public boolean isUseIncludeListAsMask() {
		return useIncludeListAsMask;
	}
	/**
	 * @param useIncludeListAsMask the useIncludeListAsMask to set
	 */
	public void setUseIncludeListAsMask(boolean useIncludeListAsMask) {
		this.useIncludeListAsMask = useIncludeListAsMask;
	}
	/**
	 * @param overwriteIfFileExist the overwriteIfFileExist to set
	 */
	public void setOverwriteIfFileExist(boolean overwriteIfFileExist) {
		this.overwriteIfFileExist = overwriteIfFileExist;
	}
	/**
	 * @return the overwriteIfFileExist
	 */
	public boolean isOverwriteIfFileExist() {
		return overwriteIfFileExist;
	}
	/**
	 * @param maxSubLevel the maxSubLevel to set
	 */
	public void setMaxSubLevel(int maxSubLevel) {
		this.maxSubLevel = maxSubLevel;
	}
	/**
	 * @return the maxSubLevel
	 */
	public int getMaxSubLevel() {
		return maxSubLevel;
	}
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}
	/**
	 * @param outputPathXml the outputPathXml to set
	 */
	public void setOutputPathXml(String outputPathXml) {
		this.outputPathXml = outputPathXml;
	}
	/**
	 * @return the outputPathXml
	 */
	public String getOutputPathXml() {
		return outputPathXml;
	}
	public void setOutputPathImage(String outputPathImage) {
		this.outputPathImage = outputPathImage;
	}
	public String getOutputPathImage() {
		return outputPathImage;
	}
	/**
	 * @return the proxyHost
	 */
	public String getProxyHost() {
		return proxyHost;
	}
	/**
	 * @param proxyHost the proxyHost to set
	 */
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}
	/**
	 * @return the proxyUser
	 */
	public String getProxyUser() {
		return proxyUser;
	}
	/**
	 * @param proxyUser the proxyUser to set
	 */
	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}
	/**
	 * @return the proxyPwd
	 */
	public String getProxyPwd() {
		return proxyPwd;
	}
	/**
	 * @param proxyPwd the proxyPwd to set
	 */
	public void setProxyPwd(String proxyPwd) {
		this.proxyPwd = proxyPwd;
	}
	/**
	 * @return the proxyPort
	 */
	public String getProxyPort() {
		return proxyPort;
	}
	/**
	 * @param proxyPort the proxyPort to set
	 */
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	/**
	 * @param escapeAttrVal the escapeAttrVal to set
	 */
	public void setEscapeAttrVal(String escapeAttrVal) {
		this.escapeAttrVal = escapeAttrVal;
	}
	/**
	 * @return the escapeAttrVal
	 */
	public String getEscapeAttrVal() {
		return escapeAttrVal;
	}
}
