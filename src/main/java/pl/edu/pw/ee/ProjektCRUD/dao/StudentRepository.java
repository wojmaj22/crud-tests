package pl.edu.pw.ee.ProjektCRUD.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.ee.ProjektCRUD.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{


}
