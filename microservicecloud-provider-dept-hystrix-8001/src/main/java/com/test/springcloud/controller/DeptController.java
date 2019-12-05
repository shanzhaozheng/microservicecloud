package com.test.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.test.springcloud.entities.Dept;
import com.test.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptService service;

    @Autowired
    private DiscoveryClient client;

    @PostMapping("/dept/add")
    public boolean add(@RequestBody Dept dept){
        return service.add(dept);
    }
    @GetMapping("/dept/get/{id}")
    @HystrixCommand(fallbackMethod = "processHystrix_Get")
    public Dept get(@PathVariable Long id){
        Dept dept = service.get(id);
        if (dept == null) {
            throw new RuntimeException("该Id"+id+"没有对应信息");
        }
        return dept;
    }
    public Dept processHystrix_Get(@PathVariable("id") Long id){
        return new Dept().setDeptno(id).setDname("该Id"+id+"没有对应信息，null--@HystrixCommand")
                .setDb_source("no this database in MYSQL");
    }

    @GetMapping("/dept/list")
    public List<Dept> list(){
       return service.list();
    }

    @RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
    public Object discovery()
    {
        List<String> list = client.getServices();
        System.out.println("**********" + list);

        List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");
        for (ServiceInstance element : srvList) {
            System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
                    + element.getUri());
        }
        return this.client;
    }

}
