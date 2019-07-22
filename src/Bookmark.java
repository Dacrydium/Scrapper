import java.util.Set;
import java.util.HashSet;

public class Bookmark {
   /**
    * <pre>
    *           1..1     1..1
    * Bookmark ------------------------- Utilisateur
    *           bookmark        &lt;       utilisateur
    * </pre>
    */
   private Utilisateur utilisateur;
   
   public void setUtilisateur(Utilisateur value) {
      this.utilisateur = value;
   }
   
   public Utilisateur getUtilisateur() {
      return this.utilisateur;
   }
   
   /**
    * <pre>
    *           0..*     0..*
    * Bookmark ------------------------- Annonce
    *           bookmark        &gt;       annonce
    * </pre>
    */
   private Set<Annonce> annonce;
   
   public Set<Annonce> getAnnonce() {
      if (this.annonce == null) {
         this.annonce = new HashSet<Annonce>();
      }
      return this.annonce;
   }
   
   }
