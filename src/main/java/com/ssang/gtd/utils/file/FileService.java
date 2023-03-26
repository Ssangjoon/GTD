package com.ssang.gtd.utils.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {
    List<Map<String, Object>> fileUpload(String type, List<MultipartFile> files, int id) throws Exception;
    int fileDelete(String name);
    int fileDeleteAll(List<FileDto> fileDto) throws Exception;
}
