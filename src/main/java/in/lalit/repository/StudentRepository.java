package in.lalit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lalit.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
