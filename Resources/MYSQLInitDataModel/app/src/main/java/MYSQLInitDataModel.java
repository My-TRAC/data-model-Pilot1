import MySQLHandlers.MySQLDriver;
import MySQLHandlers.SQLConnection;
import Objects.Version;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;

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


/**
 * This App initializes a MYSQL data base with the given topics of My-Trac data model
 */

public class MYSQLInitDataModel {
    
    
    //THIS PART OF THE CODE HELPS HANDLING THE ARGUMENTS REQUIRED BY THE APPLICATION
    public static class Arguments {
        
        
        @Parameter(names = {"-db", "--database"}, description = "MySQL Database name", required = true)
        public String mysqlDatabase;
        
        @Parameter(names = {"-ms", "--mysql"}, description = "MySQL server URL", required = true)
        public String mysqlURL;
        
        @Parameter(names = {"-u", "--username"}, description = "MySQL username", required = true)
        public String mysqlUser = "confluent";
        
        
        @Parameter(names = {"-pw", "--password"}, description = "MySQL password", required = true)
        public String mysqlPassword = "confluent";
        
        
        @Parameter(names={"-sr","--schema-registry"}, description="Schema registry URL", required= true)
        public String schemaRegistryURL="";
        
        
        @Parameter(names={"-tn","--topic-names"}, description="Topic names", required= true)
        public List<String> topicNames= new ArrayList<>();
        
        @Parameter(names = {"-h","--help"}, help = true)
        private boolean help;
        
    }
    
    
    private static Map<String, Schema> loadSchemasMap(String schemaRegistryURL) throws IOException {
        
        System.out.println("Getting schema from "+schemaRegistryURL);
        
        Map<String,Schema> schemas = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(schemaRegistryURL+"/subjects").openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null; ) {
                
                ObjectMapper mapper = new ObjectMapper();
                String[] subjects = mapper.readValue(line, String[].class);
                
                for (String schemaName : subjects) {
                    URL subjectURL = new URL(schemaRegistryURL + "/subjects/" + schemaName + "/versions/latest");
                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(subjectURL.openStream(), "UTF-8"));
                    for (String versionString; (versionString = reader2.readLine()) != null; ) {
                        Version version = mapper.readValue(versionString, Version.class);
                        Schema schema = version.getAvroSchema();
                        schemas.put(schema.getName(), schema);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return schemas;
    }
    
    
    public static void main (String[] args) throws IOException, InterruptedException, SQLException {
        
        Arguments arguments = new Arguments();
        
        //Handle the program arguments
        JCommander jct = JCommander.newBuilder()
                                   .addObject(arguments)
                                   .build();
        jct.parse(args);
        if(arguments.help)
        {
            jct.usage();
            return;
        }

       
        Map<String, Schema> schemas = loadSchemasMap(arguments.schemaRegistryURL);
        
        //It handles the uploaded file
        createTables(schemas,
                     arguments.mysqlURL,
                     arguments.mysqlDatabase,
                     arguments.mysqlUser,
                     arguments.mysqlPassword,
                     arguments.topicNames
                    );
        
    }
    
    private static void createTables(Map<String,Schema> schemas,
                                     String mysqlURL,
                                     String mysqlDatabase,
                                     String mysqlUser,
                                     String mysqlPassword,
                                     List<String> topicNames) throws InterruptedException {
        
        
        //Connect with the database
        Connection connection = SQLConnection.getConnection(mysqlURL, mysqlDatabase, mysqlUser, mysqlPassword);
        System.out.println("Connection to MYSQL established");
        
        for(String topicName: topicNames) {
                MySQLDriver.createTable(connection,schemas.get(topicName));
            /*catch (Exception e)
            {
                System.out.println("\n\nTable for "+topicName+" could not be created.");
            }*/
        }
    }
    
}