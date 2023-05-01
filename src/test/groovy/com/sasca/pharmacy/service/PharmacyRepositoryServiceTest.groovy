package com.sasca.pharmacy.service

import com.sasca.AbstractIntegrationBaseTest
import com.sasca.pharmacy.entity.Pharmacy
import com.sasca.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryServiceTest extends AbstractIntegrationBaseTest {

    @Autowired
    private PharmacyRepositoryService pharmacyRepositoryService

    @Autowired
    private PharmacyRepository pharmacyRepository;


    def setup() {
        pharmacyRepository.deleteAll();
    }

    def "pharmacyRepository update - dirty checking success"() {
        given:
        String address = "월드컵북로 502-36"
        String newAddress = "서울 광진구 구의동"
        String name = "약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build()


        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(entity.getId(), newAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == newAddress
    }

}
