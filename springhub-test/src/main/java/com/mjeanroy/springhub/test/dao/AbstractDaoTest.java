package com.mjeanroy.springhub.test.dao;

import com.mjeanroy.springhub.test.db.AbstractDatabaseTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public abstract class AbstractDaoTest extends AbstractDatabaseTest {

	@PersistenceContext
	private EntityManager entityManager;

	@AfterClass
	public static void afterClass() throws Exception {
		stopHsqlDb();
	}

	@Before
	public void setUp() throws Exception {
		startHsqlDb();
	}

	/**
	 * Get reference of object.
	 *
	 * @param klass Object's klass.
	 * @param id    Object's primary key.
	 * @return Object's reference.
	 */
	protected <T> T getReference(Class<T> klass, Long id) {
		return entityManager.getReference(klass, id);
	}

	/**
	 * Get object from database by its id.
	 *
	 * @param klass Object's class.
	 * @param id    Id of object.
	 * @return Object from database.
	 */
	protected <T> T byId(Class<T> klass, Long id) {
		return entityManager.find(klass, id);
	}

	/**
	 * Check if an entity is loaded, i.e. has been fetched and is stored in first level cache.
	 *
	 * @param entity Entity to check.
	 * @return True if entity is loaded, false otherwise.
	 */
	protected boolean isLoaded(Object entity) {
		PersistenceUnitUtil unitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
		return unitUtil.isLoaded(entity);
	}
}
