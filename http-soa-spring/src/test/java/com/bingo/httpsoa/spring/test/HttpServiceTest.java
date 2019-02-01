package com.bingo.httpsoa.spring.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/4 16:31
 * @lastdate:
 */
public class HttpServiceTest extends BaseTest {

    @Autowired
    private BaiduService service;
    @Test
    public void test(){
        System.out.println(service.query("www.baidu.com"));
    }
}
