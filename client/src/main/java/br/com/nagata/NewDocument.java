package br.com.nagata;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewDocument
{

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        try (KafkaDispatcher kafkaDispatcher = new KafkaDispatcher())
        {
            for (int i = 0; i < 100; i++)
            {
                kafkaDispatcher.send("EMAIL_TOPIC", UUID.randomUUID().toString(), "email+" + i +"+"+UUID.randomUUID().toString());
                kafkaDispatcher.send("ELASTIC_TOPIC", UUID.randomUUID().toString(), "elastic+"+ i +"+"+UUID.randomUUID().toString());
            }
        }
    }
}
