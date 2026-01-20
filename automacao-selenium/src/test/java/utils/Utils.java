package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class Utils {

    public static String tirarScreenshot(WebDriver driver, String nomeTeste) {
        // Cria a pasta de screenshots se não existir
        File diretorio = new File("target/relatorios/screenshots");
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        
        // Define o caminho onde a imagem será salva
        String caminhoArquivo = "target/relatorios/screenshots/" + nomeTeste + "_" + System.currentTimeMillis() + ".png";
        
        try {
            FileHandler.copy(source, new File(caminhoArquivo));
            return caminhoArquivo; // Retorna o caminho para anexar no relatório
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}