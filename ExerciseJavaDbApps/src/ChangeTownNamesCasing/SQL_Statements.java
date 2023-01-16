package ChangeTownNamesCasing;

public class SQL_Statements {

    static final String GET_TOWNS_IN_COUNTRY = "SELECT * FROM towns WHERE country = ?";

    static final String TOWN_UPPERCASE = "UPDATE towns \n" +
            "SET name = UPPER(name)\n" +
            "WHERE name = ?";
}
