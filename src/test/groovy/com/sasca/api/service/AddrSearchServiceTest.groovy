package com.sasca.api.service

import com.sasca.AbstractIntegrationBaseTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class AddrSearchServiceTest extends AbstractIntegrationBaseTest {

    @Autowired
    private AddrSearchService addrSearchService;

    def "address 파라미터 값이 null이면, null 리턴"() {
        given:
        def address = null;

        when:
        def result = addrSearchService.requestAddrSearch(address);

        then:
        result == null
    }

    def "address 파라미터 값이 정상이면, url이 리턴된다"() {
        given:
        def address = "월드컵북로 502-36"

        when:
        def result = addrSearchService.requestAddrSearch(address)

        then:
        result.documentDtoList.size() > 0
        result.metaDto.totalCount > 0
        result.documentDtoList.get(0).addressName != null
    }
}
