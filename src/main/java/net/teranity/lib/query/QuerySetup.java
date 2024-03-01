package net.teranity.lib.query;

import net.teranity.lib.OrionException;

public abstract class QuerySetup {

    public QuerySetup() {
        try {
            setup();
        }catch (OrionException e) {
            e.printStackTrace();
        }
    }

    public abstract Class<?> setup() throws OrionException;
}
