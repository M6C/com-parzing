package org.m6c.parzing.bean;

import java.util.List;

public class ThreadList {
	private List<ItemTableLink> UrlIncludeList = null;
	private List<ItemTableLink> UrlExcludeList = null;
	private List<ItemTableDwnld> DownloadList = null;

	/**
	 * @return the urlIncludeList
	 */
	public List<ItemTableLink> getUrlIncludeList() {
		return UrlIncludeList;
	}

	/**
	 * @param urlIncludeList
	 *            the urlIncludeList to set
	 */
	public void setUrlIncludeList(List<ItemTableLink> urlIncludeList) {
		UrlIncludeList = urlIncludeList;
	}

	/**
	 * @return the urlExcludeList
	 */
	public List<ItemTableLink> getUrlExcludeList() {
		return UrlExcludeList;
	}

	/**
	 * @param urlExcludeList
	 *            the urlExcludeList to set
	 */
	public void setUrlExcludeList(List<ItemTableLink> urlExcludeList) {
		UrlExcludeList = urlExcludeList;
	}

	/**
	 * @return the downloadList
	 */
	public List<ItemTableDwnld> getDownloadList() {
		return DownloadList;
	}

	/**
	 * @param downloadList
	 *            the downloadList to set
	 */
	public void setDownloadList(List<ItemTableDwnld> downloadList) {
		DownloadList = downloadList;
	}

}
