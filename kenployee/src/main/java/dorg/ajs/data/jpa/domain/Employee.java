package dorg.ajs.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "EMPLOYEES")
@ApiModel(description = "All details about the Employee. ")
public class Employee {
	@ApiModelProperty(notes = "The database generated employee ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(notes = "The employee's first name")
	@Column(name = "first_name")
	private String firstName;

	@ApiModelProperty(notes = "The employee's middle initial. 5chrs max")
	@Column(name = "middle_i")
	private String middleInitial;

	@ApiModelProperty(notes = "The employee's surname.")
	@Column(name = "last_name")
	private String lastName;

	@ApiModelProperty(notes = "The employee's date of birth.")
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@ApiModelProperty(notes = "The employee's date of hire.")
	@Column(name = "date_of_employment")
	private LocalDate dateOfEmployment;

	@ApiModelProperty(notes = "A status indicating if the employee is 'active' on the payrol.")
	@Column(name = "active_status")
	private boolean activeStatus = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns value of middleInitial
	 *
	 * @return
	 */
	public String getMiddleInitial() {
		return middleInitial;
	}

	/**
	 * Sets new value of middleInitial
	 *
	 * @param
	 */
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	/**
	 * Returns value of dateOfBirth
	 *
	 * @return
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets new value of dateOfBirth
	 *
	 * @param
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Returns value of activeStatus
	 *
	 * @return
	 */
	public boolean isActiveStatus() {
		return activeStatus;
	}

	/**
	 * Sets new value of activeStatus
	 *
	 * @param
	 */
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	/**
	 * Default empty Employee constructor
	 */
	public Employee() {
		super();
	}

	/**
	 * Default Employee constructor
	 */
	public Employee(String firstName, String middleInitial, String lastName, LocalDate dateOfBirth,
			LocalDate dateOfEmployment, boolean activeStatus) {
		super();
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.dateOfEmployment = dateOfEmployment;
		this.activeStatus = activeStatus;
	}

	/**
	 * Returns value of dateOfEmployment
	 *
	 * @return
	 */
	public LocalDate getDateOfEmployment() {
		return dateOfEmployment;
	}

	/**
	 * Sets new value of dateOfEmployment
	 *
	 * @param
	 */
	public void setDateOfEmployment(LocalDate dateOfEmployment) {
		this.dateOfEmployment = dateOfEmployment;
	}

	/**
	 * Create string representation of Employee for printing
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", middleInitial=" + middleInitial + ", lastName="
				+ lastName + ", dateOfBirth=" + dateOfBirth + ", dateOfEmployment=" + dateOfEmployment + ", activeStatus="
				+ activeStatus + "]";
	}
}
