package com.ssang.gtd.entity;


import com.ssang.gtd.test.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;


@DynamicInsert
@Entity
@Getter
@ToString
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Member_Type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = true)
    private String email;
    @Column(length = 100, nullable = true)
    private String password;
    @Column(length = 50, nullable = true)
    private String userName;
    @Column(length = 50, nullable = true)
    private  String name;
    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Member(String email, String password, String userName, String name, Gender gender) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.name = name;
        this.gender = gender;
    }

    public Member(Long id) {
        this.id = id;
    }

    public Member(String email) {
        this.email = email;
    }

    public void update(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }


    public Member update(String name) {
        this.name = name;
        return this;
    }

}

