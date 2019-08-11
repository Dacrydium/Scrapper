import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
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

import org.apache.bcel.generic.INSTANCEOF;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class ScrapperInterface {

	
	/**
	 * Liste des sites Web du logiciel.
	 */
	private HashMap<Integer,SiteWeb> listeSiteWeb = new HashMap<Integer,SiteWeb>();

	public HashMap<Integer,SiteWeb> getSiteWeb() {
		if (this.listeSiteWeb == null) {
			this.listeSiteWeb = new HashMap<Integer,SiteWeb>();
		}
		return this.listeSiteWeb;
	}

	/**
	 * Liste des utilisateurs du logiciel
	 */
	private HashMap<String,Utilisateur> listeUtilisateur = new HashMap<String,Utilisateur>();

	public HashMap<String,Utilisateur> getListeUtilisateur() {
		if (this.listeUtilisateur == null) {
			this.listeUtilisateur = new HashMap<String,Utilisateur>();
		}
		return this.listeUtilisateur;
	}

	/**
	 * Attribut indicant quel est l'utilisateur actuellement connecté.
	 */
	private Utilisateur UtilisateurConnecte;

	public Utilisateur getUtilisateurConnecte() {
		return UtilisateurConnecte;
	}

	public void setUtilisateurConnecte(Utilisateur user) {
		this.UtilisateurConnecte=user;
	}
/**
 * Methode permettant d'ajouter un site web à la liste des sites.
 * @param url Url du site web
 * @param nom nom du site web
 * @throws InterruptedException 
 */
	public void addWebsite(String url,String nom) throws InterruptedException {
		SiteWeb siteToAdd = new SiteWeb(url,nom);
		this.getSiteWeb().put(siteToAdd.id,siteToAdd);
		System.out.println("Site Web ajouté avec succès !");
		TimeUnit.SECONDS.sleep(1);

	}

/**
 * Methode qui enregistre la liste d'utilisateur et la liste de site dans un fichier Json.
 * utilise la serialisation
 * @return true si la sauvegarde reussie, false sinon.
 */
	public boolean SaveToFile() {

		try {

			/**
			 * Sauvegarde de la liste  des Sites
			 */
			final String jsonSite = new Gson().toJson(listeSiteWeb);
			BufferedWriter out = new BufferedWriter( new FileWriter("Liste_site.json"));
			out.write(jsonSite);
			out.close();

			/**
			 * Sauvegarde de la liste des utilisateurs
			 */
			final String jsonUser = new Gson().toJson(listeUtilisateur);
			BufferedWriter out2 = new BufferedWriter( new FileWriter("Liste_Users.json"));
			out2.write(jsonUser);
			out2.close();
			System.out.println("Sauvegarde reussie !");
			//System.out.println(Json);
			return true;
		}catch(Exception e){ 
			System.out.print("Erreur lors de la sauvegarde de la BDD...");
			return false;
		}






	}

/**
 * Methode qui permet d'importer la liste d'utilisateur et de site web a partir d'un fichier Json
 * Utilise la deserialisation
 * @return true si l'import réussi, false sinon.
 * @throws IOException 
 */
	public boolean readFromFile() throws NullPointerException, IOException {

		try {


			//sauvegarde liste site
			BufferedReader inSite = new BufferedReader( new FileReader("Liste_site.json"));
			String json_read = inSite.readLine();
			inSite.close();

			Type type = new TypeToken<HashMap<Integer,SiteWeb>>(){}.getType();


			listeSiteWeb.clear();
			listeSiteWeb = new Gson().fromJson(json_read, type);

			//save liste user

			BufferedReader inUser = new BufferedReader( new FileReader("Liste_Users.json"));
			String json_readUser = inUser.readLine();
			inUser.close();

			Type type2 = new TypeToken<HashMap<String,Utilisateur>>(){}.getType();


			listeUtilisateur.clear();
			listeUtilisateur = new Gson().fromJson(json_readUser, type2);

			System.out.println("Import réussi !");
			return true;
		}catch(NullPointerException e){ 
			e.printStackTrace();
			System.out.println("Erreur lors de la lecture des annonces...");
			return false;
		}

	}
/**
 * nettoie la console en imprimant 100 fois une nouvelle ligne
 */
	public void clearConsole() {
		for(int i = 1 ; i<10; i++) {
			System.out.println();
		}
	}
/**
 * imprime dans la console un HashMap
 * @param <K> clé du HashMap
 * @param <V> valeur du HashMap
 * @param liste HashMap à imprimer
 */
	public <K,V> void printHashMap(HashMap<K, V > liste) {
		for(K i: liste.keySet()) {
			String cle = i.toString();
			String valeur = liste.get(i).toString();
			System.out.println(cle+"------"+valeur);

		}
	}

/**
 * Menu de recherche permettant soit de creer des recherche soit de gerer des recherche
 * @throws InterruptedException
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 */
	public void menuRecherche() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {

		try {
			int swValue;
			System.out.println("|   MENU RECHERCHE                         |");
			System.out.println("| Options:                                 |");
			System.out.println("|        1. Creer une recherche            |");
			System.out.println("|        2. Consulter mes recherches       |");
			System.out.println("|        3. retour                         |");
			Scanner myObj = new Scanner(System.in);
			System.out.println("Choisissez puis appuyer sur ENTER");
			swValue = myObj.nextInt();

			// Switch construct
			switch (swValue) {
			case 1:
				try {
					int swValue2;
					System.out.println("|   CREER UNE RECHERCHE             |");
					System.out.println("| Options:                          |");
					System.out.println("|        1. Recherche Simple        |");
					System.out.println("|        2. Recherche Avancee       |");
					System.out.println("|        3. Retour                  |");
					Scanner myObj2 = new Scanner(System.in);
					System.out.println("Choisissez puis appuyer sur ENTER");
					swValue2 = myObj2.nextInt();

					switch(swValue2) {
					case 1 :
						try {
							if(! listeSiteWeb.isEmpty()) {
								createSimpleSearch();
								System.out.println("recherche créée, retour au menu");
								TimeUnit.SECONDS.sleep(1);
								menuRecherche();
								break;
							}
							else {
								System.out.println("Il n'y a pas de site disponible, ajoutez d'abord un site via le menu Administration");
								TimeUnit.SECONDS.sleep(1);
								menuAdmin();
								break;
							}

						} catch (FailingHttpStatusCodeException | InterruptedException | IOException e) {
							System.out.println("Erreur lors de la création de la recherche, retour au menu Recherche");
							TimeUnit.SECONDS.sleep(1);

							menuRecherche();
							break;
						}

					case 2:
						try {
							if(! listeSiteWeb.isEmpty()) {
								createAdvancedSearch();
								System.out.println("recherche créée, retour au menu");
								TimeUnit.SECONDS.sleep(1);
								menuRecherche();
								break;
							}
							else {
								System.out.println("Il n'y a pas de site disponible, ajoutez d'abord un site via le menu Administration");
								TimeUnit.SECONDS.sleep(1);
								menuAdmin();
								break;
							}
						} catch (FailingHttpStatusCodeException | InterruptedException | IOException e) {

							System.out.println("Erreur lors de la création de la recherche, retour au menu Recherche");
							TimeUnit.SECONDS.sleep(1);
							menuRecherche();
							break;
						}
					case 3:
						menuPrincipal();
						break;
					}

					break;
				} catch (InputMismatchException e) {
					// TODO Auto-generated catch block
					System.out.println("Erreur de saisie");
					TimeUnit.SECONDS.sleep(1);
					menuRecherche();
					break;
				}
			case 2:
				menuGererRecherche();
				break;
			case 3:
				menuPrincipal();
				break;
			default:
				System.out.println("Invalid selection");
				break; // This break is not really necessary
			}
		} catch (InputMismatchException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur de saisie");
			TimeUnit.SECONDS.sleep(1);
			menuRecherche();
			
		}

	}
/**
 * Créer une nouvelle recherche simple et l'ajoute a la liste des recherches de l'utilisateur connecte.
 * @throws InterruptedException
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 */
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
		UtilisateurConnecte.getRechercheSimple().put(newRecherSimple.id, newRecherSimple);






	}

