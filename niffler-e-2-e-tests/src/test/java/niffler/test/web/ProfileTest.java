package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.User;
import niffler.model.UserJson;
import niffler.page.ProfilePage;
import org.junit.jupiter.api.Test;


public class ProfileTest extends BaseTest {

    @Test
    @AllureId("2")
    void userNameShouldBeUpdatedAfterChangingInProfile(@User UserJson user) {
        ProfilePage profilePage = Selenide.open(ProfilePage.URL, ProfilePage.class)
                .waitForPageLoaded()
                .setName("Pizzly")
                .submitProfile();

        Selenide.refresh();

        profilePage.checkName("Pizzly");
    }
}
