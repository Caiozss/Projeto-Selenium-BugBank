package tests;

import utils.Utils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;

import java.time.Duration;
import java.util.Optional;

public class BaseTest {
    protected WebDriver driver;
    
    // Variáveis estáticas do ExtentReports (para serem compartilhadas por todos os testes)
    private static ExtentReports extent;
    private static ExtentSparkReporter spark;
    protected ExtentTest test; // O teste atual sendo executado

    // Configuração Inicial do Relatório (Roda uma vez só antes de tudo)
    @BeforeAll
    public static void configurarRelatorio() {
        extent = new ExtentReports();
        // Onde o arquivo HTML será salvo
        spark = new ExtentSparkReporter("target/relatorios/RelatorioTestes.html");
        spark.config().setDocumentTitle("Automação BugBank");
        spark.config().setReportName("Relatório de Testes de Regressão");
        extent.attachReporter(spark);
    }

    // Finalização do Relatório (Roda uma vez só no fim de tudo)
    @AfterAll
    public static void finalizarRelatorio() {
        extent.flush(); // Escreve o arquivo HTML
    }

    @BeforeEach
    public void iniciar(TestInfo testInfo) {
        // Inicia o driver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // Corrige alguns bugs de conexão
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Cria uma entrada no relatório com o nome do teste atual
        String nomeTeste = testInfo.getDisplayName();
        test = extent.createTest(nomeTeste);
        
        driver.get("https://bugbank.netlify.app/");
    }

    @AfterEach
    public void finalizar() {
        if (driver != null) {
            driver.quit();
        }
    }

    // --- "O Espião" (Watcher) ---
    // Essa classe interna "assiste" os testes e age quando eles passam ou falham
    @RegisterExtension
    TestWatcher watcher = new TestWatcher() {
        @Override
        public void testSuccessful(ExtensionContext context) {
            test.log(Status.PASS, "Cenário executado com sucesso!");
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            test.log(Status.FAIL, "O teste falhou: " + cause.getMessage());
            
            // Tira Screenshot e anexa ao relatório
            String caminhoScreenshot = Utils.tirarScreenshot(driver, context.getDisplayName());
            try {
                // Truque para o caminho da imagem funcionar no HTML local
                test.addScreenCaptureFromPath(new File(caminhoScreenshot).getAbsolutePath());
            } catch (Exception e) {
                test.log(Status.WARNING, "Erro ao anexar screenshot.");
            }
        }
    };
}