import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class RechercheSimple extends Recherche {

/**
 * Constructeur de la reche simple
 * @param site site sur lequel effectuer le recherche
 * @param rubrique liste de rubrique sur lesquels effectuer les recherches
 * @param keywords mot clés a rechercher dans les annonces
 */
	public RechercheSimple(SiteWeb site, ArrayList<Rubrique> rubrique,ArrayList<String> keywords) {

		this.id = (int)(Math.random() * ((4000 - 0001) + 1)) + 0001;
		this.keywords=keywords;
		this.site=site;
		this.listeRubrique=rubrique;




	}

/**
 * Execute la recherche
 * @return HashMap<Integer,Annonce>
 */
	public HashMap<Integer,Annonce> run(){
		try {
			HashMap<Integer,Annonce> resultats = new HashMap<Integer,Annonce>();
			LocalDate today = LocalDate.now();
			for(int rubrique = 0 ; rubrique<listeRubrique.size();rubrique++) {
				if(listeRubrique.get(rubrique).getDateLastUpdate().until(today, ChronoUnit.DAYS)>1) {
					listeRubrique.get(rubrique).majAnnonce();
				}
				for(int annonce : listeRubrique.get(rubrique).listeAnnonce.keySet()) {
					for(int keyword = 0; keyword<keywords.size();keyword++) {
						if(listeRubrique.get(rubrique).listeAnnonce.get(annonce).getTitre().contains(keywords.get(keyword))) {
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