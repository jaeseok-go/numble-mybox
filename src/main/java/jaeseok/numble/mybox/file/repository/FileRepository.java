package jaeseok.numble.mybox.file.repository;

import jaeseok.numble.mybox.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT SUM(f.size) FROM File f WHERE f.owner = :memberId")
    Long sumSizeByMemberId(@Param("memberId") String memberId);
}
