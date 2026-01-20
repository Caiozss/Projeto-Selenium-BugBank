package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pages.RegistroPage;

public class CadastroTest extends BaseTest {

    @Test
    public void deveCadastrarUsuarioComSaldo() {
        RegistroPage registroPage = new RegistroPage(driver);
        
        // Massa de dados
        String email = "qa." + System.currentTimeMillis() + "@gmail.com"; // Email único
        String nome = "QA Portfolio";
        String senha = "senha123";

        // Ação
        registroPage.registrarUsuario(email, nome, senha);
        String mensagem = registroPage.obterMensagemSucesso();

        System.out.println("Teste passou! " + mensagem);

        // Validação (Assert)
        Assertions.assertTrue(mensagem.contains("criada com sucesso"), 
            "A mensagem de sucesso não apareceu! Mensagem atual: " + mensagem);
            
        
        
        registroPage.fecharModal();
    }
}