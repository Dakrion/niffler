package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.User;
import niffler.jupiter.extension.UsersQueueExtension;
import niffler.model.UserModel;
import niffler.page.WelcomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static niffler.jupiter.annotation.User.UserType.ADMIN;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(UsersQueueExtension.class)
public class LoginTest extends BaseTest {

    @Test
    @AllureId("1")
    void mainPageShouldBeDisplayedAfterSuccessLogin(@User UserModel user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .doLogin()
                .fillLoginPage(user.getUsername(), user.getPassword())
                .submit()
                .waitForPageLoaded();
    }

    @Test
    @AllureId("2")
    void checkUsersFromUserQueue(@User UserModel user,
                                 @User(userType = ADMIN) UserModel admin) {

        assertThat(user.getUsername()).isIn("Vlad", "test");
        assertThat(admin.getUsername()).isEqualTo("Roman");
    }
}
