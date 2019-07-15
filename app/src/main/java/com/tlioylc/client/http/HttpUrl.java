package com.tlioylc.client.http;


public class HttpUrl {

    private static final class INSTANCE {
        public static final HttpUrl instance = new HttpUrl();
    }

    public static HttpUrl obtain() {
        return INSTANCE.instance;
    }

    /**
     * 示例URL
     * @return
     */
    public String demoUrl(){
        return "/dksaldjals/kdakdwa";
    }

}
