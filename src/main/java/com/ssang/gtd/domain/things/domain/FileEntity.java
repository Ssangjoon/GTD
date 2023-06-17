package com.ssang.gtd.domain.things.domain;

import com.ssang.gtd.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String board_type;
    @Column(length = 50, nullable = false)
    private String file_name;
    @Column(length = 50, nullable = false)
    private String saved_file_name;
    @Column(length = 50, nullable = false)
    private long file_size;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="matcolId")
    private MatCol matcol;
    @Builder
    public FileEntity(Long id, String board_type, String file_name, String saved_file_name, long file_size, MatCol matcol) {
        this.id = id;
        this.board_type = board_type;
        this.file_name = file_name;
        this.saved_file_name = saved_file_name;
        this.file_size = file_size;
        this.matcol = matcol;
    }
}
