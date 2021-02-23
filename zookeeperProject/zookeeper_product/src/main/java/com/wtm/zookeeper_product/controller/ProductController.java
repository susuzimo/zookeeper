package com.wtm.zookeeper_product.controller;

import com.wtm.zookeeper_product.pojo.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/product")
@RestController
public class ProductController {

    @RequestMapping("/getProduct/{id}")
    public Object getProduct(HttpServletRequest request,@PathVariable("id") String id){
        int localPort = request.getLocalPort();
        System.out.println("访问成功");
        return new Product(id,"productname:"+localPort);
    }
}
