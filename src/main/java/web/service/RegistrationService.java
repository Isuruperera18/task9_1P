package web.service;

/**
 * Business logic to handle registration functions.
 * 
 * @author Ahsan.
 */
public class RegistrationService {

	public static boolean register(
			String fName, String lName, String email, String dob) {
		System.out.println("First name: " + fName);
		System.out.println("Last name: " + lName);
		System.out.println("Email: " + email);
		System.out.println("DoB (yyyy-mm-dd): " + dob);
		return true;
	}
}
