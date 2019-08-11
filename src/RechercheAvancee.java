import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class RechercheAvancee extends Recherche {
	/**
	 * prix minimum des annonces a rechercher
	 */
	private int prixMin;

	public void setPrixMin(int value) {
		this.prixMin = value;
	}

	public int getPrixMin() {
		return this.prixMin;
	}
/**
 * prix maximum des annonces a rechercher
 */
	private int prixMax;

	public void setPrixMax(int value) {
		this.prixMax = value;
	}

	public int getPrixMax() {
		return this.prixMax;
	}
/**
 * COnstructeur de la recherche avancée
 * @param site site sur lequel effectuer la recherche
 * @param rubrique liste de rubriques sur lesquelle effectuer la recherche
 * @param keywords mot clé a rechercher dans les annonces
 * @param prixMin prix minimum des annonces a rechercher
 * @param prixMax prix maximum des annonces a rechercher
 */
	public RechercheAvancee(SiteWeb site, ArrayList<Rubrique> rubrique,ArrayList<String> keywords,int prixMin,int prixMax) {

		this.id = (int)(Math.random() * ((4000 - 0001) + 1)) + 0001;
		this.keywords=keywords;
		this.site=site;
		this.listeRubrique=rubrique;
		this.prixMin=prixMin;
		this.prixMax=prixMax;
	}
	
	/**
	 * execute une recherche
	 * @return retourne un HashMap<Integer,Annonce>
	 */
	public HashMap<Integer,Annonce> run() {
		try {
			HashMap<Integer,Annonce> resultats = new HashMap<Integer,Annonce>();
			LocalDate today = LocalDate.now();
			for(int rubrique = 0 ; rubrique<listeRubrique.size();rubrique++) {
				if(listeRubrique.get(rubrique).getDateLastUpdate().until(today, ChronoUnit.DAYS)>1) {
					listeRubrique.get(rubrique).majAnnonce();
				}
				for(int annonce : listeRubrique.get(rubrique).listeAnnonce.keySet()) {
					for(int keyword = 0; keyword<keywords.size();keyword++) {
						if((listeRubrique.get(rubrique).listeAnnonce.get(annonce).getTitre().contains(keywords.get(keyword)) || listeRubrique.get(rubrique).listeAnnonce.get(annonce).getDescription().contains(keywords.get(keyword))) && ( prixMin <= listeRubrique.get(rubrique).listeAnnonce.get(annonce).getPrix() &&  listeRubrique.get(rubrique).listeAnnonce.get(annonce).getPrix() <= prixMax) ) {
							resultats.put(listeRubrique.get(rubrique).listeAnnonce.get(annonce).getId(), listeRubrique.get(rubrique).listeAnnonce.get(annonce));
						}
					}

				}
			}
			
			return resultats;
		}
		catch(Exception e) {
			e.printStackTrace();
			return new HashMap<Integer,Annonce>();
		}


	}
}
