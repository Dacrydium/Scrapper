import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Recherche {
   /**
    * <pre>
    *           0..*     fait dans     1..*
    * Recherche ------------------------- Rubrique
    *           recherche        &gt;       rubrique
    * </pre>
    */
   private ArrayList<Rubrique> listeRubrique;
   
   public boolean addRubrique(Rubrique rubriqueToAdd) {
	   
	   this.listeRubrique.add(rubriqueToAdd);
	   return true;
	   
   }
   
   public ArrayList<Rubrique> getRubrique() {
      if (this.listeRubrique == null) {
         this.listeRubrique = new ArrayList<Rubrique>();
      }
      return this.listeRubrique;
   }
   
   private String id;
   
   public void setId(String value) {
      this.id = value;
   }
   
   public String getId() {
      return this.id;
   }
   
   private String keywords;
   
   public void setKeywords(String value) {
      this.keywords = value;
   }
   
   public String getKeywords() {
      return this.keywords;
   }
   
   public Recherche() {}
   
   public Recherche(String keywords) {
	   
	   this.id = UUID.randomUUID().toString();
	   this.keywords=keywords;
	   
	   
	   
   }
   
   }
