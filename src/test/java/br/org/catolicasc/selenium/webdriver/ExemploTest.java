package br.org.catolicasc.selenium.webdriver;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

public class ExemploTest extends TestCase {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", ".\\src\\test\\resources\\chromedriver.exe");
		driver = new ChromeDriver();
	
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	
	
	@Test
	public void testeGmail() throws Exception {
		driver.get("http://www.gmail.com.br/");

		WebDriverWait wait = new WebDriverWait(driver, 15);
		
		/*
		 * insere e-mail para login
		 */
		WebElement campoDeTexto = driver.findElement(By.name("identifier"));
		campoDeTexto.sendKeys("seleniumteste7@gmail.com");
		driver.findElement(By.id("identifierNext")).click();
		

		//driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		
		// Aguarda ate existir na pagina o campo "password" com a classe def em @class
		/*
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.name("password"), By.xpath("//input[@class='whsOnd zHQkBf']")));
		 * 
		 */

			
		// aguarda 2s para @GET pagina insercao senha
		Thread.sleep(2000);
		
		
		// insere a senha e "next"
		WebElement campoDeSenha = driver.findElement(By.xpath("//input[@class='whsOnd zHQkBf']"));
		campoDeSenha.sendKeys("Teste12345");
		driver.findElement(By.id("passwordNext")).click();
		
		System.out.println("1. Login realizado com sucesso");
		
		
		Thread.sleep(4000);

		
		
		/*
		 * verificar se esta na caixa de entrada
		 */
		
		if ( driver.getCurrentUrl().equals("https://mail.google.com/mail/#inbox") ) {
			System.out.println("2. Estamos na caixa de entrada");
			assertTrue(true);
		} else {
			assertTrue(false);
		}
		

		
		
		
		
		
		
		// Clica em compose = criar nova mensagem
		// font: https://nithinsasalu.wordpress.com/2014/09/19/gmail-compose-mail-using-selenium-webdriver/
		driver.findElement(By.xpath("//div[@class='z0']/div")).click();
		

		Thread.sleep(2000);
		
		
		// font: https://stackoverflow.com/questions/26390634/clicking-compose-button-in-gmail-using-selenium-webdriver
		//driver.findElement(By.xpath("//div[contains(text(),'COMPOSE')]")).click();
		
		boolean achouCompose = driver.getPageSource().contains("New Message");
		assertTrue(achouCompose);
		
		// preenche os campos destinatario, assunto e conteúdo respectivamente;
		driver.findElement(By.xpath("//textarea[@class='vO']")).sendKeys("seleniumteste7@gmail.com");
		driver.findElement(By.xpath("//input[@class='aoT']")).sendKeys("Teste de Sistema");	
		
		
		
		//Thread.sleep(2000);
		
		// preenche o corpo da mensagem;
		driver.findElement(By.xpath("//div[@aria-label=\"Message Body\"]")).sendKeys("Selenium webdrive é legal!");
	
		// clica para enviar o e-mail
		driver.findElement(By.xpath("//div[text()='Send']")).click();
		
		// aguarda mensagem "Message sent" aparecer para continuar
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Message sent')]")));

		System.out.println("3. Novo e-mail enviado");



		/*
		 * Valida e-mail da caixa de entrada;
		 */		

		WebElement unreadmailvalid = driver.findElement(By.xpath("//*[@class='zA zE']"));

		boolean validEmail = false;

		if ( driver.getCurrentUrl().equals("https://mail.google.com/mail/#inbox")) {
			System.out.print("\t Estamos na caixa de entrada e, ");
			//Se está na caixa de entrada então verifica se tem email 			
			if( unreadmailvalid.isDisplayed() == true ){		    	
				//Verifica se o email contem o titulo Teste de Sistema
				if( unreadmailvalid.getText().contains("me") & unreadmailvalid.getText().contains("Teste de Sistema")){
					//Verifica se o email contem o conteudo Selenium webdrive é legal!
					if( unreadmailvalid.getText().contains("Selenium webdrive é legal!")){
						System.out.println("o novo e-mail está na caixa de entrada");
						validEmail = true;
					} else {
						System.out.println("o novo e-mail não está na caixa de entrada");
						validEmail = false;
					}
				}
			}
			assertTrue(validEmail); 
		}else {
			assertTrue(false); 
		}		    



		/*
		 * Valida e-mail da caixa de saída;
		 */		

		// access sent messages;
		driver.findElement(By.xpath("//a[@title='Sent']")).click();

		Thread.sleep(5000);
		
		System.out.println(driver.getCurrentUrl());

		WebElement validSentMail = driver.findElement(By.xpath("//*[@class='zA zE']"));

		if ( driver.getCurrentUrl().equals("https://mail.google.com/mail/#sent")) {
			// TODO: Entrando nessa condição e dando false
			System.out.println("RESULT: " +validSentMail.isDisplayed());
			//Se está na caixa de entrada então verifica se tem email 			
			if( validSentMail.isDisplayed() == true ){		    	
				//Verifica se o email contem o titulo Teste de Sistema
				if( validSentMail.getText().contains("Teste de Sistema")){
					//Verifica se o email contem o conteudo Selenium webdrive é legal!
					if( validSentMail.getText().contains("Selenium webdrive é legal!")){
						assertTrue(true); 
						System.out.println("\t E-mail está na caixa de enviados");
					} else {
						System.out.println("\t E-mail não está na caixa de enviados");
						assertTrue(false); 
					}
				}
			}
		} else {
			System.out.println("Não estamos na caixa de enviados...");
			assertTrue(false);
		}





		/*
		 * Acessa caixa de entrada e abre o 1º e-mail não lido
		 */

		// access sent messages;
		driver.findElement(By.xpath("//a[@title='Inbox']")).click();


		// @GET all emails
		List<WebElement> unreadEmails = driver.findElements(By.xpath("//*[@class='zF']"));

		for(WebElement mail : unreadEmails) {
			if( mail.isDisplayed() == true ){
				mail.click();
			}
		}

		System.out.println("5. E-mail recebido foi aberto");



		/*
		 * Responde o e-mail
		 */		

		// click button reply
		driver.findElement(By.xpath("//div[@data-tooltip='Reply']")).click();

		// preenche o corpo da mensagem;
		driver.findElement(By.xpath("//div[@aria-label=\"Message Body\"]")).sendKeys("Ok, email lido. Obrigado.");		

		// click send e-mail
		driver.findElement(By.xpath("//div[text()='Send']")).click();

		// wait notification "message sent." 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Message sent.')]")));
		//Thread.sleep(2000);

		System.out.println("6. E-mail foi respondido");



		/*
		 * Clica no ícone da lixeira p/ enviar para lixeira
		 */

		// click the trash icon
		driver.findElement(By.cssSelector(".iH > div:nth-child(1) > div:nth-child(2) > div:nth-child(3)")).click();		

		// wait message "Conversation moved to Trash."
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Conversation moved to Trash.')]")));

		System.out.println("7. E-mail enviado para lixeira");


		
		/*
		 * Acessa lixeira para verificar se o e-mail está na lixeira
		 */		
		
		// access trash using URL
		driver.get("https://mail.google.com/mail/#trash/");

		// wait list e-mails
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='aeF']")));

