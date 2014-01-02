package com.mjeanroy.springhub.test.db;

import javax.sql.DataSource;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractDatabaseTest {

	/** Class logger */
	private static final Logger log = LoggerFactory.getLogger(AbstractDatabaseTest.class);

	/** DBUnit database tester handler */
	protected static IDatabaseTester databaseTester;

	/** Datasource configured on databaseTester */
	protected static DataSource databaseDatasource;

	/** Flag that check if tests database has been initialized or not */
	protected static boolean initialized = false;

	/** Test datasource */
	@Autowired
	protected DataSource datasource;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	/**
	 * Stop in memory database.
	 *
	 * @throws Exception
	 */
	protected static void stopHsqlDb() throws Exception {
		if (databaseTester != null) {
			log.info("Stop in memory database");

			databaseTester.onTearDown();
			databaseTester.getConnection().close();
			databaseDatasource.getConnection().close();

			databaseTester = null;
			databaseDatasource = null;
			initialized = false;
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

	/**
	 * Start in-memory database and load all datasets.<br> If database was already initialized, it will not be initialized twice.
	 *
	 * @throws Exception
	 */
	protected void startHsqlDb() throws Exception {
		if (!initialized) {
			log.info("Start in-memory database");

			// Initialize connection to tests database
			databaseTester = new DataSourceDatabaseTester(datasource);
			databaseDatasource = datasource;

			databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
			databaseTester.setTearDownOperation(DatabaseOperation.CLOSE_CONNECTION(DatabaseOperation.DELETE_ALL));

			// Load datasets
			String[] xmlDatasets = getDataSetFilename();
			IDataSet[] datasets = new IDataSet[xmlDatasets.length];
			for (int i = 0; i < xmlDatasets.length; i++) {
				String nameDataset = xmlDatasets[i];

				log.debug("Load dataset : " + nameDataset);
				FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
				builder.setColumnSensing(true);
				IDataSet dataSet = builder.build(AbstractDatabaseTest.class.getResourceAsStream(pathDbunitDatasets() + nameDataset));

				log.debug("Replace null values in dataset : " + nameDataset);
				ReplacementDataSet dataSetReplacement = new ReplacementDataSet(dataSet);
				dataSetReplacement.addReplacementObject("[NULL]", null);

				datasets[i] = dataSetReplacement;
			}

			databaseTester.setDataSet(new CompositeDataSet(datasets));
			databaseTester.onSetup();

			initialized = true;
		}
	}

	/**
	 * Count number of rows in a table.
	 *
	 * @param tableName Table name.
	 * @return Number of rows in table.
	 *
	 * @throws Exception
	 */
	public int count(String tableName) throws Exception {
		return countSql("SELECT COUNT(*) FROM " + tableName);
	}

	/**
	 * Count number of rows in a table.
	 *
	 * @param sql SQL query to execute
	 * @return Number of rows in table.
	 *
	 * @throws Exception
	 */
	public int countSql(String sql) throws Exception {
		Integer count = query(sql, Integer.class);
		return count == null ? 0 : count;
	}

	/**
	 * Query database.
	 *
	 * @param sql    SQL Query.
	 * @param mapper Mapper to return object associated to database result.
	 * @param <T>    Type of object to return.
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
	 * @param sql   SQL Query.
	 * @param klass Class of returned object.
	 * @param <T>   Type of object to return.
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
	 * @param sql    SQL Query.
	 * @param mapper Row mapper.
	 * @param <T>    Type of object to return.
	 * @return Result list.
	 */
	public <T> List<T> queryList(String sql, RowMapper<T> mapper) {
		return jdbcTemplate.query(sql, mapper);
	}

	/**
	 * Get all datasets to load in database.<br />
	 *
	 * @return Array of all datasets' names.
	 */
	protected String[] getDataSetFilename() throws Exception {
		URL url = getClass().getResource(pathDbunitDatasets());
		URI uri = url.toURI();
		File directory = (new File(uri));

		List<File> files = new ArrayList<File>(FileUtils.listFiles(directory, new String[]{"xml"}, false));

		// Order by name
		Collections.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		String[] results = new String[files.size()];
		int i = 0;
		for (File file : files) {
			results[i] = file.getName();
			i++;
		}

		return results;
	}
}
