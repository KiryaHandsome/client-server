package ru.clevertec;

import ru.clevertec.entity.implementation.RequestEntity;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        List<RequestEntity> requests = List.of(
                new RequestEntity(new BigInteger("1")),
                new RequestEntity(new BigInteger("2")),
                new RequestEntity(new BigInteger("3")),
                new RequestEntity(new BigInteger("4"))
        );

        Server server = new Server();
        Client client = new Client(requests, server);
        client.request();
        long end = System.currentTimeMillis();
        System.out.println("Execution time = " + (end - begin));
    }
}