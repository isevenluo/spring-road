import com.sevenluo.spring.config.AppConfig;
import com.sevenluo.spring.services.HelloService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SevenluoSpringTest {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		HelloService bean = ac.getBean(HelloService.class);
		bean.hello();
	}
}
