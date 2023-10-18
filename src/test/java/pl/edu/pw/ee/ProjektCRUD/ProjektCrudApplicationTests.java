package pl.edu.pw.ee.ProjektCRUD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pw.ee.ProjektCRUD.dao.StudentRepository;
import pl.edu.pw.ee.ProjektCRUD.model.Student;
import org.junit.Assert;

import java.sql.Struct;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProjektCrudApplicationTests {

	@Autowired
	private StudentRepository repository;

	@BeforeEach
	public void BeforeEach(){
		repository.deleteAll();
	}

	Comparator<Student> byIndexNumber = new Comparator<Student>() {
		@Override
		public int compare(Student o1, Student o2) {
			return o1.getIndexNumber().compareTo(o2.getIndexNumber());
		}
	};
	@Test
	public void ShouldReadReturnAlreadyAddedStudent(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		repository.save(student);
		//when
		Student studentFromDB = repository.findById(319069).get();
		//then
		Assert.assertEquals(student,studentFromDB);
	}

	@Test
	public void ShouldReadReturnCorrectAlreadyAddedStudentIfManyAreInDB(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		repository.save(student);
		Student otherStudent = new Student( 111111, "Dawid", "Szczepankowski", 21, "02301234357");
		repository.save(otherStudent);
		Student otherStudent2 = new Student( 319420, "Dawid", "Stereńczak", 21, "02301104321");
		repository.save(otherStudent2);
		//when
		Student newStudent = repository.findById(319069).get();
		//then
		Assert.assertEquals(student,newStudent);
	}

	@Test
	public void ShouldReadAllReturnAllStudents(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		repository.save(student);
		Student otherStudent = new Student( 111111, "Dawid", "Szczepankowski", 21, "02301234357");
		repository.save(otherStudent);
		Student otherStudent2 = new Student( 319420, "Dawid", "Stereńczak", 21, "02301104321");
		repository.save(otherStudent2);
		//when
		List<Student> allStudents = repository.findAll();
		allStudents.sort(byIndexNumber);
		//then
		Student[] studentArray = {student,otherStudent,otherStudent2};
		List<Student> expectedList = Arrays.asList(studentArray);
		expectedList.sort(byIndexNumber);
		for(int i=0;i<3;i++) {
			Assert.assertEquals(expectedList.get(i),allStudents.get(i));
		}
	}
	@Test
	public void ShouldReadThrowErrorWhenSpecifiedStudentIsNotInDB(){
		//given
		//when
		//then
		Assert.assertThrows(NoSuchElementException.class,()->repository.findById(319069).get());
	}
	@Test
	public void ShouldCreateAddItemToArray(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		//when
		repository.save(student);
		//then
		Student studentFromDB = repository.findById(319069).get();
		Assert.assertEquals(student,studentFromDB);
	}

	@Test
	public void ShouldCreateReplaceStudentIfItemWithSpecifiedIndexNumberIsAlreadyInDB(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		Student studentWithSameIndex = new Student( 319069, "Piotr", "Wiśniewski", 22, "02301104351");
		//when
		repository.save(student);
		repository.save(studentWithSameIndex);
		//then
		Student studentFromDB = repository.findById(319069).get();
		Assert.assertEquals(studentWithSameIndex,studentFromDB);
	}

	@Test
	public void ShouldCreateThrowErrorIfStudentIsNull(){
		//given
		Student student = null;
		//when
		//then
		Assert.assertThrows(InvalidDataAccessApiUsageException.class,()->repository.save(student));
	}

	@Test
	public void ShouldDeleteRemoveItemFromDB(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		repository.save(student);
		//when
		repository.delete(student);
		//then
		Assert.assertThrows(NoSuchElementException.class,()->repository.findById(319069).get());
	}

	@Test
	public void ShouldDeleteThrowErrorWhenDeletingItemIsNotInDB(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		repository.save(student);
		//when
		Student notExistingStudent = new Student( 319123, "Michał", "Ktokolwiek", 21, "02101104357");
		repository.delete(notExistingStudent);
		//then
		Optional<Student> studentFromDB = repository.findById(319123);
		Assert.assertFalse(studentFromDB.isPresent());
	}

}
