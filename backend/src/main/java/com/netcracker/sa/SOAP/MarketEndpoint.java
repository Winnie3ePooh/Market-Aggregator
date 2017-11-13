package com.netcracker.sa.SOAP;

import com.netcracker.sa.SOAP.methods.RegistryMarketRequest;
import com.netcracker.sa.SOAP.methods.RegistryMarketResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class MarketEndpoint {

    @PayloadRoot(namespace = "http://methods", localPart = "registryMarketRequest")
    @ResponsePayload
    public RegistryMarketResponse registryMarket(@RequestPayload RegistryMarketRequest request) {
        RegistryMarketResponse response = new RegistryMarketResponse();
        //
        return response;
    }
}
