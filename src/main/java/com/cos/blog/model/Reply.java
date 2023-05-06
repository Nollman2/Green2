package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "reply22")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "REPLY22_SEQ_GENERATOR3",
        sequenceName = "REPLY22_SEQ3",
        initialValue = 1,
        allocationSize = 1
)
@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLY22_SEQ_GENERATOR3")
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne // Many = Reply, One = Board 하나의게시글에 여려개의 댓글
    @JoinColumn(name = "boardid")
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Board board;  

    @ManyToOne(fetch = FetchType.EAGER) //Many = Reply, One = Users 하나의유저가 여러개의 댓글
    @JoinColumn(name = "userid")    
    private Users users;

    @CreationTimestamp
    private Timestamp createDate;
    
    public void update(Users user, Board board, String content) {
    	setUsers(user);
    	setBoard(board);
    	setContent(content);
    	
    }
    
}
