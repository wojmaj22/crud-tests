package pl.edu.pw.ee.ProjektCRUD;

import jakarta.persistence.EntityNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pw.ee.ProjektCRUD.dao.StudentRepository;
import pl.edu.pw.ee.ProjektCRUD.model.Student;
import pl.edu.pw.ee.ProjektCRUD.service.StudentService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProjektCrudApplicationTests {
	
	private final StudentService crudService;
	
	@Autowired
	public ProjektCrudApplicationTests( StudentService service){
		crudService = service;
	}

	@BeforeEach
	public void BeforeEach(){
		crudService.deleteAll();
	}

	Comparator<Student> byIndexNumber = Comparator.comparing(Student::getIndexNumber);
	
	@Test
	public void ShouldReadReturnAlreadyAddedStudent(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		crudService.save(student);
		//when
		Student studentFromDB = crudService.read(319069);
		//then
		Assert.assertEquals(student,studentFromDB);
	}

	@Test
	public void ShouldReadReturnCorrectAlreadyAddedStudentIfManyAreInDB(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		crudService.save(student);
		Student otherStudent = new Student( 111111, "Dawid", "Szczepankowski", 21, "02301234357");
		crudService.save(otherStudent);
		Student otherStudent2 = new Student( 319420, "Dawid", "Stereńczak", 21, "02301104321");
		crudService.save(otherStudent2);
		//when
		Student newStudent = crudService.read(319069);
		//then
		Assert.assertEquals(student,newStudent);
	}

	@Test
	public void ShouldReadAllReturnAllStudents(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		crudService.save(student);
		Student otherStudent = new Student( 111111, "Dawid", "Szczepankowski", 21, "02301234357");
		crudService.save(otherStudent);
		Student otherStudent2 = new Student( 319420, "Dawid", "Stereńczak", 21, "02301104321");
		crudService.save(otherStudent2);
		//when
		List<Student> allStudents = crudService.readAll();
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
		Assert.assertThrows(EntityNotFoundException.class,()->crudService.read(319069));
	}
	@Test
	public void ShouldCreateAddItemToArray(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		//when
		crudService.save(student);
		//then
		Student studentFromDB = crudService.read(319069);
		Assert.assertEquals(student,studentFromDB);
	}

	@Test
	public void ShouldCreateReplaceStudentIfItemWithSpecifiedIndexNumberIsAlreadyInDB(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		Student studentWithSameIndex = new Student( 319069, "Piotr", "Wiśniewski", 22, "02301104351");
		//when
		crudService.save(student);
		crudService.save(studentWithSameIndex);
		//then
		Student studentFromDB = crudService.read(319069);
		Assert.assertEquals(studentWithSameIndex,studentFromDB);
	}

	@Test
	public void ShouldCreateThrowErrorIfStudentIsNull(){
		//given
		Student student = null;
		//when
		//then
		Assert.assertThrows(InvalidDataAccessApiUsageException.class,()->crudService.save(student));
	}

	@Test
	public void ShouldDeleteRemoveItemFromDB(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		crudService.save(student);
		//when
		crudService.delete(student);
		//then
		Assert.assertThrows(EntityNotFoundException.class,()->crudService.read(319069));
	}

	@Test
	public void ShouldDeleteAllItemsFromDB(){
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		Student student2 = new Student( 319070, "Dawid", "Szczepankowski", 21, "02987104357");
		Student student3 = new Student( 319071, "Piotr", "Cherry", 21, "02306454357");
		crudService.save(student);
		crudService.save(student2);
		crudService.save(student3);
		//when
		crudService.deleteAll();
		//then
		Student[] studentArray = {student,student2,student3};
		for (Student stud:studentArray
			 ) {
			Assert.assertFalse(crudService.existsById(stud.getIndexNumber()));
		}
	}

	@Test
	public void ShouldDeleteThrowErrorWhenDeletingItemIsNotInDB(){
		//given
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
		crudService.save(student);
		//when
		Student notExistingStudent = new Student( 319123, "Michał", "Ktokolwiek", 21, "02101104357");
		crudService.delete(notExistingStudent);
		//then
		Assert.assertThrows(EntityNotFoundException.class, ()->crudService.read(319123));
		
	}

}
