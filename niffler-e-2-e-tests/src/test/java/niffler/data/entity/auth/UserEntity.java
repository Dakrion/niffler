package niffler.data.entity.auth;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users", schema = "public", catalog = "niffler-auth")
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNoneExpired;

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNoneLocked;

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNoneExpired;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNoneExpired() {
        return accountNoneExpired;
    }

    public void setAccountNoneExpired(boolean accountNoneExpired) {
        this.accountNoneExpired = accountNoneExpired;
    }

    public boolean isAccountNoneLocked() {
        return accountNoneLocked;
    }

    public void setAccountNoneLocked(boolean accountNoneLocked) {
        this.accountNoneLocked = accountNoneLocked;
    }

    public boolean isCredentialsNoneExpired() {
        return credentialsNoneExpired;
    }

    public void setCredentialsNoneExpired(boolean credentialsNoneExpired) {
        this.credentialsNoneExpired = credentialsNoneExpired;
    }
}
