package MySQLHandlers;

import FileHandlers.FileDriver;
import Objects.Schema;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.lang.Thread.getAllStackTraces;
import static java.lang.Thread.sleep;

public class MySQLDriver {

    //Create a table called following the defined schema
    public static String createTable(Connection connection, Schema schema) {
        String createTable = SQLQueryBuilder.createTable(schema);
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTable);
            return createTable;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return ("ERROR in SQL query: " + createTable);
        }
    }
}
