package com.mjeanroy.springhub.commons.collections;

import org.junit.Test;

import java.util.Collection;

import static com.mjeanroy.springhub.commons.collections.CollectionsUtils.isEmpty;
import static com.mjeanroy.springhub.commons.collections.CollectionsUtils.isNotEmpty;
import static com.mjeanroy.springhub.commons.collections.CollectionsUtils.size;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.fest.assertions.api.Assertions.assertThat;

public class CollectionsUtilsTest {

	@Test
	public void size_should_return_zero_if_collection_is_null() {
		// GIVEN
		Collection collection = null;

		// WHEN
		int size = size(collection);

		// THEN
		assertThat(size).isZero();
	}

	@Test
	public void size_should_return_zero_if_collection_is_empty() {
		// GIVEN
		Collection collection = emptyList();

		// WHEN
		int size = size(collection);

		// THEN
		assertThat(size).isZero();
	}

	@Test
	public void size_should_return_size_of_collection() {
		// GIVEN
		Collection<Integer> collection = asList(1, 2, 3);

		// WHEN
		int size = size(collection);

		// THEN
		assertThat(size).isEqualTo(collection.size());
	}

	@Test
	public void isEmpty_should_return_true_if_collection_is_null() {
		// GIVEN
		Collection collection = null;

		// WHEN
		boolean isEmpty = isEmpty(collection);

		// THEN
		assertThat(isEmpty).isTrue();
	}

	@Test
	public void isEmpty_should_return_true_if_collection_is_empty() {
		// GIVEN
		Collection collection = emptyList();

		// WHEN
		boolean isEmpty = isEmpty(collection);

		// THEN
		assertThat(isEmpty).isTrue();
	}

	@Test
	public void isEmpty_should_return_true_if_collection_is_not_empty() {
		// GIVEN
		Collection<Integer> collection = asList(1, 2, 3);

		// WHEN
		boolean isEmpty = isEmpty(collection);

		// THEN
		assertThat(isEmpty).isFalse();
	}

	@Test
	public void isNotEmpty_should_return_false_if_collection_is_null() {
		// GIVEN
		Collection collection = null;

		// WHEN
		boolean isNotEmpty = isNotEmpty(collection);

		// THEN
		assertThat(isNotEmpty).isFalse();
	}

	@Test
	public void isNotEmpty_should_return_false_if_collection_is_empty() {
		// GIVEN
		Collection collection = emptyList();

		// WHEN
		boolean isNotEmpty = isNotEmpty(collection);

		// THEN
		assertThat(isNotEmpty).isFalse();
	}

	@Test
	public void isNotEmpty_should_return_true_if_collection_is_not_empty() {
		// GIVEN
		Collection<Integer> collection = asList(1, 2, 3);

		// WHEN
		boolean isNotEmpty = isNotEmpty(collection);

		// THEN
		assertThat(isNotEmpty).isTrue();
	}
}