/**
 * Créer une nouvelle recherche avancée et l'ajoute a la liste des recherches de l'utilisateur connecté.
 * @throws InterruptedException
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 */
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


		UtilisateurConnecte.getRechercheAvancee().put(newRecherAvancee.id, newRecherAvancee);






	}

/**
 * Créer un nouvel utilisateur et l'ajoute a la liste des Utilisateurs du logiciel.	
 * @param username pseudo de l'utilisateur, clé de la HashMap<Integer,Utilisateur>.
 * @param password mot de passe de l'utilisateur.
 */
	public void createUser(String username,String password) {
		Utilisateur newUser = new Utilisateur(username,password);
		listeUtilisateur.put(newUser.getUsername(), newUser);
		System.out.println("Utilisateur "+listeUtilisateur.get(newUser.getUsername())+ " ajouté avec Success !");

	}
	/**
	 * interface permettant d'enregistrer un nouvel utilisateur
	 * @throws InterruptedException
	 */
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
/**
 * Interface permettant a l'utilisateur de se connecter au logiciel.
 * @throws InterruptedException
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 */
	public void login() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
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
/**
 * Menu de connexion reunissant le login() et le register()
 * @throws InterruptedException
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 */
	public void menuConnexion() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {

		try {
			int swValue;
			System.out.println("|   AUTHENTIFICATION                            |");
			System.out.println("| Disposez vous d'un compte ? :                 |");
			System.out.println("|        1. Oui - Me connecter                  |");
			System.out.println("|        2. non - M'inscrire                    |");
			System.out.println("|        3. Quitter                             |");
			Scanner myObj = new Scanner(System.in);
			System.out.println("Choisissez puis appuyer sur ENTER");
			swValue = myObj.nextInt();

			switch(swValue) {

			case 1 :
				//Login
				login();
				break;
			case 2 :
				//Register
				register();
				TimeUnit.SECONDS.sleep(1);
				menuConnexion();
				break;
			case 3 :
				System.out.println("Au revoir");
				SaveToFile();
				break;

			}
		} catch (InputMismatchException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur de saisie");
			TimeUnit.SECONDS.sleep(1);
			menuConnexion();
			
		}

	}
