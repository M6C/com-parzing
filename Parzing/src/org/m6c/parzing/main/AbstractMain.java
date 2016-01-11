package org.m6c.parzing.main;

import java.util.ArrayList;
import java.util.List;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ItemTableLink;
import org.m6c.parzing.bean.ThreadList;
import org.m6c.parzing.bean.ThreadParameter;
import org.m6c.parzing.thread.ThrdParseSequence;

public abstract class AbstractMain {

	private static boolean bClean = false;// true;
	private static boolean bIndent = false;// true;
	private static boolean bSubLink = true;
	private static boolean bCorrectHtml = false;// true;
	private static boolean bUseIncludeList = false;
	private static boolean bUseTimeOut = false;
	private static boolean bEditorHtmlUpd = false;
	private static boolean bShowPage = true;// false;
	private static boolean bParse = false;
	private static boolean bCloseAtEnd = false;
	private static boolean bUseIncludeListAsMask = true;
	private static boolean bOverwriteIfFileExist = false;
	private static int maxSubLevel = 3;
	private static String txtDataEncode = "";

	private static String html = "";
	private static String escapeAttrVal = "?";

	private static ThreadParameter parameter;
	private static ThreadList list;
	private static ArrayList<ItemTableDwnld> downloadList = new ArrayList<ItemTableDwnld>();
	private static ArrayList<ItemTableLink> urlExcludeList = new ArrayList<ItemTableLink>();
	private static ArrayList<ItemTableLink> urlIncludeList = new ArrayList<ItemTableLink>();

	/**
	 * @param args
	 */
	public void launch(String[] args) {
		initializeParameter();
		initializeList();
		new ThrdParseSequence(getUrl(), getHtml(), parameter, list).start();
	}

	protected String getUrl() {
		throw new UnsupportedOperationException("Child Class must implement 'getUrl' method");
	}

	protected String getPath() {
		throw new UnsupportedOperationException("Child Class must implement 'getPath' method");
	}

	protected String getPathImage() {
		throw new UnsupportedOperationException("Child Class must implement 'getPathImage' method");
	}

	protected void initializeUrlIncludeList(List<ItemTableLink> urlIncludeList) {
	}

	protected void initializeUrlExcludeList(List<ItemTableLink> urlExcludeList) {
	}

	protected void initializeDownloadList(List<ItemTableDwnld> downloadList) {
	}

	protected String getEscapeAttrVal() {
		return escapeAttrVal;
	}
	private void initializeParameter() {
		parameter = new ThreadParameter();
		parameter.setClean(bClean);
		parameter.setCloseAtEnd(bCloseAtEnd);
		parameter.setCorrectHtml(bCorrectHtml);
		parameter.setEditorHtmlUpd(bEditorHtmlUpd);
		parameter.setEncoding(getTxtDataEncode());
		parameter.setOutputPathXml(getPath());
		parameter.setOutputPathImage(getPathImage());
		parameter.setIndent(bIndent);
		parameter.setMaxSubLevel(getMaxSubLevel());
		parameter.setParse(bParse);
		parameter.setShowPage(bShowPage);
		parameter.setSubLink(bSubLink);
		parameter.setUseIncludeList(bUseIncludeList);
		parameter.setUseIncludeListAsMask(bUseIncludeListAsMask);
		parameter.setUseTimeOut(bUseTimeOut);
		parameter.setOverwriteIfFileExist(bOverwriteIfFileExist);
		parameter.setEscapeAttrVal(escapeAttrVal);
		parameter.setProxyHost("web.pandore.log.intra.laposte.fr");
		parameter.setProxyPort("8080");
		parameter.setProxyUser("pckh146");
		parameter.setProxyPwd("k5F+n7S!");
	}

	private void initializeList() {
		initializeDownloadList(downloadList);
		initializeUrlExcludeList(urlExcludeList);
		initializeUrlIncludeList(urlIncludeList);

		list = new ThreadList();
		list.setDownloadList(getDownloadList());
		list.setUrlExcludeList(getUrlExcludeList());
		list.setUrlIncludeList(getUrlIncludeList());
	}

	private List<ItemTableLink> getUrlIncludeList() {
		return urlIncludeList;
	}

	private String getHtml() {
		return html;
	}

	private List<ItemTableLink> getUrlExcludeList() {
		return urlExcludeList;
	}

	private List<ItemTableDwnld> getDownloadList() {
		return downloadList;
	}

	private int getMaxSubLevel() {
		return maxSubLevel;
	}

	private String getTxtDataEncode() {
		return txtDataEncode;
	}
}
