package br.maua;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionFactory {

    // Acessar .env
    static Dotenv dotenv = Dotenv.configure().load();

    private static String user = dotenv.get("DB_USER");
    private static String pass = dotenv.get("DB_PASS");
    private static String host = dotenv.get("DB_HOST");
    private static String port = dotenv.get("DB_PORT");
    private static String database = "jornadamauadb";


    public static Connection obterConexao(){
        String url = String.format(
                "jdbc:mysql://%s:%s/%s",
                host, port, database
        );
        try{
            return DriverManager.getConnection(url, user, pass);
        }catch (SQLException e){
            System.out.println("Erro ao realizar conexão");
            e.printStackTrace();
            return null;
        }
    }
}
