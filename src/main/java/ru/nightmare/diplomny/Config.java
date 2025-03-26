package ru.nightmare.diplomny;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.dialect.H2Dialect;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@EnableJdbcHttpSession
public class Config {
    private static final Logger log = LoggerFactory.getLogger(Config.class);
    @Bean
    public DataSource dataSource() throws SQLException, IOException {
        String db = "./local";
        log.info("Dream Team Database will be loaded from " + db);
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        log.info("Configuring JDBC DataSource");
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:file:" + db);
        dataSourceBuilder.username("SA");
        dataSourceBuilder.password("");
        log.info("Connecting to database...");
        DataSource dataSource = dataSourceBuilder.build();
        log.info("Connected to database");
        if (Files.notExists(new File(db).toPath())&&(Files.notExists(new File(db+".mv.db").toPath())||Files.notExists(new File(db+".trace.db").toPath()))) {
            log.info("Creating database...");
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            //statement.addBatch(Common.readFileFromResources("jdbc/schema.sql"));
            statement.execute(Common.readFileFromResources("jdbc/schema.sql"));
        }

        log.info("Loaded H2 database");
        return dataSource;
        /*return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("dimplomy")
                .setScriptEncoding("UTF-8")
                .build();*/
    }
    @Bean
    public Gson gson(GsonBuilder gsonBuilder) {
        return gsonBuilder.create();
    }
    @Bean
    public GsonBuilder gsonBuilder() {
        return new GsonBuilder();
    }
}
