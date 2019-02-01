package com.bingo.http;

import com.bingo.http.annotation.RequestHeader;
import com.bingo.http.annotation.RequestMapping;
import com.bingo.http.annotation.RequestParam;
import com.bingo.http.annotation.Type;
import com.bingo.http.ser.RequestParameter;
import com.bingo.http.ser.RequestParameters;
import com.bingo.http.ser.executor.HttpResponse;
import com.bingo.http.ser.filter.Filter;
import com.bingo.http.utils.HttpResponseEntity;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/12/25 13:38
 * @lastdate:
 */
public class Main {
    public static void main(String[] args) throws ParseException, NoSuchMethodException {
//        BaiduService service = HttpFactory.create(BaiduService.class);
//        System.out.println(service.query());
        String host = "10.175.51.146";
        SipMonitorLogService service = HttpFactory.createPreInit(SipMonitorLogService.class, new Filter() {
            @Override
            public void before(Method method, RequestParameters params) {
                params.addParam(new RequestParameter(Type.PATHVAL, "host", "http://" + host));
//            params.addParam(new RequestParameter(Type.COOKIE,"",cookies));
            }

            @Override
            public HttpResponse doFilter(Method method, RequestParameters param) {
                HttpResponse response = super.doFilter(method, param);
                if (response.getHeaders() != null) {
//                    response.getHeaders().forEach((k,v)->System.out.println(k+">>>"+v));
                }
                return response;
            }
        });
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SipMonitorLogQuery query = new SipMonitorLogQuery();
        query.setType("order");
        query.setSize(12);
        query.setFrom(0);
        query.setCreateTimeStart("2018-12-22 00:00:00");
        query.setCreateTimeEnd("2018-12-23 00:00:00");
        for (int i = 0; i < 1; i++) {
            long current = System.currentTimeMillis();
//            Map<Object, Object> map = service.query("order",df.parse("2018-12-22 00:00:00"),df.parse("2018-12-23 00:00:00"),0,11);
//            HttpResponseEntity<Map<Object, Object>> entity = service.queryByQuery(query);
//            System.out.println(entity.getHeaders());
//            System.out.println(entity.getStatus());
//            System.out.println(entity.getResponse());
//            System.out.println(System.currentTimeMillis() - current);
            Object obj = service.queryByQuery(query);
            System.out.println(obj);
        }
    }

}

interface DefaultService {

    HttpResponseEntity<String> query();
}

class SipMonitorLogQuery {
    private String type;
    private int from;
    private int size;
    private String createTimeStart;
    private String createTimeEnd;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}

@RequestMapping(value = "/sip/monitor/", host = "${host}")
interface SipMonitorLogService {
    @RequestHeader({"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "Accept-Language:zh-CN,zh;q=0.9",
            "Cache-Control:max-age=0",
            "Connection:keep-alive",
            "Upgrade-Insecure-Requests:1",
            "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36",})
    @RequestMapping("query")
    Map<Object, Object> query(@RequestParam(name = "type") String type, @RequestParam(name = "createTimeStart", value = "yyyy-MM-dd HH:mm:ss") Date start, @RequestParam(name = "createTimeEnd", value = "yyyy-MM-dd HH:mm:ss") Date end, @RequestParam(name = "from") int from, @RequestParam(name = "size") int size);

    @RequestMapping("query")
    HttpResponseEntity<HttpResponseEntity<HttpResponseEntity<Map<Object, Object>>>> queryByQuery(SipMonitorLogQuery query);
}

@RequestMapping(host = "http://www.baidu.com", value = "")
interface BaiduService {
    @RequestMapping("query")
    String query();
}
