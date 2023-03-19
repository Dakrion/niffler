package niffler.test.database;

import niffler.jupiter.annotation.Authorities;
import niffler.jupiter.annotation.Data;
import niffler.jupiter.extension.PostgresPrecondition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static niffler.jupiter.annotation.Authorities.Authority.READ;
import static niffler.jupiter.annotation.Authorities.Authority.READ_WRITE;

@ExtendWith(PostgresPrecondition.class)
public class DataTests {

    @Test
    @Authorities(READ_WRITE)
    void checkCreateUserWithRead(@Data Map<String, Object> user) {
        System.out.println("User  " + user.keySet().stream().findFirst() + "\n Password  " + user.values().stream().findFirst());
    }

    @Test
    @Authorities(READ)
    void checkCreateUserWithReadAndWrite(@Data Map<String, Object> user) {
        System.out.println("User  " + user.keySet().stream().findFirst() + "\n Password  " + user.values().stream().findFirst());
    }
}
