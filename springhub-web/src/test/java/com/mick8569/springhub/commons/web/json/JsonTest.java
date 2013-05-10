package com.mick8569.springhub.commons.web.json;

import org.fest.assertions.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

public class JsonTest {

	@Test
	public void testFromJson() throws Exception {
		String json = "{\"id\":134}";
		Foo foo = Json.fromJson(json, Foo.class);
		Assertions.assertThat(foo).isNotNull();
		Assertions.assertThat(foo.getId()).isNotNull().isEqualTo(134);
	}

	@Test
	public void testToJson() throws Exception {
		Foo foo = new Foo();
		foo.setId(134L);
		String json = Json.toJson(foo);
		Assert.assertEquals("{\"id\":134}", json);
	}

	private static class Foo {
		private Long id;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}
}
