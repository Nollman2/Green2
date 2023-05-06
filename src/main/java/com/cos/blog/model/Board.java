package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "board22")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity //테이블생성
@SequenceGenerator( //시퀀스생성
        name = "FREE22_SEQ_GENERATOR6",
        sequenceName = "FREE22_SEQ6",
        initialValue = 1,
        allocationSize = 1
)

public class Board {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FREE22_SEQ_GENERATOR6")
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터
    private String content;    
    
    private int count;     
    
    //@JoinColumn DB는 Users라는 객체를 인식하지 못하므로 해댱 어노테이션을통해 인지시켜줌 //Users테이블을 통째로 들고온다    				   
    //@joinColumn(name="userId") Board테이블에 저장될때 Users테이블이 해당 이름으로 저장 됨
    //데이테베이스에서 보여질때는 UsersId의 값은 Users테이블의 @Id로 지정한 id값으로 보임
    @ManyToOne(fetch = FetchType.EAGER)	 //Many = Board, User = One
	@JoinColumn(name="userId")
	private Users users;
    
    //Reply테이블을 List로 통째로 들고옴
    //Reply는 many임으로 List를 통해 들고온다
    //Board와 Reply의 관계는 Board가 주인임 
    //mappedBy를 통해 연관관계의 주인을 표시
    //mappedBy="board"를 통해 연결시켜줌 이때 "board"의 값은 Reply테이블의 Board테이블의 이름 board를 쓴다
    //cascade=CascadeType.REMOVE  //Board테이블이 삭제될때 reply테이블까지 함께 삭제됨
    @OneToMany(mappedBy="board", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
    @JsonIgnoreProperties({"board"}) //무한참조 방지
    @OrderBy("id desc") //id순으로 정렬
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;
}