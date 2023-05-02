package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "reply2")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "REPLY2_SEQ_GENERATOR3",
        sequenceName = "REPLY2_SEQ3",
        initialValue = 1,
        allocationSize = 1
)
@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLY2_SEQ_GENERATOR3")
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne // Many = Reply, One = Board 하나의게시글에 여려개의 댓글
    @JoinColumn(name = "boardId")
    private Board board;  

    @ManyToOne(fetch = FetchType.EAGER) //Many = Reply, One = Users 하나의유저가 여러개의 댓글
    @JoinColumn(name = "userid")
    private Users users;

    @CreationTimestamp
    private Timestamp createDate;
}
