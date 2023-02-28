package niffler.test.rest;

import io.qameta.allure.AllureId;
import niffler.api.NifflerUserdataClient;
import niffler.jupiter.annotation.User;
import niffler.model.UserJson;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class UserDataRestTest {

    private final NifflerUserdataClient nus = new NifflerUserdataClient();

    @AllureId("101")
    @ValueSource(strings = {
            "data/user0.json",
            "data/user1.json"
    })
    @ParameterizedTest
    void updateUserApiTest(@User UserJson userBody) throws Exception {

        nus.updateUser(userBody);

        UserJson user = nus.getCurrentUser("Roman");

        assertSoftly(assertions -> {
            assertions.assertThat(user.getUserName()).isEqualTo(userBody.getUserName());
            assertions.assertThat(user.getFirstname()).isEqualTo(userBody.getFirstname());
            assertions.assertThat(user.getSurname()).isEqualTo(userBody.getSurname());
        });
    }
}
