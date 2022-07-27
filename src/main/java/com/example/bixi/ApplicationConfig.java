//package com.example.bixi;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.sql.SparkSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.core.env.Environment;
//
//
//@Configuration
//	@PropertySource("classpath:application.properties")
//public class ApplicationConfig {
//
//    @Value("${spark.app.name}")
//    private String appName;
//    @Value("${spark.master}")
//    private String masterUri;
//
//    @Bean
//    public SparkConf conf() {
//        return new SparkConf().setAppName(appName).setMaster(masterUri);
//    }
// 
//    @Bean
//    public JavaSparkContext sc() {
//        return new JavaSparkContext(conf());
//    }
//
//    @Bean
//    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//}
//	