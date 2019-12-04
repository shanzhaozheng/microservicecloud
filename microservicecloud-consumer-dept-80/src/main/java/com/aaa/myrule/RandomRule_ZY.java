package com.aaa.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class RandomRule_ZY extends AbstractLoadBalancerRule {

    private int total = 0; //total==5以后，我们指针才能往下走
    private int currentIndex = 0;



    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        // TODO Auto-generated method stub
    }

    @Override
    public Server choose(Object key) {
         return choose(getLoadBalancer(), key);
    }


    public Server choose(ILoadBalancer lb,Object key) {
        if (lb==null){
            return null;
        }
        Server server=null;
        while (server == null){
            if (Thread.interrupted()){//线程中断
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();
            int size = allList.size();
            if (size == 0){
                return null;
            }
            if (total<5){
                server = upList.get(currentIndex);
                total++;
            }else {
                total=0;
                currentIndex++;
                if (currentIndex>=upList.size()){
                    currentIndex=0;
                }
            }
            if (server == null ){
                Thread.yield();
                continue;
            }
            if (server.isAlive()){
                return (server);
            }
            server=null;
            Thread.yield();
        }
        return server;
    }
}
