package tests;

import pages.HomePage;
import pages.LoginPage;
import pages.RegistroPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class e2eTest extends BaseTest {

    @Test
    public void deveRegistrarELogarComSaldo() {
        // Inicializa as páginas
        RegistroPage registroPage = new RegistroPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Massa de Dados
        String email = "qa.automacao" + System.currentTimeMillis() + "@gmail.com";
        String nome = "QA Ninja";
        String senha = "senhaForte123";

        // 1. REGISTRAR
        registroPage.registrarUsuario(email, nome, senha);
        registroPage.obterMensagemSucesso(); // Espera o modal aparecer
        registroPage.fecharModal(); // Fecha o modal para liberar a tela de login

        // 2. LOGAR (Usa o mesmo email e senha criados acima)
        loginPage.realizarLogin(email, senha);

        // 3. VALIDAR
        // Verifica se entrou na Home
        Assertions.assertTrue(homePage.isSaldoVisivel(), "O login falhou ou a Home não carregou!");
        
        // Verifica se o saldo veio correto (já que marcamos a opção de saldo no registro)
        String saldo = homePage.obterSaldo();
        System.out.println("Saldo encontrado: " + saldo);
        
        // O Bugbank cria com R$ 1.000,00 se marcar a opção
        Assertions.assertTrue(saldo.contains("1.000,00"), "O saldo não está correto!");
    }
}