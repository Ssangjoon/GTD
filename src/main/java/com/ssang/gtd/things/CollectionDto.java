package com.ssang.gtd.things;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDto {
    int c_no;
    String content;
    boolean is_material;
    Date createdDate;
    Date updatedTime;
}
