package jaeseok.numble.mybox.file.domain;

import jaeseok.numble.mybox.folder.domain.Element;
import jaeseok.numble.mybox.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("file")
public class File extends Element {

    @Column(name = "size", nullable = false)
    @ColumnDefault("0")
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    public String getCurrentPath() {
        return super.getParent().getCurrentPath() + "/" + this.getName();
    }
}
