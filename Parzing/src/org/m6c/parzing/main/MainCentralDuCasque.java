package org.m6c.parzing.main;

import java.util.List;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ItemTableLink;

public class MainCentralDuCasque extends AbstractMain {

	private static String url = "http://www.jdubuzz.com/";
	private static String path = "C:\\Temp\\JournalDuGeek";
	private static String pathImg =  path + "\\img";

	public static void main(String[] args) {
		new MainCentralDuCasque().launch(args);
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
