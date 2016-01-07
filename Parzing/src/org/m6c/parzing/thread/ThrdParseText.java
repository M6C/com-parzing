package org.m6c.parzing.thread;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ThreadList;
import org.m6c.parzing.bean.ThreadParameter;

import ressource.FxHtml;
import ressource.XML.FxXML;
import ressource.XML.XMLDocument;

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

public class ThrdParseText extends ThrdParse {

	public ThrdParseText(String uri, String txt, ThreadParameter parameter, ThreadList list) {
		super(uri, txt, parameter, list);
	}

	/**
	 * Analyse la page actuelle à la recherche des informations à téléchanger
	 * contenu dans la liste jTableDownload
	 */
	protected void parseUrl(String url, String html) {
		// DEBUG
		// System.out.println( "FUNCTION parseUrl:" );
		List<ItemTableDwnld> vListAction = this.getDownloadList();
		Vector vListText;
		Vector vListImage;
		String szTextPath = "";

		// if (getDestination().equals(DESTINATION_XML)) {
		StringBuffer sbHTML = new StringBuffer(html);
		sbHTML = new StringBuffer(sbHTML.toString().replaceAll("\r", "").replaceAll("\n", "\r\n"));

		XMLDocument xmlDoc = new XMLDocument();
		xmlDoc.setEncode(getEncoding());
		int cnt = 0, iEnd = 0;
		boolean bContinue = true;
		do {
			int iTmp = 0;
			vListText = new Vector();
			vListImage = new Vector();
			for (int i = 0; i < vListAction.size(); i++) {
				ItemTableDwnld item = (ItemTableDwnld) vListAction.get(i);
				if (item.getTextType().equalsIgnoreCase(ItemTableDwnld.TYPE_TEXT)) {
					Vector data = null;
					clearUrl_Text(sbHTML, item);
					if (data.size() > 0) {
						Vector itemData = new Vector();
						itemData.add(item.getTextName());
						itemData.add(data);
						vListText.add(itemData);
						szTextPath = item.getTextPath();
						// Recupère la position où à été trouvé le text
						iTmp = Integer.parseInt((String) data.elementAt(1));
						// Supprime la position dans la liste
						data.remove(1);
						// DEBUG
						// System.out.println( "itemData TXT Name:" +
						// itemData.elementAt(0) + " data:" +
						// itemData.elementAt(1) );
					}
				} else if (item.getTextType().equalsIgnoreCase(ItemTableDwnld.TYPE_IMAGE)) {
					Vector data = clearUrl_Image(sbHTML, item);
					if (data.size() > 0) {
						Vector itemData = new Vector();
						itemData.add(item.getTextName());
						itemData.add(data);
						vListImage.add(itemData);
						vListText.add(itemData);
						// Recupère la position où à été trouvé le text
						iTmp = Integer.parseInt((String) data.elementAt(1));
						// Supprime la position dans la liste
						data.remove(1);
						// DEBUG
						// System.out.println( "itemData IMG Name:" +
						// itemData.elementAt(0) + " data:" +
						// itemData.elementAt(1) );
					}
				}
				if (iTmp > iEnd)
					iEnd = iTmp;
			}
			bContinue = (vListText.size() > 0 || vListImage.size() > 0);
			if (bContinue) {
				cnt++;
				Hashtable attribut = new Hashtable();
				attribut.put("id", Integer.toString(cnt));
				FxXML.beginXMLElement(xmlDoc, "DATA", attribut);
				if (vListText.size() > 0)
					FxXML.addXMLData(xmlDoc, vListText);
				if (vListImage.size() > 0)
					FxXML.addXMLData(xmlDoc, vListImage);
				FxXML.closeXMLElement(xmlDoc, "DATA");
				sbHTML.replace(0, iEnd, "");
			}
		} while (bContinue);

		if (cnt > 1) {
			String szFileName = buildFileName(url, szTextPath);
			setTitle("Writing File:" + szFileName);
			FxXML.writeXML(szFileName, xmlDoc);
			setTitle("File:" + szFileName + " writed");
		}
		// } else if (getDestination().equals(DESTINATION_DATABASE)) {
		// vListText = new Vector();
		// vListImage = new Vector();
		// for (int i = 0; i < vListAction.size(); i++) {
		// ItemTableDwnld item = (ItemTableDwnld) vListAction.elementAt(i);
		// if (item.getTextType().equalsIgnoreCase(ItemTableDwnld.TYPE_TEXT)) {
		// Vector itemData = new Vector();
		// itemData.add(item.getTextName());
		// itemData.add(parseUrl_Text_RegEx(html, item));
		// vListText.add(itemData);
		// szTextPath = item.getTextPath();
		// // DEBUG
		// // System.out.println( "itemData TXT Name:" +
		// // itemData.elementAt(0) + " data:" + itemData.elementAt(1)
		// // );
		// } else if (item.getTextType().equalsIgnoreCase(ItemTableDwnld.TYPE_IMAGE)) {
		// Vector itemData = new Vector();
		// itemData.add(item.getTextName());
		// itemData.add(parseUrl_Image(html, item));
		// vListImage.add(itemData);
		// vListText.add(itemData);
		// // DEBUG
		// // System.out.println( "itemData IMG Name:" +
		// // itemData.elementAt(0) + " data:" + itemData.elementAt(1)
		// // );
		// }
		// }
		//
		// if (vListText.size() > 0) {
		// // Récupération du ClassLoader actuel à la place du
		// // Class.forName
		// // pour une meilleur gestion de la mémoire
		// ClassLoader classloader = this.getClass().getClassLoader();
		// try {
		// // Récupération de la classe
		// Class classe =
		// classloader.loadClass("videofuture.toolz.ImportURLFilmToDB");
		// // Récupération de la methode avec comme parametre deux
		// // String
		// Method method = classe.getMethod("execute", new Class[] {
		// Vector.class });
		// // Invocation de la methode
		// method.invoke(classe.newInstance(), new Object[] { vListText });
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// }
		// }
	}

//	private Vector parseUrl_Text_RegEx(String html, ItemTableDwnld item) {
//		Vector ret = new Vector();
//		StringBuffer szHTML = new StringBuffer();
//		szHTML = new StringBuffer(szHTML.toString().replaceAll("\r", "").replaceAll("\n", "\r\n"));
//
//		if (szHTML.length() > 0) {
//			boolean bContinue = true;
//			String baliseDebTmp = item.getTextStart().replaceAll("\r", "").replaceAll("\n", "\r\n");
//			String baliseFinTmp = item.getTextEnd().replaceAll("\r", "").replaceAll("\n", "\r\n");
//			int iDeb = 0;
//			int iFin = 0;
//			while (bContinue) {
//				Pattern pDeb = Pattern.compile(baliseDebTmp);
//				Matcher mDeb = pDeb.matcher(szHTML.toString()); // get a matcher
//				// object
//
//				Pattern pFin = Pattern.compile(baliseFinTmp);
//				Matcher mFin = pFin.matcher(szHTML.toString()); // get a matcher
//				// object
//
//				bContinue = (mDeb.find() && mFin.find());
//				if (bContinue) {
//					iDeb = mDeb.end();
//					iFin = mFin.start();
//					String str = szHTML.substring(iDeb, iFin);
//					str = cleanTag(str);
//					ret.add(str);
//				}
//			}
//		}
//		return ret;
//	}
//
//	private Vector parseUrl_Image(String html, ItemTableDwnld item) {
//		Vector ret = new Vector();
//		StringBuffer szHTML = new StringBuffer(html);
//		szHTML = new StringBuffer(szHTML.toString().replaceAll("\r", "").replaceAll("\n", "\r\n"));
//
//		if (szHTML.length() > 0) {
//			boolean bContinue = true;
//			String baliseDebTmp = item.getTextStart().replaceAll("\r", "").replaceAll("\n", "\r\n");
//			String baliseFinTmp = item.getTextEnd().replaceAll("\r", "").replaceAll("\n", "\r\n");
//			int iDeb = 0;
//			int iFin = 0;
//			while (bContinue) {
//				iDeb = szHTML.toString().indexOf(baliseDebTmp, iDeb);
//				iFin = szHTML.toString().indexOf(baliseFinTmp, iDeb + baliseDebTmp.length());
//				// Cherche la dernière occurance de la balise de fin
//				// Même si il y a d'autres balises du même type à l'interieur
//				// exemple: <SCRIPT><SCRIPT>blablabla...</SCRIPT></SCRIPT>
//				int iTmp = szHTML.toString().indexOf(baliseDebTmp, iDeb + baliseFinTmp.length());
//				while ((iTmp > -1) && (iTmp < iFin)) {
//					iFin = szHTML.toString().indexOf(baliseFinTmp, iFin + baliseFinTmp.length());
//					iTmp = szHTML.toString().indexOf(baliseDebTmp, iTmp + baliseDebTmp.length());
//				}
//				bContinue = ((iDeb > -1) && (iFin > -1));
//				if (bContinue) {
//					iDeb += baliseDebTmp.length();
//					String str = szHTML.substring(iDeb, iFin).toUpperCase();
//					String imgPath = FxHtml.getNextText(str, "SRC=", 0);
//					if (imgPath.length() > 0) {
//						ret.add(imgPath);
//					}
//				}
//			}
//		}
//		return ret;
//	}

