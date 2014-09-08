package com.mjeanroy.springhub.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mjeanroy.springhub.dao.AbstractGenericDao;

@Repository
public class FooDao extends AbstractGenericDao<Long, FooEntity> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}
}
