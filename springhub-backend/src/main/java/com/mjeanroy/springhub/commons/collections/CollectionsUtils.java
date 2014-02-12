package com.mjeanroy.springhub.commons.collections;

import java.util.Collection;

public final class CollectionsUtils {

	private CollectionsUtils() {
	}

	/**
	 * Get size of collection.
	 * If collection is null, zero is returned.
	 *
	 * @param collection Collection.
	 * @return Size of collection.
	 */
	public static int size(Collection collection) {
		return collection == null ? 0 : collection.size();
	}

	/**
	 * Check if collection is empty.
	 * If collection is null, true will be returned.
	 *
	 * @param collection Collection.
	 * @return True if collection is empty, false otherwise.
	 */
	public static boolean isEmpty(Collection collection) {
		return size(collection) == 0;
	}

	/**
	 * Check if collection is not empty.
	 * If collection is null, false will be returned.
	 *
	 * @param collection Collection.
	 * @return True if collection is not empty, false otherwise.
	 */
	public static boolean isNotEmpty(Collection collection) {
		return !isEmpty(collection);
	}
}
