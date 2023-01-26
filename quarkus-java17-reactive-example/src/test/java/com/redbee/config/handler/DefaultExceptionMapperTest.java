package com.redbee.config.handler;

import com.redbee.config.error.ErrorCode;
import com.redbee.config.model.ApiErrorResponse;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

@QuarkusTest
class DefaultExceptionMapperTest {

    private DefaultExceptionMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new DefaultExceptionMapper();
    }

    @Test
    void shouldMapAnExceptionToAnInternalServerError() {
        final ApiErrorResponse entity;
        try (final var actual = mapper.toResponse(new RuntimeException("Something terrible has happened!"))) {
            entity = Assertions.assertInstanceOf(ApiErrorResponse.class, actual.getEntity());
        }

        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), entity.name());
        Assertions.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), entity.status());
        Assertions.assertEquals(ErrorCode.INTERNAL_ERROR.getCode(), entity.code());
        Assertions.assertEquals(ErrorCode.INTERNAL_ERROR.getReasonPhrase(), entity.detail());
    }

}
