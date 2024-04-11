package com.example.GYMFIT.repository;

import com.example.GYMFIT.entity.Facility;
import com.example.GYMFIT.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;


public interface FacilityRepository extends CrudRepository<Facility, Long>, FacilityRepositoryCustom ,
        JpaRepository<Facility, Long>, QuerydslPredicateExecutor<Facility> {

//    @Query("SELECT f FROM facility f WHERE f.facilityClCd = %:facilityClCd% ORDER BY  f.facilityNm desc")
//    List<Facility> findByFacilityClCd(@Param("facilityClCd") Long facilityClCd);


    @Override
    ArrayList<Facility> findAll();
    List<Facility> findByFacilityNm(String facilityNm);
    List<Facility> findByFacilityTerm(String facilityTerm);
    List<Facility> findByFacilityPrice(int facilityPrice);
    List<Facility> findByEntranceNo(int entranceNo);
}