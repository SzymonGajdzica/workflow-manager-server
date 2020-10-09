package pl.polsl.workflow.manager.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class DataSourceConfig {

    @Value(Parameters.Datasource.Local.USERNAME)
    private String localUsername;

    @Value(Parameters.Datasource.Local.PASSWORD)
    private String localPassword;

    @Value(Parameters.Datasource.Local.URL)
    private String localUrl;

    @Value(Parameters.Datasource.Remote.USERNAME)
    private String remoteUsername;

    @Value(Parameters.Datasource.Remote.PASSWORD)
    private String remotePassword;

    @Value(Parameters.Datasource.Remote.URL)
    private String remoteUrl;

    @SuppressWarnings("rawtypes")
    @Bean
    public DataSource getDataSource() throws UnknownHostException {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        if(InetAddress.getLocalHost().getHostAddress().contains("192.168")){
            dataSourceBuilder.url(localUrl);
            dataSourceBuilder.username(localUsername);
            dataSourceBuilder.password(localPassword);
        } else {
            dataSourceBuilder.url(remoteUrl);
            dataSourceBuilder.username(remoteUsername);
            dataSourceBuilder.password(remotePassword);
        }
        return dataSourceBuilder.build();
    }
}