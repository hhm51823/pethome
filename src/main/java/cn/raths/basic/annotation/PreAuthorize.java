package cn.raths.basic.annotation;

import java.lang.annotation.*;

// 表示注解在方法上、类上生效
@Target({ElementType.METHOD,ElementType.TYPE})
// 表示注解可以通过反射获取
@Retention(RetentionPolicy.RUNTIME)
// 可以被javadoc工具提取成文档，可以不加
@Documented
// 表示可以被继承
@Inherited
public @interface PreAuthorize {
    
    // 注解的第一个值：permission的sn字段值
    String sn();
    
    // 注解的第二个值：permission的name字段值
    String name();
    
}