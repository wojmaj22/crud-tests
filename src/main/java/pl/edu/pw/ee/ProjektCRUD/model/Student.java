package pl.edu.pw.ee.ProjektCRUD.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "student")
@NoArgsConstructor
public class Student {

	@Id
	@Column(name = "index_number")
	private Integer indexNumber;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "age")
	private Integer age;
	@Column(name = "pesel")
	private String pesel;
	
	public Student(Integer indexNumber, String firstName, String lastName, Integer age, String pesel) {
		this.indexNumber = indexNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.pesel = pesel;
	}
}
