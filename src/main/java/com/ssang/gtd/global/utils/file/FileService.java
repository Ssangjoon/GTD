package com.ssang.gtd.global.utils.file;

import com.ssang.gtd.domain.things.domain.FileEntity;
import com.ssang.gtd.global.utils.file.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    public List<FileEntity> fileUpload(String type, List<MultipartFile> files, Long id) throws Exception;
    public int fileDelete(String name);
    public int fileDeleteAll(List<FileDto> fileDto) throws Exception;
}
