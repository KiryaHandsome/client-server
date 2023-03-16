package ru.clevertec;

import ru.clevertec.entity.implementation.RequestEntity;
import ru.clevertec.entity.implementation.ResponseEntity;

import java.math.BigInteger;
import java.util.Random;

public class Server {
    public static final int RANDOM_BOUND = 2000;
    public static final BigInteger COMPUTATION_QUOTIENT = BigInteger.valueOf(111111111);

    public ResponseEntity respond(RequestEntity request) {
        ResponseEntity response = null;
        try {
            Thread.sleep(new Random().nextInt(RANDOM_BOUND));
            response = new ResponseEntity(request.getValue().multiply(COMPUTATION_QUOTIENT));
            System.out.println("Sent response: " + response.getValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
