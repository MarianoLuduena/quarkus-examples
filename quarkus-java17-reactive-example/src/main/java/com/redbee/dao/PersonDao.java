package com.redbee.dao;

import com.redbee.model.entity.PersonEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonDao implements PanacheRepositoryBase<PersonEntity, Long> {
}
