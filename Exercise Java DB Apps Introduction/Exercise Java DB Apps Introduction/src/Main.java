import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Main {

    private static final String GET_MINION_COUNT_BY_VILLAIN_NAME = "SELECT v.`name` ,COUNT(DISTINCT mv.`minion_id`) AS `count`\n" +
            " FROM `villains` AS v" +
            " JOIN `minions_villains` AS mv" +
            " ON mv.`villain_id` = v.`id`" +
            " GROUP BY v.`id`" +
            " HAVING `count` > ? " +
            " ORDER BY `count` DESC";

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MINIONS_COUNT = "count";
    private static final String PRINT_FORMAT = "%s %d";


    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement statement = connection.prepareStatement(GET_MINION_COUNT_BY_VILLAIN_NAME);

        statement.setInt(1, 15);

        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            final String villainName = resultSet.getString(COLUMN_NAME);
            final int minionsCount = resultSet.getInt(COLUMN_MINIONS_COUNT);

            System.out.printf(PRINT_FORMAT,villainName,minionsCount);


        }
        connection.close();
    }
}