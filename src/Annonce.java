import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;

public class Annonce {
   /**
    * <pre>
    *           0..*     1..1
    * Annonce ------------------------- Rubrique
    *           annonce        &lt;       rubrique
    * </pre>
    */
   private Rubrique rubrique;
   
   public void setRubrique(Rubrique value) {
      this.rubrique = value;
   }
   
   public Rubrique getRubrique() {
      return this.rubrique;
   }
   
   /**
    * <pre>
    *           0..*     0..*
    * Annonce ------------------------- Bookmark
    *           annonce        &lt;       bookmark
    * </pre>
    */
   private Set<Bookmark> bookmark;
   
   public Set<Bookmark> getBookmark() {
      if (this.bookmark == null) {
         this.bookmark = new HashSet<Bookmark>();
      }
      return this.bookmark;
   }
   
   private String titre;
   
   public void setTitre(String value) {
      this.titre = value;
   }
   
   public String getTitre() {
      return this.titre;
   }
   
   private LocalDate datePublication;
   
   public void setDatePublication(LocalDate value) {
      this.datePublication = value;
   }
   
   public LocalDate getDatePublication() {
      return this.datePublication;
   }
   
   private String description;
   
   public void setDescription(String value) {
      this.description = value;
   }
   
   public String getDescription() {
      return this.description;
   }
   
   private String id;
   
   public void setId(String value) {
      this.id = value;
   }
   
   public String getId() {
      return this.id;
   }
   
   public Annonce(String id,LocalDate datePublication,String titre) {
	   
	   this.id=id;
	   this.datePublication=datePublication;
	   this.titre=titre;
   }
   
   public String toString() {
	   return "annonce numero : "+id+" publiée le : "+datePublication+" Titre : "+titre;
	   
   }
   
   }
