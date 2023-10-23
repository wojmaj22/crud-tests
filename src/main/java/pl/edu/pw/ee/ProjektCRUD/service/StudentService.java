package pl.edu.pw.ee.ProjektCRUD.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.ProjektCRUD.dao.StudentRepository;
import pl.edu.pw.ee.ProjektCRUD.model.Student;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
	
	private final StudentRepository repository;
	
	public List<Student> readAll(){
		return repository.findAll();
	}
	
	public Student read(Integer id){
		Optional<Student> optionalStudent = repository.findById(id);
		if( optionalStudent.isPresent()){
			return optionalStudent.get();
		} else
			throw new EntityNotFoundException("No student with id: " + id + ", has been found!");
	}
	
	public Student save(Student student){
		return repository.save(student);
	}
	
	public void deleteAll(){
		repository.deleteAll();
	}
	
	public void delete(Integer id){
		repository.deleteById(id);
	}
	
	public void delete(Student student){
		repository.delete(student);
	}
	
	public void update(Student student){
		if ( repository.findById(student.getIndexNumber()).isPresent()){
			repository.save(student);
		} else
			throw new EntityNotFoundException("Cannot update student with id: " + student.getIndexNumber() + ", because entity does not exist");
	}
	
}
