package IncreaseAgeStoredProcedure;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String COLUMN_NAME = "name";
    static final String COLUMN_AGE = "age";
    static final String PRINT_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();
        Scanner scanner = new Scanner(System.in);

        int inputMinionId = Integer.parseInt(scanner.nextLine());

        CallableStatement updateMinionAgeStm = connection.prepareCall("CALL usp_get_older(?)");
        updateMinionAgeStm.setInt(1,inputMinionId);
        updateMinionAgeStm.executeUpdate();

        PreparedStatement minionNameAge = connection.prepareStatement(SQL_Statements.MINION_NAME_AGE);
        minionNameAge.setInt(1,inputMinionId);
        ResultSet minionNameAgeResult = minionNameAge.executeQuery();

        minionNameAgeResult.next();
        String name = minionNameAgeResult.getString(COLUMN_NAME);
        int age = minionNameAgeResult.getInt(COLUMN_AGE);

        System.out.printf(PRINT_FORMAT,name,age);

        connection.close();
    }
}
