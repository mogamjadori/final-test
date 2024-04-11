package com.example.GYMFIT.repository;

import com.example.GYMFIT.dto.MainMemberDto;
import com.example.GYMFIT.dto.MemberSearchDto;
import com.example.GYMFIT.dto.QMainMemberDto;
import com.example.GYMFIT.entity.Member;

import com.example.GYMFIT.entity.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryCustomImpl(EntityManager em){

        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("memNm", searchBy)){
            return QMember.member.memNm.like("%"+searchQuery+"%");
        } else if (StringUtils.equals("memCno", searchBy)) {
           return QMember.member.memCno.like("%"+searchQuery+"%");
        }
        return null;
    }


    @Override
    public Page<Member> getAdminMemberPage(MemberSearchDto memberSearchDto, Pageable pageable) {
        List<Member> content =jpaQueryFactory.selectFrom(QMember.member)

                .where(searchByLike(memberSearchDto.getSearchBy(),
                        memberSearchDto.getSearchQuery()))

                .orderBy(QMember.member.memId.desc())
                .offset(pageable.getOffset()) // 데이터를 가지고 올 시작 인덱스
                .limit(pageable.getPageSize()) // 한번에 조회할 limit(최대 개수)
                .fetch();
        long total = jpaQueryFactory.select(Wildcard.count).from(QMember.member)
                .where(
                        searchByLike(memberSearchDto.getSearchBy(),
                                memberSearchDto.getSearchQuery())
                ).fetchOne();


        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression memNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QMember.member.memNm.like( "%"+searchQuery+"%");
    }

    private BooleanExpression memCnoLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QMember.member.memCno.like( "%"+searchQuery+"%");
    }

    @Override
    public Page<MainMemberDto> getMainMemberPage(MemberSearchDto memberSearchDto, Pageable pageable) {
        QMember member = QMember.member;
        List<MainMemberDto> content = jpaQueryFactory.select(
                new QMainMemberDto(member.memId, member.memNm,
                        member.memAdr, member.memCno,
                        member.memEmlAdr, member.memBirthDt))
                .from(member).join(member)
                .where(memNmLike(memberSearchDto.getSearchQuery()))
                .where(memCnoLike(memberSearchDto.getSearchQuery()))
                .orderBy(member.memId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = jpaQueryFactory.select(Wildcard.count)
                .from(member).join(member)
                .where(memNmLike(memberSearchDto.getSearchQuery()))
                .where(memCnoLike(memberSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
