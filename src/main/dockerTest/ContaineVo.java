package com.whu;

import java.util.List;
import java.util.Map;

public class ContaineVo {
    private String id;

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getNetwork() {
        return network;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, List<String>> getPortBindings() {
        return portBindings;
    }

    public List<String> getEnv() {
        return env;
    }

    public List<String> getCmd() {
        return cmd;
    }

    public Map<String, String> getVolumes() {
        return volumes;
    }

    private String image;

    private String name;
    private String network;
    private String status;

    private Map<String, List<String>> portBindings;
    private List<String> env;
    private List<String> cmd;
    private Map<String,String> volumes;

    public String getName(){return name;}
    public ContaineVo(String id,String image,String name,String network,String status,Map<String,List<String>> portBindings,List<String> env,List<String> cmd,Map<String,String> volumes){
        this.id=id;
        this.image=image;
        this.name=name;
        this.network=network;
        this.status=status;
        this.portBindings=portBindings;
        this.env=env;
        this.cmd=cmd;
        this.volumes=volumes;
    }
}
