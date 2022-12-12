package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 현 클래스가 ORM  임을 알리기 위한 어노테이션이 최하단에 있는 것이 이상적 
public class Board {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob //대용량 데이터
	private String content; //섬머노트 라이브러리 <html> 태그가 섞여 디자인이 되기 때문에 대용량 필요
	
	@ColumnDefault("0")
	private int count; //조회수
	
	// User객체에서 외래키로 User의 UserId를 가져올 것임
//	private int userId; 	//DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있따.
	@ManyToOne(fetch = FetchType.EAGER)// Many = Board, User = One
	@JoinColumn(name="userId")
	private User user; 
	
	@OneToMany(mappedBy="board", fetch=FetchType.EAGER) 
	//mappedBy가 적혀있으면: 연관관계의 주인이 아니다(난FK가 아니에요) DB에 칼럼을 만들지 마세요. <=Reply 객체 중 필드를 갖고온 것뿐
//	@JoinColumn(name="replyId")
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
