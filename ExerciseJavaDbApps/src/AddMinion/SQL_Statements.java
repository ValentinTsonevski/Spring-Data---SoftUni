package AddMinion;

public class SQL_Statements {

     static final String TOWNS = "SELECT `name` FROM `towns` WHERE `name` = ?";
     static final String ADD_TOWN = "INSERT INTO `towns` (`name`) VALUES(?)";

     static  final String INSERT_MINION = "INSERT INTO `minions`(`name`,`age`,`town_id`)" +
             " VALUES(?,?,(SELECT `id` FROM `towns` AS t WHERE t.`name` = ?))";

     static final String CHECK_VILLAIN_EXIST = "SELECT `name` FROM `villains` WHERE `name` = ?";
     static final String ADD_VILLAIN = "INSERT INTO `villains`(`name`,`evilness_factor`) VALUES(?,'evil')";

     static final String LAST_MINION = "SELECT `id` FROM `minions` ORDER BY `id` DESC  LIMIT 1";
     static final String LAST_VILLAIN = "SELECT `id` FROM `villains` ORDER BY `id` DESC  LIMIT 1";

     static final String INSERT_MINION_TO_VILLAIN = "INSERT INTO `minions_villains`(minion_id,`villain_id`) VALUES(?,?)";


}
