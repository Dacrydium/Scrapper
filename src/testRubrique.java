import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class testRubrique {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		
		ScrapperInterface Interface = new ScrapperInterface();
		
		SiteWeb siteAjoute = Interface.addWebsite("https://automobiles.nc","auto.nc");
		SiteWeb siteAjoute1 = Interface.addWebsite("https://annonces.nc","annonce.nc");
				
		Rubrique RubriqueAjoute = siteAjoute.addRubrique("https://automobiles.nc/rubrique_voiturettes", "Voiturette");
		Rubrique RubriqueAjoute2 = siteAjoute1.addRubrique("https://annonces.nc/rubrique_modelisme", "Sex-Shop");
		Rubrique RubriqueAjoute3 = siteAjoute1.addRubrique("https://annonces.nc/rubrique_modelisme", "Fesse");
		
		
		
	//	Interface.readFromFile();
		
	//	System.out.println(Interface.getSiteWeb());
	//	System.out.println(Interface.getSiteWeb().get(2050).getRubrique().get(1427).listeAnnonce.get(3291481));
	//	System.out.println(Interface.getSiteWeb().toString());
		
		Interface.createSimpleSearch();
		
	//	RubriqueAjoute.majAnnonce();
		
	//Interface.SaveToFile();
	
		//RubriqueAjoute.SaveToFile();
		//informatique.readFromFile();
		
		
		//System.out.print(informatique.getAnnonce());

	}

}
