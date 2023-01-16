package ChangeTownNamesCasing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static final String COLUMN_NAME = "name";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();
        List<String> townsUpdated = new ArrayList<>();

        Connection connection = Utils.getSQLConnection();

        PreparedStatement TownsInCountryStm = connection.prepareStatement(SQL_Statements.GET_TOWNS_IN_COUNTRY);
        TownsInCountryStm.setString(1,country);
        ResultSet townsInCountrySet = TownsInCountryStm.executeQuery();

        while (townsInCountrySet.next()){

            String townName = townsInCountrySet.getString(COLUMN_NAME);
            PreparedStatement TownUpperCase = connection.prepareStatement(SQL_Statements.TOWN_UPPERCASE);
            TownUpperCase.setString(1,townName);
            TownUpperCase.executeUpdate();
            townsUpdated.add(townName);

        }

        if (townsUpdated.isEmpty()){
            System.out.println(Outputs.NO_TOWNS_AFFECTED);
        }else {
            int countTownsUpdated = townsUpdated.size();
            System.out.printf(Outputs.COUNT_TOWNS_UPDATED,countTownsUpdated);
            System.out.println(townsUpdated);
        }

    }

}
