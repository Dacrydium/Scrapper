import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public  abstract class Recherche {
	/**
	 * <pre>
	 *           0..*     fait dans     1..*
	 * Recherche ------------------------- Rubrique
	 *           recherche        &gt;       rubrique
	 * </pre>
	 **/
	protected ArrayList<Rubrique> listeRubrique;
/**
 * ajoute une rubrique a la liste de la recherche des rubrique a explorer.
 * @param rubriqueToAdd rubrique a ajouter dans la liste
 * @return true si l'ajout a reussi, false sinon
 */
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
/** 
 * identifiant unique de la recherche
 */
	protected int id;

	public void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return this.id;
	}
/**
 * mot clé de la recherche
 */
	protected ArrayList<String> keywords;

	public void setKeywords(String value) {
		this.keywords.add(value) ;
	}

	public ArrayList<String> getKeywords() {
		return this.keywords;
	}
/**
 * site web sur lequel effectue la recherche
 */
	protected SiteWeb site;

	public void setSite(SiteWeb site) {
		this.site=site;
	}

	public SiteWeb getSite() {
		return this.site;
	}

	public Recherche() {}
/**
 * COnstructeur de l'objet recherche.
 * @param site site web de la recherche
 * @param rubrique Liste des rubrique a parcourir
 * @param keywords mot clé de la recherche
 */
	public Recherche(SiteWeb site, ArrayList<Rubrique> rubrique,ArrayList<String> keywords) {

		this.id = (int)(Math.random() * ((4000 - 0001) + 1)) + 0001;;
		this.keywords=keywords;
		this.site=site;
		this.listeRubrique=rubrique;



	}



	public String toString() {

		return this.keywords.get(0); 


	}

}
