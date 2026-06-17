package br.maua.config;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class AppConfig {
    private static final Properties prop = new Properties();
    private static final String APP_FOLDER_NAME = "/JornadaMaua";
    public static final String BASE_DIR = System.getenv("LOCALAPPDATA") + APP_FOLDER_NAME;
    private static final String BASE_DATA = BASE_DIR + "/data/uploads/";
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

    public static void inicializarPastas() {
        File pastaAluno = new File(BASE_ALUNO);
        File pastaProfessor = new File(BASE_PROFESSOR);

        if (!pastaAluno.exists()) {
            pastaAluno.mkdirs();
        }
        if (!pastaProfessor.exists()) {
            pastaProfessor.mkdirs();
        }
    }
}
