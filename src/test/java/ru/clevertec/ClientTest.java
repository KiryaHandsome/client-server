package ru.clevertec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ClientTest {
    private Client client;
    private Server server;
    private int upperBound;

    @BeforeEach
    void setUp() {
        upperBound = 5;
        server = new Server();
        client = new Client(Client.generate1ToNSequence(upperBound), server);
    }

    @ParameterizedTest
    @ValueSource(ints = {123, 10, 2, 18, 8})
    void checkRequestShouldReturnExpectedResult(int n) {
        Integer expectedResult = Client.oneToNSum(n);
        client = new Client(Client.generate1ToNSequence(n), server);

        Integer actualResult = client.request();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {123, 10, 2, 18, 8})
    void checkGetRequestsAmountShouldReturn0(int n) {
        List<Integer> sourceData = Client.generate1ToNSequence(n);
        client = new Client(sourceData, server);
        int expectedAmount = 0;

        client.request();
        int actualAmount = sourceData.size();

        assertThat(actualAmount).isEqualTo(expectedAmount);
    }
}