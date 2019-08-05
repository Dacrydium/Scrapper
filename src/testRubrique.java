import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class testRubrique {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		
		ScrapperInterface Interface = new ScrapperInterface();
		
		SiteWeb siteAjoute = Interface.addWebsite("https://annonces.nc","annonce.nc");
				
		Rubrique RubriqueAjoute = siteAjoute.addRubrique("https://annonces.nc/rubrique_modelisme", "Informatique");
		
		
		int id = (int) (Math.random()*((4000-1000)+1));
		System.out.print(id);
		//Interface.readFromFile();
		
	//	System.out.println(Interface.getSiteWeb());
	//	System.out.println(Interface.getSiteWeb().get(0).getRubrique().get(0).listeAnnonce);
		
	//	RubriqueAjoute.majAnnonce();
		
	//	Interface.SaveToFile();
	
		//RubriqueAjoute.SaveToFile();
		//informatique.readFromFile();
		
		
		//System.out.print(informatique.getAnnonce());

	}

}
