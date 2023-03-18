package jaeseok.numble.mybox.folder.domain;

import jaeseok.numble.mybox.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "folder")
public class Folder {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Parent parent;
}
