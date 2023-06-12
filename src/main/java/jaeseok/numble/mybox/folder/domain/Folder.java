package jaeseok.numble.mybox.folder.domain;

import jaeseok.numble.mybox.common.entity.BaseEntity;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

import static jaeseok.numble.mybox.common.constant.MyBoxConstant.FOLDER_SEPARATOR;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
@Table(name = "folder")
public class Folder extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent_path", nullable = false, columnDefinition = "VARCHAR(2000) DEFAULT '/'")
    @ColumnDefault("/")
    private String parentPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Folder parent;


    public String getCurrentPath() {
        return this.getParentPath() + FOLDER_SEPARATOR + this.getName();
    }

    public void validateOwner(String memberId) {
        if (!memberId.equals(getOwner().getId())) {
            throw new MyBoxException(ResponseCode.INVALID_TOKEN);
        }
    }
}
