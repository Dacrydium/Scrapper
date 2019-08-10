import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Utilisateur {

	private String username;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
	 */
	private Bookmark listeBookmark;

	public void setListeBookmark(Bookmark value) {
		this.listeBookmark = value;
	}

	public Bookmark getListeBookmark() {
		return this.listeBookmark;
	}

	/**
	 * <pre>
	 *           1..1     fait des     0..*
	 * Utilisateur ------------------------- Recherche
	 *           utilisateur        &gt;       recherche
	 * </pre>
	 */
	private HashMap<Integer,Recherche> recherche = new HashMap<Integer,Recherche>();

	public HashMap<Integer,Recherche> getRecherche() {
		if (this.recherche == null) {
			this.recherche = new HashMap<Integer,Recherche>();
		}
		return this.recherche;
	}

	public Utilisateur (String username,String password){
		this.username=username;
		this.password=password;
		this.listeBookmark=new Bookmark();
		
	}
	
	public String toString() {
		return username;
	}
	
	
}