/**
 * Menu principal
 * @throws InterruptedException
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 */
	public void menuPrincipal() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {

		try {
			int choix;
			System.out.println("|   MENU PRINCIPAL               |");
			System.out.println("| Options:                       |");
			System.out.println("|        1. Recherche            |");
			System.out.println("|        2. Mon bookmark         |");
			System.out.println("|        3. Administration       |");
			System.out.println("|        4. Deconnexion          |");
			Scanner scanChoix = new Scanner(System.in);
			System.out.println("Choisissez puis appuyer sur ENTER");
			choix = scanChoix.nextInt();

			switch(choix) {
			case 1 :
				menuRecherche();
				break;
			case 3 :
				menuAdmin();
				break;
			case 4 : 
				menuConnexion();
				break;

			case 2:
				menuBookmark();
				break;
			}
		} catch (InputMismatchException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur de saisie");
			TimeUnit.SECONDS.sleep(1);
			menuPrincipal();
			
		}
	}
/**
 * menu d'administration pour ajouter un site et des rubriques
 * @throws InterruptedException
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 */
	public void menuAdmin() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {


		try {
			int choix;
			System.out.println("|   MENU ADMINISTRATION                |");
			System.out.println("| Options:                             |");
			System.out.println("|        1. Ajouter un site            |");
			System.out.println("|        2. Ajouter une rubrique       |");
			System.out.println("|        3. retour                     |");
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
				break;


			case 2 :
				if(! listeSiteWeb.isEmpty()) {
					try {
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
						break;
					} catch (InputMismatchException e) {


						System.out.println("Erreur de saisie !");
						TimeUnit.SECONDS.sleep(1);
						menuAdmin();
						break;
					}
				}
				else {
					System.out.println("Il n'y a pas de site disponible, ajoutez d'abord un site via le menu Administration");
					TimeUnit.SECONDS.sleep(1);
					menuAdmin();
					break;
				}

			case 3 :
				menuPrincipal();
				break;


			}
		} catch (InputMismatchException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur de saisie");
			TimeUnit.SECONDS.sleep(1);
			menuAdmin();
			
		}
	}
