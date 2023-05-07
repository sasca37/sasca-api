package com.sasca.api.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class UriBuilderService {

    private static final String KAOKAO_ADDR_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildUriByAddressSearch(String address) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(KAOKAO_ADDR_URL);
        builder.queryParam("query", address);
        URI uri = builder.build().encode().toUri();
        log.info("[Addr URI] -  address : {},  uri : {}", address, uri);
        return uri;
    }

}
