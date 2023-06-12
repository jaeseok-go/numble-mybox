package jaeseok.numble.mybox.member.dto.mapper;

import jaeseok.numble.mybox.member.domain.Member;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("MemberMapper Class")
@SpringBootTest
class MemberMapperTest {
    @Test
    @DisplayName("MemberSignUpDto를 Member로 매핑 성공한다.")
    void to_member_success() {
        // given
        String id = "test_id";
        String password = "1234";
        String nickname = "테스트_닉네임";

        Member expect = Member.builder()
                .id(id).password(password)
                .nickname(nickname)
                .build();

        // when
        Member actual = MemberMapper.INSTANCE
                .toMember(new MemberSignUpDto(id, password, nickname));

        // then
        Assertions.assertEquals(expect, actual);
    }
}