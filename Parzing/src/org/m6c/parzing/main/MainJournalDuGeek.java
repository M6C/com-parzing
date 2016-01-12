package org.m6c.parzing.main;

import java.util.List;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ItemTableLink;

public class MainJournalDuGeek extends AbstractMain {

	private static String url = "http://www.jdubuzz.com/";
//	private static String url = "http://www.jdubuzz.com/2015/03/28/si-les-pokemon-existaient-vraiment";
	private static String path = "/Temp/JournalDuGeek";
	private static String pathImg =  path + "\\img";

	public static void main(String[] args) {
		new MainJournalDuGeek().launch(args);
	}

	protected void initializeUrlIncludeList(List<ItemTableLink> urlIncludeList) {
		urlIncludeList.add(new ItemTableLink("http://www.jdubuzz.com/20"));
	}

	protected void initializeDownloadList(List<ItemTableDwnld> downloadList) {
		downloadList.add(new ItemTableDwnld("Title", ItemTableDwnld.TYPE_TEXT, "<h1>", "</h1>", path));
		downloadList.add(new ItemTableDwnld("Author", ItemTableDwnld.TYPE_TEXT, "<p class=\"author-name\">", "</p>", path));
		downloadList.add(new ItemTableDwnld("Content", ItemTableDwnld.TYPE_TEXT, "<div class=\"post-content\">", "</div>", path));
		downloadList.add(new ItemTableDwnld("Image", ItemTableDwnld.TYPE_IMAGE, "<div class=\"gallery\">", "</div>", pathImg ));
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
