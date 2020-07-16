package com.whu;

import com.spotify.docker.client.messages.RegistryAuth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TryDockerController {
    @RequestMapping("/hello")
    public String hello(){return "hello";}

    @RequestMapping("createimage")
    public String createImage()
    {
        DockerService dockerService=new DockerService();
        return dockerService.buildImageFromDockerFile("/root/trydockerfile","test2");
    }

    @RequestMapping("execCreate")
    public String ExecCreate()
    {
        DockerService dockerService=new DockerService();
        String[] cmd=new String[2];
        cmd[0]="ls";
        cmd[1]="ping www.baidu.com";

        String containerId="0e7cd26d03fa";

        return dockerService.execCreator(containerId,cmd);
    }

    @RequestMapping("tag")
    public void Tag()
    {
        String image="c7c37e472d31";
        String name="registry.cn-qingdao.aliyuncs.com/sam_wang/busybox:v1";

        DockerService dockerService=new DockerService();
        dockerService.tagImage(image,name);
    }

    @RequestMapping("push")
    public void Push()
    {
        String image="registry.cn-qingdao.aliyuncs.com/sam_wang/busybox:v1";
        DockerService dockerService=new DockerService();
        dockerService.pushImage(image);
    }

    @RequestMapping("pull")
    public void Pull()
    {
        DockerService dockerService=new DockerService();
        RegistryAuth registryAuth= dockerService.createRegistryAuth("samwang1024","wangshuai3");
        dockerService.createImageByPulling("registry.cn-qingdao.aliyuncs.com/sam_wang/busybox:v1",registryAuth);
    }

    @RequestMapping("run")
    public  void Run()
    {
        DockerService dockerService=new DockerService();
        List<String> cmd=new ArrayList<>();
        cmd.add("python");
        cmd.add("train.py");
        Map<String,String> volume=new HashMap<>();
        volume.put("/tmp/output","/root/output");
        ContaineVo containeVo=new ContaineVo("","registry.cn-hangzhou.aliyuncs.com/cve01/keras_mnist:v1","trymnist2","","",null,null,cmd,volume);

        dockerService.run(containeVo);
    }
}