/**
 * Menu de gestion des recherches
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 * @throws InterruptedException
 */
	public void menuGererRecherche() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {

		try {
			int choix;
			System.out.println("|   MENU RECHERCHE                     |");
			System.out.println("| Options:                             |");
			System.out.println("|        1. Lancer une recherche       |");
			System.out.println("|        2. Supprimer une recherche    |");
			System.out.println("|        3. retour                     |");
			Scanner scanChoix = new Scanner(System.in);
			System.out.println("Choisissez puis appuyer sur ENTER");
			choix = scanChoix.nextInt();

			switch(choix) {

			case 1 :
				System.out.println("|   EXECUTER UNE RECHERCHE         |");
				System.out.println("| Options:                         |");
				System.out.println("|        1. Recherche Simple       |");
				System.out.println("|        2. Recherche Avancee      |");

				int choixType;
				Scanner scanChoixType = new Scanner(System.in);
				System.out.println("Choisissez un type de recherche puis appuyer sur ENTER");

				choixType = scanChoixType.nextInt();

				switch(choixType) {

				case 1 :
					if(!UtilisateurConnecte.getRechercheSimple().isEmpty()) {
						System.out.println("|   RECHERCHE SIMPLE       |");
						System.out.println("|   Liste des Recherches   |");
						printHashMap(UtilisateurConnecte.getRechercheSimple());
						int choixRech;
						Scanner scanChoixRech = new Scanner(System.in);
						System.out.println("Choisissez une recherche à exécuter puis appuyer sur ENTER");

						choixRech = scanChoixRech.nextInt();

						System.out.println("Vous avez Choisi la recherche suivante : "+UtilisateurConnecte.getRechercheSimple().get(choixRech));
						System.out.println("Recherche en cours...");
						RechercheSimple rechSimple = (UtilisateurConnecte.getRechercheSimple().get(choixRech));
						printHashMap(rechSimple.run());


						addToBookmark();


						break;
					}
					else {
						System.out.println("Aucune recherche disponible, ajoutez en une via le menu Recherches");
						TimeUnit.SECONDS.sleep(1);
						menuRecherche();
						break;
					}

				case 2 :

					if(! UtilisateurConnecte.getRechercheAvancee().isEmpty()) {

						System.out.println("|   RECHERCHE AVANCEE      |");
						System.out.println("|   Liste des Recherches   |");
						printHashMap(UtilisateurConnecte.getRechercheAvancee());
						int choixRech2;
						Scanner scanChoixRech2 = new Scanner(System.in);
						System.out.println("Choisissez une recherche à exécuter puis appuyer sur ENTER");
						choixRech2 = scanChoixRech2.nextInt();

						System.out.println("Vous avez Choisi la recherche suivante : "+UtilisateurConnecte.getRechercheAvancee().get(choixRech2));
						System.out.println("Recherche en cours...");
						RechercheAvancee rechAvancee =  (UtilisateurConnecte.getRechercheAvancee().get(choixRech2));
						printHashMap(rechAvancee.run());


						addToBookmark();

						break;
					}
					else {
						System.out.println("Aucune recherche disponible, ajoutez en une via le menu Recherches");
						TimeUnit.SECONDS.sleep(1);
						menuRecherche();
						break;
					}
				}
				break;

			case 2:

				System.out.println("|   SUPPRIMER UNE RECHERCHE        |");
				System.out.println("| Options:                         |");
				System.out.println("|        1. Recherche Simple       |");
				System.out.println("|        2. Recherche Avancee      |");


				int choixType3;
				Scanner scanChoixType3 = new Scanner(System.in);
				System.out.println("Choisissez un type de recherche puis appuyer sur ENTER");
				choixType3 = scanChoixType3.nextInt();

				switch(choixType3) {

				case 1 :
					if(!UtilisateurConnecte.getRechercheSimple().isEmpty()) {
						System.out.println("|   RECHERCHE SIMPLE       |");
						System.out.println("|   Liste des Recherches   |");
						printHashMap(UtilisateurConnecte.getRechercheSimple());
						int choixDel;
						Scanner scanChoixDel = new Scanner(System.in);
						System.out.println("Choisissez une recherche à supprimer puis appuyer sur ENTER");
						choixDel = scanChoixDel.nextInt();

						UtilisateurConnecte.getRechercheSimple().remove(choixDel);
						System.out.println("Annonce supprimée");
						TimeUnit.SECONDS.sleep(1);
						menuGererRecherche();
						break;
					}
					else {
						System.out.println("Aucune recherche disponible, ajoutez en une via le menu Recherches");
						TimeUnit.SECONDS.sleep(1);
						menuRecherche();
					}
				case 2 :
					if(!UtilisateurConnecte.getRechercheAvancee().isEmpty()) {
						System.out.println("|   RECHERCHE AVANCEE      |");
						System.out.println("|   Liste des Recherches   |");
						printHashMap(UtilisateurConnecte.getRechercheSimple());
						int choixType2;
						Scanner scanChoixType2 = new Scanner(System.in);
						System.out.println("Choisissez une recherche à supprimer puis appuyer sur ENTER");
						choixType2 = scanChoixType2.nextInt();

						UtilisateurConnecte.getRechercheAvancee().remove(choixType2);
						System.out.println("Annonce supprimée");
						TimeUnit.SECONDS.sleep(1);
						menuGererRecherche();
						break;
					}
					else {
						System.out.println("Aucune recherche disponible, ajoutez en une via le menu Recherches");
						TimeUnit.SECONDS.sleep(1);
						menuRecherche();
					}
				}
				break;
			case 3 :

				menuRecherche();
				break;

			default : 
				menuGererRecherche();
				break;

			}
		} catch (InputMismatchException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur de saisie");
			TimeUnit.SECONDS.sleep(1);
			menuGererRecherche();
			
		}
	}
