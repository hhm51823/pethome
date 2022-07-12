package cn.raths;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@MapperScan("cn.raths.*.mapper")
// 解决Swagger页面弹框问题
@EnableSwagger2
@ServletComponentScan("cn.raths.basic.listener")
public class PetHomeApp {
    public static void main(String[] args) {
        SpringApplication.run(PetHomeApp.class, args);
    }
}
