package com.cooperativa.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ResourceNotFoundExceptionTest {

    @Test
    void testNewResourceNotFoundException() {
        ResourceNotFoundException actualResourceNotFoundException = new ResourceNotFoundException("Msg");
        assertEquals("Msg", actualResourceNotFoundException.getLocalizedMessage());
        assertEquals("Msg", actualResourceNotFoundException.getMessage());
        assertNull(actualResourceNotFoundException.getCause());
        assertEquals(0, actualResourceNotFoundException.getSuppressed().length);
    }
}
