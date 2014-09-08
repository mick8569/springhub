package com.mjeanroy.springhub.dao;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.mjeanroy.springhub.utils.DaoTestConfiguration;
import com.mjeanroy.springhub.utils.FooDao;
import com.mjeanroy.springhub.utils.FooEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfiguration.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AbstractGenericDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private FooDao fooDao;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void it_should_get_entity_manager_flush_mode() {
		FlushModeType flushModeType = fooDao.getFlushMode();
		assertThat(flushModeType).isNotNull();
	}

	@Test
	public void it_should_return_false_if_entity_manager_does_not_contains_entity() {
		FooEntity entity = new FooEntity();
		entity.setId(1L);
		assertThat(fooDao.isManaged(entity)).isFalse();
	}

	@Test
	public void it_should_return_false_if_entity_manager_contains_entity() {
		FooEntity entity = entityManager.find(FooEntity.class, 1L);
		assertThat(fooDao.isManaged(entity)).isTrue();
	}

	@Test
	public void it_should_clear_entity_manager() {
		FooEntity foo1 = entityManager.find(FooEntity.class, 1L);
		FooEntity foo2 = entityManager.find(FooEntity.class, 2L);

		assertThat(entityManager.contains(foo1)).isTrue();
		assertThat(entityManager.contains(foo2)).isTrue();

		fooDao.clear();

		assertThat(entityManager.contains(foo1)).isFalse();
		assertThat(entityManager.contains(foo2)).isFalse();
	}

	@Test
	public void it_should_detach_entity() {
		FooEntity foo1 = entityManager.find(FooEntity.class, 1L);
		FooEntity foo2 = entityManager.find(FooEntity.class, 2L);

		assertThat(entityManager.contains(foo1)).isTrue();
		assertThat(entityManager.contains(foo2)).isTrue();

		fooDao.detach(foo2);

		assertThat(entityManager.contains(foo1)).isTrue();
		assertThat(entityManager.contains(foo2)).isFalse();
	}

	@Test
	public void it_should_refresh_entity() {
		FooEntity foo1 = entityManager.find(FooEntity.class, 1L);
		foo1.setName("foobar");
		assertThat(foo1.getName()).isEqualTo("foobar");

		fooDao.refresh(foo1);

		assertThat(foo1.getName()).isEqualTo("foo");
	}

	@Test
	public void it_should_get_a_reference_to_an_entity() {
		FooEntity foo = fooDao.getOne(1L);
		assertThat(foo).isNotNull();
		assertThat(foo.getId()).isEqualTo(1L);
	}

	@Test
	public void it_should_get_a_reference_to_an_entity_and_return_null_if_entity_is_not_found() {
		FooEntity foo = fooDao.getOne(1000L);
		assertThat(foo).isNull();
	}

	@Test
	public void it_should_persist_entity() {
		String sql = "SELECT COUNT(foo) FROM FooEntity foo";
		Long countBefore = (Long) entityManager.createQuery(sql).getSingleResult();

		FooEntity foo = new FooEntity();
		foo.setName("hello world");
		fooDao.persist(foo);

		assertThat(foo.getId()).isNotNull();
		Long countAfter = (Long) entityManager.createQuery(sql).getSingleResult();
		assertThat(countAfter).isEqualTo(countBefore + 1);
	}

	@Test
	public void it_should_merge_entity() {
		FooEntity foo = entityManager.find(FooEntity.class, 1L);
		foo.setName("foobar");
		entityManager.detach(foo);

		FooEntity newFoo = fooDao.merge(foo);

		assertThat(newFoo).isNotSameAs(foo);
		assertThat(newFoo.getName()).isEqualTo("foobar");
	}

	@Test
	public void it_should_remove_entity() {
		String sql = "SELECT COUNT(foo) FROM FooEntity foo";
		Long countBefore = (Long) entityManager.createQuery(sql).getSingleResult();

		FooEntity foo = entityManager.find(FooEntity.class, 1L);
		fooDao.remove(foo);

		Long countAfter = (Long) entityManager.createQuery(sql).getSingleResult();
		assertThat(countAfter).isEqualTo(countBefore - 1);
	}

	@Test
	public void findOne_should_find_entity_by_id() {
		FooEntity foo = fooDao.findOne(1L);
		assertThat(foo).isNotNull();
		assertThat(foo.getId()).isEqualTo(1L);
	}

	@Test
	public void findOne_should_return_null_if_id_does_not_exist() {
		FooEntity foo = fooDao.findOne(1000L);
		assertThat(foo).isNull();
	}

	@Test
	public void findAll_should_return_everything() {
		int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo", Integer.class);
		List<FooEntity> foos = fooDao.findAll();
		assertThat(foos).isNotNull().isNotEmpty().hasSize(count);
	}

	@Test
	public void findAll_by_id() {
		List<Long> ids = asList(1L, 2L);
		List<FooEntity> foos = fooDao.findAll(ids);
		assertThat(foos).isNotNull().hasSize(ids.size());
	}

	@Test
	public void index_by_id() {
		List<Long> ids = asList(1L, 2L);
		Map<Long, FooEntity> foos = fooDao.indexById(ids);
		assertThat(foos).isNotNull().hasSize(ids.size());
		assertThat(foos.get(1L).getId()).isEqualTo(1L);
		assertThat(foos.get(2L).getId()).isEqualTo(2L);
	}

	@Test
	public void count_should_return_number_of_entities() {
		int realCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo", Integer.class);
		long count = fooDao.count();
		assertThat(count).isEqualTo(realCount);
	}

	@Test
	public void findAll_using_jpql_query() {
		String jpql = "SELECT foo FROM FooEntity foo WHERE foo.name = 'foo'";
		List<FooEntity> foos = fooDao.findAll(jpql);
		assertThat(foos).isNotNull().isNotEmpty();
	}

	@Test
	public void findAll_using_jpql_query_and_parameters() {
		String jpql = "SELECT foo FROM FooEntity foo WHERE foo.name = :name";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "foo");

		List<FooEntity> foos = fooDao.findAll(jpql, params);

		assertThat(foos).isNotNull().isNotEmpty();
	}

	@Test
	public void findAll_using_query() {
		Query query = entityManager.createQuery("SELECT foo FROM FooEntity foo WHERE foo.name = :name");
		query.setParameter("name", "foo");

		List<FooEntity> foos = fooDao.findAll(query);

		assertThat(foos).isNotNull().isNotEmpty();
	}

	@Test
	public void findOne_using_jpql_query() {
		String jpql = "SELECT foo FROM FooEntity foo WHERE foo.id = 1";
		FooEntity foo = fooDao.findOne(jpql);
		assertThat(foo).isNotNull();
	}

	@Test
	public void findOne_should_return_null_if_entity_does_not_exist() {
		String jpql = "SELECT foo FROM FooEntity foo WHERE foo.id = 1000";
		FooEntity foo = fooDao.findOne(jpql);
		assertThat(foo).isNull();
	}

	@Test
	public void findOne_using_jpql_query_and_parameters() {
		String jpql = "SELECT foo FROM FooEntity foo WHERE foo.id = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", 1L);

		FooEntity foo = fooDao.findOne(jpql, params);
		assertThat(foo).isNotNull();
	}

	@Test
	public void findOne_using_query() {
		Query query = entityManager.createQuery("SELECT foo FROM FooEntity foo WHERE foo.id = :id");
		query.setParameter("id", 1L);

		FooEntity foo = fooDao.findOne(query);
		assertThat(foo).isNotNull();
	}

	@Test
	public void count_using_jpql_query() {
		String jpql = "SELECT COUNT(foo) FROM FooEntity foo WHERE foo.name = 'foo'";
		long count = fooDao.count(jpql);
		assertThat(count).isNotNull().isNotZero();
	}

	@Test
	public void count_using_jpql_query_and_parameters() {
		String jpql = "SELECT COUNT(foo) FROM FooEntity foo WHERE foo.name = :name";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "foo");

		long count = fooDao.count(jpql, params);
		assertThat(count).isNotNull().isNotZero();
	}

	@Test
	public void count_query() {
		Query query = entityManager.createQuery("SELECT COUNT(foo) FROM FooEntity foo WHERE foo.name = :name");
		query.setParameter("name", "foo");

		long count = fooDao.count(query);
		assertThat(count).isNotNull().isNotZero();
	}
}
