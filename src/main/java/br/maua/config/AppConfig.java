package br.maua.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties prop = new Properties();
    public static final String BASE_DATA = System.getProperty("user.dir") + "/data/uploads/";
    public static final String BASE_ALUNO = BASE_DATA + "aluno/";
    public static final String BASE_PROFESSOR = BASE_DATA + "professor/";


    static {
        try {
            InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("config.properties");

            prop.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo configurado");
        }
    }

    public static String get(String chave) {
        return prop.getProperty(chave);
    }
}
