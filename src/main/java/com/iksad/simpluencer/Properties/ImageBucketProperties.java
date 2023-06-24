package com.iksad.simpluencer.Properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bucket.image")
@NoArgsConstructor @Getter @Setter
public class ImageBucketProperties {
    private String path;
}
