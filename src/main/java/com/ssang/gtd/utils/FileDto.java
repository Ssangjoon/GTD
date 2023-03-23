package com.ssang.gtd.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String board_type;
    private int board_id;
    private int file_num;
    private String file_name;
    private String saved_file_name;
    private int file_size;
    private String upload_dt;
    private String insert_dt;
}
