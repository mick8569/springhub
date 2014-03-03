package com.mjeanroy.springhub.test.db;

import javax.sql.DataSource;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mjeanroy.springhub.test.dbunit.DBUnit;
import com.mjeanroy.springhub.test.exceptions.InMemoryDatabaseException;

public abstract class AbstractDatabaseTest {

	/** Class logger */
	private static final Logger log = LoggerFactory.getLogger(AbstractDatabaseTest.class);

	/** DBUnit instance. */
	protected static DBUnit dbUnit = null;

	/** Test datasource */
	@Autowired
	protected DataSource datasource;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	/** Stop in memory database. */
	protected static void stopHsqlDb() {
		if (dbUnit != null) {
			dbUnit.tearDown();
			dbUnit = null;
		}
	}

	/**
	 * Path (relative to classpath) where dbunit dataset are stored.
	 *
	 * @return Default path for dbunit dataset.
	 */
	public String pathDbunitDatasets() {
		return "/dbunit/datasets/";
	}

	/** Start in-memory database and load all datasets. If database was already initialized, it will not be initialized twice. */
	protected void startHsqlDb() {
		if (dbUnit == null) {
			try {
				dbUnit = new DBUnit(datasource)
						.addDirectory(pathDbunitDatasets())
						.setUp();
			}
			catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				throw new InMemoryDatabaseException(ex);
			}
		}
	}

	/**
	 * Count number of rows in a table.
	 *
	 * @param tableName Table name.
	 * @return Number of rows in table.
	 */
	public int count(String tableName) {
		return countSql("SELECT COUNT(*) FROM " + tableName);
	}

	/**
	 * Count number of rows in a table.
	 *
	 * @param sql SQL query to execute
	 * @return Number of rows in table.
	 */
	public int countSql(String sql) {
		log.debug("Execute query : {}", sql);
		Integer count = query(sql, Integer.class);
		return count == null ? 0 : count;
	}

	/**
	 * Query database.
	 *
	 * @param sql SQL Query.
	 * @param mapper Mapper to return object associated to database result.
	 * @param <T> Type of object to return.
	 * @return Object, null if cannot be found.
	 */
	public <T> T query(String sql, RowMapper<T> mapper) {
		try {
			return jdbcTemplate.queryForObject(sql, mapper);
		}
		catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	/**
	 * Query database.
	 *
	 * @param sql SQL Query.
	 * @param klass Class of returned object.
	 * @param <T> Type of object to return.
	 * @return Object, null if object cannot be found.
	 */
	public <T> T query(String sql, Class<T> klass) {
		try {
			return jdbcTemplate.queryForObject(sql, klass);
		}
		catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	/**
	 * Query database and return result list.
	 *
	 * @param sql SQL Query.
	 * @param mapper Row mapper.
	 * @param <T> Type of object to return.
	 * @return Result list.
	 */
	public <T> List<T> queryList(String sql, RowMapper<T> mapper) {
		return jdbcTemplate.query(sql, mapper);
	}

	/**
	 * Get last insertion using "id" as column name to sort data.
	 * Null is returned if insertion is not found.
	 *
	 * @param tableName Table name.
	 * @param mapper Object mapper.
	 * @param <T> Type of object to return.
	 * @return Last insertion, null if insertion does not exist.
	 */
	public <T> T lastInserted(String tableName, RowMapper<T> mapper) {
		return lastInserted(tableName, "id", 0, mapper);
	}

	/**
	 * Get last insertion.
	 * Null is returned if insertion is not found.
	 *
	 * @param tableName Table name.
	 * @param keyName Id column name.
	 * @param mapper Object mapper.
	 * @param <T> Type of object to return.
	 * @return Last insertion, null if insertion does not exist.
	 */
	public <T> T lastInserted(String tableName, String keyName, RowMapper<T> mapper) {
		return lastInserted(tableName, keyName, 0, mapper);
	}

	/**
	 * Get last n insertion.
	 * Null is returned if insertion is not found.
	 *
	 * @param tableName Table name.
	 * @param sortName Id column name.
	 * @param idx Last insertion index (use 0 for last insertion, 1 for penultimate insertion etc).
	 * @param mapper Object mapper.
	 * @param <T> Type of object to return.
	 * @return Last insertion, null if insertion does not exist.
	 */
	public <T> T lastInserted(String tableName, String sortName, int idx, RowMapper<T> mapper) {
		String sql = String.format("SELECT * FROM %s ORDER BY %s DESC", tableName, sortName);
		List<T> results = queryList(sql, mapper);
		return results.size() <= idx ? null : results.get(idx);
	}
}
