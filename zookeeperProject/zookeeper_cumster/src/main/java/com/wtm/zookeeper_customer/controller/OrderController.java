package com.wtm.zookeeper_customer.controller;

import com.wtm.zookeeper_customer.pojo.Order;
import com.wtm.zookeeper_customer.pojo.Product;
import com.wtm.zookeeper_customer.utils.LoadBalance;
import com.wtm.zookeeper_customer.utils.RamdomLoadBalance;
import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private RestTemplate restTemplate;

    private LoadBalance loadBalance=new RamdomLoadBalance();

    @RequestMapping("/getOrder/{id}")
    public Object getProduct(HttpServletRequest request, @PathVariable("id") String id){
        String host=loadBalance.choseServiceHost();
        System.out.println(host);
        Product product=restTemplate.getForObject("http://"+host+"/product/getProduct/1",Product.class);
        return new Order(id,"ordername",product);
    }
}
