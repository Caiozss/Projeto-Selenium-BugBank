Automação de Testes - BugBank
Projeto de automação de testes E2E (End-to-End) para o site de internet banking BugBank, focado em validação de fluxos críticos de negócio.

Tecnologias Utilizadas
Java 17
Selenium WebDriver 4
JUnit 5
Page Object Model (POM)
WebDriverManager
Maven
Cenários Automatizados
O projeto cobre fluxos que exigem manipulação de estado e sessão (Local Storage):

Cadastro de Usuário:
Validação de criação de conta com saldo.
Extração dinâmica do número da conta gerado (Regex).
Autenticação:
Login com credenciais recém-criadas.
Validação de acesso à Home Page.
Transferência entre Contas:
Criação de usuário "Receptor" (para obter conta destino).
Criação de usuário "Remetente" (com saldo).
Fluxo de transferência de valores.
Validação Matemática: Verificação do desconto correto no saldo final.
Como Rodar o Projeto
Pré-requisitos
Java JDK 11 ou superior.
Maven instalado (ou usar o embutido na IDE).
Executando os testes
Clone o repositório e execute via terminal ou IDE:

mvn test
