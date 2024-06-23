package com.cooperativa.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cooperativa.exception.ResourceNotFoundException;
import com.cooperativa.exception.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

class ResourceNotFoundExceptionHandlerTest {

    @Test
    void testResourceNotFound() {
        ResourceNotFoundExceptionHandler resourceNotFoundExceptionHandler = new ResourceNotFoundExceptionHandler();
        ResourceNotFoundException e = new ResourceNotFoundException("Msg");
        ResponseEntity<StandardError> actualResourceNotFoundResult = resourceNotFoundExceptionHandler.resourceNotFound(e,
                new MockHttpServletRequest());
        StandardError body = actualResourceNotFoundResult.getBody();
        assertEquals("", body.getPath());
        assertEquals("Msg", body.getMessage());
        assertEquals("Resource not found", body.getError());
        assertEquals(404, body.getStatus().intValue());
        assertEquals(404, actualResourceNotFoundResult.getStatusCodeValue());
        assertTrue(actualResourceNotFoundResult.hasBody());
        assertTrue(actualResourceNotFoundResult.getHeaders().isEmpty());
    }
}
