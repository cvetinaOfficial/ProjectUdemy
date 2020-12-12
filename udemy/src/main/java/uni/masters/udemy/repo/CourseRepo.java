package uni.masters.udemy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.masters.udemy.bean.CourseBean;

@Repository
public interface CourseRepo extends JpaRepository<CourseBean, Integer>{
	
	CourseBean findCourseById(int id);
}
