package com.sasca.pharmacy.repository

import com.sasca.AbstractIntegrationBaseTest
import com.sasca.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

class PharmacyRepositoryTest extends AbstractIntegrationBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    def "save"() {
        given:
        String address = "월드컵북로 502-36"
        String name = "약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
            .pharmacyAddress(address)
            .pharmacyName(name)
            .latitude(latitude)
            .longitude(longitude).build()

        when:
        def result = pharmacyRepository.save(pharmacy)

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }
}
