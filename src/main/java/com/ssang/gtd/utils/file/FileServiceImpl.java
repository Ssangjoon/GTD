package com.ssang.gtd.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService{
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private String path = "/file";

    @Override
    public List<Map<String, Object>> fileUpload(String type, List<MultipartFile> files, int id) throws Exception {
        List<Map<String, Object>> params = new ArrayList<Map<String,Object>>();

        File f = new File(path);

        if(!f.exists()) {
            f.mkdirs();
        }

        for (MultipartFile file : files) {
            Map<String, Object> param = new HashMap<String, Object>();

            String originalName = file.getOriginalFilename();

            // 임시 파일 생성
            File destination = File.createTempFile("F_"+System.currentTimeMillis(),originalName.substring(originalName.lastIndexOf(".")), f); // prefix, subfix지정

            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(destination));

            String savedName = destination.getName();
            long size = file.getSize();

            param.put("board_type", type);
            param.put("board_id", id);
            param.put("file_name", originalName);
            param.put("saved_file_name", savedName);
            param.put("file_size", size);

            params.add(param);
        }

        return params;
    }
    @Override
    public int fileDelete(String name) {
        File file = new File(path+"/"+name);

        if( file.exists() ){
            if(file.delete()){
                System.out.println("파일삭제 성공");
                return 1;
            }else{
                System.out.println("파일삭제 실패");
                return -1;
            }
        }else{
            System.out.println("파일이 존재하지 않습니다.");
            return -1;
        }
    }

    @Override
    public int fileDeleteAll(List<FileDto> dtoList) throws Exception {
        logger.info("갤러리 게시판 사진 전체 삭제");

        for(FileDto dto:dtoList) {
            int result = fileDelete(dto.getSaved_file_name());
            if(result < 0) {
                return -1;
            }
        }
        return 1;
    }
}
