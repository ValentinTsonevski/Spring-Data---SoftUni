package PrintAllMinionNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

public class Main {
    static final String COLUMN_NAME = "name";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();


        final PreparedStatement allMinionsNames = connection.prepareStatement(SQL_Statements.MINIONS_NAMES);
        final ResultSet minionsNamesResultSet = allMinionsNames.executeQuery();

        ArrayDeque<String> minionNamesQueue = new ArrayDeque<>();

        while(minionsNamesResultSet.next()){
            String minionName = minionsNamesResultSet.getString(COLUMN_NAME);
            minionNamesQueue.add(minionName);
        }

        while (!minionNamesQueue.isEmpty()){
            System.out.println(minionNamesQueue.pollFirst());
            System.out.println(minionNamesQueue.pollLast());

        }

    connection.close();
    }

}