	protected Vector clearUrl_Text(StringBuffer szHTML, ItemTableDwnld item) {
		Vector ret = new Vector();

		if (szHTML.length() > 0) {
			String baliseDebTmp = item.getTextStart().replaceAll("\r", "").replaceAll("\n", "\r\n");
			String baliseFinTmp = item.getTextEnd().replaceAll("\r", "").replaceAll("\n", "\r\n");
			int iDeb = 0;
			int iFin = 0;
			iDeb = szHTML.toString().indexOf(baliseDebTmp, iDeb);
			iFin = szHTML.toString().indexOf(baliseFinTmp, iDeb + baliseDebTmp.length());
			// Cherche la dernière occurance de la balise de fin
			// Même si il y a d'autres balises du même type à l'interieur
			// exemple: <SCRIPT><SCRIPT>blablabla...</SCRIPT></SCRIPT>
			int iTmp = szHTML.toString().indexOf(baliseDebTmp, iDeb + baliseFinTmp.length());
			while ((iTmp > -1) && (iTmp < iFin)) {
				iFin = szHTML.toString().indexOf(baliseFinTmp, iFin + baliseFinTmp.length());
				iTmp = szHTML.toString().indexOf(baliseDebTmp, iTmp + baliseDebTmp.length());
			}
			if ((iDeb > -1) && (iFin > -1)) {
				iDeb += baliseDebTmp.length();
				String str = szHTML.substring(iDeb, iFin);
				str = cleanTag(str);
				ret.add(str);
				ret.add(Integer.toString(iFin));
			}
			// szHTML.replace(iDeb, iFin, "");
		}
		return ret;
	}

