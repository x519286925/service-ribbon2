package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
@SpringBootApplication
@EnableDiscoveryClient   //通过@EnableDiscoveryClient向服务中心注册；并且向程序的ioc注入一个bean
@EnableFeignClients    //@EnableFeignClients注解开启Feign的功能
@EnableHystrix   //注解开启Hystrix  Hystrix默认的超时时间是1秒，如果超过这个时间尚未响应，将会进入fallback代码
@EnableHystrixDashboard    //开启hystrixDashboard【Hystrix Dashboard (断路器：Hystrix 仪表盘)】
public class DemoApplication{
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
