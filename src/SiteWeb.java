import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SiteWeb {
	
	
public Rubrique addRubrique(String urlRubrique,String nomRubrique) {

		Rubrique rubriqueToAdd = new Rubrique(urlRubrique,nomRubrique);
	      this.getRubrique().put(rubriqueToAdd.getId(),rubriqueToAdd);
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
   private HashMap<Integer,Rubrique> listeRubrique;
   
   public HashMap<Integer,Rubrique> getRubrique() {
      if (this.listeRubrique == null) {
         this.listeRubrique = new HashMap<Integer,Rubrique>();
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
   
   protected int id;
   
   public void setId(int value) {
	   this.id=value;
   }
   
   public int getId() {
	   return this.id;
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
	   this.id=(int)(Math.random() * ((4000 - 0001) + 1)) + 0001;
	   
   }
   
   public String toString() {
	   
	   return this.getNom() + "\n";
   }
   
   }
