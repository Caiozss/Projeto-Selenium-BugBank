package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Elementos
    private By inputEmail = By.name("email"); // O Bugbank usa name='email' no login também
    private By inputSenha = By.name("password");
    private By btnAcessar = By.xpath("//button[contains(text(), 'Acessar')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void realizarLogin(String email, String senha) {
        // Garantir que os campos de login estão limpos e visíveis
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail)).sendKeys(email);
        driver.findElement(inputSenha).sendKeys(senha);
        driver.findElement(btnAcessar).click();
    }
}