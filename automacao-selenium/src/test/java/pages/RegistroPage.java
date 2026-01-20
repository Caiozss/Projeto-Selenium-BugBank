package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions; // Importante
import org.openqa.selenium.support.ui.WebDriverWait;     // Importante
import java.time.Duration;

public class RegistroPage {
    private WebDriver driver;
    private WebDriverWait wait; // Criamos o objeto de espera

    // Elementos (Locators)
    private By btnRegistrar = By.xpath("//button[contains(text(), 'Registrar')]");
    private By inputEmail = By.xpath("//div[@class='card__register']//input[@name='email']");
    private By inputNome = By.xpath("//div[@class='card__register']//input[@name='name']");
    private By inputSenha = By.xpath("//div[@class='card__register']//input[@name='password']");
    private By inputConfirmaSenha = By.xpath("//div[@class='card__register']//input[@name='passwordConfirmation']");
    private By toggleSaldo = By.id("toggleAddBalance"); 
    private By btnCadastrar = By.xpath("//button[contains(text(), 'Cadastrar')]");
    private By modalTexto = By.id("modalText");
    private By btnFecharModal = By.id("btnCloseModal");

    public RegistroPage(WebDriver driver) {
        this.driver = driver;
        // Inicializa o Wait com 10 segundos de paciência máxima
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void registrarUsuario(String email, String nome, String senha) {
        driver.findElement(btnRegistrar).click();
        
        // Pequena melhoria: esperar os campos estarem visíveis antes de digitar
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail)).sendKeys(email);
        driver.findElement(inputNome).sendKeys(nome);
        driver.findElement(inputSenha).sendKeys(senha);
        driver.findElement(inputConfirmaSenha).sendKeys(senha);
        
        WebElement toggle = driver.findElement(toggleSaldo);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggle);

        driver.findElement(btnCadastrar).click();
    }

    public String obterMensagemSucesso() {
        // CORREÇÃO CRÍTICA AQUI:
        // Esperamos não só o elemento existir, mas ele estar VISÍVEL na tela
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalTexto));
        
        // Opcional: Esperar que o texto não seja vazio
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(modalTexto, "")));

        return modal.getText();
    }
    
    public void fecharModal() {
        // Espera o botão estar clicável antes de clicar
        wait.until(ExpectedConditions.elementToBeClickable(btnFecharModal)).click();
    }
}