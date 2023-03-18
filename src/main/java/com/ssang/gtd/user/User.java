package com.ssang.gtd.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class User {
    int id;
    String password;
    String nickname;
    String notes;
    String membership;
    Date createdDate;
    Date updatedTime;
    String status;
}
