package net.teranity.test;

import net.teranity.lib.OrionLib;

import java.sql.SQLException;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
         if (OrionLib.getInstance() == null) {
             OrionLib.setInstance(new OrionLib());
         }

         OrionLib orionLib = OrionLib.getInstance();

         orionLib.select(null, "select * from users where uuid = ?", resultSet -> {
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
