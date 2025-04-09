package in.lalit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.lalit.entity.Student;
import in.lalit.exception.UserNotFoundException;
import in.lalit.repository.StudentRepository;

@RestController
@CrossOrigin("http://localhost:3000")
public class StudentController {
	// get all the students
	@Autowired
	StudentRepository repo;
	
	@GetMapping("/students")
	public  List<Student> getAllStudents(){
		List<Student> students = repo.findAll();
		System.out.println(students);
		return students;
	}
	
	@GetMapping("/students/{id}")
	public Student getStudent(@PathVariable int id) {
		  return repo.findById(id).orElseThrow(()->new UserNotFoundException(id));
		
	}
	
	@PostMapping("/student/add")
	public void createStudent(@RequestBody Student student) {
		repo.save(student);
	}

	
	@PutMapping("/student/update/{id}")
	public Student updateStudent(@RequestBody Student student, @PathVariable int id) {
		return repo.findById(id)
				.map(user ->{
					user.setName(student.getName());
				    user.setPercentage(student.getPercentage());
				    user.setBranch(student.getBranch());
				   return repo.save(user);
				}).orElseThrow(()->new UserNotFoundException(id));
	}
	
	@DeleteMapping("/student/delete/{id}")
	public String removeStudent(@PathVariable int id) {
		
		if(!repo.existsById(id)){
			throw new UserNotFoundException(id);
		}
		repo.deleteById(id);
		return "User with id "+id+" has been deleted successfully";
		
	
		
	}
}
