package org.example;

import org.example.prefs.Prefs;
import org.flywaydb.core.Flyway;

public class DatabaseMigrationService {
    public void flywayMigration() {

        String connectionURL = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL);

        Flyway flyway = Flyway.configure()
                .dataSource(connectionURL, null, null)
                .load();

        flyway.migrate();

    }
}

