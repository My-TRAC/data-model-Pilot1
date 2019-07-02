package MySQLHandlers;

import org.apache.avro.Schema;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Thread.sleep;

public class MySQLDriver {

    //Create a table called following the defined schema
    public static String createTable(Connection connection, Schema schema) {
        String createTable = SQLQueryBuilder.createTable(schema);
        System.out.println("Query: "+createTable);
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
