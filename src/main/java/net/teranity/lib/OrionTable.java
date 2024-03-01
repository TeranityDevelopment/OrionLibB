package net.teranity.lib;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;

@AllArgsConstructor
@Getter
public class OrionTable {
    private String tableName;
    private Connection connection;
}
