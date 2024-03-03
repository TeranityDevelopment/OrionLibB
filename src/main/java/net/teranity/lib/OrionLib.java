package net.teranity.lib;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import net.teranity.lib.query.QueryExecute;
import net.teranity.lib.query.QueryGet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class OrionLib {

    @Getter
    @Setter
    private static OrionLib instance;

    private Map<String, Connection> connections;

    public OrionLib() {
        connections = new HashMap<>();
    }

    public OrionLib connect(String username, String password, String database, String host, int port, boolean ssl) throws SQLException {
        HikariDataSource hikariDataSource;

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.addDataSourceProperty("useSSL", ssl);

        hikariDataSource = new HikariDataSource(hikariConfig);
        getConnections().put(database, hikariDataSource.getConnection());
        return this;
    }

    public <T> QueryGet<T> select(OrionTable orionTable, String query, SelectCall callback, List<Object> objects) {
        try {
            QueryGet queryGet = new QueryGet<>(orionTable, query, callback, objects);
            queryGet.setup();

            return queryGet;
        } catch (OrionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public QueryExecute execute(OrionTable orionTable, String query, List<Object> objects) {
        try {
            QueryExecute queryExecute = new QueryExecute(orionTable, query, objects);
            queryExecute.setup();;

            return queryExecute;
        }catch (OrionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public OrionLib createTable(Connection connection, String tableName, String... objects) {
        if (tExists(tableName, connection)) return this;

        try (PreparedStatement statement = connection.prepareStatement(
                "create table if not exists " + tableName + " (" + objects + ")"
        )) {
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public OrionTable getTable(String tableName, Connection connection) {
        if (!tExists(tableName, connection)) return null;

        return new OrionTable(tableName, connection);
    }

    public Connection getConnection(String databaseName) {
        for (String string : getConnections().keySet()) {
            if (databaseName.equals(string)) {
                return getConnections().get(databaseName);
            }
        } return null;
    }

    public boolean tExists(String tableName, Connection connection) {
        try (ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (resultSet.next()) {
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } return false;
    }
}
