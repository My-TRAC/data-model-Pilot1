package Objects;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown=true)
public  class Version {

    String subject;
    int version;
    int id;
    String schema;
    
    public String getSubject() {
        return subject;
    }
    
    public int getVersion() {
        return version;
    }
    
    public int getId() {
        return id;
    }
    
    public String getSchema() {
        return schema;
    }
    
    public Schema getAvroSchema() throws IOException {
        org.apache.avro.Schema.Parser parser = new org.apache.avro.Schema.Parser();
        return parser.parse(this.schema);
    }
}
