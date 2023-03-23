package com.ssang.gtd.utils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component("fileUtils")
public class FileUtils {
    private static final String filePath = "C:\\upload\\"; // 파일이 저장될 위치

    public List<Map<String, Object>> parseInsertFileInfo(MultipartHttpServletRequest mpRequest) throws Exception{

        List<MultipartFile> fileList = mpRequest.getFiles("file");

        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;

        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null;


        File file = new File(filePath);
        if(file.exists() == false) {
            file.mkdirs();
        }

        for (MultipartFile mf : fileList) {
            String originFileName = mf.getOriginalFilename(); // 원본 파일 명
            if(originFileName != "") {
                originalFileName = mf.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = getRandomString() + originalFileExtension;

                file = new File(filePath + storedFileName);
                mf.transferTo(file);
                listMap = new HashMap<String, Object>();
                listMap.put("org_file", originalFileName);
                listMap.put("stored_file", storedFileName);
                listMap.put("file_size", mf.getSize());
                list.add(listMap);
            }
        }
        return list;
    }


    public static String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private String saveFile(MultipartFile file) throws Exception {
        if (file != null && file.getSize() > 0) {
            // 파일을 저장할 때 사용할 파일명을 준비한다.
            String filename = UUID.randomUUID().toString();

            // 파일명의 확장자를 알아낸다.
            int dotIndex = file.getOriginalFilename().lastIndexOf(".");
            if (dotIndex != -1) {
                filename += file.getOriginalFilename().substring(dotIndex);
            }

            // 파일을 지정된 폴더에 저장한다.
            File photoFile = new File("./upload/book/" + filename); // App 클래스를 실행하는 프로젝트 폴더
            file.transferTo(photoFile.getCanonicalFile()); // 프로젝트 폴더의 전체 경로를 전달한다.

            return filename;

        } else {
            return null;
        }
    }
}