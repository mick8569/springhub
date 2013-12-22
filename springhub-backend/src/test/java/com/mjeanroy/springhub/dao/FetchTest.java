package com.mjeanroy.springhub.dao;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class FetchTest {

	@Test
	public void testDefaultJoinFetch() {
		Fetch fetch = Fetch.entry("foo", "bar");
		assertThat(fetch.toString()).isEqualTo("INNER JOIN FETCH foo.bar");
	}

	@Test
	public void testDefaultJoinFetchWithAlias() {
		Fetch fetch = Fetch.entry("foo", "bar", "bar");
		assertThat(fetch.toString()).isEqualTo("INNER JOIN FETCH foo.bar bar");
	}

	@Test
	public void testInnerJoinFetch() {
		Fetch fetch = Fetch.entry("foo", "bar", true);
		assertThat(fetch.toString()).isEqualTo("INNER JOIN FETCH foo.bar");
	}

	@Test
	public void testInnerJoinFetchWithAlias() {
		Fetch fetch = Fetch.entry("foo", "bar", "bar", true);
		assertThat(fetch.toString()).isEqualTo("INNER JOIN FETCH foo.bar bar");
	}

	@Test
	public void testLeftJoinFetch() {
		Fetch fetch = Fetch.entry("foo", "bar", false);
		assertThat(fetch.toString()).isEqualTo("LEFT OUTER JOIN FETCH foo.bar");
	}

	@Test
	public void testLeftJoinFetchWithAlias() {
		Fetch fetch = Fetch.entry("foo", "bar", "bar", false);
		assertThat(fetch.toString()).isEqualTo("LEFT OUTER JOIN FETCH foo.bar bar");
	}
}
