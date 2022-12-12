package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다
// @Repository 생략 가능하다
// CRUD 에 필요한 메소드를 제공
public interface UserRepository extends JpaRepository<User,Integer> {

}
