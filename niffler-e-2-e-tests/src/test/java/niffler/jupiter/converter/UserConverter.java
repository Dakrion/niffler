package niffler.jupiter.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import niffler.model.SpendJson;
import niffler.model.UserJson;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class UserConverter implements ArgumentConverter {

    private final ClassLoader cl = this.getClass().getClassLoader();
    private final ObjectMapper om = new ObjectMapper();

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        if (!(source instanceof String)) {
            throw new ArgumentConversionException(source.getClass().getName() + " is not a string!");
        }
        try (InputStream is = cl.getResourceAsStream((String) source);
             InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(is))) {
            return om.readValue(isr, UserJson.class);
        } catch (IOException e) {
            throw new ArgumentConversionException("Failed to convert!", e);
        }
    }
}
