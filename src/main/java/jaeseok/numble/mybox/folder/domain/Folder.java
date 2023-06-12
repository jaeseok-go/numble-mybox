package jaeseok.numble.mybox.folder.domain;

import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static jaeseok.numble.mybox.common.constant.MyBoxConstant.FOLDER_SEPARATOR;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("folder")
@Getter
@SuperBuilder
@Table(name = "folder")
public class Folder extends Element {

    @Column(name = "parent_path", nullable = false, columnDefinition = "VARCHAR(2000) DEFAULT '/'")
    @ColumnDefault("/")
    private String parentPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    public String getCurrentPath() {
        return this.getParentPath() + FOLDER_SEPARATOR + this.getName();
    }

    public void validateOwner(String memberId) {
        if (!memberId.equals(getOwner().getId())) {
            throw new MyBoxException(ResponseCode.INVALID_TOKEN);
        }
    }
}
