package org.m6c.ressource;

import ressource.FxString;

public class FxHtml extends ressource.FxHtml {

	/**
	 * Supprime les tag du texte szHTML
	 * 
	 * @param szHTML
	 * @return
	 */
	public static String removeTag(String szHTML) {
		String str = FxString.replaceString(szHTML, "<", ">", " ");
		return str.trim();
	}

	/**
	 * Supprime toutes les informations superflu de szHTML
	 * 
	 * @param szHTML
	 *            : Chaine de caractère à traiter
	 * @return
	 */
	public static String cleanHtml(String szHTML) {
		String str = szHTML;
		str = FxString.replaceString(str, "\r", "");
		str = FxString.replaceString(str, "\n", "");
		str = FxString.replaceString(str, "\t", "");
		str = FxString.replaceString(str, "&gt;", "");
		str = FxString.replaceString(str, "&nbsp;", " ");
		str = FxString.replaceString(str, "&#160;", " ");
		str = FxString.replaceString(str, "         ", " ");
		str = FxString.replaceString(str, "        ", " ");
		str = FxString.replaceString(str, "       ", " ");
		str = FxString.replaceString(str, "     ", " ");
		str = FxString.replaceString(str, "    ", " ");
		str = FxString.replaceString(str, "   ", " ");
		str = FxString.replaceString(str, "  ", " ");
		return str.trim();
	}
}
