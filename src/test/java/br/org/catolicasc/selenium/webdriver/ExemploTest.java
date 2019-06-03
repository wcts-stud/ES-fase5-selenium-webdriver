
package br.org.catolicasc.selenium.webdriver;


import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
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

		// aguarda 1s para @GET pagina insercao senha
		Thread.sleep(1000);
		
		
		// insere a senha e próxima página
		WebElement campoDeSenha = driver.findElement(By.xpath("//input[@class='whsOnd zHQkBf']"));
		campoDeSenha.sendKeys("Teste12345");
		driver.findElement(By.id("passwordNext")).click();
		//campoDeSenha.submit();
		
		System.out.println("1. Login realizado com sucesso");
		
		/*
		//boolean caixaEntrada = driver.getPageSource().contains("Inbox");  // 
		boolean caixaEntrada = driver.findElement(By.xpath("//a[@href='https://mail.google.com/mail/#inbox']"));
		assertTrue(caixaEntrada);
		*/

		Thread.sleep(7000);
		
		
		/*
		 * verificar se esta na caixa de entrada
		 */
		//boolean emInbox = driver.getCurrentUrl().contentEquals("https://mail.google.com/mail/#inbox");
		// verifica se a pasta Inbox é mostrado;
		boolean emInbox = driver.findElement(By.xpath("//a[@title='Inbox']")).isDisplayed();
		assertTrue(emInbox);
		
		System.out.println("2. URL atual: " +driver.getCurrentUrl() +" = " + emInbox);

		
		
		//boolean achouCatolica = driver.getPageSource().contains("https://mail.google.com/mail/#inbox");
		//assertTrue(achouCatolica);
		
		
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
		
		//driver.findElement(By.xpath("//div[@class='Ar Au'"));
		
		
		// TODO: falhando ao inserir conteudo do email
		//driver.findElement(By.xpath("//*[@id=\":w1\"]")).sendKeys("Selenium webdrive é legal!");
		
		
		//Thread.sleep(2000);
		
		// preenche o corpo da mensagem;
		driver.findElement(By.xpath("//div[@aria-label=\"Message Body\"]")).sendKeys("Selenium webdrive é legal!");
	
		// clica para enviar o e-mail
		driver.findElement(By.xpath("//div[text()='Send']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Message sent')]")));

		System.out.println("3. Novo e-mail enviado");
		
		/*
		 * //font: http://shilpamynampati.blogspot.com/2013/02/composing-email-using-selenium-webdriver.html
		driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/div[2]/div/div/div/div[2]/div/div/div/div/div")).click();
		driver.findElement(By.xpath("//iframe[@class= 'Am Al editable']")).click();
	    driver.findElement(By.xpath("//iframe[@class= 'Am Al editable']")).sendKeys("Selenium webdrive é legal!");
		*/
	    
		/*
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\":ay\"]")));
		WebElement printbody = driver.switchTo().activeElement();
		printbody.sendKeys("Selenium webdrive é legal! ");
		*/

		
		Thread.sleep(2000);
				

		// Não retornando erro
		//driver.findElement(By.xpath("//div[@class='TO nZ aiq']/div/div[@class='aio UKr6le']/span/a")).click();		
		
		// acessa pasta mensagens enviadas
		driver.findElement(By.xpath("//a[@title='Sent']")).click();
		
		System.out.println("4. Acesso a pasta de e-mails enviados");
		
		
		
		
		// Lendo e-mail enviado a mim mesmo		

		// now talking un-read email form inbox into a list
		List<WebElement> unreadmail = driver.findElements(By.xpath("//*[@class='zF']"));
		
		/*
		// real logic starts here
		for(int i=0; i < unreadmail.size(); i++){
					
		    if( unreadmail.get(i).isDisplayed() == true ){		    	
		        // now verify if you have got mail form a specific mailer (Note Un-read mails)
		        // for read mails xpath loactor will change but logic will remain same
		        if( unreadmail.get(i).getText().equals("me") ){
		            System.out.println("Yes we have got mail form " + "me");
		            // also you can perform more actions here 
		            // like if you want to open email form the mailer
		            
		            break;
		        } else {
		            System.out.println("No mail form " + "me");
		        }
		    }
		}
		*/
		
		for(WebElement mail : unreadmail) {
			if( mail.isDisplayed() == true ){
				mail.click();
			}
		}

		System.out.println("5. E-mail recebido foi aberto");
		
		
		
		
		// responde e-mail enviado;
		driver.findElement(By.xpath("//div[@data-tooltip='Reply']")).click();

		// preenche o corpo da mensagem;
		driver.findElement(By.xpath("//div[@aria-label=\"Message Body\"]")).sendKeys("Ok, email lido. Obrigado.");		
		
		// clica para enviar o e-mail
		driver.findElement(By.xpath("//div[text()='Send']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Message sent.')]")));
		Thread.sleep(2000);
		
		System.out.println("6. E-mail recebido foi respondido");

		
		/*
		 * 
		 *  5. Abrir o e-mail que voce enviou para você mesmo;
		 *  6. Responder o mesmo e-mail com a mensagem:
		 * 		"Ok, email lido. Obrigado.";
		 * 		Validar este email na caixa de entrada e enviados;
		 * 
		 *  7. Deletar este e-mail;
		 *  8. Validar o e-mail na Caixa de itens excluídos;
		 */
		
		
	
		// acessa lixeira
		driver.get("https://mail.google.com/mail/#trash/");

		

		Thread.sleep(4000);	
		
		
		
		// TODO: deletando e-mail - FALHANDO 
		
		List<WebElement> deleted = driver.findElements(By.xpath("//*[@class='zA zE']"));
		
		for(WebElement mail : deleted) {
			if(mail.isDisplayed()) {
				mail.click();
			}
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='nH if']")));
		
		// clica no botão "delete forever"		driver.findElement(By.xpath("/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[1]/div[3]/div[1]/div/div[2]/div")).click();

		System.out.println("7. E-mail respondido deletado");
		


		
		


		
		
		
		
		/*
		 * saindo da conta do Google - "signout"
		*/
		driver.findElement(By.xpath("//a[@class='gb_x gb_Ea gb_f']")).click();
		driver.findElement(By.xpath("//div[@class='gb_Ef gb_jb']//a[@class='gb_0 gb_Jf gb_Qf gb_qe gb_kb']")).click();

		System.out.println("9. Logout da conta Google concluído");
		

		Thread.sleep(3000);
		
		
		/*
		 * valida se está na tela login
		 */
		boolean telaDeLogin = driver.findElement(By.name("password")).isDisplayed();
		assertTrue(telaDeLogin);
		
		System.out.println("Estamos na tela de login.\n\n"
				+ "\t Teste finalizado...");
		

		Thread.sleep(3000);
		
	}	
	
}
