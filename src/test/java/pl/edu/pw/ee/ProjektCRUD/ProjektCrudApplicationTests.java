package pl.edu.pw.ee.ProjektCRUD;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pw.ee.ProjektCRUD.dao.StudentRepository;
import pl.edu.pw.ee.ProjektCRUD.model.Student;
import org.junit.Assert;

import java.sql.Struct;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProjektCrudApplicationTests {

	@Autowired
	private StudentRepository repository;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void quickTest(){
		Student student = new Student( 319069, "Wojciech", "Majchrzak", 21, "02301104357");
	
		repository.save(student);
		
		Student newStudent = repository.findById(319069).get();
		
		Assert.assertEquals(student,newStudent);
		System.out.println(newStudent);
	}
	
}
