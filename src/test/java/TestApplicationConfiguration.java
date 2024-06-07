import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)

//@SpringBootTest is used to bootstrap the entire container.
@SpringBootTest(classes = TestApplicationConfiguration.class)
@Configuration

//@ComponentScan is configured to scan the base packages containing your DAOs and models.
@ComponentScan(basePackages = "com.wileyedge.melodymap",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = CommandLineRunner.class))

//@EnableAutoConfiguration allows Spring Boot to auto-configure the necessary components.
@EnableAutoConfiguration
public class TestApplicationConfiguration {

}