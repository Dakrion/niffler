package niffler.jupiter.extension;

import niffler.data.dao.PostgresJdbcAuthService;
import niffler.data.entity.auth.UserEntity;
import niffler.jupiter.annotation.Authorities;
import niffler.jupiter.annotation.Data;
import niffler.utils.DataUtils;
import org.junit.jupiter.api.extension.*;

import java.util.HashMap;
import java.util.Map;

public class PostgresPrecondition implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private final PostgresJdbcAuthService authService = new PostgresJdbcAuthService();

    @Override
    public void beforeEach(ExtensionContext context) {
        String username = null;
        String password = null;
        if (context.getRequiredTestMethod().isAnnotationPresent(Authorities.class)) {
            username = DataUtils.generateRandomUsername();
            password = DataUtils.generateRandomPassword();
            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setPassword(password);
            if (context.getRequiredTestMethod().getAnnotation(Authorities.class)
                    .value().equals(Authorities.Authority.READ_WRITE)) {
                authService.createUserWithReadAndWriteAuthority(user);
            } else {
                authService.createUserWithReadAuthority(user);
            }
        }
        Map<String, Object> credentials = new HashMap<>();
        credentials.put(username, password);
        String methodName = context.getRequiredTestMethod().getName();
        context.getStore(ExtensionContext.Namespace.GLOBAL).put(methodName, credentials);
    }

    @Override
    public void afterEach(ExtensionContext context) throws InterruptedException {
        String methodName = context.getRequiredTestMethod().getName();
        Map<String, Object> credentials = (Map<String, Object>) context.getStore(ExtensionContext.Namespace.GLOBAL).get(methodName);
        authService.deleteUserFromDb(credentials.keySet().stream().findFirst().get());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext context) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(HashMap.class)
                && parameterContext.getParameter().isAnnotationPresent(Data.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext context) throws ParameterResolutionException {
        String methodName = context.getRequiredTestMethod().getName();
        return context.getStore(ExtensionContext.Namespace.GLOBAL).get(methodName);
    }
}
