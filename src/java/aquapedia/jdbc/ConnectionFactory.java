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
        return DriverManager.getConnection("jdbc:mariadb://localhost:3306/AQUAPEDIA",
                "root",
                "root");
    }
}
