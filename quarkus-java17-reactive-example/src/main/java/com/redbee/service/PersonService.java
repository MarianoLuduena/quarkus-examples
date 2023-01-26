package com.redbee.service;

import com.redbee.config.error.ErrorCode;
import com.redbee.config.exception.NotFoundException;
import com.redbee.domain.Person;
import com.redbee.repository.PersonRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Uni<Person> create(final Person.Upsert person) {
        return personRepository.create(person);
    }

    public Uni<Person> get(final long id) {
        return doExecute(personRepository::get, id);
    }

    public Uni<Person> update(final Person.Upsert person) {
        return doExecute(personRepository::update, person);
    }

    public Uni<Person> delete(final long id) {
        return doExecute(personRepository::delete, id);
    }

    private <T> Uni<Person> doExecute(final Function<T, Uni<Optional<Person>>> fn, final T input) {
        return fn.apply(input)
                .map(oPerson -> oPerson.orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND)));
    }

}
