package aquapedia.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Aloísio Marques Lingo Filho BV3032558
 */
public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        try {
            // Força o Java a carregar a classe do driver
            Class.forName("org.mariadb.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            // Se der ESTE erro, o JAR DEFINITIVAMENTE não está sendo lido
            System.err.println("### ERRO FATAL: O .jar do MariaDB não foi encontrado! ###");
            e.printStackTrace();
            // Você pode até lançar uma exceção aqui para parar a execução
            // throw new RuntimeException("Driver não encontrado no classpath.", e);
        }
        return DriverManager.getConnection("jdbc:mariadb://localhost:3306/AQUAPEDIA",
                "root",
                "root");
    }
}
