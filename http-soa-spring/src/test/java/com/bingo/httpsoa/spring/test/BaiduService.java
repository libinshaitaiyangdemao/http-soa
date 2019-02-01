package com.bingo.httpsoa.spring.test;

import com.bingo.http.annotation.RequestMapping;
import com.bingo.http.annotation.RequestParam;
import com.bingo.http.annotation.Type;

/**
 * @description:
 * @author: libin29
 * @createdate: 2019/1/4 16:40
 * @lastdate:
 */
@RequestMapping(host = "http://${host}",value = "")
public interface BaiduService {

    @RequestMapping("")
    String query(@RequestParam(value = "host",name = "host", type = Type.PATHVAL) String host);
}
