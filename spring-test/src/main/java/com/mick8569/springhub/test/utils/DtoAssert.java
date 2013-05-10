package com.mick8569.springhub.test.utils;

import com.mick8569.springhub.dto.AbstractDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

public class DtoAssert extends AbstractAssert<DtoAssert, AbstractDto> {

	private static List<Class> primitiveTypes = new ArrayList<Class>() {{
		add(String.class);
		add(Integer.class);
		add(Long.class);
		add(Float.class);
		add(Boolean.class);
		add(Double.class);
		add(Date.class);
	}};

	public DtoAssert(AbstractDto actual) {
		super(actual, DtoAssert.class);
	}

	public static DtoAssert assertThat(AbstractDto actual) {
		return new DtoAssert(actual);
	}

	@SuppressWarnings("unchecked")
	private static void compare(Object obj1, Object obj2) throws Exception {
		for (PropertyDescriptor pd : Introspector.getBeanInfo(obj1.getClass()).getPropertyDescriptors()) {
			if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
				Object val1 = pd.getReadMethod().invoke(obj1);
				Object val2 = PropertyUtils.getProperty(obj2, pd.getName());

				if (val1 == null) {
					Assertions
							.assertThat(val2)
							.overridingErrorMessage("Field '<%s>' is null in DTO but not in its opposite", pd.getName())
							.isNull();
				} else {
					Class klazz1 = val1.getClass();
					Class klazz2 = val2.getClass();
					if ((klazz1.isAssignableFrom(String.class) && (!klazz2.isAssignableFrom(String.class)))) {
						Assertions
								.assertThat(val1.toString())
								.overridingErrorMessage("To String value of fields '<%s>' are not equals", pd.getName())
								.isEqualTo(val2.toString());
					} else if (primitiveTypes.contains(klazz1)) {
						Assertions
								.assertThat(val1)
								.overridingErrorMessage("Value of fields '<%s>' are not equals", pd.getName())
								.isEqualTo(val2);
					} else if (klazz1.isEnum()) {
						Assertions
								.assertThat(val1.toString())
								.overridingErrorMessage("Enum value of fields '<%s>' are not equals", pd.getName())
								.isEqualTo(val2.toString());
					} else if (!(Collection.class.isAssignableFrom(klazz1))) {
						DtoAssert.compare(val1, val2);
					} else if (Collection.class.isAssignableFrom(klazz1)) {
						Assertions
								.assertThat(Collection.class.isAssignableFrom(klazz2))
								.overridingErrorMessage("Field '<%s>' is a collection but not its opposite", pd.getName())
								.isTrue();

						Collection c1 = (Collection) val1;
						Collection c2 = (Collection) val2;
						Assertions
								.assertThat(c1)
								.hasSameSizeAs(c2);

						Iterator i1 = c1.iterator();
						Iterator i2 = c1.iterator();
						while (i1.hasNext()) {
							Object o1 = i1.next();
							Object o2 = i2.next();
							DtoAssert.compare(o1, o2);
						}
					}
				}
			}
		}
	}

	public DtoAssert isEquivalent(Object obj) {
		isNotNull();

		try {
			compare(actual, obj);
		} catch (Exception ex) {
			Assertions.fail(ex.getMessage());
		}

		return this;
	}
}
