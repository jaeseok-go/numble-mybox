package jaeseok.numble.mybox.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jaeseok.numble.mybox.member.dto.MemberInfoAndUsage;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static jaeseok.numble.mybox.member.domain.QMember.member;
import static jaeseok.numble.mybox.file.domain.QFile.file;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberInfoAndUsage> findMemberAndUsageById(Long id) {
        return Optional.of(queryFactory
                .select(Projections.bean(MemberInfoAndUsage.class,
                        member.id,
                        member.email,
                        file.size.sum().longValue().coalesce(0L).as("byteUsage")))
                .from(member).leftJoin(member.files, file)
                .where(member.id.eq(id))
                .groupBy(member.id, member.email)
                .fetch().get(0));
    }
}
