package com.pm.mss.web;

import com.pm.mss.PmMssApplicationTests;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestTest extends PmMssApplicationTests {

    private final static Logger LOG= LoggerFactory.getLogger(TestTest.class);

    @Test
    public void hello() {
        ResponseEntity<String> response =template.getForEntity(base.toString()+"web/hello",
                        String.class);
        LOG.info("======={}",response.getBody());
    }
}