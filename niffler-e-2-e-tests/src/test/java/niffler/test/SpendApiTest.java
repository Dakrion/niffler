package niffler.test;

import io.qameta.allure.Allure;
import niffler.api.NifflerSpendClient;
import niffler.model.SpendJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpendApiTest extends BaseTest {

    private final String username = "Roman";
    private final NifflerSpendClient nsc = new NifflerSpendClient();

    @Test
    @DisplayName("Проверка получения spend по username")
    void spendTest() throws Exception {
        List<SpendJson> allSpends = nsc.getSpends(username);

        Allure.step("Проверка тела ответа", () ->
                allSpends.forEach(el -> {
                    assertNotNull(el.getCategory());
                    assertEquals(el.getUsername(), username);
                    assertNotNull(el.getAmount());
                    assertNotNull(el.getCurrency());
                    assertNotNull(el.getSpendDate());
                    assertNotNull(el.getDescription());
                }));
    }
}
