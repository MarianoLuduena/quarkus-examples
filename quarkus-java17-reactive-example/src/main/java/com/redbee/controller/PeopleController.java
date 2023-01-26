package com.redbee.controller;

import com.redbee.model.controller.PersonControllerModel;
import com.redbee.model.controller.UpsertPersonControllerModel;
import com.redbee.service.PersonService;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/people")
public class PeopleController {

    private final PersonService personService;

    public PeopleController(final PersonService personService) {
        this.personService = personService;
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ReactiveTransactional
    public Uni<PersonControllerModel> create(
            @Valid final UpsertPersonControllerModel request
    ) {
        Log.infov("Call to POST /people with request {0}", request);
        return personService.create(request.toDomain()).map(person -> {
            final var response = PersonControllerModel.from(person);
            Log.infov("Response to POST /people: {0}", response);
            return response;
        });
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Uni<PersonControllerModel> get(@PathParam("id") @NotNull @Positive final Long id) {
        Log.infov("Call to GET /people/{0}", id);
        return personService.get(id).map(person -> {
            final var response = PersonControllerModel.from(person);
            Log.infov("Response to GET /people/{0}: {1}", response.id(), response);
            return response;
        });
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ReactiveTransactional
    public Uni<PersonControllerModel> update(
            @PathParam("id") @NotNull @Positive final Long id,
            @Valid final UpsertPersonControllerModel request
    ) {
        Log.infov("Call to PUT /people/{0} with request {1}", id, request);
        return personService.update(request.toDomain(id)).map(person -> {
            final var response = PersonControllerModel.from(person);
            Log.infov("Response to PUT /people/{0}: {1}", response.id(), response);
            return response;
        });
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @ReactiveTransactional
    public Uni<PersonControllerModel> delete(@PathParam("id") @NotNull @Positive final Long id) {
        Log.infov("Call to DELETE /people/{0}", id);
        return personService.delete(id).map(person -> {
            final var response = PersonControllerModel.from(person);
            Log.infov("Response to DELETE /people/{0}: {1}", response.id(), response);
            return response;
        });
    }

}
