package com.sidd;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.web.client.RestTemplate;


import java.util.Optional;
import java.util.logging.Level;

public class CallService {

    @FunctionName("callService")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        final String uri = "https://developer.api.stg.walmart.com/api-proxy/service/membership/kpijobs/v1/job/creditApply";
                            //http://kpijobs.samsclub.qa.walmart.com:8080/v1/job/creditApply";

        RestTemplate restTemplate = new RestTemplate();
        try
        {
            String result = restTemplate.getForObject(uri, String.class);
            return request.createResponseBuilder(HttpStatus.OK).body(result).build();
        }
        catch(Exception e)
        {
            context.getLogger().log(Level.SEVERE, e.getMessage(), e);
            return request.createResponseBuilder(HttpStatus.valueOf(500)).body("Failed. " + e.getMessage()).build();
        }
    }
}

