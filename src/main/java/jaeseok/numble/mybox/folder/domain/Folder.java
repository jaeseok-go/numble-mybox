package jaeseok.numble.mybox.folder.domain;

import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.domain.File;
import jaeseok.numble.mybox.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("folder")
public class Folder extends Element {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> childFolders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> childFiles = new ArrayList<>();

    public Folder addFolder(String name) {
        validateFolderName(name);

        Folder child = Folder.builder()
                .name(name)
                .parent(this)
                .owner(owner)
                .build();

        childFolders.add(child);
        return child;
    }

    public void validateFolderName(String name) {
        for (Folder folder : childFolders) {
            if (name.equals(folder.getName())) {
                throw new MyBoxException(ResponseCode.FOLDER_NAME_EXIST);
            }
        }
    }

    public void validateOwner(Long memberId) {
        if (!memberId.equals(getOwner().getId())) {
            throw new MyBoxException(ResponseCode.INVALID_TOKEN);
        }
    }

    public List<File> getAllChildFiles() {
        return addAllChildFiles(new ArrayList<>());
    }

    public List<File> addAllChildFiles(List<File> files) {
        files.addAll(childFiles);

        for (Folder folder : childFolders) {
            folder.addAllChildFiles(files);
        }
        return files;
    }

    /**
     * orphanRemoval option을 이용해 하위 폴더들을 삭제
     *  만약 해당 폴더 하위에 파일이나 폴더가 있는 경우에는 삭제하지 않는다.
     */
    public Long deleteChildFolders() {
        Long count = 0L;
        for (Folder childFolder : childFolders) {
            if (childFolder.childFiles.size() == 0
                    && childFolder.deleteChildFolders() == 0) {
                childFolders.remove(childFolder);
                count++;
            }
        }
        return count;
    }
}