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
	private HashMap<Integer,SiteWeb> listeSiteWeb = new HashMap<Integer,SiteWeb>();


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
	private HashMap<String,Utilisateur> listeUtilisateur = new HashMap<String,Utilisateur>();

	public HashMap<String,Utilisateur> getListeUtilisateur() {
		if (this.listeUtilisateur == null) {
			this.listeUtilisateur = new HashMap<String,Utilisateur>();
		}
		return this.listeUtilisateur;
	}

	//Attribut indicant quel est l'utilisateur actuellement connecte a l'interface
	private Utilisateur UtilisateurConnecte;

	public Utilisateur getUtilisateurConnecte() {
		return UtilisateurConnecte;
	}

	public void setUtilisateurConnecte(Utilisateur user) {
		this.UtilisateurConnecte=user;
	}

	public void addWebsite(String url,String nom) throws InterruptedException {
		SiteWeb siteToAdd = new SiteWeb(url,nom);
		this.getSiteWeb().put(siteToAdd.id,siteToAdd);
		System.out.println("Site Web ajouté avec succès !");
		TimeUnit.SECONDS.sleep(1);

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


	public void menuRecherche() throws InterruptedException {

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
			int swValue2;
			System.out.println("|   CREER UNE RECHERCHE    |");
			System.out.println("| Options:                 |");
			System.out.println("|        1. Recherche Simple       |");
			System.out.println("|        2. Recherche Avancee       |");
			System.out.println("|        3. Retour           |");
			Scanner myObj2 = new Scanner(System.in);
			System.out.println("Choisissez puis appuyer sur ENTER");
			swValue2 = myObj2.nextInt();

			switch(swValue2) {
			case 1 :
				try {
					createSimpleSearch();
					System.out.println("recherche créée, retour au menu");
					TimeUnit.SECONDS.sleep(1);
					menuRecherche();
					break;
				} catch (FailingHttpStatusCodeException | InterruptedException | IOException e) {
					System.out.println("Erreur lors de la création de la recherche, retour au menu Recherche");
					TimeUnit.SECONDS.sleep(1);

					menuRecherche();
					break;
				}
			case 2:
				try {
					createAdvancedSearch();
					System.out.println("recherche créée, retour au menu");
					TimeUnit.SECONDS.sleep(1);
					menuRecherche();
				} catch (FailingHttpStatusCodeException | InterruptedException | IOException e) {

					System.out.println("Erreur lors de la création de la recherche, retour au menu Recherche");
					TimeUnit.SECONDS.sleep(1);
					menuRecherche();
					break;
				}
			case 3:
				menuPrincipal();
			}

			break;
		case 2:
			System.out.println("Option 2 selected");
			break;
		case 3:
			menuPrincipal();
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
		//Rajout de la recherche cree dans la liste des recherches de l'utilisateur
		listeUtilisateur.get(UtilisateurConnecte.getUsername()).getRecherche().put(newRecherSimple.id, newRecherSimple);






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

		
		listeUtilisateur.get(UtilisateurConnecte.getUsername()).getRecherche().put(newRecherAvancee.id, newRecherAvancee);






	}


	public void createUser(String username,String password) {
		Utilisateur newUser = new Utilisateur(username,password);
		listeUtilisateur.put(newUser.getUsername(), newUser);
		System.out.println("Utilisateur "+listeUtilisateur.get(newUser.getUsername())+ " ajouté avec Success !");

	}
	public void register() throws InterruptedException {
		String username;
		System.out.println("|   CREER UN COMMPTE    |");
		Scanner scanUsername = new Scanner(System.in);
		System.out.println("Choisissez un pseudo sur ENTER");
		username = scanUsername.nextLine();
		if(listeUtilisateur.containsKey(username)) {
			System.out.println("Pseudo deja utilisé ! Saisissez a nouveau !");
			TimeUnit.SECONDS.sleep(1);
			register();
		}
		String password;
		Scanner scanPassword = new Scanner(System.in);
		System.out.println("Choisissez un mot de passe sur ENTER");
		password  = scanPassword.nextLine();

		createUser(username,password);



	}

	public void login() throws InterruptedException {
		String username;
		System.out.println("|   SE CONNECTER    |");
		Scanner scanUsername = new Scanner(System.in);
		System.out.println("Choisissez un pseudo sur ENTER");
		username = scanUsername.nextLine();
		if(! listeUtilisateur.containsKey(username)) {
			System.out.println("L'utilisateur n'existe pas ! Reessayer..");
			TimeUnit.SECONDS.sleep(1);
			login();
		}
		String password;
		Scanner scanPassword = new Scanner(System.in);
		System.out.println("Saisissez votre mot de passe sur ENTER");
		password  = scanPassword.nextLine();
		if(! listeUtilisateur.get(username).getPassword().equals(password)) {
			System.out.println("Mot de passe incorrect ! Réessayer...");
			TimeUnit.SECONDS.sleep(1);
			login();
		}

		UtilisateurConnecte = listeUtilisateur.get(username);
		System.out.println("Bienvenue "+ UtilisateurConnecte);
		menuPrincipal();
		//Menu Accueil Principal



	}

	public void menuConnexion() throws InterruptedException {

		int swValue;
		System.out.println("|   AUTHENTIFICATION    |");
		System.out.println("| Disposez vous d'un compte ? :                 |");
		System.out.println("|        1. Oui - Me connecter      |");
		System.out.println("|        2. non - M'inscrire    |");
		System.out.println("|        3. Quitter           |");
		Scanner myObj = new Scanner(System.in);
		System.out.println("Choisissez puis appuyer sur ENTER");
		swValue = myObj.nextInt();

		switch(swValue) {

		case 1 :
			//Login
			login();
		case 2 :
			//Register
			register();
			TimeUnit.SECONDS.sleep(1);
			menuConnexion();
		case 3 :
			System.out.println("Au revoir");
			break;

		}

	}

	public void menuPrincipal() throws InterruptedException {

		int choix;
		System.out.println("|   MENU PRINCIPAL    |");
		System.out.println("| Options:                 |");
		System.out.println("|        1. Recherche       |");
		System.out.println("|        2. Administration       |");
		System.out.println("|        3. Deconnexion           |");
		Scanner scanChoix = new Scanner(System.in);
		System.out.println("Choisissez puis appuyer sur ENTER");
		choix = scanChoix.nextInt();

		switch(choix) {
		case 1 :
			menuRecherche();
			break;
		case 2 :
			menuAdmin();
			break;
		case 3 : 
			menuConnexion();
			break;
		}
	}

	public void menuAdmin() throws InterruptedException {

		int choix;
		System.out.println("|   MENU ADMINISTRATION    |");
		System.out.println("| Options:                 |");
		System.out.println("|        1. Ajouter un site       |");
		System.out.println("|        2. Ajouter une rubrique       |");
		System.out.println("|        3. retour           |");
		Scanner scanChoix = new Scanner(System.in);
		System.out.println("Choisissez puis appuyer sur ENTER");
		choix = scanChoix.nextInt();

		switch(choix) {

		case 1 :
			String nomSite;
			System.out.println("|	AJOUT SITE WEB	|");
			Scanner scanNomSite = new Scanner(System.in);
			System.out.println("Rentrez le nom du site web pui appuyer sur ENTER");
			nomSite = scanNomSite.nextLine();

			String urlSite;
			Scanner scanUrlSite = new Scanner(System.in);
			System.out.println("Rentrez l'url du site web pui appuyer sur ENTER");
			urlSite = scanUrlSite.nextLine();
			for(int site : listeSiteWeb.keySet()) {
				if((listeSiteWeb.get(site).getUrl().equals(urlSite))||(listeSiteWeb.get(site).getNom().equals(nomSite))) {
					System.out.println("Site déja inscrit dans la BDD ! retour au menu Administration");
					TimeUnit.SECONDS.sleep(1);
					menuAdmin();
				}
			}
			addWebsite(urlSite, nomSite);
			menuAdmin();


		case 2 :

			int choixSite;
			System.out.println("|	AJOUT RUBRIQUE	|");
			System.out.println("Liste des sites disponibles :");
			printHashMap(getSiteWeb());
			Scanner scanChoixSite = new Scanner(System.in);
			System.out.println("Selectionnez un site et appuyer sur ENTER");
			choixSite = scanChoixSite.nextInt();
			System.out.println("Vous avez selectionnez "+listeSiteWeb.get(choixSite).getNom());


			String nomRubrique;
			Scanner scanNomRubrique = new Scanner(System.in);
			System.out.println("Rentrez le nom dla rubrique à ajouter pui appuyer sur ENTER");
			nomRubrique = scanNomRubrique.nextLine();

			String urlRubrique;
			Scanner scanUrlRubrique = new Scanner(System.in);
			System.out.println("Rentrez l'url de la rubrique à ajouter pui appuyer sur ENTER");
			urlRubrique = scanUrlRubrique.nextLine();
			for(int site : listeSiteWeb.keySet()) {
				for(int rubrique : listeSiteWeb.get(site).getRubrique().keySet()) {
					if((listeSiteWeb.get(site).getRubrique().get(rubrique).getNom().equals(nomRubrique))||(listeSiteWeb.get(site).getRubrique().get(rubrique).getUrl().equals(urlRubrique))) {
						System.out.println("Rubrique déja inscrite pour le site ! retour au menu Administration");
						TimeUnit.SECONDS.sleep(1);
						menuAdmin();
					}
				}
				
			}
			
			listeSiteWeb.get(choixSite).addRubrique(urlRubrique, nomRubrique);
			System.out.println("Rubrique ajoutée avec succès");
			menuAdmin();
			
		case 3 :
			menuPrincipal();

	
		}
	}
}
