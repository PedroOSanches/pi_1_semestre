package br.maua.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.maua.config.AppConfig;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionFactory {


    private static final String user = AppConfig.get("DB_USER");
    private static final String pass = AppConfig.get("DB_PASS");
    private static final String host = AppConfig.get("DB_HOST");
    private static final String port = AppConfig.get("DB_PORT");
    private static final String database = AppConfig.get("DB_NAME") != null ? AppConfig.get("DB_NAME") : "jornadamauadb";
    private static final String params = AppConfig.get("DB_PARAMS");

    public static Connection obterConexao() throws java.sql.SQLException{
        List<String> missing = new ArrayList<>();
        if (user == null || user.isEmpty()) missing.add("DB_USER");
        if (pass == null || pass.isEmpty()) missing.add("DB_PASS");
        if (host == null || host.isEmpty()) missing.add("DB_HOST");
        if (port == null || port.isEmpty()) missing.add("DB_PORT");

        if (!missing.isEmpty()) {
            throw new SQLException("Variáveis de ambiente ausentes: " + String.join(", ", missing) + ". Verifique o arquivo .env ou variáveis do ambiente.");
        }

        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
        if (params != null && !params.isEmpty()) {
            url = url + "?" + params;
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
