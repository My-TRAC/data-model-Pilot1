package Objects;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown=true)
public  class Version {

    String subject;
    int version;
    int id;
    String schema;

    Schema schema_object;

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
    }


   public Schema getSchema() throws IOException {


        if (this.schema_object!=null) return schema_object;

       org.apache.avro.Schema.Parser parser = new org.apache.avro.Schema.Parser();

       org.apache.avro.Schema parse = parser.parse(this.schema);

       this.schema_object= Schema.getSchema (parse);


        return this.schema_object;
    }
}
