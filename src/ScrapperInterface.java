import java.util.HashSet;
import java.util.Set;

public class ScrapperInterface {
   /**
    * <pre>
    *           1..1     enregistre     0..*
    * ScrapperInterface ------------------------- SiteWeb
    *           scrapperInterface        &gt;       siteWeb
    * </pre>
    */
	
	//Liste des Sites Web
   private Set<SiteWeb> siteWeb;
   
   public Set<SiteWeb> getSiteWeb() {
      if (this.siteWeb == null) {
         this.siteWeb = new HashSet<SiteWeb>();
      }
      return this.siteWeb;
   }
   
   /**
    * <pre>
    *           1..1     est associé     0..*
    * ScrapperInterface ------------------------- Utilisateur
    *           scrapperInterface        &lt;       utilisateur
    * </pre>
    */
   
   //Liste des Utilisateurs
   private Set<Utilisateur> utilisateur;
   
   public Set<Utilisateur> getUtilisateur() {
      if (this.utilisateur == null) {
         this.utilisateur = new HashSet<Utilisateur>();
      }
      return this.utilisateur;
   }
   
   public boolean addWebsite() {
      return false;
         }
   
   }
