package br.com.nagata;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {
    public static void main(String[] args) {
        EmailService emailService =  new EmailService();

        try(KafkaService service = new KafkaService(EmailService.class.getSimpleName(), "EMAIL_TOPIC", emailService::parse))
        {
            service.run();
        }
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
