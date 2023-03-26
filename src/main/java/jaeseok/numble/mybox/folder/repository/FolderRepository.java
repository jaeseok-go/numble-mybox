package jaeseok.numble.mybox.folder.repository;

import jaeseok.numble.mybox.folder.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByOwnerAndParentPathStartsWithOrderByParentPathDesc(String ownerId, String path);
}
