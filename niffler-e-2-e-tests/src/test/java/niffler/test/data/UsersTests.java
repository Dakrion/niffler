package niffler.test.data;

import niffler.data.dao.PostgresHibernateUsersDAO;
import niffler.data.entity.UsersEntity;
import niffler.jupiter.extension.DAOResolver;
import niffler.jupiter.extension.JpaExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;



@ExtendWith({JpaExtension.class, DAOResolver.class})
public class UsersTests {

    PostgresHibernateUsersDAO usersDAO = new PostgresHibernateUsersDAO();

    @Test
    void addFriend() {
        UsersEntity friend = usersDAO.getByUsername("test");
        UsersEntity user = usersDAO.getByUsername("Roman");
        user.addFriends(friend);
        usersDAO.updateUser(user);
    }

    @Test
    void getFriends() {
        usersDAO.getFriends("Roman").forEach(System.out::println);
    }
}
