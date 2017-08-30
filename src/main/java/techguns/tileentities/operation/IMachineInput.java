package techguns.tileentities.operation;

public interface IMachineInput<T> {
	public boolean matches(T other);
}
