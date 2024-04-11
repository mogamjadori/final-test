package com.example.GYMFIT.repository;

import com.example.GYMFIT.dto.FacilitySearchDto;
import com.example.GYMFIT.entity.Facility;
import com.example.GYMFIT.entity.QFacility;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class FacilityRepositoryCustomImpl implements FacilityRepositoryCustom{

    private JPAQueryFactory jpaQueryFactory;

    public FacilityRepositoryCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("facilityClCd", searchBy)){
            return QFacility.facility.facilityClCd.like("%"+searchQuery+"%");
        } else if (StringUtils.equals("facilityNm", searchBy)) {
            return QFacility.facility.facilityNm.like("%"+searchQuery+"%");
        }
        return null;
    }

    @Override
    public Page<Facility> getAdminFacilityPage(FacilitySearchDto facilitySearchDto, Pageable pageable) {
        List<Facility> content = jpaQueryFactory.selectFrom(QFacility.facility)
                .where(searchByLike(facilitySearchDto.getSearchBy(),
                        facilitySearchDto.getSearchQuery()))
                .orderBy(QFacility.facility.facilityId.desc())
                .offset(pageable.getOffset()) // 데이터를 가지고 올 시작 인덱스
                .limit(pageable.getPageSize()) // 한번에 조회할 limit(최대 개수)
                .fetch();
        long total = jpaQueryFactory.select(Wildcard.count).from(QFacility.facility)
                .where(
                        searchByLike(facilitySearchDto.getSearchBy(),
                                facilitySearchDto.getSearchQuery())
                ).fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
