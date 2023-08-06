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

    public void addChildFile(File file) {
        this.childFiles.add(file);
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

    public boolean isRemain() {
        return this.childFiles.size() + this.childFolders.size() > 0;
    }

    public void removeChildFile(File file) {
        this.childFiles.remove(file);
        file.removeParent();
    }

    public void removeChildFolder(Folder folder) {
        this.childFolders.remove(folder);
        folder.removeParent();
    }

    public void addChildFolder(Folder folder) {
        this.childFolders.add(folder);
        folder.setParent(this);
    }

    public Integer countChild() {
        return this.childFiles.size()
                + this.childFolders.size();
    }
}