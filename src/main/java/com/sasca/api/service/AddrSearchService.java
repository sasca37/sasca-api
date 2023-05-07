package com.sasca.api.service;


import com.sasca.api.dto.AddrResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddrSearchService {

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    private final RestTemplate restTemplate;
    private final UriBuilderService uriBuilderService;

    public AddrResponseDto requestAddrSearch(String address) {

        if (ObjectUtils.isEmpty(address)) {
            return null;
        }

        URI uri = uriBuilderService.buildUriByAddressSearch(address);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);

        HttpEntity httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, AddrResponseDto.class).getBody();
    }
}
