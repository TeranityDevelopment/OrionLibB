package net.teranity.lib.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.teranity.lib.OrionException;
import net.teranity.lib.OrionTable;
import net.teranity.lib.SelectCall;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Getter
public class QueryGet extends QuerySetup {
    private OrionTable orionTable;
    private String query;
    private SelectCall callback;
    private List<Object> objects;

    public QueryGet(String query, SelectCall callback, List<Object> objects) {
        this.query = query;
        this.callback = callback;
        this.objects = objects;
    }

    @Override
    public Class<?> setup() throws OrionException {
        try (PreparedStatement statement = orionTable.getConnection().prepareStatement(query)) {
            for (int i = 0; i < objects.size(); i++) {
                statement.setObject((i + 1), objects.get(i));
            }
            callback.call(statement.executeQuery());
        }catch (SQLException e) {
            throw new OrionException();
        }

        return this.getClass();
    }
}
