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
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jaeseok.numble.mybox.common.constant.MyBoxConstant.FOLDER_SEPARATOR;

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
    @OneToMany(mappedBy = "parent")
    private List<Folder> childFolders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<File> childFiles = new ArrayList<>();

    public String getCurrentPath() {
        return this.getParentPath() + FOLDER_SEPARATOR + this.getName();
    }

    public void validateOwner(String memberId) {
        if (!memberId.equals(getOwner().getId())) {
            throw new MyBoxException(ResponseCode.INVALID_TOKEN);
        }
    }
}
