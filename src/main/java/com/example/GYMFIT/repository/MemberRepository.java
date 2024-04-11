package com.example.GYMFIT.repository;


import com.example.GYMFIT.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long>,
        QuerydslPredicateExecutor<Member>, MemberRepositoryCustom, CrudRepository<Member,Long> {

    List<Member> findByMemCno(String memCno);

    List<Member> findByMemNmAndMemCno(String memNm, String memCno);

    @Query("SELECT m FROM member m WHERE m.memNm Like %:memNm% ORDER BY  m.memCno desc")
    List<Member> findByMemNm(@Param("memNm") String memNm);


    @Override
    ArrayList<Member> findAll();

}
