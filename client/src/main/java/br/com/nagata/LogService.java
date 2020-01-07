package br.com.nagata;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import java.util.regex.Pattern;

public class LogService {
    public static void main(String[] args) {

        LogService logService = new LogService();
        KafkaService service = new KafkaService(LogService.class.getSimpleName(), Pattern.compile(".*TOPIC"), logService::parse);
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record){
        System.out.println("------------Processing new record-----------------");

        System.out.println("Key: " + record.key());
        System.out.println("Value: " + record.value());
        System.out.println("Topico: " + record.topic());
        System.out.println("Partition: " + record.partition());
        System.out.println("Offset: " + record.offset());
        System.out.println("" + record);

        System.out.println("Processed");
    }
}