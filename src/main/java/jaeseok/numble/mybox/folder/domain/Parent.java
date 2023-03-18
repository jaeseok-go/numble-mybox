package jaeseok.numble.mybox.folder.domain;

import javax.persistence.*;

@Entity
@Table
public class Parent {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "path", nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;
}
