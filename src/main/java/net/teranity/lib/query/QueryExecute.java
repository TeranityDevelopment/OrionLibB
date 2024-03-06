package net.teranity.lib.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.teranity.lib.OrionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Getter
public class QueryExecute extends QuerySetup {
    private Connection connection;
    private String query;
    private List<Object> objects;

    @Override
    public void setup() throws OrionException {
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            for (int i = 0; i < objects.size(); i++) {
                statement.setObject((i + 1), objects.get(i));
            }
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new OrionException("Cannot execute: " + query);
        }

    }
}
