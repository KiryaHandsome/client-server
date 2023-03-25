package ru.clevertec;

public class Main {
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        Server server = new Server();
        Client client = new Client(Client.generate1ToNSequence(10), server);
        System.out.println("Result: " + client.request());
        long end = System.currentTimeMillis();
        System.out.println("Execution time = " + (end - begin));
    }
}