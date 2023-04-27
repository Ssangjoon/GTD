package com.ssang.gtd.utils.file.dto;

import com.ssang.gtd.entity.FileEntity;
import com.ssang.gtd.entity.MatCol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class FileCreateDto {
    String board_type;
    String file_name;
    String saved_file_name;
    long file_size;
    MatCol matcol;
    public FileEntity toEntity(){
        return FileEntity.builder()
                .board_type(board_type)
                .file_name(file_name)
                .saved_file_name(saved_file_name)
                .file_size(file_size)
                .matcol(matcol)
                .build();
    }
}
