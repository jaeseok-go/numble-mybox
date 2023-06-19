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
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Folder> childFolders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public void validateOwner(String memberId) {
        if (!memberId.equals(getOwner().getId())) {
            throw new MyBoxException(ResponseCode.INVALID_TOKEN);
        }
    }

    public String getCurrentPath() {
        Folder parent = super.getParent();
        return parent == null ?
                "/" :
                parent.getCurrentPath() + "/" + this.getName();
    }

    public Integer countTotalElement() {
        int currentFolderCount = 1;
        int folderCount = childFolders.stream().mapToInt(Folder::countTotalElement).sum();
        int fileCount = childFiles.size();
        
        return currentFolderCount + folderCount +fileCount;

    }
}
