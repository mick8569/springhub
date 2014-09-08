package com.mjeanroy.springhub.models.entities;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

import com.mjeanroy.springhub.models.Model;

/**
 * JPA Entity (specific model object).
 * Id of a JPA Entity should be the primary key.
 * JPA entities extends {@link Persistable} object from spring-data-jpa.
 *
 * @param <PK> Generic type of id.
 */
public interface JPAEntity<PK extends Serializable> extends Model<PK>, Persistable<PK> {
}
