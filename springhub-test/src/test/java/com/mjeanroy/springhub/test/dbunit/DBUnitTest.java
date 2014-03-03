package com.mjeanroy.springhub.test.dbunit;

import static java.util.Collections.emptyMap;
import static org.apache.commons.io.FileUtils.toFile;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.MapEntry.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.util.Iterator;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementTable;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class DBUnitTest {

	private DataSource dataSource;

	@Before
	public void setUp() {
		dataSource = mock(DataSource.class);
	}

	@Test
	public void should_build_new_dbunit_instance() {
		// WHEN
		DBUnit dbUnit = new DBUnit(dataSource);

		// THEN
		assertThat(dbUnit.dataSource).isNotNull().isEqualTo(dataSource);
		assertThat(dbUnit.xmlDataSet).isNotNull().isEmpty();
		assertThat(dbUnit.replacements).isNotNull().isEmpty();
		assertThat(dbUnit.setUpOperation).isNotNull().isEqualTo(DatabaseOperation.CLEAN_INSERT);
		assertThat(dbUnit.tearDownOperation).isNotNull().isEqualTo(DatabaseOperation.DELETE_ALL);
		assertThat(dbUnit.databaseTester).isNull();
	}

	@Test
	public void addXML_should_add_xml_from_classpath() {
		// GIVEN
		String path = "/dbunit/datasets/01-foo.xml";
		DBUnit dbUnit = new DBUnit(dataSource);

		// WHEN
		DBUnit result = dbUnit.addXML(path);

		// THEN
		assertThat(result).isSameAs(dbUnit);
		assertThat(dbUnit.xmlDataSet).isNotNull().hasSize(1);
		assertThat(dbUnit.xmlDataSet.iterator().next()).exists().canRead();
	}

	@Test
	public void addDirectory_should_add_xml_in_directory() {
		// GIVEN
		String path = "/dbunit/datasets";
		DBUnit dbUnit = new DBUnit(dataSource);

		// WHEN
		DBUnit result = dbUnit.addDirectory(path);

		// THEN
		assertThat(result).isSameAs(dbUnit);
		assertThat(dbUnit.xmlDataSet).isNotNull().hasSize(2);

		Iterator<File> iterator = dbUnit.xmlDataSet.iterator();
		assertThat(iterator.next()).exists().canRead();
		assertThat(iterator.next()).exists().canRead();
	}

	@Test
	public void addFile_should_add_single_file() {
		// GIVEN
		String path = "/dbunit/datasets/02-bar.xml";
		URL url = getClass().getResource(path);
		File file = toFile(url);
		DBUnit dbUnit = new DBUnit(dataSource);

		// WHEN
		DBUnit result = dbUnit.addFile(file);

		// THEN
		assertThat(result).isSameAs(dbUnit);
		assertThat(dbUnit.xmlDataSet).isNotNull().hasSize(1);

		Iterator<File> iterator = dbUnit.xmlDataSet.iterator();
		assertThat(iterator.next()).exists().canRead().isEqualTo(file);
	}

	@Test
	public void addFile_should_add_directory() {
		// GIVEN
		String path = "/dbunit/datasets";
		URL url = getClass().getResource(path);
		File file = toFile(url);
		DBUnit dbUnit = new DBUnit(dataSource);

		// WHEN
		DBUnit result = dbUnit.addFile(file);

		// THEN
		assertThat(result).isSameAs(dbUnit);
		assertThat(dbUnit.xmlDataSet).isNotNull().hasSize(2);
	}

	@Test
	public void addReplacement_should_add_replacement_value() {
		// GIVEN
		String key = "foo";
		String value = "bar";
		DBUnit dbUnit = new DBUnit(dataSource);

		// WHEN
		DBUnit result = dbUnit.addReplacement(key, value);

		// THEN
		assertThat(result).isSameAs(dbUnit);
		assertThat(dbUnit.replacements).isNotNull().hasSize(1).contains(
				entry(key, value)
		);
	}

	@Test
	public void setSetUpOperation_should_replace_set_up_operation() {
		// GIVEN
		DatabaseOperation operation = DatabaseOperation.NONE;
		DBUnit dbUnit = new DBUnit(dataSource);

		// WHEN
		DBUnit result = dbUnit.setSetUpOperation(operation);

		// THEN
		assertThat(result).isSameAs(dbUnit);
		assertThat(dbUnit.setUpOperation).isNotNull().isEqualTo(operation);
	}

	@Test
	public void setTearDownOperation_should_replace_tear_down_operation() {
		// GIVEN
		DatabaseOperation operation = DatabaseOperation.NONE;
		DBUnit dbUnit = new DBUnit(dataSource);

		// WHEN
		DBUnit result = dbUnit.setTearDownOperation(operation);

		// THEN
		assertThat(result).isSameAs(dbUnit);
		assertThat(dbUnit.tearDownOperation).isNotNull().isEqualTo(operation);
	}

	@Test
	public void loadDataSet_should_load_data_set_without_replacements() throws Exception {
		// GIVEN
		DBUnit dbUnit = new DBUnit(dataSource);
		dbUnit.replacements = emptyMap();

		String path1 = "/dbunit/datasets/01-foo.xml";
		URL url1 = getClass().getResource(path1);
		File file1 = toFile(url1);

		String path2 = "/dbunit/datasets/02-bar.xml";
		URL url2 = getClass().getResource(path2);
		File file2 = toFile(url2);

		dbUnit.xmlDataSet.add(file1);
		dbUnit.xmlDataSet.add(file2);

		// WHEN
		IDataSet dataSet = dbUnit.loadDataSet();

		// THEN
		assertThat(dataSet).isNotNull();
		assertThat(dataSet).isInstanceOf(CompositeDataSet.class);

		CompositeDataSet compositeDataSet = (CompositeDataSet) dataSet;

		ITable[] tables = compositeDataSet.getTables();
		assertThat(tables).isNotEmpty().hasSize(2);
		assertThat(tables[0]).isInstanceOf(DefaultTable.class);
		assertThat(tables[1]).isInstanceOf(DefaultTable.class);
	}

	@Test
	public void loadDataSet_should_load_data_set_with_replacements() throws Exception {
		// GIVEN
		DBUnit dbUnit = new DBUnit(dataSource);

		String path1 = "/dbunit/datasets/01-foo.xml";
		URL url1 = getClass().getResource(path1);
		File file1 = toFile(url1);

		String path2 = "/dbunit/datasets/02-bar.xml";
		URL url2 = getClass().getResource(path2);
		File file2 = toFile(url2);

		dbUnit.xmlDataSet.add(file1);
		dbUnit.xmlDataSet.add(file2);
		dbUnit.replacements.put("[NULL]", "foo");

		// WHEN
		IDataSet dataSet = dbUnit.loadDataSet();

		// THEN
		assertThat(dataSet).isNotNull();
		assertThat(dataSet).isInstanceOf(CompositeDataSet.class);

		CompositeDataSet compositeDataSet = (CompositeDataSet) dataSet;

		ITable[] tables = compositeDataSet.getTables();
		assertThat(tables).isNotEmpty().hasSize(2);
		assertThat(tables[0]).isInstanceOf(ReplacementTable.class);
		assertThat(tables[1]).isInstanceOf(ReplacementTable.class);
	}

	@Test
	public void isSetUp_should_return_true_if_database_tester_is_not_null() {
		// GIVEN
		DBUnit dbUnit = new DBUnit(dataSource);
		dbUnit.databaseTester = mock(IDatabaseTester.class);

		// WHEN
		boolean isSetUp = dbUnit.isSetUp();

		// THEN
		assertThat(isSetUp).isTrue();
	}

	@Test
	public void isSetUp_should_return_false_if_database_tester_is_null() {
		// GIVEN
		DBUnit dbUnit = new DBUnit(dataSource);
		dbUnit.databaseTester = null;

		// WHEN
		boolean isSetUp = dbUnit.isSetUp();

		// THEN
		assertThat(isSetUp).isFalse();
	}

	@Test
	public void setUp_should_setup_db_unit_data_set() {
		// GIVEN
		EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.build();

		DBUnit dbUnit = new DBUnit(db);
		dbUnit.databaseTester = null;

		// WHEN
		DBUnit result = dbUnit.setUp();

		// THEN
		assertThat(result).isSameAs(dbUnit);
		assertThat(dbUnit.databaseTester).isNotNull().isInstanceOf(DataSourceDatabaseTester.class);
		db.shutdown();
	}

	@Test
	public void tearDown_should_tear_down_db_unit_data_set() throws Exception {
		// GIVEN
		DBUnit dbUnit = new DBUnit(dataSource);
		IDatabaseTester tester = mock(IDatabaseTester.class);
		dbUnit.databaseTester = tester;

		// WHEN
		DBUnit result = dbUnit.tearDown();

		// THEN
		assertThat(result).isSameAs(dbUnit);
		assertThat(dbUnit.databaseTester).isNull();
		verify(tester).onTearDown();
	}
}
