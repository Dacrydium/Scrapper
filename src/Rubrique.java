import java.util.Set;
import java.util.HashSet;
import java.time.*;

public class Rubrique {
private LocalDate dateLastUpdate;
   
   public void setDateLastUpdate(LocalDate value) {
      this.dateLastUpdate = value;
   }
   
   public LocalDate getDateLastUpdate() {
      return this.dateLastUpdate;
   }
   
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
   private Set<Annonce> annonce;
   
   public Set<Annonce> getAnnonce() {
      if (this.annonce == null) {
         this.annonce = new HashSet<Annonce>();
      }
      return this.annonce;
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
   
   }
