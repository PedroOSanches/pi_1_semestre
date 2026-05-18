module br.maua {
    // Módulos do JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    
    // Módulo para Banco de Dados (Resolve o erro do java.sql)
    requires java.sql;
    
    // Módulo para o Dotenv (Resolve o erro do io.github.cdimascio.dotenv)
    requires java.dotenv;
    requires java.desktop;
    requires java.logging;

    // Permite que o JavaFX acesse suas classes de interface
    opens br.maua to javafx.fxml;
    
    // Exporta seu pacote principal
    exports br.maua;
}