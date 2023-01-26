package com.redbee.config.handler;

import com.redbee.config.error.ErrorCode;
import com.redbee.config.exception.NotFoundException;
import com.redbee.config.model.ApiErrorResponse;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

@QuarkusTest
class NotFoundExceptionMapperTest {

    private NotFoundExceptionMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new NotFoundExceptionMapper();
    }

    @Test
    void shouldMapAnExceptionToANotFound() {
        final ApiErrorResponse entity;
        try (final var actual = mapper.toResponse(new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND))) {
            entity = Assertions.assertInstanceOf(ApiErrorResponse.class, actual.getEntity());
        }

        Assertions.assertEquals(Response.Status.NOT_FOUND.getReasonPhrase(), entity.name());
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), entity.status());
        Assertions.assertEquals(ErrorCode.RESOURCE_NOT_FOUND.getCode(), entity.code());
        Assertions.assertEquals(ErrorCode.RESOURCE_NOT_FOUND.getReasonPhrase(), entity.detail());
    }

}
