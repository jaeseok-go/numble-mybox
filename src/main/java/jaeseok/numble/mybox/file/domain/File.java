package jaeseok.numble.mybox.file.domain;

import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

import static jaeseok.numble.mybox.common.constant.MyBoxConstant.FILE_SEPARATOR;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "file")
public class File {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size", nullable = false)
    @ColumnDefault("0")
    private Long size;

    @Column(name = "parent_path", nullable = false, columnDefinition = "VARCHAR(2000) DEFAULT '/'")
    @ColumnDefault("/")
    private String parentPath;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = true)
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Folder parent;

    public String getCurrentPath() {
        return this.getParentPath() + FILE_SEPARATOR + this.getName();
    }
}
