package com.theironyard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class MasterMindApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(MasterMindApplication.class, args);
	}
}
