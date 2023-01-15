package IncreaseMinionsAge;

public class SQL_Statements {
    static final String MINIONS_BY_ID = "SELECT `name`,`age` FROM `minions` WHERE `id` = ?";
    static final String UPDATE_MINION_AGE = "UPDATE `minions` SET `age` = `age` + 1 WHERE `id` = ?";
    static final String UPDATE_MINION_NAME = "UPDATE `minions` SET `name` = LOWER(`name`) WHERE `id` = ?";
    static final String PRINT_MINIONS_NAME_AGE = "SELECT `name`,`age` FROM `minions`";


}
