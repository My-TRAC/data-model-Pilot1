import MySQLHandlers.MySQLDriver;
import MySQLHandlers.SQLConnection;
import Objects.Schema;
import Objects.Version;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;


/*
 * This app creates an API REST to upload a CSV File. Then, the CSV file is processed
 * and it's content is upload into a remote MySQL Database.
 */


public class ReadDataModel {


    //THIS PART OF THE CODE HELPS HANDLING THE ARGUMENTS REQUIRED BY THE APPLICATION
    public static class Arguments {


        @Parameter(names = {"-db", "--database"}, description = "Database name", required = true)
        public String database;

        @Parameter(names = {"-ip", "--ip"}, description = "MySQL server ip", required = true)
        public String ip;

        @Parameter(names = {"-u", "--user","--username"}, description = "DB username", required = true)
        public String user = "confluent";


        @Parameter(names = {"-pw", "--password"}, description = "DB password", required = true)
        public String pw = "confluent";


        @Parameter(names={"-sr","--schemaregistry","--schema-registry"}, description="Schema registry ip", required= true)
        public String schemaregistry="";


        @Parameter(names={"-sn","--schema_names","--schema-names"}, description="Schema name", required= true)
        public List<String> schemanames= new ArrayList<>();


        @Parameter(names = "--help,-help", help = true)
        private boolean help;

    }




    static Map<String,Schema> schemas = new HashMap<>();

    private static void prepare() throws IOException {
        //Access defined schemas
        loadSchemasMap();
    }


    private static void loadSchemasMap() throws IOException {

        System.out.println("Getting schema from http://"+arguments.schemaregistry+":8081/subjects");



        try {
            URL url = new URL("http://" + arguments.schemaregistry + ":8081/subjects");


            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    //System.out.println(line);

                    ObjectMapper mapper = new ObjectMapper();
                    String[] subjects = mapper.readValue(line, String[].class);


                    for (String schema_name : subjects) {
                        //System.out.println(schema_name);
                        url = new URL("http://"+arguments.schemaregistry + ":8081/subjects/" + schema_name + "/versions/latest");
                        try (BufferedReader reader2 = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                            for (String versionString; (versionString = reader2.readLine()) != null; ) {
                                Version version = mapper.readValue(versionString, Version.class);
                                Schema schema1 = version.getSchema();
                                schemas.put(schema1.getName(), schema1);
                            }
                        }

                    }

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static    Connection connection = null;

    static Arguments arguments = new Arguments();
    public static void main (String[] args) throws IOException, InterruptedException, SQLException {

        //Handle the program arguments
        JCommander.newBuilder().addObject(arguments).build().parse(args);

        //Function to prepare the system to receive the files to be uploaded.
        prepare();

        //It handles the uploaded file
        createTables();

    }

    private static void createTables() throws InterruptedException {


        //Connect with the database
        connection = SQLConnection.getConnection(arguments.ip,arguments.database,arguments.user,arguments.pw);
        System.out.println("Connection to MYSQL established");

        for(String topicName:arguments.schemanames) {
            try {
                String result = writeFileContentToMySQL(schemas.get(topicName));
                System.out.println("\n\n"+result+";");
            }
            catch (Exception e)
            {
                System.out.println("\n\nTable for "+topicName+" could not be created.");
            }

        }
    }


    //Writes the content of the file into a table called tableName of the database
    private static String writeFileContentToMySQL(Schema schema) {
        String tableName=schema.getName();

        //Creates the table
        String createdTable = MySQLDriver.createTable(connection,schemas.get(tableName));
        return createdTable;

    }
}