package niffler.jupiter.annotation;

import niffler.jupiter.converter.UserConverter;
import org.junit.jupiter.params.converter.ConvertWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@ConvertWith(UserConverter.class)
public @interface User {

    UserType userType() default UserType.COMMON;

    enum UserType {
        ADMIN, COMMON
    }
}
