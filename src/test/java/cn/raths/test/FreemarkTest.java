package cn.raths.test;

import cn.raths.base.BaseTest;
import cn.raths.pet.domain.Pet;
import cn.raths.pet.service.IPetService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;

public class FreemarkTest extends BaseTest {

    // 注入freemarker核心对象
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    // 获取自定义的模板生成路径
    @Value("${pagedir}")
    private String path;

    @Autowired
    private IPetService petService;

    @Test
    public void testHtml() throws Exception {
        // 1.创建模板技术的核心配置对象
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        // 2.指定模板名称
        Template template = configuration.getTemplate("testFreemarker.ftl");
        // 3.准备数据
        Pet pet = petService.loadById(1L);

        // 4.根据数据+模板在指定位置生成静态页面
        template.process(pet, new FileWriter(new File(path + "\\" + pet.getId() + ".html")));
    }
}
