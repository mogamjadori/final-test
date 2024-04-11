package com.example.GYMFIT.service;

import com.example.GYMFIT.dto.FacilityFormDto;
import com.example.GYMFIT.dto.FacilitySearchDto;
import com.example.GYMFIT.entity.Facility;
import com.example.GYMFIT.repository.FacilityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class FacilityService {
    private final FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public List<Facility> index(){
        return (List<Facility>) facilityRepository.findAll();
    }
    public Facility show(Long facilityId) {
        return facilityRepository.findById(facilityId).orElse(null);
    }

    public Long saveFacility(FacilityFormDto facilityFormDto) throws Exception {
        Facility facility = facilityFormDto.createFacility();
        facilityRepository.save(facility);
        return facility.getFacilityId();
    }

    public void deleteFacility(FacilityFormDto facilityFormDto) throws Exception{
        facilityRepository.deleteById(facilityFormDto.getFacilityId());
    }

    @Transactional(readOnly = true)
    public FacilityFormDto getFacilityDtoList(Long facilityId) {
        // 시설 이용 정보 처리
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(EntityNotFoundException::new);
        // Entity --> Dto
        FacilityFormDto facilityFormDto = FacilityFormDto.of(facility);
        return facilityFormDto;
    }
    public Long updateFacility(FacilityFormDto facilityFormDto) throws Exception {
        Facility facility = facilityRepository.findById(facilityFormDto.getFacilityId())
                .orElseThrow(EntityNotFoundException::new);
        facility.updateFacility(facilityFormDto);
        return facility.getFacilityId();
    }

    @Transactional(readOnly = true) // 회원정보 특정조회
    public Page<Facility> getAdminFacilityPage(FacilitySearchDto facilitySearchDto,
                                             Pageable pageable){
        return facilityRepository.getAdminFacilityPage(facilitySearchDto, pageable);
    }

}
