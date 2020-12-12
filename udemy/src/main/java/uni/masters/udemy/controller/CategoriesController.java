package uni.masters.udemy.controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CategoriesController{

		CourseRepo courseRepo;
		CategoryRepo categoryRepo;
		
		private Logger logger= LoggerFactory.getLogger(this.getClass());
		
		public CategoriesController(CourseRepo courseRepo, CategoryRepo categoryRepo) {
			this.courseRepo = courseRepo;
			this.categoryRepo = categoryRepo;
		}
		
		@GetMapping(path = "/categories/all")
		public List<CategoryBean> getAllCategories(
				HttpSession session){
			
			UserBean user = (UserBean)session.getAttribute("user");
			
			if(user == null) {
				return Collections.emptyList();
			}
			
			return categoryRepo.findAll();
		}
		
		@GetMapping(path = "category")
		public CategoryBean getById(
				@RequestParam(value="id")int id,
				HttpSession session
				){
			
			UserBean user = (UserBean)session.getAttribute("user");
			
			if(user == null) {
				return null;
			}
			
			Optional<CategoryBean> categoryBean = categoryRepo.findById(id);
			
			if(categoryBean.isPresent()) {
				return categoryBean.get();
				
			}
			else {
				return null;
			}
		}
		
		@PostMapping(path = "/category/add")
		public ResponseEntity<CategoryBean> addCourse(
				@RequestParam(value = "name")String name,
				@RequestParam(value="imagePath")String imagePath,
				HttpSession session
				) {
			
			UserBean user = (UserBean)session.getAttribute("user");
			
			if(user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			else{
					CategoryBean categoryWithSameName = categoryRepo.findByName(name);
					
					if(categoryWithSameName == null) {
						CategoryBean categoryBean = new CategoryBean(name,imagePath);
						
						this.categoryRepo.saveAndFlush(categoryBean);
						
						return new ResponseEntity<>(categoryBean,HttpStatus.CREATED);
					}else {
						return new ResponseEntity<>(HttpStatus.CONFLICT);
					}
				}
		}
		
		@DeleteMapping(path = "/category/delete")
		public ResponseEntity<Boolean> deleteCategory(
				@RequestParam(value = "id")int id,
				HttpSession session
				){
			
			UserBean user = (UserBean)session.getAttribute("user");
			
			if(user == null) {
				return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
			}
			
			Optional<CategoryBean> optionalCategory = categoryRepo.findById(id);
			
			if(optionalCategory.isPresent()) {
				CategoryBean category = optionalCategory.get();
				
					categoryRepo.delete(category);				
					return new ResponseEntity<>(true, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
			
		}
		
		@PutMapping(path = "/category/update")
		public ResponseEntity<Boolean> updateCourse(
				@RequestParam(value = "id")int id,
				@RequestParam(value = "name")String name,
				@RequestParam(value="imagePath")String imagePath,
				HttpSession session
				){
			
			UserBean user = (UserBean)session.getAttribute("user");
			
			if(user == null) {
				return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
			}
			
			Optional<CategoryBean> categoryBean = categoryRepo.findById(id);
			
			if(categoryBean.isPresent()) {
					categoryBean.get().setName(name);
					categoryBean.get().setImagePath(imagePath);
					categoryRepo.saveAndFlush(categoryBean.get());
					
					return new ResponseEntity<>(true, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
		}
		
		
	}
