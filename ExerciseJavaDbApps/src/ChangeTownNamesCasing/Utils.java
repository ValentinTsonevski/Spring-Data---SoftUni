package ChangeTownNamesCasing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum Utils {
    ;
    static Connection getSQLConnection() throws SQLException {

        final Properties properties = new Properties();
        properties.setProperty(ChangeTownNamesCasing.Constants.USER_KEY, ChangeTownNamesCasing.Constants.USER_VALUE);
        properties.setProperty(ChangeTownNamesCasing.Constants.PASSWORD_KEY, ChangeTownNamesCasing.Constants.PASSWORD_VALUE);

        return DriverManager.getConnection(ChangeTownNamesCasing.Constants.JDBC_URL,properties);

    }
}
