package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Elemento que prova que estamos logados (ex: texto de saldo ou saudação)
    private By textoSaldo = By.id("textBalance"); 
    private By msgBemVindo = By.xpath("//div[contains(@class, 'home__Container')]//p[contains(text(), 'bem vindo')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isSaldoVisivel() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(textoSaldo));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String obterSaldo() {
        return driver.findElement(textoSaldo).getText();
    }
    // Novos Elementos para transferencia e logout
    private By btnTransferencia = By.id("btn-TRANSFERÊNCIA");
    private By btnSair = By.id("btnExit");

    public void acessarAreaTransferencia() {
        wait.until(ExpectedConditions.elementToBeClickable(btnTransferencia)).click();
    }

    public void fazerLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSair)).click();
    }
    
    // Método auxiliar para pegar o saldo limpo (sem R$ e pontos)
    public double obterSaldoNumerico() {
        String texto = obterSaldo(); // "Saldo em conta R$ 1.000,00"
        // Remove tudo que não é número ou vírgula
        String valorLimpo = texto.split("R\\$")[1].trim().replace(".", "").replace(",", ".");
        return Double.parseDouble(valorLimpo);
    }
}