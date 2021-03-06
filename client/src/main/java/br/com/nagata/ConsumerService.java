package br.com.nagata;

import org.apache.kafka.clients.consumer.ConsumerRecord;


public class ConsumerService {
    public static void main(String[] args) {

        ConsumerService consumerService = new ConsumerService();
        KafkaService service = new KafkaService(ConsumerService.class.getSimpleName(), "ELASTIC_TOPIC", consumerService::parse);
    }

    private void parse(ConsumerRecord<String, String> record){
        System.out.println("------------Processing new record-----------------");

        System.out.println("Key: " + record.key());
        System.out.println("Value: " + record.value());
        System.out.println("Partition: " + record.partition());
        System.out.println("Offset: " + record.offset());
        System.out.println("" + record);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Processed");
    }

}
