import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class testRubrique {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		
		ScrapperInterface Interface = new ScrapperInterface();
		


		
		
	//	Interface.readFromFile();
		
	//	System.out.println(Interface.getSiteWeb());
	//	System.out.println(Interface.getSiteWeb().get(2050).getRubrique().get(1427).listeAnnonce.get(3291481));
	//	System.out.println(Interface.getSiteWeb().toString());
		
		//RubriqueAjoute.majAnnonce();
		Interface.readFromFile();
		Interface.menuConnexion();
		
		
	//	RubriqueAjoute.majAnnonce();
		
	//Interface.SaveToFile();
	
		//RubriqueAjoute.SaveToFile();
		//informatique.readFromFile();
		
		
		//System.out.print(informatique.getAnnonce());

	}

}
