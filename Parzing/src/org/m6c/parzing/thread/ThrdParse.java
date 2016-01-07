package org.m6c.parzing.thread;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.m6c.parzing.bean.ItemTableDwnld;
import org.m6c.parzing.bean.ItemTableLink;
import org.m6c.parzing.bean.ThreadList;
import org.m6c.parzing.bean.ThreadParameter;
import org.m6c.parzing.tool.ToolLoadUrl;
import org.m6c.ressource.FxHtml;

import ressource.FxString;
import ressource.bean.BeanTag;
import ressource.bean.BeanTagAttribut;
import ressource.image.FxJpg;

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

public abstract class ThrdParse extends AncestorThread {

	public ThrdParse(String uri, String txt, ThreadParameter parameter, ThreadList list) {
		super(uri, txt, parameter, list);
	}

	public void run() {
	  	String szUrl = this.getUri();
		if (this.isSubLink() || this.isUseIncludeList())
			this.getLink(szUrl);
		else {
			try {
				// Analyse seulement la page actuelle
				this.parseUrl(szUrl);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void getLink(String szUrl) {
		int iNiveauMax = (this.isSubLink()) ? getMaxSubLevel() : 0;// 3 : 0;
		int iNiveauActuel = 0;

		// Liste des Url de la page à traiter
		Vector vGlobalListUrl = new Vector();
		// Liste des Index pour chaque vGlobalListUrl
		Vector vGlobalListLoop = new Vector();
		// Liste des liens de la premiere page à traiter
		Vector vListUrl = new Vector();
		// Liste des mask (RegEx) de liens à traiter
		Vector vListMaskUrlInc = null;
		// Liste des mask (RegEx) de liens à exclure
		Vector vListMaskUrlExc = null;
		if (this.isUseIncludeList()) { // Parcoure les liens contenu dans la
			// liste jTableLinkInclude
			vListUrl = new Vector();
			for (int i = 0; i < this.getUrlIncludeList().size(); i++) {
				ItemTableLink item = (ItemTableLink) this.getUrlIncludeList().get(i);
				// Ajoute les liens ajouter dans la liste jTableLinkInclude
				vListUrl.add(item.getUrl());
			}
		} else {
			vListUrl.add(szUrl);
			if (this.isUseIncludeListAsMask()) { // Parcoure les liens
				// contenu dans la liste
				// jTableLinkInclude
				vListMaskUrlInc = new Vector();
				for (int i = 0; i < this.getUrlIncludeList().size(); i++) {
					ItemTableLink item = (ItemTableLink) this.getUrlIncludeList().get(i);

					Pattern pat = null;
					if (item.getUrl().endsWith("/i")) {
						String szPat = szUrl.substring(0, szUrl.length() - 2);
						pat = Pattern.compile(szPat, Pattern.CASE_INSENSITIVE);
					} else
						pat = Pattern.compile(item.getUrl());

					// Ajoute les liens ajouter dans la liste
					// jTableLinkInclude
					vListMaskUrlInc.add(pat);
				}

				// Parcoure les liens contenu dans la liste
				// jTableLinkInclude
				vListMaskUrlExc = new Vector();
				for (int i = 0; i < this.getUrlExcludeList().size(); i++) {
					ItemTableLink item = (ItemTableLink) this.getUrlExcludeList().get(i);

					Pattern pat = null;
					if (item.getUrl().endsWith("/i")) {
						String szPat = szUrl.substring(0, szUrl.length() - 2);
						pat = Pattern.compile(szPat, Pattern.CASE_INSENSITIVE);
					} else
						pat = Pattern.compile(item.getUrl());

					// Ajoute les liens ajouter dans la liste
					// jTableLinkInclude
					vListMaskUrlExc.add(pat);
				}
			}
		}

		// Ajout des liens de la première page
		vGlobalListUrl.addElement(vListUrl);
		// Ajout un index à Zero par default
		vGlobalListLoop.addElement(new Integer(0));

		if (vListUrl.size() > 0) {

			// Affiche la liste des liens chez le parent
			showLink(vListUrl);

			// Boucle sur toutes les listes des liens
			while (vGlobalListUrl.size() > 0) {
				// Liste du niveau Actuel
				Vector item = (Vector) vGlobalListUrl.elementAt(iNiveauActuel);
				// Index Actuel de la liste des liens
				int iLoop = ((Integer) vGlobalListLoop.elementAt(iNiveauActuel)).intValue();
				if (iLoop < item.size()) { // Il y a encore des liens à traiter

					// Lien a traiter
					String szUrl2 = (String) item.elementAt(iLoop++);
					// Teste si le lien doit être traiter ou non
					if (!this.isExclude(szUrl2)) {
						// Met à jour l'index Actuel de la liste des liens
						vGlobalListLoop.setElementAt(new Integer(iLoop), iNiveauActuel);
						try {
							if ((szUrl2 != null) && (szUrl2.length() > 0)) {
								URLConnection conn = createConnection(szUrl2);
								String html = ToolLoadUrl.load(conn, isCorrectHtml(), isClean(), isIndent());
								// Analyse la page
								this.parseUrl(szUrl2, html);
								// Si nous ne somme pas arrivé au niveau Maximum de
								// traitement
								// et si nous voulons aussi les liens contenus dans
								// la page
								if ((iNiveauActuel < iNiveauMax) && this.isSubLink()) {
									// Passe au niveau "Superieur"
									iNiveauActuel++;
									// Met à Zero l'index dans la liste
									iLoop = 0;
									// Recupère la liste des liens contenu dans la
									// page à traiter
									Vector vList = getListLink(szUrl2, html, vListMaskUrlInc, vListMaskUrlExc);
									// Fait un distinct sur la liste
									vList = createListDistinct(vList);
									// Affiche la liste des liens chez le parent
									showLink(vList);
									// Ajoute la liste des liens a traiter
									vGlobalListUrl.addElement(vList);
									// Ajoute
									vGlobalListLoop.addElement(new Integer(iLoop));
	
									log("Loop:" + iLoop + " vGlobalListUrl.size:" + vGlobalListUrl.size());
	
									// Ajout les liens trouvés dans la page pour ne
									// pas boucler sur les liens déjà trouvé.
									for (int i = 0; i < vList.size(); i++) {
										vListMaskUrlExc.add(Pattern.compile((String)vList.get(i)));
									}
								}
							}
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						// Passe à l'Url suivante
						vGlobalListLoop.setElementAt(new Integer(iLoop + 1), iNiveauActuel);
					}
				} else { // Il n'y a plus de liens à traiter dans cette liste
					// Supprime la liste des Url
					vGlobalListUrl.remove(iNiveauActuel);
					// Supprime la liste Index
					vGlobalListLoop.remove(iNiveauActuel);
					// Reviend au niveau "Inferieur"
					iNiveauActuel--;
				}
			}
		}
	}

	/**
	 * Retourne la liste des liens contenu dans szHTML à partir de l'adresse
	 * szURL
	 * 
	 * @param szURL
	 * @param szHTML
	 * @param listMaskUrlInc
	 * @return liste d'objet URL
	 */
	protected Vector getListLink(String szURL, String szHTML, Vector listMaskUrlInc, Vector listMaskUrlExc) {
		Vector ret = new Vector();
		if (szHTML.length() > 0) {
			String szHTMLUp = szHTML.toLowerCase();


//			char[] htmlChar = szHTML.toCharArray();
//			int size = htmlChar.length;
//			int nb=1;
//			for(int i=0 ; i<size ; i++) {
//				if (!szHTML.substring(i, i+nb).toUpperCase().equals(szHTMLUp.substring(i, i+nb))) {
//					int a = 0;
//					a++;
//				}
//			}
			
			
			String baliseLnk = "href=";//"HREF=";
			String szCommentDeb = "<!--";
			String szCommentFin = "-->";
			int iDeb = 0;

			while (iDeb > -1) {
				iDeb = szHTMLUp.indexOf(baliseLnk, iDeb);
				int iDebComment = szHTMLUp.indexOf(szCommentDeb, iDeb);
				int iFinComment = szHTMLUp.indexOf(szCommentFin, iDebComment);
				if ((iDeb > -1) && ((iFinComment == -1) || (iFinComment >= iDebComment))) {
					iDeb += baliseLnk.length();
					StringBuffer str = new StringBuffer();
					boolean bGuillemet = (boolean) (szHTMLUp.charAt(iDeb) == '"');
					boolean bLoop = true;
					if (bGuillemet)
						iDeb++;
					while (bLoop) {
						// char c = szHTMLUp.charAt(iDeb++);
						char c = szHTML.charAt(iDeb++);
						// Pour contourner un problème avec l'Allemand par exemple qui fait une difference entre le minuscule et le majuscule de certaines lettres
						//iDeb += szHTMLUp.substring(iDeb, iDeb+1).length();
						switch (c) {
						case ' ':
							if (!bGuillemet)
								bLoop = false;
							break;
						case '>':
						case '"':
							bLoop = false;
							break;
						}
						if (bLoop)
							str.append(c);
					}
					iDeb += str.length();

					if (str.length() > 0) {
						try {
							/**
							 * Ajout l'url si elle correspond à une entrée de la
							 * liste des liens à Inclure (ou si la liste est
							 * null)
							 */
							boolean bAdd = false;
							URL root = new URL(szURL);
							URL urlTmp = new URL(root, str.toString());
							String szUrl = urlTmp.toString();
							if (listMaskUrlInc == null) {
								// ret.add(szUrl);
								bAdd = true;
							} else {
								for (Iterator iter = listMaskUrlInc.iterator(); iter.hasNext();) {
									Pattern element = (Pattern) iter.next();
									if (element.matcher(szUrl).find()) {
										// ret.add(szUrl);
										bAdd = true;
										break;
									}
								}
							}

							if (bAdd) {
								/**
								 * Ajout l'url si elle ne correspond pas à une
								 * entrée de la liste des liens à Exclure (ou si
								 * la liste est null)
								 */
								if (listMaskUrlExc != null) {
									for (Iterator iter = listMaskUrlExc.iterator(); iter.hasNext();) {
										Pattern element = (Pattern) iter.next();
										if (element.matcher(szUrl).find()) {
											bAdd = false;
											break;
										}
									}
								}
							}

							if (bAdd) {
								ret.add(szUrl);
							}
						} catch (MalformedURLException ex) {
							logEr("ERROR: Malformed Url: " + str.toString());
						}
					}
				} else if (iDeb > -1)
					iDeb++;
			}
		}
		return ret;
	}

	/**
	 * Analyse la page actuelle à la recherche des informations à téléchanger
	 * contenu dans la liste jTableDownload
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private void parseUrl(String url) throws MalformedURLException, IOException {
		URLConnection conn = createConnection(url);

		String html = ToolLoadUrl.load(conn, isCorrectHtml(), isClean(),isIndent());

		parseUrl(url, html);
	}

	/**
	 * Analyse la page actuelle à la recherche des informations à téléchanger
	 * contenu dans la liste jTableDownload
	 */
	protected abstract void parseUrl(String url, String html);

	protected String buildFileName(String szUrl, String path) {
		// return path.concat("\\").concat( (
		// (this.getUrlContext().getQuery()==null ||
		// this.getUrlContext().getQuery().equals("")) ?
		// this.getUrlContext().getFile() : this.getUrlContext().getQuery()));
		URL url = null;
		try {
			url = new URL(szUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String ret = path + "\\";
		ret += Calendar.getInstance().getTimeInMillis();
		if (url!=null) {
			if (url.getQuery() != null && !url.getQuery().equals("")) {
				ret += "_" + this.getUrl().getQuery();
			} else {
				if (url.getFile() != null && !url.getFile().equals("")) {
					String filename = url.getFile();
					while(filename.endsWith("/")) {
						filename = filename.substring(0, filename.length()-1);
					}
					int idx = filename.lastIndexOf('/');
					if (idx >= 0)
						filename = filename.substring(idx + 1);
					ret += "_" + filename;
				} else {
					ret += "_" + url.getHost();
				}
			}
		}

		return ret;
	}
	
	protected String formatFileName(String filename, List list) {
		if (list!=null && list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				List vList = (List)list.get(i);
				filename = FxString.replaceString(filename, "[" + vList.get(0) + "]", ((List)vList.get(1)).get(0).toString());
//				filename = filename.replaceAll("\\[" + ((List)vList.get(1)).get(0) + "]", (String)vList.get(0));
			}
		}
		return filename;
	}

	/**
	 * Supprime toutes les informations superflu de szHTML
	 * 
	 * @param szHTML
	 *            : Chaine de caractère à traiter
	 * @return
	 */
	protected String cleanTag(String szHTML) {
		String str = szHTML;
		str = FxHtml.removeTag(szHTML);
		str = FxHtml.cleanHtml(szHTML);
		return str.trim();
	}

	private URLConnection createConnection(String url) throws IOException,
			MalformedURLException {
		URLConnection conn = new URL(url).openConnection();

		/* Essai avec le paramètrage proxy */
		String proxyHost = getProxyHost();
		String proxyUser = getProxyUser();
		String proxyPwd = getProxyPwd();
		String proxyPort = getProxyPort();
		if (proxyHost != null && !"".equals(proxyHost)) {
			String authentication = proxyUser + ":" + proxyPwd;
			String encodedPassword = "Basic " + new sun.misc.BASE64Encoder().encode(authentication.getBytes());
			System.getProperties().put("http.proxySet", "true");
			System.getProperties().put("http.proxyHost", proxyHost);
			System.getProperties().put("http.proxyPort", proxyPort);
			conn.setRequestProperty("Proxy-Authorization", encodedPassword);
		}
		return conn;
	}

	private Vector createListDistinct(Vector vList) {
		Vector list = new Vector();
		for(int i=0 ; i<vList.size() ; i++) {
			if (!list.contains(vList.elementAt(i)))
				list.add(vList.elementAt(i));
		}
		return list;
	}

	protected boolean isEquals(BeanTag tag1, BeanTag tag2) {
		String escapeAttrVal = getEscapeAttrVal();
		boolean equals = tag1.equalsAtLeast(tag2, getEscapeAttrVal());
		if (equals) {
			String content1 = tag1.getContent();
			String content2 = tag2.getContent();
			content1 = (content1==null) ? content1 : content1.trim();
			content2 = (content2==null) ? content2 : content2.trim();
			equals =(content1==null && content2==null) ||
					(
						content1!=null && content2!=null &&
						content1.equals(content2)
					) ||
					(escapeAttrVal!=null &&
							(
								escapeAttrVal.equals(content1) ||
								escapeAttrVal.equals(content2)
							));
				
		}
		return equals;
	}

	protected List downloadImage(String szHtml, String destinationPath) {
		List ret = new Vector();
		List listTag = FxHtml.extractHashTag(szHtml);
		for(int i=0 ; i<listTag.size() ; i++) {
			BeanTag tag = (BeanTag) listTag.get(i);
			if (tag.getName().equalsIgnoreCase("img")) {
				List listAttr = tag.getListAttribut();
				for(int j=0 ; j<listAttr.size() ; j++) {
					BeanTagAttribut attr = (BeanTagAttribut)listAttr.get(j);
					if ("src".equalsIgnoreCase(attr.getName())) {
						setTitle("Writing Image:" + attr.getValue() + " to " + destinationPath);
						String filename = "";
						try {
							URLConnection urlConnection = createConnection(attr.getValue());
							filename = FxJpg.writeFromUrl(urlConnection, destinationPath);
						} catch (Exception e) {
							filename = e.getMessage();
							e.printStackTrace();
						} finally {
							List img = new Vector();
							img.add(attr.getValue());
							img.add(filename);
							ret.add(img);
						}
					}
				}
			}
		}
		return ret;
	}

	protected boolean isFileExistEndWith(String directory, final String end) {
		File dir = new File(directory);
		if (!dir.isDirectory())
			return false;

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
			    return fileName.toUpperCase().endsWith(end.toUpperCase());
			}
		};
		String[] myFiles = new File(directory).list(filter);

		return (myFiles!=null && myFiles.length>0);
	}
	
	protected void showLink(Vector vListUrl) {
		for (int i = 0; i < vListUrl.size(); i++) {
			log("showLink["+i+"]:" + vListUrl.get(i));
		}
	}
	protected void setTitle(String string) {
		log("title:" + string);
	}
	
	protected void log(String log) {
		System.out.println(log);
	}
	
	protected void logEr(String log) {
		System.err.println(log);
	}
}