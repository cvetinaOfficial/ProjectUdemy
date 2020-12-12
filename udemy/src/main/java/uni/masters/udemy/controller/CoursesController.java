package uni.masters.udemy.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.masters.udemy.bean.CategoryBean;
import uni.masters.udemy.bean.CourseBean;
import uni.masters.udemy.bean.UserBean;
import uni.masters.udemy.repo.CategoryRepo;
import uni.masters.udemy.repo.CourseRepo;

@RestController
public class CoursesController {

	CourseRepo courseRepo;
	CategoryRepo categoryRepo;
	
	public CoursesController(CourseRepo courseRepo, CategoryRepo categoryRepo) {
		this.courseRepo = courseRepo;
		this.categoryRepo = categoryRepo;
	}
	
	@GetMapping(path = "/course/all")
	public List<CourseBean> getAllCourses(
			@RequestParam(value="id")int id,
			HttpSession session
			){
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user == null) {
			return Collections.emptyList();
		}
		
		CategoryBean categoryBean = categoryRepo.findCategoryById(id);
		if(categoryBean != null) {
			
			return categoryBean.getCourses();
			
		}
		else {
			return Collections.emptyList();
		}
	}
	
	@GetMapping(path = "course")
	public CourseBean getById(
			@RequestParam(value="id")int id,
			HttpSession session
			){
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user == null) {
			return null;
		}
		
		Optional<CourseBean> courseBean = courseRepo.findById(id);
		
		if(courseBean.isPresent()) {
			return courseBean.get();
			
		}
		else {
			return null;
		}
	}
	
	@PostMapping(path = "/course/add")
	public ResponseEntity<CourseBean> addCourse(
			@RequestParam(value = "name")String name,
			@RequestParam(value = "price")double price,
			@RequestParam(value = "videoPath")String videoPath,
			@RequestParam(value = "imagePath")String imagePath,
			@RequestParam(value="id")int id,
			HttpSession session
			) {
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		else {
			
				Optional<CategoryBean> optionalCategoryBean = categoryRepo.findById(id);
				if(optionalCategoryBean.isPresent()) {
					CourseBean courseBean = new CourseBean(name,price,videoPath,imagePath);
					courseBean.setCategory(optionalCategoryBean.get());
					
					this.courseRepo.saveAndFlush(courseBean);
					
					return new ResponseEntity<>(courseBean,HttpStatus.CREATED);
				}
				else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
	}
	
	@DeleteMapping(path = "/course/delete")
	public ResponseEntity<Boolean> deleteCourse(
			@RequestParam(value = "id")int id,
			HttpSession session
			){
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user == null) {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
		
		Optional<CourseBean> courseBean = courseRepo.findById(id);
		
		if(courseBean.isPresent()) {
			courseRepo.deleteById(id);
			
			return new ResponseEntity<>(true, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/course/update")
	public ResponseEntity<Boolean> updateCourse(
			@RequestParam(value = "id")int id,
			@RequestParam(value = "name")String name,
			@RequestParam(value="videoPath")String videoPath,
			@RequestParam(value = "imagePath")String imagePath,
			@RequestParam(value="price")double price,
			@RequestParam(value="categoryId")int categoryId,
			HttpSession session
			){
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user == null) {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
		
		CourseBean courseBean = courseRepo.findCourseById(id);
		
		if(courseBean != null) {
			Optional<CategoryBean> categoryBean = categoryRepo.findById(categoryId);
			
			if(categoryBean.isPresent()) {
				courseBean.setCategory(categoryBean.get());
				courseBean.setName(name);
				courseBean.setPrice(price);
				courseBean.setImagePath(imagePath);
				courseBean.setVideoPath(videoPath);
				
				courseRepo.saveAndFlush(courseBean);

				return new ResponseEntity<>(true, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
	
}
}