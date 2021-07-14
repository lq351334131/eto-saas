package etocrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author chengrong.yang
 * @Date 2020/12/23 10:38
 */

@SpringBootApplication(scanBasePackages = {"org.etocrm","cn.hutool.extra.spring"})
@EnableJpaRepositories(basePackages = {"org.etocrm"})
@EntityScan(basePackages = {"org.etocrm"})
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
public class SaasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaasApplication.class, args);
    }
}
