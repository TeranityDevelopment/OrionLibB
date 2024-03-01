package net.teranity.test;

import net.teranity.lib.OrionLib;
import net.teranity.lib.OrionTable;

import java.sql.SQLException;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
         if (OrionLib.getInstance() == null) {
             OrionLib.setInstance(new OrionLib());
         }

         OrionLib orionLib = OrionLib.getInstance();
         OrionTable orionTable = orionLib.getTable("users", orionLib.getConnection("spcapi"));

         orionLib.select(orionTable, "select * from users where uuid = ?", resultSet -> {
             try {
                 if (resultSet.next()) {
                     String playerName = resultSet.getString("name");
                 }
             }catch (SQLException e) {
                 e.printStackTrace();
             }
         }, Arrays.asList("uuidhere"));
    }
}
