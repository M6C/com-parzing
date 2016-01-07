package org.m6c.parzing.thread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ItemTableLink;
import org.m6c.parzing.bean.ThreadList;
import org.m6c.parzing.bean.ThreadParameter;

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

public class AncestorThread extends Thread {

	public static final String DESTINATION_XML = "Xml";
	public static final String DESTINATION_DATABASE = "Database";

	public static final String METHODE_SEQUENCE = "Séquence";
	public static final String METHODE_TEXT_REGEX = "Regex";
	public static final String METHODE_TEXT_LITTERALE = "Littérale";

	public static final String BROWSER_JAVA = "JavaBrowser";
	public static final String BROWSER_JDIC = "JdicBrowser";

	private ThreadParameter parameter;
	private boolean bSubLink = false;
	private boolean bIndent = false;
	private boolean bClean = false;
	private boolean bCorrectHtml = true;
	private boolean bUseIncludeList = false;
	private boolean bUseTimeOut = false;
	private boolean bShowPage = false;
	private boolean bParse = false;
	private boolean bCloseAtEnd = false;
	private boolean bUseIncludeListAsMask = false;
	private List<ItemTableLink> UrlIncludeList = null;
	private List<ItemTableLink> UrlExcludeList = null;
	private List<ItemTableDwnld> DownloadList = null;
	private int iMaxSubLevel = 0;
	private String uri;
	private URL url;
	private String txt;
	private String encoding;

	public AncestorThread(String uri, String txt, ThreadParameter parameter, ThreadList list) {
		super();
		this.parameter = parameter;
		this.setSubLink(parameter.isSubLink());
		this.setIndent(parameter.isIndent());
		this.setClean(parameter.isClean());
		this.setCorrectHtml(parameter.isCorrectHtml());
		this.setUseIncludeList(parameter.isUseIncludeList());
		this.setUseTimeOut(parameter.isUseTimeOut());
		this.setUseIncludeListAsMask(parameter.isUseIncludeListAsMask());
		this.setShowPage(parameter.isShowPage());
		this.setParse(parameter.isParse());
		this.setCloseAtEnd(parameter.isCloseAtEnd());
		this.setMaxSubLevel(parameter.getMaxSubLevel());
		this.setEncoding(parameter.getEncoding());

		this.setUrlIncludeList(list.getUrlIncludeList());
		this.setUrlExcludeList(list.getUrlExcludeList());
		this.setDownloadList(list.getDownloadList());

		this.setTxt(txt);
		this.setUri(uri);
		try {
			this.setUrl(new URL(uri));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	protected boolean isExclude(String szUrl) {
		boolean ret = false;
		List<ItemTableLink> list = getUrlExcludeList();
		for (int i = 0; (i < list.size()) && !ret; i++) {
			ItemTableLink item = (ItemTableLink) list.get(i);
			if (item != null)
				ret = item.getUrl().toUpperCase().startsWith(szUrl.toUpperCase());
		}

		return ret;
	}

	/**
	 * @return the proxyHost
	 */
	public String getProxyHost() {
		return parameter==null ? null : parameter.getProxyHost();
	}
	/**
	 * @return the proxyUser
	 */
	public String getProxyUser() {
		return parameter==null ? null : parameter.getProxyUser();
	}
	/**
	 * @return the proxyPwd
	 */
	public String getProxyPwd() {
		return parameter==null ? null : parameter.getProxyPwd();
	}
	/**
	 * @return the proxyPort
	 */
	public String getProxyPort() {
		return parameter==null ? null : parameter.getProxyPort();
	}

	protected String getEscapeAttrVal() {
		return parameter==null ? null : parameter.getEscapeAttrVal();
	}

	public boolean isOverwriteIfFileExist() {
		return parameter==null ? false : parameter.isOverwriteIfFileExist();
	}

	public String getOutputPathXml() {
		return parameter==null ? null : parameter.getOutputPathXml();
	}

	public String getOutputPathImage() {
		return parameter==null ? null : parameter.getOutputPathImage();
	}

	public boolean isClean() {
		return bClean;
	}

	public boolean isIndent() {
		return bIndent;
	}

	public boolean isCorrectHtml() {
		return bCorrectHtml;
	}

	public boolean isSubLink() {
		return bSubLink;
	}

	public boolean isUseIncludeListAsMask() {
		return bUseIncludeListAsMask;
	}

	public boolean isUseIncludeList() {
		return bUseIncludeList;
	}

	public boolean isUseTimeOut() {
		return bUseTimeOut;
	}

	public boolean isShowPage() {
		return bShowPage;
	}

	public List<ItemTableLink> getUrlExcludeList() {
		return UrlExcludeList;
	}

	public List<ItemTableLink> getUrlIncludeList() {
		return UrlIncludeList;
	}

	public void setClean(boolean bClean) {
		this.bClean = bClean;
	}

	public void setIndent(boolean bIndent) {
		this.bIndent = bIndent;
	}

	public void setCorrectHtml(boolean bCorrectHtml) {
		this.bCorrectHtml = bCorrectHtml;
	}

	public void setSubLink(boolean bSubLink) {
		this.bSubLink = bSubLink;
	}

	public void setUseIncludeListAsMask(boolean bUseIncludeListAsMask) {
		this.bUseIncludeListAsMask = bUseIncludeListAsMask;
	}

	public void setUseIncludeList(boolean bUseIncludeList) {
		this.bUseIncludeList = bUseIncludeList;
	}

	public void setUseTimeOut(boolean bUseTimeOut) {
		this.bUseTimeOut = bUseTimeOut;
	}

	public void setShowPage(boolean bShowPage) {
		this.bShowPage = bShowPage;
	}

	public void setUrlExcludeList(List<ItemTableLink> UrlExcludeList) {
		this.UrlExcludeList = UrlExcludeList;
	}

	public void setUrlIncludeList(List<ItemTableLink> UrlIncludeList) {
		this.UrlIncludeList = UrlIncludeList;
	}

	public List<ItemTableDwnld> getDownloadList() {
		return DownloadList;
	}

	public void setDownloadList(List<ItemTableDwnld> DownloadList) {
		this.DownloadList = DownloadList;
	}

	public boolean isCloseAtEnd() {
		return bCloseAtEnd;
	}

	public void setCloseAtEnd(boolean closeAtEnd) {
		bCloseAtEnd = closeAtEnd;
	}

	public boolean isParse() {
		return bParse;
	}

	public void setParse(boolean parse) {
		bParse = parse;
	}

	public int getMaxSubLevel() {
		return iMaxSubLevel;
	}

	public void setMaxSubLevel(int maxSubLevel) {
		iMaxSubLevel = maxSubLevel;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @param txt
	 *            the txt to set
	 */
	public void setTxt(String txt) {
		this.txt = txt;
	}

	/**
	 * @return the txt
	 */
	public String getTxt() {
		return txt;
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
}