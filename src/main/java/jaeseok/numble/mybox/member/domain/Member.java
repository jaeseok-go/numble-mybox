package jaeseok.numble.mybox.member.domain;

import jaeseok.numble.mybox.common.entity.BaseEntity;
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

import static jaeseok.numble.mybox.common.constant.MyBoxConstant.ROOT_FOLDER_NAME;

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

    public Folder createRootFolder() {
        Folder rootFolder = Folder.builder()
                .name(ROOT_FOLDER_NAME)
                .owner(this)
                .build();

        folders.add(rootFolder);

        return rootFolder;
    }

    public boolean equalPassword(String password) {
        return this.password.equals(password);
    }

    public Long calculateUsage() {
        return files.stream().mapToLong(File::getSize).sum();
    }
}