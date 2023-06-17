package com.ssang.gtd.global.utils.file;

import com.ssang.gtd.domain.things.domain.FileEntity;
import com.ssang.gtd.domain.things.domain.MatCol;
import com.ssang.gtd.global.utils.file.dto.FileDto;
import com.ssang.gtd.global.utils.file.dto.FileCreateDto;
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
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String FILEPATH = "C:\\upload\\"; // 파일이 저장될 위치

    @Override
    public List<FileEntity> fileUpload(String type, List<MultipartFile> files, Long id) throws Exception {
        //List<Map<String, Object>> params = new ArrayList<Map<String,Object>>();
        List<FileEntity> params = new ArrayList<>();

        File f = new File(FILEPATH);

        if(!f.exists()) {
            f.mkdirs();
        }

        for (MultipartFile file : files) {
            Map<String, Object> param = new HashMap<String, Object>();

            String originalName = file.getOriginalFilename(); // 원본 파일 명
            if(originalName != "") {
                // 임시 파일 생성
                File destination = File.createTempFile("F_"+System.currentTimeMillis(),originalName.substring(originalName.lastIndexOf(".")), f); // prefix, subfix지정

                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(destination));

                String savedName = destination.getName();
                long size = file.getSize();

                FileCreateDto fileCreateDto = new FileCreateDto();
                fileCreateDto.setBoard_type(type);
                fileCreateDto.setFile_name(originalName);
                fileCreateDto.setSaved_file_name(savedName);
                fileCreateDto.setFile_size(size);

                MatCol matcol = MatCol.builder().id(id).build();
                fileCreateDto.setMatcol(matcol);

                params.add(fileCreateDto.toEntity());
            }
        }

        return params;
    }
    @Override
    public int fileDelete(String name) {
        File file = new File(FILEPATH+"/"+name);

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

        for(FileDto dto:dtoList) {
            int result = fileDelete(dto.getSaved_file_name());
            if(result < 0) {
                return -1;
            }
        }
        return 1;
    }
}
