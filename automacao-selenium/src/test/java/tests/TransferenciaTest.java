package tests;

import pages.HomePage;
import pages.LoginPage;
import pages.RegistroPage;
import pages.TransferenciaPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransferenciaTest extends BaseTest {

    @Test
    public void deveRealizarTransferenciaEntreContas() {
        RegistroPage registroPage = new RegistroPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        TransferenciaPage transferenciaPage = new TransferenciaPage(driver);

        // --- PARTE 1: CRIAR O USUÁRIO QUE VAI RECEBER (CONTA B) ---
        String emailB = "receptor" + System.currentTimeMillis() + "@gmail.com";
        registroPage.registrarUsuario(emailB, "Receptor", "senha123");
        String contaB_Completa = registroPage.obterMensagemSucesso(); // "A conta 123-4 foi criada..."
        
        // Extrair o número "123-4" do texto
        String numeroContaB = contaB_Completa.replaceAll("[^0-9-]", "");
        String[] partesConta = numeroContaB.split("-"); // Divide em ["123", "4"]
        String contaSemDigito = partesConta[0];
        String digito = partesConta[1];

        registroPage.fecharModal();
        
        // O sistema loga automaticamente após registro? Não no BugBank, precisamos limpar
        // ou garantir que estamos na tela de login. Como acabamos de registrar, estamos na tela de login.
        // Se o Bugbank fizesse auto-login, teríamos que chamar homePage.fazerLogout();
        
        // --- PARTE 2: CRIAR O USUÁRIO QUE VAI ENVIAR (CONTA A) ---
        // Recarregamos a página para limpar os campos de registro ou garantimos que o form está limpo
        driver.navigate().refresh(); 

        String emailA = "remetente" + System.currentTimeMillis() + "@gmail.com";
        registroPage.registrarUsuario(emailA, "Remetente Rico", "senha123");
        registroPage.obterMensagemSucesso(); // Só espera aparecer
        registroPage.fecharModal();

        // --- PARTE 3: LOGAR COM CONTA A (REMETENTE) ---
        loginPage.realizarLogin(emailA, "senha123");
        
        // Valida saldo inicial (deve ser 1000)
        double saldoInicial = homePage.obterSaldoNumerico();
        Assertions.assertEquals(1000.00, saldoInicial);

        // --- PARTE 4: TRANSFERIR ---
        homePage.acessarAreaTransferencia();
        
        // Transfere 200 reais para a conta B
        transferenciaPage.realizarTransferencia(contaSemDigito, digito, "200", "Pagamento Teste");

        // Valida mensagem de sucesso
        String msgSucesso = transferenciaPage.obterMensagemSucesso();
        Assertions.assertTrue(msgSucesso.contains("sucesso"), "Falha na transferência!");
        
        transferenciaPage.fecharModalEVoltar();

        // --- PARTE 5: VALIDAR SALDO FINAL ---
        double saldoFinal = homePage.obterSaldoNumerico();
        
        System.out.println("Saldo Inicial: " + saldoInicial);
        System.out.println("Saldo Final: " + saldoFinal);

        // 1000 - 200 = 800
        Assertions.assertEquals(800.00, saldoFinal, "O saldo não foi descontado corretamente!");
    }
}