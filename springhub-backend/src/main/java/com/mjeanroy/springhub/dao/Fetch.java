package com.mjeanroy.springhub.dao;

import com.google.common.base.Objects;

public class Fetch {

	/** Flag to specify type of join (INNER JOIN or LEFT OUTER JOIN). */
	private boolean inner;

	/** Target object. */
	private String target;

	/** Field to fetch. */
	private String field;

	/** Alias of fetch query. */
	private String alias;

	private Fetch(String target, String field) {
		this.inner = true;
		this.target = target;
		this.field = field;
	}

	private Fetch(String target, String field, String alias) {
		this.inner = true;
		this.target = target;
		this.field = field;
		this.alias = alias;
	}

	private Fetch(String target, String field, boolean inner) {
		this.target = target;
		this.inner = inner;
		this.field = field;
	}

	private Fetch(String target, String field, String alias, boolean inner) {
		this.target = target;
		this.inner = inner;
		this.field = field;
		this.alias = alias;
	}

	public static Fetch entry(String target, String field) {
		return new Fetch(target, field);
	}

	public static Fetch entry(String target, String field, String alias) {
		return new Fetch(target, field, alias);
	}

	public static Fetch entry(String target, String field, boolean inner) {
		return new Fetch(target, field, inner);
	}

	public static Fetch entry(String target, String field, String alias, boolean inner) {
		return new Fetch(target, field, alias, inner);
	}

	public String getTarget() {
		return target;
	}

	public String getField() {
		return field;
	}

	public boolean getInner() {
		return inner;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	public String toString() {
		String join = inner ? "INNER JOIN" : "LEFT OUTER JOIN";
		join += " FETCH " + target + "." + field;
		if (alias != null) {
			join += " " + alias;
		}
		return join;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof Fetch) {
			Fetch f = (Fetch) o;
			return Objects.equal(target, f.target) &&
					Objects.equal(field, f.field) &&
					Objects.equal(inner, f.inner);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(target, field, inner);
	}
}
