import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

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
   public ArrayList<Annonce> listeAnnonce = new ArrayList<Annonce>();
   
   public ArrayList<Annonce> getAnnonce() {
      if (this.listeAnnonce == null) {
         this.listeAnnonce = new ArrayList<Annonce>();
      }
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
				//System.out.println("id annonce:"+ id );
				//System.out.println("Mots clés:"+hashtag.substring(0, indiceSeparateur-1));


				// titre de l'annonce			
				DomElement tableTitre = listTable.get(1);
				//System.out.println(tableTitre);
				DomNodeList<HtmlElement> listTD = tableTitre.getElementsByTagName("td");
				String titre = listTD.get(1).getTextContent();
				
				//conversion String en LocalDate
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				String date = listTD.get(3).getTextContent().replaceAll("\\s", "").concat("/2019");
				LocalDate datePubli = LocalDate.parse(date, formatter);
				
				
				String type = listTD.get(0).getTextContent();
				//System.out.println("titre annonce :"+titre+"date publication: "+date+type);
				
				
				//creation d'une annonce
				
				Annonce addAnnonce = new Annonce(id, datePubli, titre);
				
				//Ajout dans la liste des annonces de la rubrique
				//System.out.println(addAnnonce);
				this.listeAnnonce.add(addAnnonce);
				
				
				

				// activation du détail de l'annonce
				tableHeader.click();

				// details mais lent si fait au fur et à mesure
				/*
				 * webClient.waitForBackgroundJavaScript(1500); // attente du chargment de
				 * l'annonce 
				 * List<DomElement> details0 = annonce.getByXPath("//div[contains(@id, 'detail_"+id+"')]");
				 * System.out.println("nb details:"+details0.size()); for (DomElement
				 * annoncedetail : details0) {
				 * System.out.println(annoncedetail.getTextContent()); }
				 */
			}


			// Récupération de tous les contenus détaillés 
			webClient.waitForBackgroundJavaScriptStartingBefore(2000); // wait
			List<DomElement> details = page.getByXPath("//div[contains(@id, 'detail_')]");
			for (DomElement annoncedetail : details) {
				int indice = annoncedetail.getAttribute("id").indexOf('_');
				String idDetail = annoncedetail.getAttribute("id").substring(indice+1);
				
				System.out.println(idDetail);
				
				System.out.println("Description : "+annoncedetail.getTextContent()); 
				
				
				for(int i = 0 ; i < listeAnnonce.size(); i++ ) {
					if(idDetail == listeAnnonce.get(i).getId()) {
						
						listeAnnonce.get(i).addDescription(annoncedetail.getTextContent());
						
					}
					
				}
								
				
				//System.out.println("ID details:"+idDetail); 
				//System.out.println(annoncedetail.getTextContent()); 
			}
			
			System.out.println(listeAnnonce);
			
			




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
   
   }
