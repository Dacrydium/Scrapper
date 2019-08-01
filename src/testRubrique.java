import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class testRubrique {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		
		Rubrique informatique = new Rubrique("https://annonces.nc/rubrique_modelisme", "Rubrique_Informatique");
		
		informatique.majAnnonce();
	
		informatique.SaveToFile();
		//informatique.readFromFile();
		
		
		//System.out.print(informatique.getAnnonce());

	}

}
