package ru.clevertec;

import ru.clevertec.entity.implementation.RequestEntity;
import ru.clevertec.entity.implementation.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private final Lock lock = new ReentrantLock();
    private final List<Integer> requestsData;
    private Integer accumulator = 0;
    private final Server server;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(THREADS_NUMBER);

    private static final int THREADS_NUMBER = 10;

    public Client(List<Integer> data, Server server) {
        this.server = server;
        requestsData = data;
    }



    public Integer request() {
        List<Future<ResponseEntity>> futureResponses = new ArrayList<>();
        int size = requestsData.size();
        for(int i = 0; i < size; i++) {
            Integer requestValue = requestsData.remove(nextListIndex());
            Future<ResponseEntity> response = sendRequest(requestValue);
            futureResponses.add(response);
        }
        return computeResponses(futureResponses);
    }

    private Future<ResponseEntity> sendRequest(int requestValue) {
        RequestEntity request = new RequestEntity(requestValue);
        Callable<ResponseEntity> responseCallable = () -> server.processRequest(request);
        return threadPool.submit(responseCallable);
    }

    private Integer computeResponses(List<Future<ResponseEntity>> futureResponses) {
        futureResponses.parallelStream()
                .forEach(fr -> {
                    try {
                        Integer value = fr.get().getValue();
                        lock.lock();
                        accumulator += value;
                        lock.unlock();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );
        return accumulator;
    }

    private int nextListIndex() {
        return new Random().nextInt(requestsData.size());
    }

    public static List<Integer> generate1ToNSequence(int n) {
        List<Integer> data = new ArrayList<>(n);
        for(int i = 1; i <= n; i++) {
            data.add(i);
        }
        return data;
    }

    public static int oneToNSum(int n) {
        return (1 + n) * n / 2;
    }
}
