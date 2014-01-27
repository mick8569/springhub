package com.mjeanroy.springhub.test.db;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SuppressWarnings("unchecked")
public class AbstractDatabaseTestTest {

	private AbstractDatabaseImpl databaseTest;

	private JdbcTemplate jdbcTemplate;

	private DataSource dataSource;

	private Connection dataSourceConnection;

	private DataSourceDatabaseTester dataSourceDatabaseTester;

	private IDatabaseConnection dataSourceDatabaseTesterConnection;

	@Before
	public void setUp() throws Exception {
		jdbcTemplate = mock(JdbcTemplate.class);

		dataSourceDatabaseTester = mock(DataSourceDatabaseTester.class);
		dataSourceDatabaseTesterConnection = mock(IDatabaseConnection.class);
		when(dataSourceDatabaseTester.getConnection()).thenReturn(dataSourceDatabaseTesterConnection);

		dataSource = mock(DataSource.class);
		dataSourceConnection = mock(Connection.class);
		when(dataSource.getConnection()).thenReturn(dataSourceConnection);

		databaseTest = new AbstractDatabaseImpl(dataSource, jdbcTemplate, dataSourceDatabaseTester);

		AbstractDatabaseImpl.initialized = false;
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
	public void test_get_dataset_filename_should_return_dataset_ordered_by_name() {
		String[] dataset = databaseTest.getDataSetFilename();
		assertThat(dataset).isNotNull().hasSize(2).isSorted().contains(
				"01-foo.xml",
				"02-bar.xml"
		);
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
	public void test_should_start_hsqldb() throws Exception {
		databaseTest.startHsqlDb();

		assertThat(databaseTest.datasource).isEqualTo(dataSource);
		assertThat(AbstractDatabaseImpl.initialized).isTrue();
		verify(dataSourceDatabaseTester).setSetUpOperation(DatabaseOperation.CLEAN_INSERT);

		ArgumentCaptor<IDataSet> argDataSet = ArgumentCaptor.forClass(IDataSet.class);
		verify(dataSourceDatabaseTester).setDataSet(argDataSet.capture());
		verify(dataSourceDatabaseTester).onSetup();

		IDataSet dataSet = argDataSet.getValue();
		assertThat(dataSet).isExactlyInstanceOf(CompositeDataSet.class);

		CompositeDataSet compositeDataSet = (CompositeDataSet) dataSet;
		ITable[] tables = compositeDataSet.getTables();
		assertThat(tables).isNotEmpty().hasSize(2);
	}

	@Test
	public void test_should_not_start_hsqldb_twice() throws Exception {
		databaseTest.startHsqlDb();
		databaseTest.startHsqlDb();

		verify(dataSourceDatabaseTester, times(1)).setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		verify(dataSourceDatabaseTester, times(1)).setDataSet(any(IDataSet.class));
		verify(dataSourceDatabaseTester, times(1)).onSetup();
	}

	@Test
	public void test_should_stop_hsqldb() throws Exception {
		AbstractDatabaseImpl.initialize(dataSource, dataSourceDatabaseTester);
		AbstractDatabaseImpl.stopHsqlDb();

		verify(dataSourceDatabaseTester).onTearDown();
		verify(dataSourceConnection).close();
		verify(dataSourceDatabaseTesterConnection).close();
		assertThat(AbstractDatabaseImpl.databaseDatasource).isNull();
		assertThat(AbstractDatabaseImpl.databaseTester).isNull();
		assertThat(AbstractDatabaseImpl.initialized).isFalse();
	}

	public static final class AbstractDatabaseImpl extends AbstractDatabaseTest {

		private DataSourceDatabaseTester tester;

		public AbstractDatabaseImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, DataSourceDatabaseTester databaseTester) {
			this.datasource = dataSource;
			this.jdbcTemplate = jdbcTemplate;
			this.tester = databaseTester;
		}

		public static void initialize(DataSource dataSource, DataSourceDatabaseTester tester) {
			initialized = true;
			databaseTester = tester;
			databaseDatasource = dataSource;
		}

		@Override
		public IDatabaseTester buildDatabaseTester(DataSource dataSource) {
			return tester;
		}
	}

	public static final class Foo {

	}
}
