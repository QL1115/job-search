package com.ql.jobsearch.pojo;

public class Keyword {

	private String name;

	private int count;

	private double weight;

	public Keyword() {}

	public Keyword(String name) {
		this(name, 0);
	}

	public Keyword(String name, int count)  {
		this.name = name;
		this.count = count;
	}

	public Keyword(String name, double weight) {
		this(name);
		this.weight = weight;
	}

	public Keyword(String name, int count, double weight) {
		this(name, count);
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "[" + name + ", " + count + "] ";
	}
}
