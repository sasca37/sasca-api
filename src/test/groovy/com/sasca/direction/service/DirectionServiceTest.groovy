package com.sasca.direction.service

import com.sasca.api.dto.DocumentDto
import com.sasca.pharmacy.dto.PharmacyDto
import com.sasca.pharmacy.service.PharmacySearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService = Mock()
    private DirectionService directionService = new DirectionService(pharmacySearchService)

    private List<PharmacyDto> pharmacyList;

    def setup() {
        pharmacyList = new ArrayList<>();
        pharmacyList.addAll(
                PharmacyDto.builder()
                    .id(1L)
                    .pharmacyName("돌곶이온누리약국")
                    .pharmacyAddress("주소1")
                    .latitude(37.61040424)
                    .longitude(127.0569046)
                .build(),

                PharmacyDto.builder()
                    .id(2L)
                    .pharmacyName("호수온누리약국")
                    .pharmacyAddress("주소2")
                    .latitude(37.60240424)
                    .longitude(127.0269046)
                .build()
        )
    }

    def "buildDirectionList - 결과 값이 거리 순으로 정렬이 되는지 확인"() {
        given:

        def addressName = "월드컵북로 502-36"
        double inputLatitude = 37.596965
        double inputLongitude = 127.037033

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList // stub

        def result = directionService.buildDirectionList(
                DocumentDto.builder().addressName(addressName).longitude(inputLongitude).latitude(inputLatitude).build()
        )
        then:
        result.size() == 2
        result.get(0).distance < result.get(1).distance
    }

    def "buildDirectionList - 정해진 반경 10KM 내에 검색이 되는지 확인"() {
        def addressName = "월드컵북로 502-36"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 281.037033
        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList // stub

        def result = directionService.buildDirectionList(
                DocumentDto.builder().addressName(addressName).longitude(inputLongitude).latitude(inputLatitude).build()
        )

        then:
        result.size() == 0
    }
}
