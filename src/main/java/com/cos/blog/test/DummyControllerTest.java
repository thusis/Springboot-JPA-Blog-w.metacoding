package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다." + id ;
	}
	
	/*
	//email, password
	//save 함수는 id 를 전달하지 않으면 insert를 해주고
	//save 함수는 id 를 전달 & id 에 대한 데이터가 있으면 update 를 해주고
	//save 함수는 id 를 전달 & id 에 대한 데이터가 없으면 insert 를 해주고
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		// json 데이터를 요청  => javaObject (MessageConverter의 Jackson라이브러리)를 이용해서 받아준다
		
		System.out.println("id" + id);
		System.out.println("password"+requestUser.getPassword());
		System.out.println("email"+ requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		userRepository.save(user);

		return null;
	}
		*/
	
	@Transactional //함수 종료시에 자동 commit 이 됨. 더티체킹
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		// json 데이터를 요청  => javaObject (MessageConverter의 Jackson라이브러리)를 이용해서 받아준다
		
		System.out.println("id" + id);
		System.out.println("password"+requestUser.getPassword());
		System.out.println("email"+ requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		return user;
	}
	
	
	//http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser= userRepository.findAll(pageable);		
		List<User> users= pagingUser.getContent();
		return users;
	}
	
	
	//{id}주소로 파라미터를 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		/**
		 * findById(id) : return 타입이 User가 아닌 Optional 임.
		user/4를 찾으면 내가 데이터베이스에서 못찾아오게 되면 user 가 null 이 될테니 return null 이 리턴된다. 그럼 프로그램에 문제가 생길 테니
		Optional 로 User 객체를 감싸서 가져온다. null 인지 아닌지 판단해서 return 할 수 있도록
		 */
		
		/*람다식
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 사용자는 없습니다.");
		});
		 */

		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		
		// 요청 : 웹브라우저
		// user객체 = 자바 오브젝트
		// 변환 ( 웹브라우저가 이해할 수 있는 데이터 ) -> json(GSON 라이브러리) {Content-Type: application/json; UTF-8}
		// 스프링 부트 = MessageConverter 라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter 가 Jackson 이라는 라이브러리를 호출해서 user 오브젝트를 json 으로 변환해 브라우저에 전달한다.
		return user;
		
	}
	// http://localhost:8000/blog/dummy/join(요청)
	// http의 body에 username, password, email데이터를 가지고(요청)
	
//	public String join(@RequestParam("username")String username, String password, String email) { 
	// (1) 파라미터로 받을 수 있다.

	//	public String join(String username, String password, String email) { 
	// (2) 각각  key=value (약속된 규칙)로 받을 수 있다.
//		System.out.println("username : " +username);
//		System.out.println("password : " +password);
//		System.out.println("email : " +email);
//		return "회원가입이 완료되었습니다.";
//	}

	// User 객체로 받을 수 있다
	@PostMapping("/dummy/join")
	public String join(User user) { // key=value (약속된 규칙)
	System.out.println("username : " +user.getUsername());
	System.out.println("password : " +user.getPassword());
	System.out.println("email : " +user.getEmail());
	System.out.println(user.toString());
	
	user.setRole(RoleType.USER);
	userRepository.save(user);

	return "회원가입이 완료되었습니다.";
}
}
