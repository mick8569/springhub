package com.mjeanroy.springhub.models.entities;

import javax.persistence.MappedSuperclass;

import java.io.Serializable;

import com.mjeanroy.springhub.models.AbstractModel;
import com.mjeanroy.springhub.models.Model;

/**
 * Abstract implementation of jpa entity.
 *
 * @param <PK> Generic type of id.
 */
@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> extends AbstractModel<PK> implements Model<PK>, JPAEntity<PK> {

}
