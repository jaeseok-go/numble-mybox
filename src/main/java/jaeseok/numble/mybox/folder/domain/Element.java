package jaeseok.numble.mybox.folder.domain;

import jaeseok.numble.mybox.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Element extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Folder parent;

    protected void removeParent() {
        this.parent = null;
    }

    protected void setParent(Folder folder) {
        this.parent = folder;
    }
}
