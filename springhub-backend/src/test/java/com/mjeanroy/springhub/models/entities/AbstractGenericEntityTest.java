package com.mjeanroy.springhub.models.entities;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class AbstractGenericEntityTest {

	@Test
	public void test_equals() {
		FooEntity1 foo1 = new FooEntity1(1L);
		FooEntity1 foo2 = new FooEntity1(1L);
		FooEntity1 foo3 = new FooEntity1(1L);
		FooEntity1 foo4 = new FooEntity1(10L);

		FooEntity1 foo6 = new FooEntity1(null);
		FooEntity2 foo5 = new FooEntity2(1L);

		// Symetrics
		Assertions.assertThat(foo1.equals(null)).isFalse();
		Assertions.assertThat(foo1.equals(foo1)).isTrue();

		// Reflexive
		Assertions.assertThat(foo1.equals(foo2)).isTrue();
		Assertions.assertThat(foo2.equals(foo1)).isTrue();

		// Transitive
		Assertions.assertThat(foo2.equals(foo3)).isTrue();
		Assertions.assertThat(foo1.equals(foo3)).isTrue();

		// Check
		Assertions.assertThat(foo1.equals(foo4)).isFalse();
		Assertions.assertThat(foo4.equals(foo1)).isFalse();
		Assertions.assertThat(foo1.equals(foo6)).isFalse();
		Assertions.assertThat(foo6.equals(foo1)).isFalse();
		Assertions.assertThat(foo1.equals(foo5)).isFalse();
		Assertions.assertThat(foo5.equals(foo1)).isFalse();
	}

	@Test
	public void test_hashCode() {
		FooEntity1 foo1 = new FooEntity1(1L);
		FooEntity1 foo2 = new FooEntity1(1L);
		Assertions.assertThat(foo1.hashCode()).isEqualTo(foo2.hashCode());
	}

	@Test
	public void test_isNew() {
		FooEntity1 foo1 = new FooEntity1(1L);
		FooEntity1 foo2 = new FooEntity1(null);

		Assertions.assertThat(foo1.isNew()).isFalse();
		Assertions.assertThat(foo2.isNew()).isTrue();
	}

	private static class FooEntity1 extends AbstractGenericEntity {
		private Long id;

		public FooEntity1(Long id) {
			this.id = id;
		}

		@Override
		public Long entityId() {
			return id;
		}
	}

	private static class FooEntity2 extends AbstractGenericEntity {
		private Long id;

		public FooEntity2(Long id) {
			this.id = id;
		}

		@Override
		public Long entityId() {
			return id;
		}
	}
}
