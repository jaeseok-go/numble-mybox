package jaeseok.numble.mybox.folder.repository;

import jaeseok.numble.mybox.folder.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

// ParentPathStartsWith
public interface FolderRepository extends JpaRepository<Folder, Long> {
    Long deleteByOwnerAndParentPathStartsWith(String ownerId, String path);
}
