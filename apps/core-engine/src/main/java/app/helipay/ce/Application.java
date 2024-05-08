package app.helipay.ce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "app.helipay.am", "app.helipay.bo", "app.helipay.be", "app.helipay.cm", "app.helipay.cn", "app.helipay.pm", "app.helipay.se", "app.helipay.sm", "app.helipay.um"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
