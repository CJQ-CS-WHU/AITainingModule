package com.whu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TryDocker {
    public static void main(String[] args) {

        SpringApplication.run(TryDocker.class,args);
        //DockerService dockerService =new DockerService();
        //这个测通了
        //dockerService.getContainers();

        //下面测试一个 通过Dockerfile构建的 相对 难一点 希望成功
        /*
        String s=dockerService.buildImageFromDockerFile("/home/sam/trydockerfile","Dockerfile");
        System.out.println(s);
        System.out.println("目前ok");
        */

    }
}
