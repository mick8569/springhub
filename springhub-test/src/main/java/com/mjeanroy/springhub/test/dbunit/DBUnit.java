package com.mjeanroy.springhub.test.dbunit;

import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.io.FileUtils.toFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mjeanroy.springhub.test.exceptions.DBUnitException;

public class DBUnit {

	/** Class logger. */
	private static final Logger log = LoggerFactory.getLogger(DBUnit.class);

	/** Target data source. */
	protected DataSource dataSource;

	/** XML data set. */
	protected SortedSet<File> xmlDataSet;

	/** Replacement values. */
	protected Map<String, Object> replacements;

	/** Database set up operation. */
	protected DatabaseOperation setUpOperation;

	/** Database tear down operation. */
	protected DatabaseOperation tearDownOperation;

	/**
	 * Build new DBUnit instance.
	 *
	 * @param dataSource Target data source.
	 */
	public DBUnit(DataSource dataSource) {
		this.dataSource = dataSource;
		this.replacements = new HashMap<String, Object>();
		this.setUpOperation = DatabaseOperation.CLEAN_INSERT;
		this.tearDownOperation = DatabaseOperation.DELETE_ALL;
		this.xmlDataSet = new TreeSet<File>(new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
			}
		});
	}

	/**
	 * Replace database set up operation.
	 *
	 * @param operation New operation.
	 * @return this.
	 */
	public DBUnit setSetUpOperation(DatabaseOperation operation) {
		setUpOperation = operation;
		return this;
	}

	/**
	 * Replace database tear down operation.
	 *
	 * @param operation New operation.
	 * @return this.
	 */
	public DBUnit setTearDownOperation(DatabaseOperation operation) {
		tearDownOperation = operation;
		return this;
	}

	/**
	 * Add single XML file.
	 *
	 * @param path Path (relative to classpath).
	 * @return this.
	 */
	public DBUnit addXML(String path) {
		URL url = getClass().getResource(path);
		File xml = toFile(url);
		return addFile(xml);
	}

	/**
	 * Add single XML file.
	 *
	 * @param file File to addFile to current data set.
	 * @return this.
	 */
	public DBUnit addFile(File file) {
		if (file.isDirectory()) {
			xmlDataSet.addAll(listFiles(file, new String[]{"xml"}, true));
		}
		else {
			xmlDataSet.add(file);
		}
		return this;
	}

	/**
	 * Add xml stored in given directory.
	 *
	 * @param directory Directory.
	 * @return this.
	 */
	public DBUnit addDirectory(String directory) {
		URL url = getClass().getResource(directory);
		File dir = toFile(url);
		return addFile(dir);
	}

	/**
	 * Add new replacement value.
	 *
	 * @param key Key.
	 * @param value Value.
	 * @return this.
	 */
	public DBUnit addReplacement(String key, Object value) {
		replacements.put(key, value);
		return this;
	}

	/**
	 * Load DBUnit data set.
	 *
	 * @return Data set.
	 */
	protected IDataSet loadDataSet() {
		try {
			IDataSet[] datasets = new IDataSet[xmlDataSet.size()];
			int i = 0;
			for (File currentFile : xmlDataSet) {
				FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
				builder.setColumnSensing(true);
				InputStream stream = new FileInputStream(currentFile);

				IDataSet dataSet = builder.build(stream);

				if (replacements != null && !replacements.isEmpty()) {
					dataSet = new ReplacementDataSet(dataSet);
					for (Map.Entry<String, Object> replacement : replacements.entrySet()) {
						String key = replacement.getKey();
						Object value = replacement.getValue();
						((ReplacementDataSet) dataSet).addReplacementObject(key, value);
					}
				}

				datasets[i] = dataSet;
				i++;
			}

			return new CompositeDataSet(datasets);
		}
		catch (FileNotFoundException ex) {
			log.error(ex.getMessage(), ex);
			throw new DBUnitException(ex);
		}
		catch (DataSetException ex) {
			log.error(ex.getMessage(), ex);
			throw new DBUnitException(ex);
		}
	}
}
