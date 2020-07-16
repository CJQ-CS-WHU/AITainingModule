package com.whu;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.messages.RegistryAuth;
//连接配置类
public class DockerClientConfig {
    //docker服务器的IP地址和端口号，例如："http://202.114.66.76:2375"
    private static String url;

    private static long connectTimeoutMillis;

    private static long readTimeoutMillis;

    private static boolean useProxy;

    private static int connectionPoolSize;

    private static String apiVersion;

    public  DockerClientConfig(String url,long connectTimeoutMillis,long readTimeoutMillis,boolean useProxy,int connectionPoolSize,String apiVersion)
    {
        this.setUrl(url);
        this.setConnectTimeoutMillis(connectTimeoutMillis);
        this.setConnectionPoolSize(connectionPoolSize);
        this.setUseProxy(useProxy);
        this.setReadTimeoutMillis(readTimeoutMillis);
        this.setApiVersion(apiVersion);
    }

    //设置dockerClient的url
    public void setUrl(String url1) {
        url = url1;
    }

    public void setConnectTimeoutMillis(long connectTimeoutMillis1) {
        connectTimeoutMillis = connectTimeoutMillis1;
    }

    public void setReadTimeoutMillis(long readTimeoutMillis1) {
        readTimeoutMillis = readTimeoutMillis1;
    }

    public void setUseProxy(boolean useProxy1) {
        useProxy = useProxy1;
    }

    public void setConnectionPoolSize(int connectionPoolSize1) {
        connectionPoolSize = connectionPoolSize1;
    }

    public void setApiVersion(String apiVersion1) {
        apiVersion = apiVersion1;

    }
    private static class Single {
        //private static String url="http://192.168.235.140:2375";
        //private static String url="http://localhost:2375";
        private  static String url="http://202.114.66.76:2375";

        private static long connectTimeoutMillis=30000;

        private static long readTimeoutMillis=60000;

        private static boolean useProxy=false;

        private static int connectionPoolSize=10;

        private static RegistryAuth registryAuth=RegistryAuth.builder().username("samwang1024").password("wangshuai3").build();

        private static String apiVersion="";

        static DefaultDockerClient defaultDockerClient = DefaultDockerClient.builder().uri(url)
                .connectionPoolSize(connectionPoolSize)
                .apiVersion(apiVersion)
                .readTimeoutMillis(readTimeoutMillis)
                .connectTimeoutMillis(connectTimeoutMillis).registryAuth(registryAuth).build();

    }

    //好像是 获取一个和docker的连接
    public static DefaultDockerClient getLocalDockerClient() {
        return Single.defaultDockerClient;
    }
}
