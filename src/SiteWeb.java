import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SiteWeb {
	
	
public Rubrique addRubrique(String urlRubrique,String nomRubrique) {

		Rubrique rubriqueToAdd = new Rubrique(urlRubrique,nomRubrique);
	      this.getRubrique().add(rubriqueToAdd);
	      return rubriqueToAdd;
	
   }


   
   /**
    * <pre>
    *           1..1     contient des     1..*
    * SiteWeb ------------------------- Rubrique
    *           siteWeb        &gt;       rubrique
    * </pre>
    */

//liste des rubriques pour un siteWeb
   private ArrayList<Rubrique> listeRubrique;
   
   public ArrayList<Rubrique> getRubrique() {
      if (this.listeRubrique == null) {
         this.listeRubrique = new ArrayList<Rubrique>();
      }
      return this.listeRubrique;
   }
   
   /**
    * <pre>
    *           0..*     enregistre     1..1
    * SiteWeb ------------------------- ScrapperInterface
    *           siteWeb        &lt;       scrapperInterface
    * </pre>
    */
   private ScrapperInterface scrapperInterface;
   
   public void setScrapperInterface(ScrapperInterface value) {
      this.scrapperInterface = value;
   }
   
   public ScrapperInterface getScrapperInterface() {
      return this.scrapperInterface;
   }
   
   protected String url;
   
   public void setUrl(String value) {
      this.url = value;
   }
   
   public String getUrl() {
      return this.url;
   }
   
   protected String nom;
   
   public void setNom(String value) {
      this.nom = value;
   }
   
   public String getNom() {
      return this.nom;
   }
   
   public SiteWeb(String url,String nom) {
	   
	   this.nom=nom;
	   this.url=url;
	   
   }
   
   public String toString() {
	   
	   return this.getNom() + "\n";
   }
   
   }
