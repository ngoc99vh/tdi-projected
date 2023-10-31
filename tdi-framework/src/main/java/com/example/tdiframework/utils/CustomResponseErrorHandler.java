package com.example.tdiframework.utils;

import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {
    private final Logger log = LoggerFactory.getLogger(CustomResponseErrorHandler.class);

    private void traceResponse(ClientHttpResponse response) {
        try {
            StringBuffer inputStringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuffer.append(line);
                inputStringBuffer.append("\n");
                line = bufferedReader.readLine();
            }
            log.debug("============================ BEGIN ==========================================");
            log.debug("Status code  : " + response.getStatusCode());
            log.debug("Status text  : " + response.getStatusText());
            log.debug("Headers      : " + response.getHeaders());
            log.debug("Response body: " + inputStringBuffer);
            log.debug("============================ END =================================================");
        } catch (IOException e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
        }


    }

}
