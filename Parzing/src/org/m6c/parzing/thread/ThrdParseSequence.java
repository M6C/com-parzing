package org.m6c.parzing.thread;

import java.io.File;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ThreadList;
import org.m6c.parzing.bean.ThreadParameter;
import org.m6c.ressource.FxHtml;

import ressource.XML.FxXML;
import ressource.XML.XMLDocument;
import ressource.bean.BeanTag;

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

public class ThrdParseSequence extends ThrdParse {

	public ThrdParseSequence(String uri, String txt, ThreadParameter parameter, ThreadList list) {
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
		String szPathXml = getOutputPathXml();
		String szPathImage = getOutputPathImage();
		String szFileNameFromUrl = buildFileName(url);

//		if (getDestination().equals(DESTINATION_XML)) {

			StringBuffer sbHTML = new StringBuffer(html);
			String szHtml = sbHTML.toString();

			List<?> listTag = FxHtml.extractHashTag(szHtml);

			if (listTag != null && listTag.size() > 0 && vListAction != null && vListAction.size() > 0) {

				int cnt = 0;
				Hashtable<String, String> attribut1 = new Hashtable<String, String>();
				attribut1.put("url", url);
				XMLDocument xmlDoc = new XMLDocument(attribut1);
				xmlDoc.setEncode(getEncoding());

				Vector<Vector<Serializable>> vList = null;
				Vector<Vector<Serializable>> mainList = new Vector<Vector<Serializable>>();
				try {
					Iterator<ItemTableDwnld> itAction = vListAction.iterator();
					List<Object> listAction = new LinkedList<Object>();
					while (itAction.hasNext()) {
						ItemTableDwnld data = (ItemTableDwnld) itAction.next();
						listAction.add(data); // Bean de donnée
						listAction.add(FxHtml.extractHashTag(data.getTextStart())); // Text de début
						listAction.add(FxHtml.extractHashTag(data.getTextEnd())); // Text de fin
						listAction.add(new Integer(0)); // Indice de recherche dans la liste
					}

					boolean bContinue = true;
					int tagIdx = 0, sizeTag = listTag.size();
					while (bContinue) {
						vList = new Vector<Vector<Serializable>>();
						for (int iAct = 0; iAct < listAction.size();) {
							ItemTableDwnld item = (ItemTableDwnld) listAction.get(iAct++);
							boolean mandatory = item.isMandatory();
							@SuppressWarnings("unchecked")
							List<BeanTag> lstActTag = (List<BeanTag>) listAction.get(iAct++);
							List<?> lstActTagEnd = (List<?>) listAction.get(iAct++);
							tagIdx = ((Integer) listAction.get(iAct++)).intValue();

							String szData = "";
							BeanTag tagDat = null, tagAct = null;
							boolean bFind = false;
							Iterator<BeanTag> itActTag = lstActTag.iterator();
							while (tagIdx < sizeTag) {
								if (tagAct == null || bFind)
									tagAct = (BeanTag) itActTag.next();
								tagDat = (BeanTag) listTag.get(tagIdx++);
								if (isEquals(tagDat, tagAct)) {
									bFind = true;
									if (!itActTag.hasNext())
										break;
								} else {
									bFind = false;
								}
							}

							if (bFind) {

								if (item.getTextType().equalsIgnoreCase(ItemTableDwnld.TYPE_IMAGE) /*&& tagDat.getName().equalsIgnoreCase("IMG")*/) {
									szData += tagDat.getTag();
								} else {
									if (tagDat.getContent() != null)
										szData += tagDat.getContent();
								}

								int cntFind = 0;
								for (int i = tagIdx; i < sizeTag; i++) {
									@SuppressWarnings("unchecked")
									Iterator<BeanTag> itActTagEnd = (Iterator<BeanTag>) lstActTagEnd.iterator();
									while (itActTagEnd.hasNext() && tagIdx < sizeTag) {
										tagAct = (BeanTag) itActTagEnd.next();
										tagDat = (BeanTag) listTag.get(i);
										if (isEquals(tagDat, tagAct)) {
											cntFind++;
											i++;
										} else {
											if (item.getTextType().equalsIgnoreCase(ItemTableDwnld.TYPE_IMAGE) /*&& tagDat.getName().equalsIgnoreCase("IMG")*/) {
												szData += tagDat.getTag();
											} else {
												if (tagDat.getTag() != null)
													szData += tagDat.getTag();
												if (tagDat.getContent() != null)
													szData += tagDat.getContent();
												if (tagDat.getEndTag() != null)
													szData += tagDat.getEndTag();
											}
											cntFind = 0;
											break;
										}
									}
									if (cntFind == lstActTagEnd.size()) {
										// szData += szTmp;
										bFind = true;
										break;
									} else {
										bFind = false;
									}
								}

								if (bFind) {
									Vector<String> itemData = new Vector<String>();
									if (ItemTableDwnld.TYPE_IMAGE.equals(item.getTextType())) {
										String szPath = formatFileName(szPathImage, vList);
										szPath += "\\" + szFileNameFromUrl;
										File fPath = new File(szPath);
										if (!fPath.exists()) {
											fPath.mkdirs();
										}
										List<List<String>> listImg = downloadImage(szData, szPath);
										szData = "";
										int size = listImg.size();
										for(int i=0 ; i<size ; i++) {
											List<String> img = (List<String>)listImg.get(i);
											String imgUrl = (String)img.get(0);
											String imgFile = (String)img.get(1);

											Hashtable<String, String> attribut = new Hashtable<String, String>();
											attribut.put("id", Integer.toString(i));
											attribut.put("url", imgUrl);

											XMLDocument xml = new XMLDocument("Item", attribut, false);
											xml.add(imgFile);
											xml.createEnd();

											szData += xml.toString();
										}
										itemData.add("\r\n"+szData);
									}
									else {
										if (ItemTableDwnld.TYPE_TEXT.equals(item.getTextType())) {
											szData = FxHtml.removeTag(szData);
										}
										itemData.add(FxHtml.cleanHtml(szData));
									}

									Vector<Serializable> vListText = new Vector<Serializable>();
									vListText.add(item.getTextName());
									vListText.add(itemData);
									vList.add(vListText);
								} else {
									tagIdx++;
								}
							} else {
								if (mandatory) {
									bContinue = false;
									vList.clear();
									cnt = 0;
									iAct = listAction.size();
								}
								tagIdx++;
							}
							// Verifi si on est arrivé à la fin de la liste
							// de tag du document
							if (tagIdx >= sizeTag) {
								if (listAction.size() > 0 && iAct > 0) {
									// Supprime l'action
									listAction.remove(--iAct);
									listAction.remove(--iAct);
									listAction.remove(--iAct);
									listAction.remove(--iAct);
								} else {
								}
							} else {
								// Met à jour l'ancien indice
								listAction.set(iAct - 1, new Integer(tagIdx));
							}
						}

						if (vList.size() > 0) {
							cnt++;
							Hashtable<String, String> attribut2 = new Hashtable<String, String>();
							attribut2.put("id", Integer.toString(cnt));

							FxXML.beginXMLElement(xmlDoc, "DATA", attribut2);
							FxXML.addXMLData(xmlDoc, vList);
							FxXML.closeXMLElement(xmlDoc, "DATA");
							
							mainList.addAll(vList);
						}
						if (listTag.size() <= tagIdx) {
							break;
						} else {
							tagIdx++;
						}
					}
				} finally {

					if (cnt > 0) {
						String szPath = formatFileName(szPathXml, mainList);
						String szFileName = szPath + "\\" + szFileNameFromUrl;
						String szFileEnd = szFileName.substring(szFileName.indexOf("_")+1) + ".xml";

						if (!isOverwriteIfFileExist() && isFileExistEndWith(szPath, szFileEnd)) {
							logEr("No Overwrite already existing file '" + szFileName + "'");
						}
						else {
							setTitle("Writing File:" + szFileName);
	
							FxXML.writeXML(szFileName, xmlDoc);
							setTitle("File:" + szFileName + " writed");
	
							// Clean
							cnt = 0;
							xmlDoc = new XMLDocument();
							xmlDoc.setEncode(getEncoding());
						}

						mainList = new Vector<Vector<Serializable>>();
					}
				}
			}
//		} else if (getDestination().equals(DESTINATION_DATABASE)) {
//			// TODO A FAIRE
//		}
	}

	@Override
	protected String formatFileName(String filename, List<Vector<Serializable>> list) {
		String szPath = super.formatFileName(filename, list);
		File fPath = new File(szPath);
		if (!fPath.exists()) {
			fPath.mkdirs();
		}
		return szPath;
	}
}