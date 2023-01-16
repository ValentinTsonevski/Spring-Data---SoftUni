package GetMinionNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

   private static final String GET_MINION_NAME_AGE_BY_VILLAIN_NAME = "SELECT m.`name`,m.`age` FROM `minions` AS m " +
            " JOIN `minions_villains` AS mv ON mv.`minion_id` = m.`id`\n" +
            " JOIN `villains` AS v ON mv.`villain_id` = v.`id`  WHERE v.`id` = ?;";

   private  static final String COLUMN_NAME = "name";
   private  static final String COLUMN_AGE = "age";
   private static final String NO_VILLAIN_FOUND = "No villain with ID %d exists in the database.";
   private static final String VILLAIN_NAME_BY_ID = "SELECT name FROM `villains`  WHERE `id` = ?";
   private static final String PRINT_FORMAT = "%d. %s %d%n";


    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final int villainId = Integer.parseInt(scanner.nextLine());
        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement villainStatement = connection.prepareStatement(VILLAIN_NAME_BY_ID);
        villainStatement.setInt(1,villainId);

        final ResultSet vilainSet = villainStatement.executeQuery();

        if(!vilainSet.next()){
            System.out.printf(NO_VILLAIN_FOUND,villainId);
            return;
        }

     String villainName = vilainSet.getString(COLUMN_NAME);
        System.out.println("Villain: " + villainName);

        final PreparedStatement minionsStatement = connection.prepareStatement(GET_MINION_NAME_AGE_BY_VILLAIN_NAME);
        minionsStatement.setInt(1,villainId);

        ResultSet minionSet = minionsStatement.executeQuery();

        for (int i = 1; minionSet.next(); i++) {
            String minionName = minionSet.getString(COLUMN_NAME);
            int minionAge = minionSet.getInt(COLUMN_AGE);
            System.out.printf(PRINT_FORMAT,i,minionName,minionAge);
        }
        connection.close();
    }
}