/**
 * Menu de gestion du bookmark
 * @throws InterruptedException
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 */
	public void menuBookmark() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		try {
			int choix;
			System.out.println("|   MON BOOKMARK                    |");
			System.out.println("| Options:                          |");
			System.out.println("|        1. Voir mes annonces       |");
			System.out.println("|        2. Supprimer une annonce   |");
			Scanner scanChoix = new Scanner(System.in);
			System.out.println("Choisissez puis appuyer sur ENTER");
			choix = scanChoix.nextInt();

			switch(choix) {

			case 1 : 
				if(!UtilisateurConnecte.getBookmark().isEmpty()) {
					printHashMap(UtilisateurConnecte.getBookmark());

					String retour;
					Scanner scanRetour = new Scanner(System.in);
					System.out.println("retour en arrière y/n ? si n l'application va enregistrer et se fermer ");
					retour = scanRetour.nextLine();

					if(retour.contentEquals("y")) {
						menuBookmark();
					}
					else if(retour.contentEquals("n")) {
						SaveToFile();
						break;
					}
					else {
						System.out.println("erreur, retour au menu precedent !");
					}
				}
				else {
					System.out.println("Aucune annonce disponible");
					TimeUnit.SECONDS.sleep(1);
					menuPrincipal();
					break;
				}
				break;
			case 2 :
				if(! UtilisateurConnecte.getBookmark().isEmpty()) {
					System.out.println("|   SUPPRIMER  ANNONCE               |");
					System.out.println("|Liste des annnonces du bookmark :   |");
					printHashMap(UtilisateurConnecte.getBookmark());

					Scanner in = new Scanner(System.in);
					System.out.println("Saisissez les annonces a supprimer en les separant par des virgules puis appuyer sur ENTER");

					String AnnonceString = in.nextLine();
					String[] newAnnonces =AnnonceString.split(",");



					for(int i = 0; i<newAnnonces.length;i++) {
						UtilisateurConnecte.getBookmark().remove(Integer.parseInt(newAnnonces[i]));
					}

					System.out.println("Annonces supprimées");
					TimeUnit.SECONDS.sleep(1);
					menuGererRecherche();
					break;


				}
				else {
					System.out.println("Aucune annonce disponible");
					TimeUnit.SECONDS.sleep(1);
					menuPrincipal();
					break;
				}

			case 3 :
				menuPrincipal();
				break;
			}
		} catch (InputMismatchException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur de saisie");
			TimeUnit.SECONDS.sleep(1);
			menuBookmark();
			
		}


	}
/**
 * Ajoute une ou plusieur annonces dans le bookmark.
 * @throws InterruptedException
 * @throws FailingHttpStatusCodeException
 * @throws MalformedURLException
 * @throws IOException
 */
	public void addToBookmark() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException {
		System.out.println("Ajouter des annonces au bookmark y/n ?");
		String bookmark;
		Scanner scanbookmark = new Scanner(System.in);
		bookmark = scanbookmark.nextLine();

		if(bookmark.contentEquals("y")) {

			Scanner in = new Scanner(System.in);
			System.out.println("Saisissez les annonces a ajouter en les separant par des virgules puis appuyer sur ENTER");

			String AnnonceString = in.nextLine();
			String[] newAnnonces =AnnonceString.split(",");
			int nbAdd = 0;

			for(int site : listeSiteWeb.keySet()) {
				for (int rubrique : listeSiteWeb.get(site).getRubrique().keySet()) {
					for(int i = 0; i<newAnnonces.length;i++) {
						if(listeSiteWeb.get(site).getRubrique().get(rubrique).listeAnnonce.containsKey(Integer.parseInt(newAnnonces[i]))) {
							UtilisateurConnecte.getBookmark().put(Integer.parseInt(newAnnonces[i]), listeSiteWeb.get(site).getRubrique().get(rubrique).listeAnnonce.get(Integer.parseInt(newAnnonces[i])));
							nbAdd++;
						}

					}
				}
			}
			System.out.println(nbAdd+" annonces ajoutée(s) au bookmark");
			TimeUnit.SECONDS.sleep(1);
			menuRecherche();






		}
		else if(bookmark.contentEquals("n")) {
			menuRecherche();
		}
		else {
			System.out.println("erreur");
			TimeUnit.SECONDS.sleep(1);
			menuRecherche();
		}
	}
	
	
	
	public static void main(String[] args) {
		
		ScrapperInterface Interface = new ScrapperInterface();
		try {
			Interface.readFromFile();
		} catch (NullPointerException | IOException e) {
			System.out.println("Fichier de sauvegarde inexistant, probablement une premiere execution de programme ?");
		}
		try {
			Interface.menuConnexion();
		} catch (FailingHttpStatusCodeException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
		
		}
		
	}


}

