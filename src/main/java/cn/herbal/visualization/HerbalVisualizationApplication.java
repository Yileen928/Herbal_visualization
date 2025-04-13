package cn.herbal.visualization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.herbal.visualization.mapper")
public class HerbalVisualizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(HerbalVisualizationApplication.class, args);
	}

}
