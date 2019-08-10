import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ScrapperInterface {
	/**
	 * <pre>
	 *           1..1     enregistre     0..*
	 * ScrapperInterface ------------------------- SiteWeb
	 *           scrapperInterface        &gt;       siteWeb
	 * </pre>
	 */

	//Liste des Sites Web
	private HashMap<Integer,SiteWeb> listeSiteWeb;


	public HashMap<Integer,SiteWeb> getSiteWeb() {
		if (this.listeSiteWeb == null) {
			this.listeSiteWeb = new HashMap<Integer,SiteWeb>();
		}
		return this.listeSiteWeb;
	}



	/**
	 * <pre>
	 *           1..1     est associé     0..*
	 * ScrapperInterface ------------------------- Utilisateur
	 *           scrapperInterface        &lt;       utilisateur
	 * </pre>
	 */

	//Liste des Utilisateurs
	private Set<Utilisateur> utilisateur;

	public Set<Utilisateur> getUtilisateur() {
		if (this.utilisateur == null) {
			this.utilisateur = new HashSet<Utilisateur>();
		}
		return this.utilisateur;
	}

	public SiteWeb addWebsite(String url,String nom) {
		SiteWeb siteToAdd = new SiteWeb(url,nom);
		this.getSiteWeb().put(siteToAdd.id,siteToAdd);
		return siteToAdd;

	}


	public boolean SaveToFile() {

		try {
			final String Json = new Gson().toJson(listeSiteWeb);
			BufferedWriter out = new BufferedWriter( new FileWriter("Liste_site.json"));
			out.write(Json);
			out.close();
			System.out.println("Sauvegarde reussie !");
			//System.out.println(Json);
			return true;
		}catch(Exception e){ 
			System.out.print("Erreur lors de la sauvegarde des sites...");
			return false;
		}






	}


	public boolean readFromFile() {

		try {



			BufferedReader in = new BufferedReader( new FileReader("Liste_site.json"));
			String json_read = in.readLine();
			in.close();

			Type type = new TypeToken<HashMap<Integer,SiteWeb>>(){}.getType();


			listeSiteWeb.clear();
			listeSiteWeb = new Gson().fromJson(json_read, type);

			System.out.println("Lecture reussie !");
			return true;
		}catch(Exception e){ 
			System.out.println("Erreur lors de la lecture des annonces...");
			return false;
		}

	}

	public void clearConsole() {
		for(int i = 1 ; i<10; i++) {
			System.out.println();
		}
	}

	public <K,V> void printHashMap(HashMap<K, V > liste) {
		for(K i: liste.keySet()) {
			String cle = i.toString();
			String valeur = liste.get(i).toString();
			System.out.println(cle+"------"+valeur);
		}
	}


	public void menuRecherche() {

		int swValue;
		System.out.println("|   MENU RECHERCHE    |");
		System.out.println("| Options:                 |");
		System.out.println("|        1. Creer une recherche       |");
		System.out.println("|        2. Consulter mes recherches       |");
		System.out.println("|        3. retour           |");
		Scanner myObj = new Scanner(System.in);
		System.out.println("Choisissez puis appuyer sur ENTER");
		swValue = myObj.nextInt();

		// Switch construct
		switch (swValue) {
		case 1:
			System.out.println("Option 1 selected");
			break;
		case 2:
			System.out.println("Option 2 selected");
			break;
		case 3:
			System.out.println("Exit selected");
			break;
		default:
			System.out.println("Invalid selection");
			break; // This break is not really necessary
		}

	}

	public void createSimpleSearch() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println("|	RECHERCHE SIMPLE	|");
		System.out.println("Liste des sites disponibles :");
		printHashMap(getSiteWeb());
		Scanner myObj = new Scanner(System.in);
		System.out.println("Selectionnez un site et appuyer sur ENTER");
		int choixSite = 1;
		try {
			choixSite = myObj.nextInt();
			System.out.println("Vous avez selectionnez : "+this.getSiteWeb().get(choixSite).nom);
		}catch(InputMismatchException | NullPointerException e) {
			createSimpleSearch();
		}

		TimeUnit.SECONDS.sleep(1);
		clearConsole();
		System.out.println("Liste des rubriques disponibles :");
		printHashMap(getSiteWeb().get(choixSite).getRubrique());

		Scanner in = new Scanner(System.in).useDelimiter("[,\\s+]");
		System.out.println("Saisissez les rubriques dans lesquelles effectuer les recherche en les separant par des virgules pui appuyer sur ENTER");
		ArrayList<Rubrique> newListe = new ArrayList<Rubrique>();


		while (in.hasNextInt()) {
			if (in.hasNextInt())
				newListe.add(this.listeSiteWeb.get(choixSite).getRubrique().get(in.nextInt()));
			else 
				in.next();

		}

		Scanner scanKeywords = new Scanner(System.in);
		System.out.println("Saisissez les termes de votre recherche en les separant par des virgules puis appuyer sur ENTER");


		String scanKeywordsString = scanKeywords.nextLine();
		String[] newKeywords = scanKeywordsString.split(",");
		ArrayList<String> keywords = new ArrayList<String>();

		for(int i = 0; i<newKeywords.length;i++) {
			keywords.add(newKeywords[i]);
		}



		RechercheSimple newRecherSimple = new RechercheSimple(this.getSiteWeb().get(choixSite),newListe,keywords);
		
		




	}
	
	
	public void createAdvancedSearch() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println("|	RECHERCHE AVANCEE	|");
		System.out.println("Liste des sites disponibles :");
		printHashMap(getSiteWeb());
		Scanner myObj = new Scanner(System.in);
		System.out.println("Selectionnez un site et appuyer sur ENTER");
		int choixSite = 1;
		try {
			choixSite = myObj.nextInt();
			System.out.println("Vous avez selectionnez : "+this.getSiteWeb().get(choixSite).nom);
		}catch(InputMismatchException | NullPointerException e) {
			createSimpleSearch();
		}

		TimeUnit.SECONDS.sleep(1);
		clearConsole();
		System.out.println("Liste des rubriques disponibles :");
		printHashMap(getSiteWeb().get(choixSite).getRubrique());

		Scanner in = new Scanner(System.in).useDelimiter("[,\\s+]");
		System.out.println("Saisissez les rubriques dans lesquelles effectuer les recherche en les separant par des virgules pui appuyer sur ENTER");
		ArrayList<Rubrique> newListe = new ArrayList<Rubrique>();


		while (in.hasNextInt()) {
			if (in.hasNextInt())
				newListe.add(this.listeSiteWeb.get(choixSite).getRubrique().get(in.nextInt()));
			else 
				in.next();

		}

		Scanner scanKeywords = new Scanner(System.in);
		System.out.println("Saisissez les termes de votre recherche en les separant par des virgules puis appuyer sur ENTER");


		String scanKeywordsString = scanKeywords.nextLine();
		String[] newKeywords = scanKeywordsString.split(",");
		ArrayList<String> keywords = new ArrayList<String>();

		for(int i = 0; i<newKeywords.length;i++) {
			keywords.add(newKeywords[i]);
		}
		
		
		Scanner scanPrixMin = new Scanner(System.in);
		System.out.println("Saisissez le prix minimum puis appuyer sur ENTER");
		int prixMin = scanPrixMin.nextInt();
		
		
		Scanner scanPrixMax = new Scanner(System.in);
		System.out.println("Saisissez le prix Maximum pus appuyer sur ENTER");
		int prixMax = scanPrixMax.nextInt();
		


		RechercheAvancee newRecherAvancee = new RechercheAvancee(this.getSiteWeb().get(choixSite),newListe,keywords,prixMin,prixMax);
		
		printHashMap(newRecherAvancee.run());
		
		




	}



}
