import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import com.amazonaws.services.schemaregistry.deserializers.avro.AWSKafkaAvroDeserializer;
import com.amazonaws.services.schemaregistry.deserializers.avro.AWSAvroDeserializer;
import com.amazonaws.services.schemaregistry.utils.AvroRecordType;
import com.amazonaws.services.schemaregistry.utils.AWSSchemaRegistryConstants;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import java.util.Collections;
import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;


/*THIS IS A EXAMPLE CONSUMER FROM AWS TO BETTER SHOW HOW AWS GLUE WORKS*/
public class exampleCons {
    private static final Properties properties = new Properties();
    private static final String topic = "tests";
    public static void main(final String[] args) throws IOException {
        // WE MUST HAVE THE SAME PROPERTIES THAT OUR PRODUCER HAD AS WE NEED THE AWS GLUE SCHEMA REGISTRY
        System.setProperty("software.amazon.awssdk.http.service.impl", "software.amazon.awssdk.http.urlconnection.UrlConnectionSdkHttpService");
        //where we run this consumer and the producer
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "gsr-client");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, AWSKafkaAvroDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AWSKafkaAvroDeserializer.class.getName());
        properties.put(AWSSchemaRegistryConstants.AWS_REGION, "us-east-2");
        //the schema we auto registered in the producer
        properties.put(AWSSchemaRegistryConstants.REGISTRY_NAME, "liko-schema-registry");
        properties.put(AWSSchemaRegistryConstants.AVRO_RECORD_TYPE, AvroRecordType.GENERIC_RECORD.getName());

        //WE PASS THE PROPERTIES TO A KAFKA CONSUMER WHICH THEN CALLS ON AWS GLUE TO GET REGISTRY AND SCHEMA
        try (final KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<String, GenericRecord>(properties)) {
            consumer.subscribe(Collections.singletonList(topic));
            while (true) {
                final ConsumerRecords<String, GenericRecord> records = consumer.poll(1000);
                for (final ConsumerRecord<String, GenericRecord> record : records) {
                    final GenericRecord value = record.value();
                    System.out.println("Received message: value = " + value);
                }
			}
        } 
        catch (final SerializationException e) {
            e.printStackTrace();
        }
    }
}