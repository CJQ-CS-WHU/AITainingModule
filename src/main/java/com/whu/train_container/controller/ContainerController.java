package com.whu.train_container.controller;

import com.whu.train_container.service.IContainerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContainerController {

    //IContainerService containerService=new ContainerController()
    @RequestMapping("/hello")
    public String hello(){return "hello";}

    @RequestMapping("/s")
    public String hel3lo(){
        return "hello";
    }
}

