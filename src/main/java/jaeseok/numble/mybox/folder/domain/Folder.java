package jaeseok.numble.mybox.folder.domain;

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

    public Folder createFolder(String name) {
        Folder child = Folder.builder()
                .name(name)
                .parent(this)
                .owner(owner)
                .build();

        childFolders.add(child);

        return child;
    }

    public boolean hasFolderName(String folderName) {
        return childFolders.stream()
                .filter(f -> folderName.equals(f.getName()))
                .findAny()
                .isPresent();
    }

    public boolean hasFileName(String fileName) {
        return childFiles.stream()
                .filter(f -> fileName.equals(f.getName()))
                .findAny()
                .isPresent();
    }

    public boolean isOwner(Long memberId) {
        return memberId.equals(this.owner.getId());
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
            count += childFolder.deleteChildFolders();

            if (childFolder.countChild() == 0) {
                childFolders.remove(childFolder);
                count++;
            }
        }
        return count;
    }

    private Long countChild() {
        return this.childFiles.size()
                + this.childFolders.size()
                + this.childFolders.stream().mapToLong(Folder::countChild).sum();
    }
}