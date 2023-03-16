package ru.clevertec;

import ru.clevertec.entity.implementation.RequestEntity;
import ru.clevertec.entity.implementation.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private final Lock lock = new ReentrantLock();
    private List<RequestEntity> requests;
    private List<ResponseEntity> responds = new ArrayList<>();
    private final Server server;

    public Client(List<RequestEntity> requests, Server server) {
        this.requests = requests;
        this.server = server;
    }

    public void request() {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        List<Future<ResponseEntity>> futureResponses = new ArrayList<>();
        requests.parallelStream().forEach(req -> {
                    lock.lock();
                    futureResponses.add(
                            threadPool.submit(() -> server.respond(req))
                    );
                    lock.unlock();
                    System.out.println("Sent request: " + req.getValue());
                }
        );

        futureResponses.parallelStream().forEach(fr -> {
                    try {
                        lock.lock();
                        responds.add(fr.get());
                        lock.unlock();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );
        threadPool.shutdown();
    }
}
