package org.m6c.parzing.main;

import java.util.ArrayList;
import java.util.List;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ItemTableLink;
import org.m6c.parzing.bean.ThreadList;
import org.m6c.parzing.bean.ThreadParameter;
import org.m6c.parzing.thread.ThrdParseSequence;

public class MainGoSportRaquetteTennis {

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

	private static String url = "http://www.go-sport.com/sport/sport-de-raquette/tennis/raquettes/l-730070206.html";
	private static String html = "";
	private static String path = "C:\\Temp\\GoSport\\[Genre]\\[Marque]";
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
		urlIncludeList.add(new ItemTableLink("http://www.go-sport.com/sport/sport-de-raquette"));
//		urlIncludeList.add(new ItemTableLink("http://www.go-sport.com/sport/sport-de-raquette/aeropro-team-gt/f-730070206-mb0000003dq.html"));
//		urlIncludeList.add(new ItemTableLink("http://www.go-sport.com/sport/sport-de-raquette/aeropro-team-gt/f-73007020602-mb0000003dq.html"));
	}

	private static void initializeUrlExcludeList() {
		urlExcludeList = new ArrayList<ItemTableLink>();
	}

	private static void initializeDownloadList() {
		downloadList = new ArrayList<ItemTableDwnld>();
		downloadList.add(new ItemTableDwnld("Nom_produit", ItemTableDwnld.TYPE_TEXT, "<div class=\"fpProductTitle\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Marque", ItemTableDwnld.TYPE_TEXT, "<td>Marque</td>", "<br/></td>", path));
		downloadList.add(new ItemTableDwnld("Genre", ItemTableDwnld.TYPE_TEXT, "<td>Genre</td>", "<br/></td>", path));
		downloadList.add(new ItemTableDwnld("Composition_cadre", ItemTableDwnld.TYPE_TEXT, "<td>Composition du cadre</td>", "<br/></td>", path));
		downloadList.add(new ItemTableDwnld("Poids", ItemTableDwnld.TYPE_TEXT, "<td>Poids</td>", "<br/></td>", path));
		downloadList.add(new ItemTableDwnld("Type_pratique", ItemTableDwnld.TYPE_TEXT, "<td>Type de pratique</td>", "<br/></td>", path));
		downloadList.add(new ItemTableDwnld("Niveau_pratique", ItemTableDwnld.TYPE_TEXT, "<td>Niveau de pratique</td>", "<br/></td>", path));
		downloadList.add(new ItemTableDwnld("Conseil", ItemTableDwnld.TYPE_TEXT, "<td>Conseil</td>", "<br/></td>", path));
		downloadList.add(new ItemTableDwnld("Maniabilite", ItemTableDwnld.TYPE_TEXT, "<td>Maniabilit&#233;</td>", "<br/></td>", path));
		downloadList.add(new ItemTableDwnld("Tamis_taille)", ItemTableDwnld.TYPE_TEXT, "<td>Tamis (Taille)</td>", "<br/></td>", path));
		downloadList.add(new ItemTableDwnld("Longueur", ItemTableDwnld.TYPE_TEXT, "<td>Longueur</td>", "<br/></td>", path));
//		downloadList.add(new ItemTableDwnld("Image", ItemTableDwnld.TYPE_IMAGE, "<a class=\"fpViewProductHref\" href=\""+escapeAttrVal+"\" onclick=\"return openZoomPage()\">", "<script type=\"text/javascript\">"+escapeAttrVal, pathImg ));
		downloadList.add(new ItemTableDwnld("Image", ItemTableDwnld.TYPE_IMAGE, "<a class=\"fpViewProductHref\" href=\""+escapeAttrVal+"\" onclick=\"return openZoomPage()\">", "<span id=\"zoneZoomSelect\">", pathImg ));
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
