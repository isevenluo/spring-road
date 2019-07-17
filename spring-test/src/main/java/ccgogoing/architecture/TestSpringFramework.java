package ccgogoing.architecture;

import ccgogoing.architecture.spring.pojo.Student;
import com.architecture.spring.beans.factory.BeanFactory;
import com.architecture.spring.beans.factory.DefaultListableBeanFactory;
import org.junit.Test;


public class TestSpringFramework {

	@Test
	public void test() throws Exception {

		// 指定xml资源路径
		String location = "classpath:beans.xml";
		// Resource resource = new ClassPathResource(location);
		// 创建工厂
		BeanFactory beanFactory = new DefaultListableBeanFactory(location);
		// 从工厂中获取指定对象
		Student student = (Student) beanFactory.getBean("student");
		// 测试对象是否可用
		System.out.println(student);
	}

}
