package ru.tele2.rtcm.ignitetracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class IgniteTracingApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgniteTracingApplication.class, args);
	}

}
