import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
   private ArrayList<SiteWeb> listeSiteWeb;
   
   
   public ArrayList<SiteWeb> getSiteWeb() {
      if (this.listeSiteWeb == null) {
         this.listeSiteWeb = new ArrayList<SiteWeb>();
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
	   this.getSiteWeb().add(siteToAdd);
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
			System.out.print("Erreur lors de la sauvegarde des annonces...");
			return false;
		}






	}
   
	public boolean readFromFile() {

		try {



			BufferedReader in = new BufferedReader( new FileReader("Liste_site.json"));
			String json_read = in.readLine();
			in.close();

			Type type = new TypeToken<ArrayList<SiteWeb>>(){}.getType();


			listeSiteWeb.clear();
			listeSiteWeb = new Gson().fromJson(json_read, type);

			System.out.println("Lecture reussie !");
			System.out.println(listeSiteWeb);
			return true;
		}catch(Exception e){ 
			System.out.println("Erreur lors de la lecture des annonces...");
			return false;
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
	
	
	
   }