	private Vector clearUrl_Image(StringBuffer szHTML, ItemTableDwnld item) {
		Vector ret = new Vector();

		if (szHTML.length() > 0) {
			boolean bContinue = true;
			String baliseDebTmp = item.getTextStart().replaceAll("\r", "").replaceAll("\n", "\r\n");
			String baliseFinTmp = item.getTextEnd().replaceAll("\r", "").replaceAll("\n", "\r\n");
			int iDeb = 0;
			int iFin = 0;
			while (bContinue) {
				iDeb = szHTML.toString().indexOf(baliseDebTmp, iDeb);
				iFin = szHTML.toString().indexOf(baliseFinTmp, iDeb + baliseDebTmp.length());
				// Cherche la dernière occurance de la balise de fin
				// Même si il y a d'autres balises du même type à l'interieur
				// exemple: <SCRIPT><SCRIPT>blablabla...</SCRIPT></SCRIPT>
				int iTmp = szHTML.toString().indexOf(baliseDebTmp, iDeb + baliseFinTmp.length());
				while ((iTmp > -1) && (iTmp < iFin)) {
					iFin = szHTML.toString().indexOf(baliseFinTmp, iFin + baliseFinTmp.length());
					iTmp = szHTML.toString().indexOf(baliseDebTmp, iTmp + baliseDebTmp.length());
				}
				bContinue = ((iDeb > -1) && (iFin > -1));
				if (bContinue) {
					iDeb += baliseDebTmp.length();
					String str = szHTML.substring(iDeb, iFin).toUpperCase();
					String imgPath = FxHtml.getNextText(str, "SRC=", 0);
					if (imgPath.length() > 0) {
						ret.add(imgPath);
						ret.add(Integer.toString(iFin));
					}
					// szHTML.replace(iDeb, iFin, "");
				}
			}
		}
		return ret;
	}
}