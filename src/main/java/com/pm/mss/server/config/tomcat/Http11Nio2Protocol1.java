package com.pm.mss.server.config.tomcat;

import org.apache.coyote.http11.Http11Nio2Protocol;

public class Http11Nio2Protocol1 extends Http11Nio2Protocol {

    public String getName() {
        return "-pm-mss";
    }
}
