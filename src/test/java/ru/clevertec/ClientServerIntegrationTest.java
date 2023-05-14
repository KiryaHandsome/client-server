package ru.clevertec;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServerIntegrationTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 33, 100})
    void checkClientServerIntegration(int n) {
        Server server = new Server();
        List<Integer> requestsData = Client.generate1ToNSequence(n);
        Client client = new Client(requestsData, server);

        Integer accumulatorExpected = Client.oneToNSum(n);
        Integer accumulatorActual = client.request();
        assertThat(accumulatorActual).isEqualTo(accumulatorExpected);

        Integer expectedSize = 0;
        Integer actualSize = requestsData.size();
        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 55})
    void checkClientServerIntegrationMock(int n) {
        Server server = spy(Server.class);
        List<Integer> data = spy(Client.generate1ToNSequence(n));
        Client client = new Client(data, server);

        client.request();

        verify(data, times(n)).remove(anyInt());
        verify(server, times(n)).processRequest(any());
    }
}
