package com.mick8569.springhub.test.dao;

import com.mick8569.springhub.test.db.AbstractDatabaseTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public abstract class AbstractDaoTest extends AbstractDatabaseTest {

	@BeforeClass
	public static void beforeClass() throws Exception {
	}

	@AfterClass
	public static void afterClass() throws Exception {
		stopHsqlDb();
	}

	@Before
	public void setUp() throws Exception {
		startHsqlDb();
	}

	@After
	public void tearDown() throws Exception {
	}
}
