package org.m6c.parzing.thread;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.m6c.parzing.bean.ItemTableDwnld;
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

public class ThrdParseTextRegex extends ThrdParseText {

	public ThrdParseTextRegex(String uri, String txt, ThreadParameter parameter, ThreadList list) {
		super(uri, txt, parameter, list);
	}

	protected Vector clearUrl_Text(StringBuffer szHTML, ItemTableDwnld item) {
		Vector ret = new Vector();
		szHTML = new StringBuffer(szHTML.toString().replaceAll("\r", "").replaceAll("\n", "\r\n"));

		if (szHTML.length() > 0) {

			String baliseDebTmp = item.getTextStart().replaceAll("\r", "").replaceAll("\n", "\r\n");
			String baliseFinTmp = item.getTextEnd().replaceAll("\r", "").replaceAll("\n", "\r\n");
			Pattern pDeb = Pattern.compile(baliseDebTmp);
			Pattern pFin = Pattern.compile(baliseFinTmp);
			int iDeb = 0;
			int iFin = 0;
			Matcher mDeb = pDeb.matcher(szHTML.toString()); // get a matcher
			// object
			while (mDeb.find(iFin)) {
				iDeb = mDeb.end();
				Matcher mFin = pFin.matcher(szHTML.toString()); // get a matcher
				// object
				if (mFin.find(iDeb)) {
					iFin = mFin.start();
					String str = szHTML.substring(iDeb, iFin);
					str = cleanTag(str);
					ret.add(str);
					ret.add(Integer.toString(iFin));
				} else {
					break;
				}
			}

		}
		return ret;
	}
}