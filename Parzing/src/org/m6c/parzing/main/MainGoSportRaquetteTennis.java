package org.m6c.parzing.main;

import java.util.List;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ItemTableLink;

public class MainGoSportRaquetteTennis extends AbstractMain {

	private static String url = "http://www.go-sport.com/sport/sport-de-raquette/tennis/raquettes/l-730070206.html";
	private static String path = "/Temp/GoSport/[Genre]/[Marque]";
	private static String pathImg =  path + "\\img";

	public static void main(String[] args) {
		new MainGoSportRaquetteTennis().launch(args);
	}

	protected void initializeUrlIncludeList(List<ItemTableLink> urlIncludeList) {
		urlIncludeList.add(new ItemTableLink("http://www.go-sport.com/sport/sport-de-raquette"));
//		urlIncludeList.add(new ItemTableLink("http://www.go-sport.com/sport/sport-de-raquette/aeropro-team-gt/f-730070206-mb0000003dq.html"));
//		urlIncludeList.add(new ItemTableLink("http://www.go-sport.com/sport/sport-de-raquette/aeropro-team-gt/f-73007020602-mb0000003dq.html"));
	}

	protected void initializeDownloadList(List<ItemTableDwnld> downloadList) {
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
		downloadList.add(new ItemTableDwnld("Image", ItemTableDwnld.TYPE_IMAGE, "<a class=\"fpViewProductHref\" href=\""+getEscapeAttrVal()+"\" onclick=\"return openZoomPage()\">", "<span id=\"zoneZoomSelect\">", pathImg ));
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