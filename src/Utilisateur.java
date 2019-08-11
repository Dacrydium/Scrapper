import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Utilisateur {
/**
 * pseudo unique de l'utilisateur
 */
	private String username;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
/**
 * mot de passe de l'utilisateur
 */
	private String password;

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <pre>
	 *           1..1     1..1
	 * Utilisateur ------------------------- Bookmark
	 *           utilisateur        &gt;       bookmark
	 * </pre>
	 * 
	 * Liste des Annonces favorites enregistrée par l'utilisateur
	 */
	private HashMap<Integer,Annonce> Bookmark = new HashMap<Integer,Annonce>() ;

	public HashMap<Integer,Annonce> getBookmark() {
		return this.Bookmark;
	}

	/**
	 * <pre>
	 *           1..1     fait des     0..*
	 * Utilisateur ------------------------- Recherche
	 *           utilisateur        &gt;       recherche
	 * </pre>
	 * 
	 * Liste des recherche Simple creer par l'utilisateur
	 */
	private HashMap<Integer,RechercheSimple> listeRechercheSimple = new HashMap<Integer,RechercheSimple>();

	public HashMap<Integer,RechercheSimple> getRechercheSimple() {
		if (this.listeRechercheSimple == null) {
			this.listeRechercheSimple = new HashMap<Integer,RechercheSimple>();
		}
		return this.listeRechercheSimple;
	}
	/**
	 * liste des recherches avancee cree par l'utilisateur
	 */
	private HashMap<Integer,RechercheAvancee> listeRechercheAvancee = new HashMap<Integer,RechercheAvancee>();

	public HashMap<Integer,RechercheAvancee> getRechercheAvancee() {
		if (this.listeRechercheAvancee == null) {
			this.listeRechercheAvancee = new HashMap<Integer,RechercheAvancee>();
		}
		return this.listeRechercheAvancee;
	}
/**
 * 
 * @param username pseudo de l'utilisateur
 * @param password mot de passe de l'utilisateur
 */
	public Utilisateur (String username,String password){
		this.username=username;
		this.password=password;
				
	}
	
	public String toString() {
		return username;
	}
	/**
	 * ajoute une annonce dans le bookmar de l'utilisateur
	 * @param annonceToAdd annonce à ajouter dans le bookmark de l'utilisateur
	 */
	public void addToBookmark(Annonce annonceToAdd) {
		this.Bookmark.put(annonceToAdd.getId(), annonceToAdd);
	}
	
	
}
