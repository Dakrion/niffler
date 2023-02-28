package niffler.test.rest;

import niffler.jupiter.extension.ExampleExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ExampleExtension.class)
public class SimpleTest {

    @Test
    void extensionTest() {
        System.out.println("#########TEST#########");
    }
}
