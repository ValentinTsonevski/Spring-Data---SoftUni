package AddMinion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        final String[] minionInput = scanner.nextLine().split(" ");
        final String minionName = minionInput[1];
        final int minionAge = Integer.parseInt(minionInput[2]);
        final String minionTownName = minionInput[3];

        final String villainName = scanner.nextLine().split(" ")[1];

        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement towns = connection.prepareStatement(SQL_Statements.TOWNS);
        towns.setString(1, minionTownName);
        ResultSet resultTown = towns.executeQuery();


       if(!resultTown.next()){
           PreparedStatement addTownStatement = connection.prepareStatement(SQL_Statements.ADD_TOWN);
           addTownStatement.setString(1,minionTownName);
           addTownStatement.executeUpdate();
           System.out.printf(Outputs.TOWN_ADDED,minionTownName);
       }

       PreparedStatement VillainExist = connection.prepareStatement(SQL_Statements.CHECK_VILLAIN_EXIST);
       VillainExist.setString(1,villainName);
       ResultSet villainExistResult= VillainExist.executeQuery();

       if(!villainExistResult.next()){
           PreparedStatement addVillain = connection.prepareStatement(SQL_Statements.ADD_VILLAIN);
           addVillain.setString(1,villainName);
           addVillain.executeUpdate();
           System.out.printf(Outputs.VILLAIN_ADDED,villainName);
       }

       PreparedStatement insertMinionStatement = connection.prepareStatement(SQL_Statements.INSERT_MINION);
       insertMinionStatement.setString(1,minionName);
       insertMinionStatement.setInt(2,minionAge);
       insertMinionStatement.setString(3,minionName);
       insertMinionStatement.executeUpdate();

       PreparedStatement lastMinion = connection.prepareStatement(SQL_Statements.LAST_MINION);
       ResultSet lastMinionResult = lastMinion.executeQuery();
       lastMinionResult.next();
       int lastMinionId = lastMinionResult.getInt("id");

        PreparedStatement lastVillain = connection.prepareStatement(SQL_Statements.LAST_VILLAIN);
        ResultSet lastVillainResult = lastVillain.executeQuery();
        lastVillainResult.next();
        int lastVillainID = lastVillainResult.getInt("id");

        PreparedStatement insertMinionToVillainStm = connection.prepareStatement(SQL_Statements.INSERT_MINION_TO_VILLAIN);
        insertMinionToVillainStm.setInt(1,lastMinionId);
        insertMinionToVillainStm.setInt(2,lastVillainID);
        insertMinionToVillainStm.executeUpdate();

        System.out.printf(Outputs.ADD_MINION_TO_VILLAIN,minionName,villainName);

        connection.close();
    }
}
