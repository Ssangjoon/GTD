package com.ssang.gtd.global.utils.file;

import com.ssang.gtd.domain.things.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
