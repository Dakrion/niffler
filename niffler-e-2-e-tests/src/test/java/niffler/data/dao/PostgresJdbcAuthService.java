package niffler.data.dao;

import lombok.extern.slf4j.Slf4j;
import niffler.data.entity.auth.AuthoritiesEntity;
import niffler.data.entity.auth.UserEntity;
import niffler.data.jdbc.DataSourceContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.UUID;

import static niffler.data.DataBase.AUTH;

@Slf4j
public class PostgresJdbcAuthService implements UserRepository {

    private final JdbcTemplate authData = new JdbcTemplate(DataSourceContext.INSTANCE.getDataSource(AUTH));

    @Override
    public void createUserWithReadAuthority(UserEntity user) {
        authData.update("INSERT INTO users (username, " +
                        "password, " +
                        "enabled, " +
                        "account_non_expired, " +
                        "account_non_locked, " +
                        "credentials_non_expired) VALUES (?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()),
                true,
                true,
                true,
                true);
        String userId = authData
                .queryForObject("SELECT id FROM users WHERE username = '" + user.getUsername() + "'", String.class);

        authData.update("INSERT INTO authorities (user_id, authority) VALUES (?, ?)",
                UUID.fromString(userId), AuthoritiesEntity.Authority.read.toString());
        log.info("Create user {} with authorities 'read'", user.getUsername());
    }

    @Override
    public void createUserWithReadAndWriteAuthority(UserEntity user) {
        authData.update("INSERT INTO users (username, " +
                        "password, " +
                        "enabled, " +
                        "account_non_expired, " +
                        "account_non_locked, " +
                        "credentials_non_expired) VALUES (?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()),
                true,
                true,
                true,
                true);
        String userId = authData
                .queryForObject("SELECT id FROM users WHERE username = '" + user.getUsername() + "'", String.class);

        authData.update("INSERT INTO authorities (user_id, authority) VALUES (?, ?)",
                UUID.fromString(userId), AuthoritiesEntity.Authority.read.toString());

        authData.update("INSERT INTO authorities (user_id, authority) VALUES (?, ?)",
                UUID.fromString(userId), AuthoritiesEntity.Authority.write.toString());
        log.info("Create user {} with authorities 'read, write'", user.getUsername());
    }

    @Override
    public void deleteUserFromDb(String username) throws InterruptedException {
        String userId = authData
                .queryForObject("SELECT id FROM users WHERE username = '" + username + "'", String.class);
        authData.update("DELETE FROM authorities WHERE user_id = ?", UUID.fromString(userId));
        Thread.sleep(1000);
        authData.update("DELETE FROM users WHERE id = ?", UUID.fromString(userId));
        log.info("Delete user {}", username);
    }
}
