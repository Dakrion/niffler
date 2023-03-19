package niffler.data.dao;

import niffler.data.entity.auth.UserEntity;

public interface UserRepository extends DAO {

    void createUserWithReadAuthority(UserEntity user);

    void createUserWithReadAndWriteAuthority(UserEntity user);

    void deleteUserFromDb(String userId) throws InterruptedException;
}
