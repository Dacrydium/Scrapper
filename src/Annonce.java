import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Annonce {

    /**
     * Prix incsrit dans l'annonce.
     */
   private int Prix;
   
   public void setPrix(int value) {
	   this.Prix=value;
   }
   
   public int getPrix() {
	   return this.Prix;
   }
   /**
    * titre de l'annonce
    */
   private String titre;
   
   public void setTitre(String value) {
      this.titre = value;
   }
   
   public String getTitre() {
      return this.titre;
   }
   /**
    * date de publication de l'annonce
    */
   private LocalDate datePublication;
   
   public void setDatePublication(LocalDate value) {
      this.datePublication = value;
   }
   
   public LocalDate getDatePublication() {
      return this.datePublication;
   }
   /**
    * contenu détaillée de l'annonce
    */
   private String description;
   
   public void setDescription(String value) {
      this.description = value;
   }
   
   public String getDescription() {
      return this.description;
   }
   /**
    * identifiant unique de l'annonce
    */
   private int id;
   
   public void setId(int value) {
      this.id = value;
   }
   
   public int getId() {
      return this.id;
   }
   /**
    * COnstructeur de l'objet annonce
    * @param id identifiant de l'annonce
    * @param datePublication date de publication de l'annonce
    * @param titre titre de l'annonce
    */
   public Annonce(int id,LocalDate datePublication,String titre) {
	   
	   this.id=id;
	   this.datePublication=datePublication;
	   this.titre=titre;
   }
   /**
    * retourne sous forme de string l'objet annonce
    */
   public String toString() {
	   return "Annonce Numero : " +id+ " \n " +   "Publiée le : " +datePublication+ " \n" + " Titre : "+titre+"\n\t"+"Description : "+description + "\n";
	   
   }
   /**
    * ajoute du contenu detaille a une annonce
    * @param description Contenu à ajouter a l'annonce
    * @return true si l'ajout a reussie, false sinon
    */
   public boolean addDescription(String description) {
	   
	   this.description=description;
	   
	   return true;
   }
   
   }
