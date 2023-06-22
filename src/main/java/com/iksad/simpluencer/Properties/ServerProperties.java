package com.iksad.simpluencer.Properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server")
@NoArgsConstructor @Getter @Setter
public class ServerProperties {
    private String schema;
    private String address;
    private int port;
}
