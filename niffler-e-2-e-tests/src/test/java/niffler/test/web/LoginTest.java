package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.User;
import niffler.model.UserJson;
import niffler.page.WelcomePage;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {

    @Test
    @AllureId("1")
    void mainPageShouldBeDisplayedAfterSuccessLogin(@User UserJson user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .doLogin()
                .fillLoginPage(user.getUserName(), user.getPassword())
                .submit()
                .waitForPageLoaded();
    }
}
