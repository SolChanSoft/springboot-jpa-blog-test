package com.cos.security1.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Getter  @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터
    private String content;  //섬머노트 라이브러리(html)태그가 섞여서 디자인이 됨.

    private int count;  //조회수

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user; //DB는 오프젝트를 저장할 수 없다.  FK,자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board",fetch = FetchType.EAGER)
    private List<Reply> replyList;

    @CreationTimestamp
    private Timestamp createDate;
}
