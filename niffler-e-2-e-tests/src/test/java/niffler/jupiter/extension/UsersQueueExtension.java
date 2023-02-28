package niffler.jupiter.extension;

import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.User;
import niffler.model.UserJson;
import niffler.model.UserModel;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static niffler.jupiter.annotation.User.UserType.ADMIN;
import static niffler.jupiter.annotation.User.UserType.COMMON;

public class UsersQueueExtension implements
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    private static final Queue<UserModel> USER_MODEL_ADMIN_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<UserModel> USER_MODEL_COMMON_QUEUE = new ConcurrentLinkedQueue<>();

    static {
        USER_MODEL_ADMIN_QUEUE.add(new UserModel("Roman", "1234"));
        USER_MODEL_COMMON_QUEUE.add(new UserModel("bill", "12345"));
        USER_MODEL_COMMON_QUEUE.add(new UserModel("test", "test"));
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String id = getTestId(context);
        List<Parameter> listOfUsers = Arrays.stream(context.getRequiredTestMethod()
                        .getParameters())
                .filter(p -> p.isAnnotationPresent(User.class))
                .toList();

        putUsersFromQueueToContext(id, listOfUsers, context);
    }

    private void putUsersFromQueueToContext(String testId, List<Parameter> params, ExtensionContext context) {
        params.forEach(el -> {
            if (el.getAnnotation(User.class).userType() == ADMIN) {
                UserModel user = USER_MODEL_ADMIN_QUEUE.poll();
                if (user == null) putUsersFromQueueToContext(testId, params, context);
                context.getStore(NAMESPACE).put(testId, Map.of(ADMIN, Objects.requireNonNull(user)));
            } else {
                UserModel user = USER_MODEL_COMMON_QUEUE.poll();
                if (user == null) putUsersFromQueueToContext(testId, params, context);
                context.getStore(NAMESPACE).put(testId, Map.of(COMMON, Objects.requireNonNull(user)));
            }
        });
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String id = getTestId(context);
        Map<User.UserType, UserModel> map = context.getStore(NAMESPACE).get(id, Map.class);
        if (map.containsKey(ADMIN)) {
            USER_MODEL_ADMIN_QUEUE.add(map.get(ADMIN));
        } else {
            USER_MODEL_COMMON_QUEUE.add(map.get(User.UserType.COMMON));
        }
    }

    private String getTestId(ExtensionContext context) {
        return Objects.requireNonNull(
                context.getRequiredTestMethod().getAnnotation(AllureId.class)
        ).value();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserModel.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserModel resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        String id = getTestId(extensionContext);
        return (UserModel) extensionContext.getStore(NAMESPACE).get(id, Map.class)
                .values()
                .iterator()
                .next();
    }
}
