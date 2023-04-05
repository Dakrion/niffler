package niffler.data.dao;

import niffler.data.entity.UsersEntity;

import java.util.Set;

public interface UsersDAO extends DAO {
    int addUser(UsersEntity users);

    void updateUser(UsersEntity user);

    void remove(UsersEntity user);

    UsersEntity getByUsername(String username);

    Set<UsersEntity> getFriends(String username);
}
