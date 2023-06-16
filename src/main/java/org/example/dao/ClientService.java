package org.example.dao;
import org.example.entity.Client;
import org.example.prefs.Prefs;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ClientService {

    public Connection connection;
    public PreparedStatement selectMaxIdST;
    public PreparedStatement getAllSt;

    public ClientService(){
        String connectionUrl = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL);

        try {
            Connection connection = DriverManager.getConnection(connectionUrl);

            selectMaxIdST = connection.prepareStatement(
                    "SELECT max(id) AS max_id FROM client"
            );
            getAllSt = connection.prepareStatement(
                    "SELECT id, name FROM client"
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long createClient(Client client)  {

        long id;
        try {
            connection = DriverManager.getConnection(new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try(PreparedStatement createClientSt = connection.prepareStatement(
                "INSERT INTO client VALUES (?, ?)"
        )) {
            createClientSt.setLong(1, client.getId());
            createClientSt.setString(2, client.getName());
            createClientSt.executeUpdate();

            try(ResultSet resultSet = selectMaxIdST.executeQuery()) {
                resultSet.next();
                id = resultSet.getLong("max_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public String getById(long id) {
        String name;
        try {
            connection = DriverManager.getConnection(new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try(PreparedStatement getByIdSt = connection.prepareStatement(
                "SELECT name FROM client WHERE id = ?"
        )) {
            getByIdSt.setLong(1, id);
            ResultSet resultSet = getByIdSt.executeQuery();
            resultSet.next();
            name = resultSet.getString(1);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return name;
    }
    public void setName(long id, String name){

        try {
            connection = DriverManager.getConnection(new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try(PreparedStatement setNameSt = connection.prepareStatement(
                "UPDATE client SET name = ? WHERE id = ?"
        ))
        {
            setNameSt.setString(1, name);
            setNameSt.setLong(2, id);
            setNameSt.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void deleteById(long id) {
        try {
            connection = DriverManager.getConnection(new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try(PreparedStatement deletedSt = connection.prepareStatement("DELETE FROM client WHERE id = ?"
        )) {
            deletedSt.setLong(1, id);
            deletedSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Client> listAll(){
        List<Client> allClients = new ArrayList<>();

        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);

                Client client = new Client();
                client.setId(id);
                client.setName(name);

                allClients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allClients;
    }
}
