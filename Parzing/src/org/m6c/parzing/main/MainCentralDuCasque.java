package org.m6c.parzing.main;

import java.util.List;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ItemTableLink;

public class MainCentralDuCasque extends AbstractMain {

//	private static String url = "http://www.centrale-du-casque.com/";
	private static String url = "http://www.centrale-du-casque.com/casques-moto/modulable-casque.htm";
	private static String path = "/Temp/CentraleDuCasque";
	private static String pathImg =  path + "\\img";

	public static void main(String[] args) {
		new MainCentralDuCasque().launch(args);
	}

	protected void initializeUrlIncludeList(List<ItemTableLink> urlIncludeList) {
		urlIncludeList.add(new ItemTableLink("/casques-moto/"));
	}

	protected void initializeDownloadList(List<ItemTableDwnld> downloadList) {
		downloadList.add(new ItemTableDwnld("Title", ItemTableDwnld.TYPE_TEXT, "<h1 id=\"cdc-produit-h1\">", "</h1>", path));
		downloadList.add(new ItemTableDwnld("Marque Image", ItemTableDwnld.TYPE_IMAGE, "<p class=\"cdc-produit-marque-image\">", "</p>", path));
		downloadList.add(new ItemTableDwnld("Synthese Description", ItemTableDwnld.TYPE_TEXT, "<div id=\"cdc-produit-description-courte\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Synthese Avis", ItemTableDwnld.TYPE_TEXT, "<div id=\"cdc-produit-notre-avis\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Synthese Point Fort", ItemTableDwnld.TYPE_TEXT, "<div id=\"cdc-produit-la-marque\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Description", ItemTableDwnld.TYPE_TEXT, "<div id=\"tabs-1\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Caracteristique Technique", ItemTableDwnld.TYPE_TEXT, "<div id=\"tabs-2\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("La Marque", ItemTableDwnld.TYPE_TEXT, "<div id=\"tabs-3\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Taille", ItemTableDwnld.TYPE_TEXT, "<div id=\"tabs-4\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Accessoires", ItemTableDwnld.TYPE_TEXT, "<div id=\"tabs-5\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Accessoires Images", ItemTableDwnld.TYPE_IMAGE, "<div id=\"tabs-5\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Image", ItemTableDwnld.TYPE_IMAGE, "<div class=\"cdc-produit-bloc-photo\">", "</div>", pathImg ));
	}

	protected String getUrl() {
		return url;
	}

	protected String getPath() {
		return path;
	}

	protected String getPathImage() {
		return pathImg;
	}
}
