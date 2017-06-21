package ch.steponline.mds.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Roland on 21.06.17.
 */
@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "ch.steponline")
public class PersistenceConfig {
}
