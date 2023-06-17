package com.ssang.gtd.domain.user.domain;

import com.ssang.gtd.global.utils.enums.Role;
import com.ssang.gtd.global.utils.enums.Gender;
import com.ssang.gtd.global.utils.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="MemberDetail")
@DiscriminatorValue("MemberDetail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberDetail extends Member {
    @Column(length = 50, nullable = true)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = true)
    private MemberStatus status;



    public void authorizeUser() {
        this.role = Role.USER;
    }

    public MemberDetail(String email, String password, String userName, String name, Gender gender, Role role, MemberStatus status) {
        super(email, password, userName, name, gender);
        this.role = role;
        this.status = status;
    }

    public MemberDetail(String email, Role role, MemberStatus status) {
        super(email);
        this.role = role;
        this.status = status;
    }

    public MemberDetail(Long id) {
        super(id);
    }
}
