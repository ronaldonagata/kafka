package br.com.nagata;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaService implements Closeable {

    private final KafkaConsumer<String, String> consumer;
    private ConsumerFunction parser;

    public KafkaService(String groupId, String topic, ConsumerFunction parser){
        this.consumer =  new KafkaConsumer<String, String>(properties(groupId));
        consumer.subscribe(Collections.singletonList(topic));

        this.parser = parser;
    }

    public KafkaService(String groupId, Pattern pattern, ConsumerFunction parser){
        this.consumer =  new KafkaConsumer<String, String>(properties(groupId));
        consumer.subscribe(pattern);

        this.parser = parser;
    }

    public void run() {
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            if(!records.isEmpty()){
                System.out.println("Get " + records.count() + " counts");

                for (ConsumerRecord<String, String> record : records ){
                    parser.consume(record);
                }
            }
        }
    }

    private static Properties properties(String groupId) {
        Properties prop =  new Properties();

        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        prop.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        prop.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

        return prop;
    }

    @Override
    public void close(){
        consumer.close();
    }
}
