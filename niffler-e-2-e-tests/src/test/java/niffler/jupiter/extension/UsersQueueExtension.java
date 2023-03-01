package niffler.jupiter.extension;

import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.User;
import niffler.model.UserModel;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static niffler.jupiter.annotation.User.UserType.ADMIN;
import static niffler.jupiter.annotation.User.UserType.COMMON;

@SuppressWarnings("unchecked")
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
        USER_MODEL_COMMON_QUEUE.add(new UserModel("Vlad", "1234"));
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
        Map<User.UserType, List<UserModel>> userContainer = new HashMap<>();
        List<UserModel> adminContainer = new ArrayList<>();
        List<UserModel> commonContainer = new ArrayList<>();
        params.forEach(el -> {
            UserModel user = null;
            while (user == null) {
                if (el.getAnnotation(User.class).userType() == ADMIN) {
                    user = USER_MODEL_ADMIN_QUEUE.poll();
                    if (user != null) adminContainer.add(user);
                } else {
                    user = USER_MODEL_COMMON_QUEUE.poll();
                    if (user != null) commonContainer.add(user);
                }
            }
        });
        userContainer.put(ADMIN, adminContainer);
        userContainer.put(COMMON, commonContainer);
        context.getStore(NAMESPACE).put(testId, userContainer);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        String id = getTestId(context);
        Map<User.UserType, List<UserModel>> map = context.getStore(NAMESPACE).get(id, Map.class);
        if (map.containsKey(ADMIN)) {
            List<UserModel> admins = map.get(ADMIN);
            USER_MODEL_ADMIN_QUEUE.addAll(admins);
        } else {
            List<UserModel> commons = map.get(COMMON);
            USER_MODEL_COMMON_QUEUE.addAll(commons);
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
        User.UserType userType = parameterContext.getParameter().getAnnotation(User.class).userType();
        Map<User.UserType, List<UserModel>> context = extensionContext.getStore(NAMESPACE).get(id, Map.class);
        List<UserModel> users = context.entrySet()
                .stream()
                .filter(el -> el.getKey().equals(userType))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow();

        return users.stream().findFirst().orElseThrow();
    }
}
