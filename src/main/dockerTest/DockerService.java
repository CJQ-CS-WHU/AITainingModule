package com.whu;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import com.spotify.docker.client.messages.Container;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DockerService {

    public void getContainers() {
        try{
            List<Container> containers = DockerClientConfig.getLocalDockerClient().listContainers();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DockerException e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }

    public String buildImageFromDockerFile(String dockerDirectory,String fileName){
        String ret="xx";
        Paths.get(dockerDirectory);
        System.out.println("目前可以");
        AtomicReference<String> imageIdFromMessage = new AtomicReference<>();
        try{
            ret=DockerClientConfig.getLocalDockerClient().build(
                    Paths.get(dockerDirectory),fileName,message -> {
                        System.out.println("目前可以");
                        final String imageId=message.buildImageId();
                        if(imageId!=null){
                            imageIdFromMessage.set(imageId);
                        }
                    });
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DockerException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // 这个应该是针对一个已经启动的容器 让他执行一组命令
    public String execCreator(String containerId,String[] cmd){
        String ret="wrong";
        try{
            ret=DockerClientConfig.getLocalDockerClient().execCreate(containerId,cmd).id();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DockerException e) {
            e.printStackTrace();
        }

        return ret;
    }

    //尝试给镜像打标签
    public void tagImage(String image,String name){
        try{
            DockerClientConfig.getLocalDockerClient().tag(image,name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DockerException e) {
            e.printStackTrace();
        }
    }

    //暂时可以
    // 尝试 把打完标签的 镜像 上传到仓库
    public void pushImage(String image){
        try{
            DockerClientConfig.getLocalDockerClient().push(image);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DockerException e) {
            e.printStackTrace();
        }
    }

    //先做一个镜像仓库权限相关的
    public RegistryAuth createRegistryAuth(String authUsername,String authPathword){
        return RegistryAuth.builder().username(authUsername).password(authPathword).build();
    }

    // 尝试 拉取镜像
    public void createImageByPulling(String url,RegistryAuth registryAuth){
        try{
            DockerClientConfig.getLocalDockerClient().pull(url,registryAuth);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DockerException e) {
            e.printStackTrace();
        }
    }

    //尝试 运行一个容器
    public void run(ContaineVo containerConfig) {
        try {
            DockerClient dockerClient =DockerClientConfig.getLocalDockerClient();

            dockerClient.inspectImage(containerConfig.getImage());

            HostConfig hostCon;
            HostConfig.Builder hostBuilder=HostConfig.builder();
            ContainerConfig containerCon;

            //格式 "container_port:host_port.."
            Map<String,List<PortBinding>> portBindings =new HashMap<>(10);
            if(containerConfig.getPortBindings() != null && !containerConfig.getPortBindings().isEmpty()){
                for(Map.Entry<String,List<String>> portBinding:containerConfig.getPortBindings().entrySet()){
                    List<PortBinding> ports = new ArrayList<>();
                    for (String port : portBinding.getValue()){
                        ports.add(new PortBindingVo("0.0.0.0",port));
                    }

                    portBindings.put(portBinding.getKey()+"/tcp",ports);
                }

                hostBuilder.portBindings(portBindings);
            }

            if(containerConfig.getVolumes() !=null && !containerConfig.getVolumes().isEmpty()){
                for(Map.Entry<String,String> bind :containerConfig.getVolumes().entrySet()){
                    hostBuilder.appendBinds(HostConfig.Bind.from(bind.getKey()).to(bind.getValue()).build());
                }
            }

            hostCon=hostBuilder.build();
            ContainerConfig.Builder builder = ContainerConfig.builder();
            if (StringUtils.isNotBlank(containerConfig.getImage())) {
                builder.image(containerConfig.getImage());
            }
            if (!CollectionUtils.isEmpty(containerConfig.getEnv())) {
                builder.env(containerConfig.getEnv());
            }
            if (!CollectionUtils.isEmpty(containerConfig.getCmd())) {
                builder.cmd(containerConfig.getCmd());
            }
            containerCon = builder.hostConfig(hostCon).build();

            List<String> envs =new ArrayList<>();
            envs.add("");
            containerCon.env();

            ContainerCreation container = dockerClient.createContainer(containerCon, containerConfig.getName());
            dockerClient.startContainer(container.id());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (DockerException e) {
            e.printStackTrace();
        }
    }

    //stop

    //查log
}
