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
   protected ArrayList<Rubrique> listeRubrique;
   
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
   
   protected int id;
   
   public void setId(int value) {
      this.id = value;
   }
   
   public int getId() {
      return this.id;
   }
   
   protected ArrayList<String> keywords;
   
   public void setKeywords(String value) {
      this.keywords.add(value) ;
   }
   
   public ArrayList<String> getKeywords() {
      return this.keywords;
   }
   
   protected SiteWeb site;
   
   public void setSite(SiteWeb site) {
	   this.site=site;
   }
   
   public SiteWeb getSite() {
	   return this.site;
   }
   
   public Recherche() {}
   
   public Recherche(SiteWeb site, ArrayList<Rubrique> rubrique,ArrayList<String> keywords) {
	   
	   this.id = (int)(Math.random() * ((4000 - 0001) + 1)) + 0001;;
	   this.keywords=keywords;
	   this.site=site;
	   this.listeRubrique=rubrique;
	   
	   
	   
   }
   
   }
