package uni.masters.udemy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.masters.udemy.bean.CategoryBean;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryBean, Integer> {
	
	CategoryBean findByName(String name);
	
	CategoryBean findCategoryById(int id);
}
