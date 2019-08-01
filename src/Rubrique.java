import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
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

	private String url;

	public void setUrl(String value) {
		this.url = value;
	}

	public String getUrl() {
		return this.url;
	}

	private LocalDate dateLastUpdate;

	public void setDateLastUpdate(LocalDate value) {
		this.dateLastUpdate = value;
	}

	public LocalDate getDateLastUpdate() {
		return this.dateLastUpdate;
	}

	private String nom;

	public void setNom(String value) {
		this.nom = value;
	}

	public String getNom() {
		return this.nom;
	}

	public Rubrique(String url,String nom) {

		this.url=url;
		this.nom=nom;
	}


	public void majAnnonce() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {

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

				// liste les tables
				DomNodeList<HtmlElement> listTable = annonce.getElementsByTagName("table");


				// id + mots clés de l'annonce
				DomElement tableHeader = listTable.get(0);
				String hashtag = tableHeader.getAttribute("data-hashtag");
				int indiceSeparateur = hashtag.indexOf('!');
				String id = hashtag.substring( indiceSeparateur+1) ;
				int id_int = Integer.parseInt(id);
				String motsCles = hashtag.substring(0, indiceSeparateur-1);
				


				// titre de l'annonce			
				DomElement tableTitre = listTable.get(1);
				DomNodeList<HtmlElement> listTD = tableTitre.getElementsByTagName("td");
				String titre = listTD.get(1).getTextContent();

				//conversion String date --> LocalDate datePubli
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				String date = listTD.get(3).getTextContent().replaceAll("\\s", "").concat("/2019");
				LocalDate datePubli = LocalDate.parse(date, formatter);

				//Type de l'annonce [offre] ou [demande]
				String type = listTD.get(0).getTextContent();
				


				//creation d'une annonce

				Annonce addAnnonce = new Annonce(id_int, datePubli, titre);

				//Ajout dans la liste des annonces de la rubrique
				
				this.listeAnnonce.put(addAnnonce.getId(), addAnnonce);
				System.out.println(addAnnonce.getTitre());
			




				// activation du détail de l'annonce
			tableHeader.click();

			
			}


			// Récupération de tous les contenus détaillés 
			webClient.waitForBackgroundJavaScriptStartingBefore(2000); // wait
			List<DomElement> details = page.getByXPath("//div[contains(@id, 'detail_')]");
			for (DomElement annoncedetail : details) {
			
				//listeAnnonce.get(Integer.parseInt(annoncedetail.getAttribute("id").substring(7))).addDescription(annoncedetail.getTextContent().replaceAll("\\s+", " "));
			
				//System.out.println(listeAnnonce.get(Integer.parseInt(annoncedetail.getAttribute("id").substring(7))));
				
				int indice = annoncedetail.getAttribute("id").indexOf('_');
				String idDetail = annoncedetail.getAttribute("id").substring(indice+1);
				int idDetail_int = Integer.parseInt(idDetail);
				String contenu = annoncedetail.getTextContent();
				
				listeAnnonce.get(idDetail_int).addDescription(contenu);
			//	System.out.println(listeAnnonce.get(idDetail_int));
				
				

				

	/*			for(int i = 0; i < listeAnnonce.size(); i++) {
					if(listeAnnonce.get(i).getId().equals(idDetail)) {

						listeAnnonce.get(i).addDescription(contenu);
						System.out.print(listeAnnonce.get(i));
					}
				}
*/

			}


			//System.out.println("ID details:"+idDetail); 
			//System.out.println(annoncedetail.getTextContent()); 


		//	System.out.println(listeAnnonce);
		//	System.out.print("Page OK");
			
			
			



			// accès à la page suivante
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



	}
	
	public boolean SaveToFile() {
		
		try {
			final String Json = new Gson().toJson(listeAnnonce);
			BufferedWriter out = new BufferedWriter( new FileWriter(this.getNom()+"_Liste_Annonce.json"));
			out.write(Json);
			out.close();
			System.out.println("Sauvegarde reussie !");
			//System.out.println(Json);
			return true;
		}catch(Exception e){ 
			System.out.print("Erreur lors de la sauvegarde des annonces...");
			return false;
		}
		
		
		
		
		
		
	}
	
	
	public boolean readFromFile() {
		
		try {
			
			
			
			BufferedReader in = new BufferedReader( new FileReader(this.getNom()+"_Liste_Annonce.json"));
			String json_read = in.readLine();
			in.close();
			
			Type type = new TypeToken<HashMap<Integer, Annonce>>(){}.getType();
			
			
			listeAnnonce.clear();
	        listeAnnonce = new Gson().fromJson(json_read, type);
	        
			System.out.println("Lecture reussie !");
			System.out.println(listeAnnonce.get(3159703).getTitre());
			return true;
		}catch(Exception e){ 
			System.out.println("Erreur lors de la lecture des annonces...");
			return false;
		}
		
	}
	
}




