package br.org.catolicasc.selenium.webdriver;


import java.util.List;
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

	

	public boolean checkBox(String box) {		
		boolean inBox;
		
		if ( driver.getCurrentUrl().equals("https://mail.google.com/mail/#" +box) || 
				driver.getCurrentUrl().equals("https://mail.google.com/mail/u/0/#" +box) ) {
			inBox = true;
		} else {
			inBox = false;
		}
		
		return inBox;
	}
	
	
	public boolean validMail(WebElement mail, String content, String messageReturn) {
		boolean valid = false;
				
		if( mail.isDisplayed() == true ){		    	
			//Verifica se o email contem o titulo Teste de Sistema
			if( mail.getText().contains("Teste de Sistema")){
				//Verifica se o email contem o conteudo Selenium webdrive é legal!
				if( mail.getText().contains(content)){
					valid = true; 
					System.out.println("\t E-mail está na caixa de " +messageReturn);
				} else {
					System.out.println("\t E-mail não está na caixa de " +messageReturn);
					valid = false; 
				}
			}
		}
		
		return valid;
	}
	
	
	
	
	@Test
	public void testeGmail() throws Exception {
		//Abre a site do Gmail
		driver.get("http://www.gmail.com.br/");

		WebDriverWait wait = new WebDriverWait(driver, 15);

		/*
		 * Insere e-mail para login
		 */
		WebElement textField = driver.findElement(By.name("identifier"));
		textField.sendKeys("seleniumteste7@gmail.com");
		driver.findElement(By.id("identifierNext")).click();


		// Aguarda 2 segundos para atualizar a pagina
		Thread.sleep(2000);


		// Insere a senha e "next"
		WebElement passwordField = driver.findElement(By.xpath("//input[@class='whsOnd zHQkBf']"));
		passwordField.sendKeys("Teste12345");
		driver.findElement(By.id("passwordNext")).click();

		System.out.println("1. Login realizado com sucesso");

		Thread.sleep(4000);



		/*
		 * Verificar se esta na caixa de entrada
		 */
		if( checkBox("inbox") ) {
			System.out.println("2. Existe a caixa de entrada");
			assertTrue(true);
		} else {
			assertTrue(false);
		}


		
		/*
		 * Criar um email e enviar para a própria conta
		 */

		// Clica em compose = criar nova mensagem
		driver.findElement(By.xpath("//div[@class='z0']/div")).click();
		Thread.sleep(2000);


		boolean composeFound = driver.getPageSource().contains("New Message");
		assertTrue(composeFound);

		// Preenche os campos destinatario, assunto e conteúdo respectivamente;
		driver.findElement(By.xpath("//textarea[@class='vO']")).sendKeys("seleniumteste7@gmail.com");
		driver.findElement(By.xpath("//input[@class='aoT']")).sendKeys("Teste de Sistema");	

		// Preenche o corpo da mensagem;
		driver.findElement(By.xpath("//div[@aria-label=\"Message Body\"]")).sendKeys("Selenium webdrive é legal!");

		// Clica para enviar o e-mail
		driver.findElement(By.xpath("//div[text()='Send']")).click();

		// Aguarda mensagem "Message sent" aparecer para continuar
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Message sent')]")));

		System.out.println("3. Novo e-mail enviado");



		/*
		 * Valida e-mail da caixa de entrada;
		 */	

		WebElement unreadMailValid = driver.findElement(By.xpath("//*[@class='zA zE']"));

		if ( checkBox("inbox") ) {
				//System.out.println("\t Estamos na caixa de entrada.");

				assertTrue(
						validMail(unreadMailValid, "Selenium webdrive é legal!", "entrada")
						);
		}else {
			assertTrue(false); 
		}	   



		/*
		 * Valida e-mail da caixa de Enviados;
		 */	

		// Entra nas mensagens enviadas;
		driver.findElement(By.xpath("//a[@title='Sent']")).click();

		Thread.sleep(5000);


		WebElement unreadMailValidSend = driver.findElement(By.xpath("//*[@id=\":1\"]/div/div[2]"));

		if ( checkBox("sent") ) {
			//System.out.println("\t Estamos na caixa de saída.");

			assertTrue(
					validMail(unreadMailValidSend, "Selenium webdrive é legal!", "saída")
					);
			
		} else {
			assertTrue(false); 
		}	



		/*
		 * Acessa caixa de entrada e abre o 1º e-mail não lido
		 */

		// Entra na caixa de entrada 
		driver.findElement(By.xpath("//a[@title='Inbox']")).click();


		// Verifica os email não lidos
		List<WebElement> unreadMails = driver.findElements(By.xpath("//*[@class='zF']"));

		for(WebElement mail : unreadMails) {
			if( mail.isDisplayed() == true ){
				mail.click();
			}
		}

		System.out.println("4. E-mail recebido foi aberto");



		/*
		 * Responde o e-mail
		 */	

		// Clica no botão de responder
		driver.findElement(By.xpath("//div[@data-tooltip='Reply']")).click();

		// Preenche o corpo da mensagem;
		driver.findElement(By.xpath("//div[@aria-label=\"Message Body\"]")).sendKeys("Ok, email lido. Obrigado.");	

		// Clica no enviar
		driver.findElement(By.xpath("//div[text()='Send']")).click();

		// Espera a mensagem "message sent." 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Message sent.')]")));

		System.out.println("5. E-mail foi respondido");


		
		
		
		// Entra na caixa de entrada 
		driver.findElement(By.xpath("//a[@title='Inbox']")).click();

		Thread.sleep(5000);


		/*
		 * Valida se o e-mail respondido está na caixa de entrada;
		 */	

		WebElement unreadMailvalidReply = driver.findElement(By.xpath("//*[@class='zA zE']"));

		if ( checkBox("inbox") ) {
			System.out.println("6. Validando e-mail respondido");

			assertTrue(
					validMail(unreadMailvalidReply, "Ok, email lido. Obrigado.", "entrada")
					);
			
		} else {
			assertTrue(false); 
		}	
				


		/*
		 * Valida se o e-mail respondido está na caixa de Enviados;
		 */	

		// Entra nas mensagens enviadas;
		driver.findElement(By.xpath("//a[@title='Sent']")).click();

		Thread.sleep(5000);


		WebElement unreadmailvalidSendReply = driver.findElement(By.xpath("//*[@id=\":1\"]/div/div[2]"));

		if ( checkBox("sent") ) {
			//System.out.println("\t Estamos na caixa de enviados.");		

			assertTrue(
					validMail(unreadmailvalidSendReply, "Ok, email lido. Obrigado.", "saída")
					);
			
		} else {
			assertTrue(false); 
		}
		
		

		
		// Entra na caixa de entrada 
		driver.findElement(By.xpath("//a[@title='Inbox']")).click();

		Thread.sleep(5000);

		List<WebElement> unreadMailsReply = driver.findElements(By.xpath("//*[@class='zF']"));

		for(WebElement mail : unreadMailsReply) {
			if( mail.isDisplayed() == true ){
				mail.click();
			}
		}

		Thread.sleep(5000);

		
		
		/*
		 * Clica no ícone da lixeira p/ enviar para lixeira
		 */
		
		// clica no ícone da lixeira do e-mail que está aberto
		driver.findElement(By.cssSelector(".iH > div:nth-child(1) > div:nth-child(2) > div:nth-child(3)")).click();	

		// Espera a mensagem "Conversation moved to Trash."
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Conversation moved to Trash.')]")));

		System.out.println("7. E-mail enviado para lixeira");
		
		Thread.sleep(10000);

		

		/*
		 * Acessa lixeira para verificar se o e-mail está na lixeira
		 */	

		// Acessa a lixeira
		driver.get("https://mail.google.com/mail/u/0/#trash");

		Thread.sleep(2000);

		

		/*
		 * Valida se o email está na lixeira 
		 */
		WebElement readMailValidTrash = driver.findElement(By.xpath("//*[@class='zA yO']"));

		Thread.sleep(2000);
		
		
		if ( checkBox("trash") ) {
			//System.out.println("\t Estamos na lixeira.");
			
			assertTrue(
					validMail(readMailValidTrash, "Ok, email lido. Obrigado.", "excluídos")
					);
			
		} else {
			assertTrue(false); 
		}		



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

		boolean loginScreen = driver.findElement(By.name("password")).isDisplayed();
		assertTrue(loginScreen);

		System.out.println("9. Estamos na tela de login.\n\n"
				+ "\t Teste finalizado...");


		Thread.sleep(3000);

	}	

}