package ru.clevertec;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.entity.implementation.RequestEntity;
import ru.clevertec.entity.implementation.ResponseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServerTest {

    private Server server;
    private ExecutorService threadPool;
    @BeforeEach
    void setUp() {
        server = new Server();
        threadPool = Executors.newFixedThreadPool(10);
    }

    private List<RequestEntity> createRequests(int n) {
        List<RequestEntity> requests = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            requests.add(new RequestEntity(i));
        }
        return requests;
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 100})
    void checkProcessRequestShouldReturnRespondWithListSize(int n) {
        List<RequestEntity> requests = createRequests(n);
        List<ResponseEntity> expectedResponses = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            expectedResponses.add(new ResponseEntity(i));
        }

        List<ResponseEntity> actualResponses = requests.parallelStream()
                .map(server::processRequest)
                .sorted(Comparator.comparingInt(ResponseEntity::getValue))
                .toList();

        Assertions.assertThat(actualResponses).isEqualTo(expectedResponses);
    }
}