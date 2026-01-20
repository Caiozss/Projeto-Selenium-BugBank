package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransferenciaPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Elementos do formulário
    private By inputNumeroConta = By.name("accountNumber");
    private By inputDigito = By.name("digit");
    private By inputValor = By.name("transferValue");
    private By inputDescricao = By.name("description");
    private By btnTransferirAgora = By.xpath("//button[contains(text(), 'Transferir agora')]");
    
    // Modal de sucesso da transferência
    private By modalTexto = By.id("modalText");
    private By btnFecharModal = By.id("btnCloseModal");
    private By btnVoltar = By.id("btnBack");

    public TransferenciaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void realizarTransferencia(String contaDestino, String digitoDestino, String valor, String descricao) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputNumeroConta)).sendKeys(contaDestino);
        driver.findElement(inputDigito).sendKeys(digitoDestino);
        driver.findElement(inputValor).sendKeys(valor);
        driver.findElement(inputDescricao).sendKeys(descricao);
        
        driver.findElement(btnTransferirAgora).click();
    }

    public String obterMensagemSucesso() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalTexto)).getText();
    }

    public void fecharModalEVoltar() {
        wait.until(ExpectedConditions.elementToBeClickable(btnFecharModal)).click();
        wait.until(ExpectedConditions.elementToBeClickable(btnVoltar)).click();
    }
}