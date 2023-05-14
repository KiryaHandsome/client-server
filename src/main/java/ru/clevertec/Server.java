package ru.clevertec;

import ru.clevertec.entity.implementation.RequestEntity;
import ru.clevertec.entity.implementation.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    private static final int MAX = 1000;
    private static final int MIN = 100;
    private final List<Integer> requestsData = new ArrayList<>();
    private final Lock lock = new ReentrantLock();

    public ResponseEntity processRequest(RequestEntity request) {
        ResponseEntity response = null;
        try {
            lock.lock();
            requestsData.add(request.getValue());
            response = new ResponseEntity(requestsData.size());
            lock.unlock();
            Thread.sleep(getRandomPeriod());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static int getRandomPeriod() {
        return new Random().nextInt(MAX - MIN + 1) + MIN;
    }
}
