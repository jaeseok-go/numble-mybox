package jaeseok.numble.mybox.member.dto.mapper;

import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;


@Mapper(imports = LocalDateTime.class)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);
    @Mapping(target = "createdAt", expression = "java( LocalDateTime.now() )")
    Member toMember(MemberSignUpDto memberSignUpDto);
}