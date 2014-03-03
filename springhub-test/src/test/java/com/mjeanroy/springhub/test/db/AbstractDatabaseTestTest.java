package com.mjeanroy.springhub.test.db;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.mjeanroy.springhub.test.dbunit.DBUnit;

@SuppressWarnings("unchecked")
public class AbstractDatabaseTestTest {

	private AbstractDatabaseImpl databaseTest;

	private JdbcTemplate jdbcTemplate;

	private EmbeddedDatabase dataSource;

	@Before
	public void setUp() throws Exception {
		jdbcTemplate = mock(JdbcTemplate.class);

		dataSource = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("/dbunit/datasets/init.sql")
				.build();

		databaseTest = new AbstractDatabaseImpl(dataSource, jdbcTemplate);
	}

	@After
	public void tearDown() {
		dataSource.shutdown();
	}

	@Test
	public void test_count_table() {
		when(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo", Integer.class)).thenReturn(10);
		int count = databaseTest.count("foo");
		assertThat(count).isEqualTo(10);
		verify(jdbcTemplate).queryForObject("SELECT COUNT(*) FROM foo", Integer.class);
	}

	@Test
	public void test_count_table_null_should_return_zero() {
		when(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM foo", Integer.class)).thenReturn(10);
		int count = databaseTest.count("foo");
		assertThat(count).isEqualTo(10);
		verify(jdbcTemplate).queryForObject("SELECT COUNT(*) FROM foo", Integer.class);
	}

	@Test
	public void test_should_query_for_object_using_row_mapper() {
		String sql = "SELECT * FROM foo";
		RowMapper mapper = mock(RowMapper.class);
		Object obj = mock(Object.class);
		when(jdbcTemplate.queryForObject(sql, mapper)).thenReturn(obj);

		Object result = databaseTest.query(sql, mapper);

		assertThat(result).isNotNull().isEqualTo(obj);
		verify(jdbcTemplate).queryForObject(sql, mapper);
	}

	@Test
	public void test_should_query_for_object_using_row_mapper_and_return_null_if_no_result() {
		String sql = "SELECT * FROM foo";
		RowMapper mapper = mock(RowMapper.class);
		when(jdbcTemplate.queryForObject(sql, mapper)).thenThrow(EmptyResultDataAccessException.class);

		Object result = databaseTest.query(sql, mapper);
		assertThat(result).isNull();
		verify(jdbcTemplate).queryForObject(sql, mapper);
	}

	@Test
	public void test_should_query_for_object_using_class() {
		String sql = "SELECT * FROM foo";
		Class klass = Foo.class;
		Object obj = mock(Object.class);
		when(jdbcTemplate.queryForObject(sql, klass)).thenReturn(obj);

		Object result = databaseTest.query(sql, klass);

		assertThat(result).isNotNull().isEqualTo(obj);
		verify(jdbcTemplate).queryForObject(sql, klass);
	}

	@Test
	public void test_should_query_for_object_using_klass_and_return_null_if_no_result() {
		String sql = "SELECT * FROM foo";
		Class klass = Foo.class;
		when(jdbcTemplate.queryForObject(sql, klass)).thenThrow(EmptyResultDataAccessException.class);

		Object result = databaseTest.query(sql, klass);
		assertThat(result).isNull();
		verify(jdbcTemplate).queryForObject(sql, klass);
	}

	@Test
	public void test_should_query_list_object() {
		String sql = "SELECT * FROM foo";
		RowMapper mapper = mock(RowMapper.class);
		List<Object> objs = asList(mock(Object.class));
		when(jdbcTemplate.query(sql, mapper)).thenReturn(objs);

		List<Object> results = databaseTest.queryList(sql, mapper);

		assertThat(results).isNotNull().isEqualTo(objs);
		verify(jdbcTemplate).query(sql, mapper);
	}

	@Test
	public void test_should_get_last_insertion() {
		// GIVEN
		String sql = "SELECT * FROM foo ORDER BY id DESC";
		RowMapper mapper = mock(RowMapper.class);
		List<Object> objs = asList(mock(Object.class));
		when(jdbcTemplate.query(sql, mapper)).thenReturn(objs);

		// WHEN
		Object result = databaseTest.lastInserted("foo", "id", 0, mapper);

		// THEN
		assertThat(result).isNotNull().isEqualTo(objs.get(0));
		verify(jdbcTemplate).query(sql, mapper);
	}

	@Test
	public void test_should_get_last_insertion_return_null_if_idx_is_out_of_bounds() {
		// GIVEN
		String sql = "SELECT * FROM foo ORDER BY id DESC";
		RowMapper mapper = mock(RowMapper.class);
		List<Object> objs = asList(mock(Object.class));
		when(jdbcTemplate.query(sql, mapper)).thenReturn(objs);

		// WHEN
		Object result = databaseTest.lastInserted("foo", "id", 1, mapper);

		// THEN
		assertThat(result).isNull();
		verify(jdbcTemplate).query(sql, mapper);
	}

	@Test
	public void test_should_get_last_insertion_using_zero_by_default() {
		// GIVEN
		String sql = "SELECT * FROM foo ORDER BY id DESC";
		RowMapper mapper = mock(RowMapper.class);
		List<Object> objs = asList(mock(Object.class), mock(Object.class));
		when(jdbcTemplate.query(sql, mapper)).thenReturn(objs);

		// WHEN
		Object result = databaseTest.lastInserted("foo", "id", mapper);

		// THEN
		assertThat(result).isNotNull().isEqualTo(objs.get(0));
		verify(jdbcTemplate).query(sql, mapper);
	}

	@Test
	public void test_should_get_last_insertion_using_id_and_zero_by_default() {
		// GIVEN
		String sql = "SELECT * FROM foo ORDER BY id DESC";
		RowMapper mapper = mock(RowMapper.class);
		List<Object> objs = asList(mock(Object.class), mock(Object.class));
		when(jdbcTemplate.query(sql, mapper)).thenReturn(objs);

		// WHEN
		Object result = databaseTest.lastInserted("foo", mapper);

		// THEN
		assertThat(result).isNotNull().isEqualTo(objs.get(0));
		verify(jdbcTemplate).query(sql, mapper);
	}

	@Test
	public void test_should_start_hsqldb() {
		// WHEN
		databaseTest.startHsqlDb();

		// THEN
		assertThat(AbstractDatabaseImpl.dbUnit).isNotNull();
	}

	@Test
	public void test_should_stop_hsqldb() throws Exception {
		// // GIVEN
		DBUnit dbUnit = mock(DBUnit.class);
		AbstractDatabaseImpl.dbUnit = dbUnit;

		// WHEN
		AbstractDatabaseImpl.stopHsqlDb();

		// THEN
		verify(dbUnit).tearDown();
	}

	public static final class AbstractDatabaseImpl extends AbstractDatabaseTest {

		public AbstractDatabaseImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
			this.datasource = dataSource;
			this.jdbcTemplate = jdbcTemplate;
		}
	}

	public static final class Foo {

	}
}
