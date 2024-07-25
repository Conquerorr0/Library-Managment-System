package application;

import java.time.LocalDate;

public abstract class UserDetails {
	protected String fullName;
	protected String password;
	protected String mail;
	private LocalDate registrationDate;
	public UserDetails(String fullName, String password, String mail, LocalDate registrationDate) {
		this.fullName = fullName;
		this.password = password;
		this.mail = mail;
		this.registrationDate = registrationDate;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	

	protected LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}


	public abstract String getDetails();

}
