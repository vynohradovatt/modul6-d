package org.example.dao;
import org.example.entity.Client;
import org.example.prefs.Prefs;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ClientService {

    public Connection connection = DriverManager.getConnection(new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL));
    public PreparedStatement selectMaxIdST;
    public PreparedStatement getAllSt;

    public ClientService() throws SQLException {

        try {

            selectMaxIdST = connection.prepareStatement(
                    "SELECT max(id) AS max_id FROM client"
            );
            getAllSt = connection.prepareStatement(
                    "SELECT id, name FROM client"
            );

        } catch (SQLException e) {
          throw new SQLException();
        }
    }

    public long createClient(Client client) throws SQLException {
        long id;

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
            throw new SQLException();
            }

        return id;

    }
    public String getById(long id) throws SQLException {
        String name;

        try(PreparedStatement getByIdSt = connection.prepareStatement(
                "SELECT name FROM client WHERE id = ?"
        )) {
            getByIdSt.setLong(1, id);
            ResultSet resultSet = getByIdSt.executeQuery();
            resultSet.next();
            name = resultSet.getString(1);
        } catch (Exception e){
            throw new SQLException();
        }
         return name;
    }
    public void setName(long id, String name) throws SQLException {

        try(PreparedStatement setNameSt = connection.prepareStatement(
                "UPDATE client SET name = ? WHERE id = ?"
        ))
        {
            setNameSt.setString(1, name);
            setNameSt.setLong(2, id);
            setNameSt.executeUpdate();
        } catch (Exception e){
            throw new SQLException();
        }
    }
    public void deleteById(long id) throws SQLException {

        try(PreparedStatement deletedSt = connection.prepareStatement("DELETE FROM client WHERE id = ?"
        )) {
            deletedSt.setLong(1, id);
            deletedSt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
    public List<Client> listAll() throws SQLException {
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
            throw new SQLException();
        }
        return allClients;
    }
    public void closeConnection() throws SQLException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
}