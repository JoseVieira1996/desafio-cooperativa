package com.cooperativa.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cooperativa.exception.IllegalArgumentException;
import com.cooperativa.exception.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

class IllegalArgumentExceptionHandlerTest {

    @Test
    void testResourceNotFound() {
        IllegalArgumentExceptionHandler illegalArgumentExceptionHandler = new IllegalArgumentExceptionHandler();
        IllegalArgumentException e = new IllegalArgumentException("Msg");

        ResponseEntity<StandardError> actualResourceNotFoundResult = illegalArgumentExceptionHandler.resourceNotFound(e,
                new MockHttpServletRequest());
        StandardError body = actualResourceNotFoundResult.getBody();
        assertEquals("", body.getPath());
        assertEquals("IllegalArgumentException", body.getError());
        assertEquals("Msg", body.getMessage());
        assertEquals(400, body.getStatus().intValue());
        assertEquals(400, actualResourceNotFoundResult.getStatusCodeValue());
        assertTrue(actualResourceNotFoundResult.hasBody());
        assertTrue(actualResourceNotFoundResult.getHeaders().isEmpty());
    }
}
