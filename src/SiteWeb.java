import java.util.Set;
import java.util.HashSet;

public class SiteWeb {
public void addRubrique(Rubrique RubriqueToAdd) {
      this.rubrique.add(RubriqueToAdd);
   }
   
   /**
    * <pre>
    *           1..1     contient des     1..*
    * SiteWeb ------------------------- Rubrique
    *           siteWeb        &gt;       rubrique
    * </pre>
    */

//liste des rubriques pour un siteWeb
   private Set<Rubrique> rubrique;
   
   public Set<Rubrique> getRubrique() {
      if (this.rubrique == null) {
         this.rubrique = new HashSet<Rubrique>();
      }
      return this.rubrique;
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
   
   private String url;
   
   public void setUrl(String value) {
      this.url = value;
   }
   
   public String getUrl() {
      return this.url;
   }
   
   private String nom;
   
   public void setNom(String value) {
      this.nom = value;
   }
   
   public String getNom() {
      return this.nom;
   }
   
   }
