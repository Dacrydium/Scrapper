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
	
	public boolean register() {
		
		System.out.println("");
		return true;
	}
}
