package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    void appHasAGreeting() {
        App classUnderTest = new App();
        // 使用 assertEquals 检查返回值是否符合预期
        assertEquals("Hello, World!", classUnderTest.getGreeting(), "App should have a greeting");
    }
}
