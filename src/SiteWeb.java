import java.util.Set;
import java.util.HashSet;

public class SiteWeb {
   /**
    * <pre>
    *           1..1     contient des     1..*
    * SiteWeb ------------------------- Rubrique
    *           siteWeb        &gt;       rubrique
    * </pre>
    */
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
   
   }