		Thread.sleep(4000);
		
		
		// TODO: Mesmo problema da linha 175
/*
		WebElement mailtrashvalid = driver.findElement(By.xpath("//*[@class='zA zE']"));
		
		System.out.println(mailtrashvalid.getText());

		if ( driver.getCurrentUrl().equals("https://mail.google.com/mail/#trash")) {
			//Se está na caixa de entrada então verifica se tem email 			
			if( mailtrashvalid.isDisplayed() == true ){

				if( mailtrashvalid.getText().contains("Teste de Sistema")){
					
					//Verifica se o email contem o conteudo Selenium webdrive é legal!					
					if( mailtrashvalid.getText().contains("Ok, email lido. Obrigado.")){
						assertTrue(true); 
						System.out.println("\t E-mail na lixeira");
					} else {
						System.out.println("\t E-mail não está na lixeira");
					}
				}
			}
		}


	*/	
		
		

		// @GET in list mails 
		List<WebElement> deleted = driver.findElements(By.xpath("//*[@class='zA zE']"));

		for(WebElement mail : deleted) {
			if(mail.isDisplayed() && 
					mail.getText().contains("Teste de Sistema") 
						&& mail.getText().contains("Ok, email lido") ) {
				System.out.println("\t E-mail na lixeira");
				break;
			} else {
				System.out.println("\t E-mail não está na lixeira");
			}
		}
				
		
		
		
		
		/*

		// @GET in list mails 
		List<WebElement> deleted = driver.findElements(By.xpath("//*[@class='zA zE']"));

		for(WebElement mail : deleted) {
			if(mail.isDisplayed()) {
				mail.click();
			}
		}


		
		// wait body full mail 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='nH if']")));
		
		
		// clica no botão "delete forever"		driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[1]/div[3]/div[1]/div/div[2]/div")).click();

		// TODO: Nao esta aguardando ser deletado e ja passa pra etapa de fazer logoff e quebra o teste
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Conversation deleted forever')]")));		
		
		System.out.println("\t7.1- E-mail deletado da lixeira");		

		Thread.sleep(10000);		
		
		

		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='nH if']")));		

		*/		
		
		
		
		/*
		 * Saindo da conta do Google - "signout"
		*/
		
		driver.findElement(By.xpath("//a[@class='gb_x gb_Ea gb_f']")).click();
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//a[text()='Sign out']")).click();

		System.out.println("8. Logout da conta Google concluído");
		

		Thread.sleep(3000);
		
		
		
		/*
		 * Valida se está na tela login
		 */
		
		boolean telaDeLogin = driver.findElement(By.name("password")).isDisplayed();
		assertTrue(telaDeLogin);
		
		System.out.println("Estamos na tela de login.\n\n"
				+ "\t Teste finalizado...");
		

		Thread.sleep(3000);
		
	}	
	
}