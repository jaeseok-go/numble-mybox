package jaeseok.numble.mybox.member.repository;


import jaeseok.numble.mybox.member.dto.MemberInfoAndUsage;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<MemberInfoAndUsage> findMemberAndUsageById(Long id);
}
