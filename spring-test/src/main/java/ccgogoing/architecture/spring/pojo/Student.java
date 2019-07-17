package ccgogoing.architecture.spring.pojo;

/**
 * 学生类
 * 		拥有课程类的对象引用
 * @author think
 *
 */
public class Student {

	private String name;

	private Course course;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public void initMethod() {
		System.out.println("初始化方法");
	}
	public void destroyMethod() {
		System.out.println("销毁方法");
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", course=" + course + "]";
	}
	
	
	
}
