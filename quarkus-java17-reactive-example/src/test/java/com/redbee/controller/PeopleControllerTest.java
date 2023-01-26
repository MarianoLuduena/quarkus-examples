package com.redbee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.mock.PersonMockFactory;
import com.redbee.service.PersonService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class PeopleControllerTest {

    private static final String BASE_URL = "/people";
    private static final String BASE_PERSON_URL = "/people/{id}";

    @Inject
    ObjectMapper objectMapper;

    @InjectMock
    PersonService personService;

    @Test
    void creatingAPersonShouldReturnTheCreatedEntity() throws JsonProcessingException {
        Mockito.when(personService.create(Mockito.any()))
                .thenReturn(Uni.createFrom().item(PersonMockFactory.person()));

        final var expected = objectMapper.writeValueAsString(PersonMockFactory.personControllerModel());

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(PersonMockFactory.upsertPersonControllerModel()))
                .log().all()
                .post(BASE_URL)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is(expected));
    }

    @Test
    void gettingAPersonShouldReturnTheFoundEntity() throws JsonProcessingException {
        final var domain = PersonMockFactory.person();
        Mockito.when(personService.get(domain.id())).thenReturn(Uni.createFrom().item(domain));

        final var expected = objectMapper.writeValueAsString(PersonMockFactory.personControllerModel());

        given()
                .when()
                .log().all()
                .get(BASE_PERSON_URL, domain.id())
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is(expected));
    }

    @Test
    void gettingAPersonWithAnInvalidIdShouldReturnABadRequest() {
        given()
                .when()
                .log().all()
                .get(BASE_PERSON_URL, -1L)
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body(containsString("get.id: must be greater than 0"));
    }

    @Test
    void updatingAPersonShouldReturnTheUpdatedEntity() throws JsonProcessingException {
        final var domain = PersonMockFactory.person();
        Mockito.when(personService.update(Mockito.any())).thenReturn(Uni.createFrom().item(domain));

        final var expected = objectMapper.writeValueAsString(PersonMockFactory.personControllerModel());

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(PersonMockFactory.upsertPersonControllerModel()))
                .log().all()
                .put(BASE_PERSON_URL, domain.id())
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is(expected));
    }

    @Test
    void deletingAPersonShouldReturnTheDeletedEntity() throws JsonProcessingException {
        final var domain = PersonMockFactory.person();
        Mockito.when(personService.delete(domain.id())).thenReturn(Uni.createFrom().item(domain));

        final var expected = objectMapper.writeValueAsString(PersonMockFactory.personControllerModel());

        given()
                .when()
                .log().all()
                .delete(BASE_PERSON_URL, domain.id())
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is(expected));
    }

}
