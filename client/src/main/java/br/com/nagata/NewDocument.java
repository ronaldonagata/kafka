package br.com.nagata;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewDocument {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties());

        ProducerRecord<String, String> record = new ProducerRecord<String, String>("ELASTIC_TOPIC","123", "123");

        producer.send(record,(data, ex)-> {
            if(ex != null)
            {
                ex.printStackTrace();
                return;
            }
            System.out.println(data.topic() + ":::" + data.partition() + "/" + data.offset() + "/");
        }).get();
    }

    private static Properties properties() {
        Properties property = new Properties();

        property.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        property.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        property.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return  property;
    }
}
