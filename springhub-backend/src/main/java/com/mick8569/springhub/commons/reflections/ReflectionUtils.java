package com.mick8569.springhub.commons.reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ReflectionUtils {

	/**
	 * Get generic type of given class at specified index.
	 *
	 * @param clazz Class.
	 * @param index Index.
	 * @return Generic type.
	 */
	public static Class<?> getGenericType(Class<?> clazz, int index) {
		Type generic = clazz.getGenericSuperclass();
		if (generic == null) {
			return null;
		}

		Class<?> current = clazz;
		while (!(generic instanceof ParameterizedType)) {
			current = current.getSuperclass();
			generic = current.getGenericSuperclass();
			if (current.equals(Object.class)) {
				return null;
			}
		}

		return (Class<?>) ((ParameterizedType) generic).getActualTypeArguments()[index];
	}
}
