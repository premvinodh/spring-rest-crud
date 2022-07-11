package my.play.ground.springrestcrud.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3310875548867748737L;

	public EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}
}
