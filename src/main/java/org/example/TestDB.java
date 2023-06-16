package org.example;

import org.example.dao.ClientService;
import org.example.entity.Client;

import java.util.List;

public class TestDB {
    public static void main(String[] args) {
        DatabaseMigrationService databaseInitService = new DatabaseMigrationService();
        databaseInitService.flywayMigration();

        Client client1 = new Client();
        client1.setId(12);
        client1.setName("TestClient1");

        Client client2 = new Client();
        client2.setId(13);
        client2.setName("TestClient2");

        ClientService daoService = new ClientService();
        long idClient1 = daoService.createClient(client1);
        long idClient2 = daoService.createClient(client2);    // створили нового клієнта

        daoService.deleteById(idClient1);  // видалила клієнта за id

        daoService.setName(idClient2, "TestNameUpdate"); // обновила імя клієнта за його id

        String clientNameByID = daoService.getById(idClient2); // отримала імя клієнта за його id

        System.out.println("clientNameByID = " + clientNameByID);

        List<Client> clientList = daoService.listAll(); // отримуємо список усіх клієнтів
        for (int i = 1; i < clientList.size(); i++) {
            Client client = clientList.get(i);
            System.out.println(client.toString());
        }
    }
}