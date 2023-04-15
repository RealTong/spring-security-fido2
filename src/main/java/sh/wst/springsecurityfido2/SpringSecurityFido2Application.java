package sh.wst.springsecurityfido2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        exclude = {
//                SecurityAutoConfiguration.class
        }
)
public class SpringSecurityFido2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityFido2Application.class, args);
    }

}
