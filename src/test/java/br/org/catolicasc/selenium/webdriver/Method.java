package br.org.catolicasc.selenium.webdriver;

import java.util.List;

import org.openqa.selenium.WebElement;

public class Method {


	public static WebElement searchFirtMail(List<WebElement> unreadmail) {

		WebElement we = null;

		for(int i=0; i < unreadmail.size(); i++){

			if( unreadmail.get(i).isDisplayed() == true ){	    	
				// now verify if you have got mail form a specific mailer (Note Un-read mails)
				// for read mails xpath loactor will change but logic will remain same
				if( unreadmail.get(0).getText().equals("me") ){
					System.out.println("Yes we have got mail form " + "me");
					we = unreadmail.get(0);
					// also you can perform more actions here 
					// like if you want to open email form the mailer

				} else {
					System.out.println("No mail form " + "me");
					break;
				}
			}

		}
		
		return we;
	}


}
