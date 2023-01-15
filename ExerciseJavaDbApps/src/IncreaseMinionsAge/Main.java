package IncreaseMinionsAge;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
   private static  final String PRINT_FORMAT = "%s %d%n";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        Scanner scanner = new Scanner(System.in);

        List<Integer> minionsIds = Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).toList();


        for (int i = 0; i < minionsIds.size(); i++) {
            int currentMinionId = minionsIds.get(i);

            PreparedStatement minionsByIdStm = connection.prepareStatement(SQL_Statements.MINIONS_BY_ID);
            minionsByIdStm.setInt(1,currentMinionId);
            minionsByIdStm.executeQuery();

            PreparedStatement updateMinionAge = connection.prepareStatement(SQL_Statements.UPDATE_MINION_AGE);
            updateMinionAge.setInt(1,currentMinionId);
            updateMinionAge.executeUpdate();

            PreparedStatement updateMinionName = connection.prepareStatement(SQL_Statements.UPDATE_MINION_NAME);
            updateMinionName.setInt(1,currentMinionId);
            updateMinionName.executeUpdate();
        }

        PreparedStatement printFormat = connection.prepareStatement(SQL_Statements.PRINT_MINIONS_NAME_AGE);
        ResultSet printFormatResult = printFormat.executeQuery();

        while(printFormatResult.next()){
            String minionName = printFormatResult.getString("name");
            int minionAge = printFormatResult.getInt("age");
            System.out.printf(PRINT_FORMAT,minionName,minionAge);
        }

        connection.close();
    }

}
