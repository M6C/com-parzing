package org.m6c.parzing.main;

import java.util.ArrayList;
import java.util.List;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ItemTableLink;
import org.m6c.parzing.bean.ThreadList;
import org.m6c.parzing.bean.ThreadParameter;
import org.m6c.parzing.thread.ThrdParseSequence;

public class MainJournalDuGeek {

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

	private static String url = "http://www.jdubuzz.com/";
	private static String html = "";
	private static String path = "C:\\Temp\\JournalDuGeek";
	private static String pathImg =  path + "\\img";
	private static String escapeAttrVal = "?";

	private static ThreadParameter parameter;
	private static ThreadList list;
	private static ArrayList<ItemTableDwnld> downloadList;
	private static ArrayList<ItemTableLink> urlExcludeList;
	private static ArrayList<ItemTableLink> urlIncludeList;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initializeParameter();
		initializeList();
		new ThrdParseSequence(getUrl(), getHtml(), parameter, list).start();
	}

	private static void initializeParameter() {
		parameter = new ThreadParameter();
		parameter.setClean(bClean);
		parameter.setCloseAtEnd(bCloseAtEnd);
		parameter.setCorrectHtml(bCorrectHtml);
		parameter.setEditorHtmlUpd(bEditorHtmlUpd);
		parameter.setEncoding(getTxtDataEncode());
		parameter.setOutputPathXml(path);
		parameter.setOutputPathImage(pathImg);
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

	private static void initializeList() {
		initializeDownloadList();
		initializeUrlExcludeList();
		initializeUrlIncludeList();

		list = new ThreadList();
		list.setDownloadList(getDownloadList());
		list.setUrlExcludeList(getUrlExcludeList());
		list.setUrlIncludeList(getUrlIncludeList());
	}
	private static void initializeUrlIncludeList() {
		urlIncludeList = new ArrayList<ItemTableLink>();
		urlIncludeList.add(new ItemTableLink("http://www.jdubuzz.com/20"));
	}

	private static void initializeUrlExcludeList() {
		urlExcludeList = new ArrayList<ItemTableLink>();
	}

	private static void initializeDownloadList() {
		downloadList = new ArrayList<ItemTableDwnld>();
		downloadList.add(new ItemTableDwnld("Title", ItemTableDwnld.TYPE_TEXT, "<h1>", "</h1>", path));
		downloadList.add(new ItemTableDwnld("Author", ItemTableDwnld.TYPE_TEXT, "<p class=\"author-name\">", "</p>", path));
		downloadList.add(new ItemTableDwnld("Content", ItemTableDwnld.TYPE_TEXT, "<div class=\"post-content\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Image", ItemTableDwnld.TYPE_IMAGE, "<div class=\"gallery\">", "</div>", pathImg ));
	}

	private static List<ItemTableLink> getUrlIncludeList() {
		return urlIncludeList;
	}

	private static String getUrl() {
		return url;
	}

	private static String getHtml() {
		return html;
	}

	private static List<ItemTableLink> getUrlExcludeList() {
		return urlExcludeList;
	}

	private static List<ItemTableDwnld> getDownloadList() {
		return downloadList;
	}

	private static int getMaxSubLevel() {
		return maxSubLevel;
	}

	private static String getTxtDataEncode() {
		return txtDataEncode;
	}

}
