import java.util.Scanner;
import java.util.UUID;

public class RechercheSimple extends Recherche {

	
	public RechercheSimple(String keywords) {
		   
		   this.id = UUID.randomUUID().toString();
		   this.keywords=keywords;
	}

	public void createSimpleSearch(ScrapperInterface Interface) {
		System.out.println("|	RECHERCHE SIMPLE	|");
		System.out.println("Liste des sites disponibles :");
		System.out.println(Interface.getSiteWeb().toString());
		Scanner myObj = new Scanner(System.in);
		System.out.println("Selectionnez un site et appuyer sur ENTER");
		
		int choixSite = myObj.nextInt();
		System.out.println("Vous avez selectionnez : "+Interface.getSiteWeb().get(choixSite).nom);
		
		
		
		
	}
}