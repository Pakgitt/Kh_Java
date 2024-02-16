package inheritsample;

public abstract class Person {
	private String name;

	public Person() {

	}

	public Person(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract void out();
//	public abstract void out2();
//	public abstract void out3();

}
