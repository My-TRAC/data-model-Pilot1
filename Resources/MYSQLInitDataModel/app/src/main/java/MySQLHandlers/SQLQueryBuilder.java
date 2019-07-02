package MySQLHandlers;

import org.apache.avro.Schema;

import java.lang.reflect.Field;

public class SQLQueryBuilder {


    public static String createTable(Schema schema) {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS "+schema.getName()+"(");

        boolean first = true;


        for(Schema.Field field : schema.getFields())
        {
            if (!first) sb.append(",\n");

            String sql_sentence = buildFieldDescriptor(field);
            sb.append(sql_sentence);
            first=false;
        }
        sb.append(");");



        return sb.toString();
    }

    private static String buildFieldDescriptor(Schema.Field field) {
        String fieldName = field.name();
        Schema fieldSchema = field.schema();
        boolean isPK  = fieldName.equals("mytrac_id");
        boolean isNullable = fieldSchema.isNullable();
        
        String mysqlType = fieldSchema.getProp("cigo.mysql.datatype");
        if(mysqlType == null)
        {
            boolean isLogicalType = fieldSchema.getLogicalType() != null;
            if( isLogicalType && fieldSchema.getLogicalType().getName().equals("timestamp-millis"))
            {
               mysqlType = "TIMESTAMP";
            } else if(isLogicalType && fieldSchema.getLogicalType().getName().equals("time-millis"))
            {
                mysqlType = "TIME";
            }
            else
            {
                mysqlType = convertToMysqlType(field);
            }
        }

        String query =  fieldName+" "+mysqlType;
        if(isPK) {
            query = query + " PRIMARY KEY";
        }
        
        if(!isNullable) {
            query = query + " NOT NULL";
        }
        else
        {
            query = query + " NULL";
        }
        
        if(!fieldSchema.isNullable() && mysqlType.contains("TIME"))
        {
            if(fieldName.equals("mytrac_last_modified"))
            {
                query = query + " DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP";
            }
            else
            {
                query = query + " DEFAULT CURRENT_TIMESTAMP";
            }
        }

        return query;
    }
    
    private static String convertToMysqlType(Schema.Field field) {
        Schema schema = field.schema();
        String type = null;
        if(!schema.isNullable())
        {
            type = schema.getType().getName().toLowerCase();
        }
        else
        {
            for( Schema t : schema.getTypes())
            {
                String next = t.getType().getName().toLowerCase();
                if(next != null)
                {
                    type = next;
                }
            }
        }
        
        switch (type) {
            case "int":
                return "INT";
            case "float":
                return "FLOAT";
            case "long":
                return "BIGINT";
            case "double":
                return "DOUBLE";
            case "boolean":
                return "BIT(1)";
            case "string":
                return "TINYTEXT";
            default:
                System.out.println("[ERROR] It doesn't exist a mapping type for "+type);
        }
        return null;
    }
}
