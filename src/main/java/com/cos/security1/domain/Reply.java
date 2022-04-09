package com.cos.security1.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter  @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reply {
    // 게시판의 내용중 답변

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne  //여러개의 개시글에는 여러개의 답변이 있다.
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Reply> replyList;

    @CreationTimestamp
    private Timestamp createDate;
}
