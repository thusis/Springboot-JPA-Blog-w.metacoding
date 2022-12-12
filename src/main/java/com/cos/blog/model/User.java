package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java(다른 언어) Object -> 테이블로 매핑해주는 기술 
@Entity //User클래스가 MySQL에 테이블이 생성이 된다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
// @DynamicInsert //insert할때 null 인 필드 제외
public class User {
	
	@Id //PrimaryKey
//	@GeneratedValue(strategy=GenerationType.AUTO) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다 (오라클의 경우 sequence, mysql의 경우 auto increment)
	private int id; //시퀀스, auto_increment
	
	@Column(nullable=false, length=30)
	private String username;
	
	@Column(nullable=false, length=100) // 123456 => 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	//myEmail, my-email
	
//	@ColumnDefault("'user'")
//	private String role; 
	//admin, user, manager 등의 권한을 주는 역할
	//사실 String 보다는 Enum을 쓰는게 좋다.
	
	@Enumerated(EnumType.STRING)
	private RoleType role;	//(1)DB는 RoleType이라느넥 없다 (2)ADMIN, USER
	
	@CreationTimestamp //시간이 자동 입력
	private Timestamp createDate;
}
