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
    int id;
    String content;
    Date createdDate;
    Date updatedTime;
    String status;
}
