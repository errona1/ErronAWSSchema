import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import com.amazonaws.services.schemaregistry.serializers.avro.AWSKafkaAvroSerializer;
import com.amazonaws.services.schemaregistry.serializers.avro.AWSAvroSerializer;
import com.amazonaws.services.schemaregistry.utils.AvroRecordType;
import com.amazonaws.services.schemaregistry.utils.AWSSchemaRegistryConstants;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;

/*THIS IS A EXAMPLE PRODUCER FROM AWS TO BETTER SHOW HOW AWS GLUE WORKS*/
public class exampleProd {
    private static final Properties properties = new Properties();
    private static final String topic = "test";
    public static void main(final String[] args) throws IOException {
        System.setProperty("software.amazon.awssdk.http.service.impl", "software.amazon.awssdk.http.urlconnection.UrlConnectionSdkHttpService");
        //OUR COMPUTER WHERE WE ARE RUNNING THIS PRODUCER COULD ALSO BE localhost:8081
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, AWSKafkaAvroSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AWSKafkaAvroSerializer.class.getName());
        properties.put(AWSSchemaRegistryConstants.AWS_REGION, "us-east-2");
        //Schema Registry to create
        properties.put(AWSSchemaRegistryConstants.REGISTRY_NAME, "liko-schema-registry");
        //name to give Schema
        properties.put(AWSSchemaRegistryConstants.SCHEMA_NAME, "customer");
        properties.put(AWSSchemaRegistryConstants.COMPATIBILITY_SETTING, Compatibility.FULL);
        //Auto register this schema
        properties.put(AWSSchemaRegistryConstants.SCHEMA_AUTO_REGISTRATION_SETTING, true);
        //we are responsible for ensuring our producer produces according to our schema
        Schema schema_customer = new Parser().parse(new File("Customer.avsc"));
        //pass our schema into a generic object
        GenericRecord customer = new GenericData.Record(schema_customer);

        //produce and auto register schema 
        try (KafkaProducer<String, GenericRecord> producer = new KafkaProducer<String, GenericRecord>(properties)) {
            final ProducerRecord<String, GenericRecord> record = new ProducerRecord<String, GenericRecord>(topic, customer);
            customer.put("first_name", "Ada");
            customer.put("last_name", "Lovelace");
            customer.put("full_name", "Ada Lovelace");
            producer.send(record);
            System.out.println("Sent message");
            Thread.sleep(1000L);
            //........... MORE CUSTOMERS
            producer.flush();
            System.out.println("Successfully produced X messages to a topic called " + topic);
        }   
        catch (final InterruptedException | SerializationException e) {
            e.printStackTrace();
        }
    }
}