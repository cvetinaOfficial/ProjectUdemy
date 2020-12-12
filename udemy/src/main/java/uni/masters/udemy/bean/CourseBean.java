package uni.masters.udemy.bean;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class CourseBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false, length = 256)
	private String name;
	
	@Column(name="rating", nullable = true)
	private int rating;
	
	@Column(name="price", nullable = true, precision = 2)
	private double price;
	
	@Column(name="subscribers", nullable = true)
	private int subscribers;
	
	@Column(name="imagePath", nullable= false)
	private String imagePath;
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name="videoPath", nullable = false)
	private String videoPath;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private CategoryBean category;
	
	public CourseBean() {};
	
	public CourseBean(String name, int rating, double price) {
		this.name = name;
		this.rating = rating;
		this.price = price;
	}
	
	public CourseBean(String name, int rating, double price, String videoPath,String imagePath, int subscribers) {
		this.name = name;
		this.rating = rating;
		this.price = price;
		this.videoPath = videoPath;
		this.imagePath = imagePath;
		this.subscribers = subscribers;
	}
	
	public CourseBean(String name, double price, String videoPath,String imagePath) {
		this.name = name;
		this.price = price;
		this.videoPath = videoPath;
		this.imagePath = imagePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public CategoryBean getCategory() {
		return category;
	}

	public void setCategory(CategoryBean category) {
		this.category = category;
	}

	public int getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(int subscribers) {
		this.subscribers = subscribers;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	
	
}
