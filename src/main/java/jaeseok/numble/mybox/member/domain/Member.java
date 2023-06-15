package jaeseok.numble.mybox.member.domain;

import jaeseok.numble.mybox.common.entity.BaseEntity;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.domain.File;
import jaeseok.numble.mybox.folder.domain.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@SuperBuilder
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Convert(converter = PasswordConverter.class)
    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "owner")
    private List<Folder> folders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "owner")
    private List<File> files = new ArrayList<>();

    @Override
    public boolean equals(Object object) {
        return object instanceof Member && this.id.equals(((Member) object).id);
    }

    public void createRootFolder() {
        folders.add(Folder.builder()
                .name("/")
                .owner(this)
                .build());
    }

    public void validatePassword(String password) {
        if (!password.equals(this.password)) {
            throw new MyBoxException(ResponseCode.INVALID_PASSWORD);
        }
    }
}