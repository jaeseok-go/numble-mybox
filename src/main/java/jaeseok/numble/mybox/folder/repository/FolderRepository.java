package jaeseok.numble.mybox.folder.repository;

import jaeseok.numble.mybox.folder.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}
