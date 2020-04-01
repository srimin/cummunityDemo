package com.srimin.demo01springbootquickstart;

import com.srimin.demo01springbootquickstart.plugin.TextTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Properties;

@SpringBootTest
class Demo01SpringBootQuickstartApplicationTests {

    @Test
    void contextLoads() throws IOException {
        TextTest textTest = new TextTest();
        String str = textTest.run("https://www.luoxia.com/longzu/");
        textTest.parse(str);
    }
}
