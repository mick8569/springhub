package com.mick8569.springhub.test.db;

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

import javax.sql.DataSource;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.*;

public abstract class AbstractDatabaseTest {

	/** Class logger */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractDatabaseTest.class);

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
			LOG.info("Stop in memory database");

			databaseTester.onTearDown();
			databaseTester.getConnection().close();
			databaseDatasource.getConnection().close();

			databaseTester = null;
			databaseDatasource = null;
			initialized = false;
		}
	}

	/**
	 * Path (relative to classpath) where dbunit datasets are stored.
	 *
	 * @return
	 */
	public String pathDbunitDatasets() {
		return "/dbunit/datasets/";
	}

	/**
	 * Start in-memory database and load all datasets.<br>
	 * If database was already initialized, it will not be initialized twice.
	 *
	 * @throws Exception
	 */
	protected void startHsqlDb() throws Exception {
		if (!initialized) {
			LOG.info("Start in-memory database");

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

				LOG.debug("Load dataset : " + nameDataset);
				FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
				builder.setColumnSensing(true);
				IDataSet dataSet = builder.build(AbstractDatabaseTest.class.getResourceAsStream(pathDbunitDatasets() + nameDataset));

				LOG.debug("Replace null values in dataset : " + nameDataset);
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
	 * @throws Exception
	 */
	public int count(String tableName) throws Exception {
		return jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + tableName);
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
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
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
