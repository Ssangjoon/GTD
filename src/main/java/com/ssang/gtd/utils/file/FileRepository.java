package com.ssang.gtd.utils.file;

import com.ssang.gtd.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
