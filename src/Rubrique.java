import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.sql.Savepoint;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.HashMap;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Rubrique {
	/**
	 * <pre>
	 *           1..*     contient des     1..1
	 * Rubrique ------------------------- SiteWeb
	 *           rubrique        &lt;       siteWeb
	 * </pre>
	 */
	private SiteWeb siteWeb;

	public void setSiteWeb(SiteWeb value) {
		this.siteWeb = value;
	}

	public SiteWeb getSiteWeb() {
		return this.siteWeb;
	}

	/**
	 * <pre>
	 *           1..1     0..*
	 * Rubrique ------------------------- Annonce
	 *           rubrique        &gt;       annonce
	 * </pre>
	 */
	public HashMap<Integer, Annonce> listeAnnonce = new HashMap<Integer,Annonce>();

	public HashMap<Integer, Annonce> getAnnonce() {
		return this.listeAnnonce;
	}



	/**
	 * <pre>
	 *           1..*     fait dans     0..*
	 * Rubrique ------------------------- Recherche
	 *           rubrique        &lt;       recherche
	 * </pre>
	 */
	private Set<Recherche> recherche;

	public Set<Recherche> getRecherche() {
		if (this.recherche == null) {
			this.recherche = new HashSet<Recherche>();
		}
		return this.recherche;
	}
/**
 * url de la rubrique
 */
	private String url;

	public void setUrl(String value) {
		this.url = value;
	}

	public String getUrl() {
		return this.url;
	}
/**
 * date de la derniere mis a jour de la rubrique
 */
	private LocalDate dateLastUpdate;

	public void setDateLastUpdate(LocalDate value) {
		this.dateLastUpdate = value;
	}

	public LocalDate getDateLastUpdate() {
		return this.dateLastUpdate;
	}
/**
 * nom de la rurique
 */
	private String nom;

	public void setNom(String value) {
		this.nom = value;
	}

	public String getNom() {
		return this.nom;
	}
/** 
 * id de la rubrique
 * */
	private int id;

	public void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return this.id;
	}
/**
 * 
 * @param url url de la rubrique
 * @param nom nom de la rubrique
 */
	public Rubrique(String url,String nom) {

		this.url=url;
		this.nom=nom;
		this.id=(int)(Math.random() * ((4000 - 0001) + 1)) + 0001;
		this.dateLastUpdate = LocalDate.of(1900, 01, 01);
	}

/**
 * Scrappe le contenu de la rubrique sur internet.
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 * @throws InterruptedException
 */
	public void majAnnonce() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		System.out.println("La rubrique a ÈtÈ mise il y a plus d'un jour, Une mise a jour va Ítre lancÈe \n Mise a jour en cour...");
		if( ! listeAnnonce.isEmpty()) {
			listeAnnonce.clear();
		}


		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
		webClient.getOptions().setUseInsecureSSL(true);

		// desactiver les warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);

		HtmlPage page = webClient.getPage(this.url);//Url de la rubrique


		List<DomElement> annonces = page.getByXPath("//span[contains(@id, 'header_annonce_')]");
		int numpage=1;

		while( annonces != null && annonces.size()>0)
		{
			for (DomElement annonce : annonces) {

				/**
				 * liste les tables
				 */
				DomNodeList<HtmlElement> listTable = annonce.getElementsByTagName("table");


				/**
				 * recupere l'id et les mots cle de l'annonce
				 */
				DomElement tableHeader = listTable.get(0);
				String hashtag = tableHeader.getAttribute("data-hashtag");
				int indiceSeparateur = hashtag.indexOf('!');
				String id = hashtag.substring( indiceSeparateur+1) ;
				int id_int = Integer.parseInt(id);
				String motsCles = hashtag.substring(0, indiceSeparateur-1);



				/**
				 * recupere le titre de l'annonce		
				 */
				DomElement tableTitre = listTable.get(1);
				DomNodeList<HtmlElement> listTD = tableTitre.getElementsByTagName("td");
				String titre = listTD.get(1).getTextContent();

				/**
				 * conversion String date --> LocalDate datePubli
				 */
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				String date = listTD.get(3).getTextContent().replaceAll("\\s", "").concat("/2019");
				LocalDate datePubli = LocalDate.parse(date, formatter);

				/**
				 * Type de l'annonce [offre] ou [demande]
				 */
				String type = listTD.get(0).getTextContent();



				/**
				 * crÈtion d'une annonce
				 */
				Annonce addAnnonce = new Annonce(id_int, datePubli, titre);

				/**
				 * Ajout dans la liste des annonces de la rubrique
				 */
				this.listeAnnonce.put(addAnnonce.getId(), addAnnonce);
				
				/**
				 *  activation du d√©tail de l'annonce
				 */
				tableHeader.click();


			}


			/**
			 *  R√©cup√©ration de tous les contenus d√©taill√©s 
			 */
			webClient.waitForBackgroundJavaScriptStartingBefore(2000); // wait
			List<DomElement> details = page.getByXPath("//div[contains(@id, 'detail_')]");
			for (DomElement annoncedetail : details) {

				int indice = annoncedetail.getAttribute("id").indexOf('_');
				String idDetail = annoncedetail.getAttribute("id").substring(indice+1);
				int idDetail_int = Integer.parseInt(idDetail);
				String contenu = annoncedetail.getTextContent();

				/**
				 * Extraire le prix de l'annonce
				 */
				if(contenu.contains("Prix : ") && contenu.contains("F cfp")) {
					int indice_Prix = contenu.indexOf("                     Prix :")+28;

					int indice_f = contenu.indexOf("F cfp");

					try {
						String Prix = contenu.substring(indice_Prix, indice_f-1).replaceAll("\\s", "");
						int Prix_int = Integer.parseInt(Prix);
						listeAnnonce.get(idDetail_int).setPrix(Prix_int);
					
					}
					catch(StringIndexOutOfBoundsException e) {
						System.out.print("impossible d'enregistrer le prix pour cette annonce");
					}
				}


				listeAnnonce.get(idDetail_int).addDescription(contenu);
				


			}


			/**
			 * acc√®s √† la page suivante
			 */
			List<DomElement> currentPage = page.getByXPath("//span[contains(@class, 'paggination_tableau')]");
			if( ! currentPage.isEmpty())
			{
				DomElement nextPages = currentPage.get(0).getNextElementSibling();
				if( nextPages != null) {
					nextPages.click();
					//webClient.setAjaxController(new NicelyResynchronizingAjaxController()); // wait
					webClient.waitForBackgroundJavaScriptStartingBefore(2000); // wait

					annonces = page.getByXPath("//span[contains(@id, 'header_annonce_')]");
					numpage++;
					System.out.println("------------------------------------------- Next Page :"+ numpage +"-------------------------------------------");
				}
				else annonces = null;

			}
			else annonces = null;

		}

		/**
		 * actualise la date de derniere mis a jour de la rubrique
		 */
		setDateLastUpdate(LocalDate.now());
		

	}



	public String toString() {
		return this.nom+"\n";
	}

}




