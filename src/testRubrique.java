import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class testRubrique {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		
		Rubrique informatique = new Rubrique("https://annonces.nc/rubrique_informatique", "Rubrique_Informatique");
		
		informatique.majAnnonce();
		informatique.SaveToFile();
		
		
		//System.out.print(informatique.getAnnonce());

	}

}
