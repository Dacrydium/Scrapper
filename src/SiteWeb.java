import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SiteWeb {
	
/**
 * ajoute une rubrique dans la liste des rubrique du site web	
 * @param urlRubrique url de la rubrique a ajouter
 * @param nomRubrique nom de la rubrique a ajouter
 * @return la rubrique qui vient d'etre ajoutée
 */
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

/**
 * liste des rubriques du site web
 */
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
   /**
    * url du site web
    */
   protected String url;
   
   public void setUrl(String value) {
      this.url = value;
   }
   
   public String getUrl() {
      return this.url;
   }
   /**
    * id du site web
    */
   protected int id;
   
   public void setId(int value) {
	   this.id=value;
   }
   
   public int getId() {
	   return this.id;
   }
   /**
    * nom du site
    */
   protected String nom;
   
   public void setNom(String value) {
      this.nom = value;
   }
   
   public String getNom() {
      return this.nom;
   }
   /**
    * 
    * @param url url du site a creer
    * @param nom nom du site a creer
    */
   public SiteWeb(String url,String nom) {
	   
	   this.nom=nom;
	   this.url=url;
	   this.id=(int)(Math.random() * ((4000 - 0001) + 1)) + 0001;
	   
   }
   
   public String toString() {
	   
	   return this.getNom() + "\n";
   }
   
   }
