package com.example.GYMFIT.repository;

import com.example.GYMFIT.dto.FacilitySearchDto;
import com.example.GYMFIT.dto.MainMemberDto;
import com.example.GYMFIT.dto.MemberSearchDto;
import com.example.GYMFIT.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FacilityRepositoryCustom {
    Page<Facility> getAdminFacilityPage(FacilitySearchDto facilitySearchDto,
                                        Pageable pageable);


}
