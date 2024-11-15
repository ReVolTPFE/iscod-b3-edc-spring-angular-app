package com.asteiner.edc.unit.Exception;

import com.asteiner.edc.Exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {
    @Test
    void testNotFoundExceptionStatusAndMessage() {
        String message = "Resource not found";

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException(message);
        });

        assertEquals(message, exception.getMessage());

        ResponseStatus responseStatus = NotFoundException.class.getAnnotation(ResponseStatus.class);
        assertEquals(HttpStatus.NOT_FOUND, responseStatus.value());
    }
}
