package jaeseok.numble.mybox.file.domain;

import jaeseok.numble.mybox.folder.domain.Element;
import jaeseok.numble.mybox.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static jaeseok.numble.mybox.common.constant.MyBoxConstant.FILE_SEPARATOR;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@Entity
@Table(name = "file")
public class File extends Element {

    @Column(name = "size", nullable = false)
    @ColumnDefault("0")
    private Long size;

    @Column(name = "parent_path", nullable = false, columnDefinition = "VARCHAR(2000) DEFAULT '/'")
    @ColumnDefault("/")
    private String parentPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    public String getCurrentPath() {
        return this.getParentPath() + FILE_SEPARATOR + this.getName();
    }
}
