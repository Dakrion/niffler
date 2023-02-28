package niffler.jupiter.extension;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.PreconditionViolationException;

public class ExampleExtension implements AfterAllCallback,
        AfterEachCallback,
        BeforeAllCallback,
        BeforeEachCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback {

    @Override
    public void afterAll(ExtensionContext context) {
        System.out.println("### AfterAllCallback");
        try {
            if (!context.getRequiredTestMethod().getName().isEmpty())
                System.out.println("After ALL test method " + context.getRequiredTestMethod().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("AFTER ALL: " + ex.getMessage());
        }
        try {
            if (!context.getRequiredTestClass().getName().isEmpty())
                System.out.println("After ALL test class " + context.getRequiredTestClass().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("AFTER ALL: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestInstance().toString().isEmpty())
                System.out.println("After ALL test instance " + context.getRequiredTestInstance().toString());
        } catch (PreconditionViolationException ex) {
            System.out.println("AFTER ALL: " + ex.getMessage());
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        System.out.println("      ### BeforeEachCallback");

        try {
            if (!context.getRequiredTestMethod().getName().isEmpty())
                System.out.println("Before EACH test method " + context.getRequiredTestMethod().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("BEFORE EACH: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestClass().getName().isEmpty())
                System.out.println("Before EACH test class " + context.getRequiredTestClass().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("BEFORE EACH: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestInstance().toString().isEmpty())
                System.out.println("Before EACH test instance " + context.getRequiredTestInstance().toString());
        } catch (PreconditionViolationException ex) {
            System.out.println("BEFORE EACH: " + ex.getMessage());
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        System.out.println("### BeforeAllCallback");
        try {
            if (!context.getRequiredTestMethod().getName().isEmpty())
                System.out.println("Before ALL test method " + context.getRequiredTestMethod().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("BEFORE ALL: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestClass().getName().isEmpty())
                System.out.println("Before ALL test class " + context.getRequiredTestClass().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("BEFORE ALL: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestInstance().toString().isEmpty())
                System.out.println("Before ALL test instance " + context.getRequiredTestInstance().toString());
        } catch (PreconditionViolationException ex) {
            System.out.println("BEFORE ALL: " + ex.getMessage());
        }

    }

    @Override
    public void afterEach(ExtensionContext context) {
        System.out.println("      ### AfterEachCallback");
        try {
            if (!context.getRequiredTestMethod().getName().isEmpty())
                System.out.println("After EACH test method " + context.getRequiredTestMethod().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("AFTER EACH: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestClass().getName().isEmpty())
                System.out.println("After EACH test class " + context.getRequiredTestClass().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("AFTER EACH: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestInstance().toString().isEmpty())
                System.out.println("After EACH test instance " + context.getRequiredTestInstance().toString());
        } catch (PreconditionViolationException ex) {
            System.out.println("AFTER EACH: " + ex.getMessage());
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        System.out.println("              ### AfterTestExecutionCallback");

        try {
            if (!context.getRequiredTestMethod().getName().isEmpty())
                System.out.println("After TEST test method " + context.getRequiredTestMethod().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("AFTER TEST: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestClass().getName().isEmpty())
                System.out.println("After TEST test class " + context.getRequiredTestClass().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("AFTER TEST: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestInstance().toString().isEmpty())
                System.out.println("After TEST test instance " + context.getRequiredTestInstance().toString());
        } catch (PreconditionViolationException ex) {
            System.out.println("AFTER TEST: " + ex.getMessage());
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        System.out.println("               ### BeforeTestExecutionCallback");

        try {
            if (!context.getRequiredTestMethod().getName().isEmpty())
                System.out.println("Before TEST test method " + context.getRequiredTestMethod().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("BEFORE TEST: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestClass().getName().isEmpty())
                System.out.println("Before TEST test class " + context.getRequiredTestClass().getName());
        } catch (PreconditionViolationException ex) {
            System.out.println("BEFORE TEST: " + ex.getMessage());
        }

        try {
            if (!context.getRequiredTestInstance().toString().isEmpty())
                System.out.println("Before TEST test instance " + context.getRequiredTestInstance().toString());
        } catch (PreconditionViolationException ex) {
            System.out.println("BEFORE TEST: " + ex.getMessage());
        }
    }
}
