package niffler.jupiter;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public interface AroundAllTestsExtension extends BeforeAllCallback {

    default void beforeAllTests(ExtensionContext context) throws Exception {
    }

    default void afterAllTests() throws Exception {
    }

    @Override
    default void beforeAll(ExtensionContext context) {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).
                getOrComputeIfAbsent(this.getClass(),
                        k -> {
                            try {
                                beforeAllTests(context);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            return (ExtensionContext.Store.CloseableResource) this::afterAllTests;
                        }
                );
    }
}
