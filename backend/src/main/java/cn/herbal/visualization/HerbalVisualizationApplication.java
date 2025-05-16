package cn.herbal.visualization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("cn.herbal.visualization.mapper")
@EnableCaching
public class HerbalVisualizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(HerbalVisualizationApplication.class, args);
	}

}
