package jaeseok.numble.mybox.folder.repository;

import jaeseok.numble.mybox.folder.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findFolderByOwnerAndParent(Long ownerId, Long parentId);
}
