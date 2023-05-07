package com.sasca.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasca.api.dto.AddrResponseDto;
import com.sasca.api.dto.DocumentDto;
import com.sasca.api.dto.MetaDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@Slf4j
class AddrSearchServiceRetryTest {
    @Autowired
    private AddrSearchService addrSearchService;

    @MockBean      // 스프링 컨테이너 내부에 있는 빈을 모킹할 때 @MockBean을 사용
    private UriBuilderService uriBuilderService;

    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        log.info(String.valueOf(mockWebServer.getPort()));
        log.info(String.valueOf(mockWebServer.url("/")));
    }

    @AfterEach
    public void clean() throws IOException {
        mockWebServer.shutdown();
    }


    private ObjectMapper objectMapper = new ObjectMapper();

    private String inputAddress = "서울 성북구 종암로 10길";

    @Test
    public void retryTest() throws JsonProcessingException {
        // given
        MetaDto metaDto = new MetaDto(1);
        DocumentDto documentDto = DocumentDto.builder()
                .addressName(inputAddress)
                .build();
        AddrResponseDto expectedResponse = new AddrResponseDto(metaDto, Arrays.asList(documentDto));
        URI uri = mockWebServer.url("/").uri();

        // when
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(expectedResponse)));

        when(uriBuilderService.buildUriByAddressSearch(inputAddress)).thenReturn(null, uri);
        AddrResponseDto addrResponseDtoFail = addrSearchService.requestAddrSearch(inputAddress);
        AddrResponseDto addrResponseDto = addrSearchService.requestAddrSearch(inputAddress);

        // then
        // recover method print : All the retries failed. address
        assertEquals(addrResponseDtoFail, null);
        assertEquals(addrResponseDto.getDocumentDtoList().size(), 1);
        assertEquals(addrResponseDto.getMetaDto().getTotalCount(), 1);
        assertEquals(addrResponseDto.getDocumentDtoList().get(0).getAddressName(), inputAddress);
    }

}