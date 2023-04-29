package com.sasca.api.service

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

@SpringBootTest
class UriBuilderServiceTest extends Specification {

    private UriBuilderService uriBuilderService;

    // 모든 feature 메서드 전에 실행되는 메서드
    def setup() {
        uriBuilderService = new UriBuilderService()
    }

    def "GetUriByAddr"() {
        given:
        String addr = "월드컵북로 502-36"
        def charset = StandardCharsets.UTF_8;

        when:
        def uri = uriBuilderService.getUriByAddr(addr)
        def decodeResult = URLDecoder.decode(uri.toString(), charset)

        then:
        decodeResult == "https://dapi.kakao.com/v2/local/search/address.json?query=월드컵북로 502-36"
    }
}
