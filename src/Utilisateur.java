import java.util.Set;
import java.util.HashSet;

public class Utilisateur {
   /**
    * <pre>
    *           0..*     est associé     1..1
    * Utilisateur ------------------------- ScrapperInterface
    *           utilisateur        &gt;       scrapperInterface
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
    * <pre>
    *           1..1     1..1
    * Utilisateur ------------------------- Bookmark
    *           utilisateur        &gt;       bookmark
    * </pre>
    */
   private Bookmark bookmark;
   
   public void setBookmark(Bookmark value) {
      this.bookmark = value;
   }
   
   public Bookmark getBookmark() {
      return this.bookmark;
   }
   
   /**
    * <pre>
    *           1..1     fait des     0..*
    * Utilisateur ------------------------- Recherche
    *           utilisateur        &gt;       recherche
    * </pre>
    */
   private Set<Recherche> recherche;
   
   public Set<Recherche> getRecherche() {
      if (this.recherche == null) {
         this.recherche = new HashSet<Recherche>();
      }
      return this.recherche;
   }
   
   private String idUser;
   
   public void setIdUser(String value) {
      this.idUser = value;
   }
   
   public String getIdUser() {
      return this.idUser;
   }
   
   public Utilisateur () 
   {
   }
   }
