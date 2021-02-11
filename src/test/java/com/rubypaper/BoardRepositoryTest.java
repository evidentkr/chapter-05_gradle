package com.rubypaper;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;


//### 디폴트 JPA 테스트 ( 쿼리메서드/쿼리어노테이션/쿼리dsl 사용 안함)
@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTest {
	@Autowired
	private BoardRepository boardRepo;

	//p250
/*	@Test
	public void testInsertBoard() {
		Board board = new Board();
		board.setTitle("첫 번째 게시글");
		board.setWriter("테스터");
		board.setContent("잘 등록되나요?");
		board.setCreateDate(new Date());
		board.setCnt(0L);

		boardRepo.save(board);
	}*/

	@Before
	public void dataPrepare() {
		Board board = new Board();
		board.setTitle("첫 번째 게시글");
		board.setWriter("테스터");
		board.setContent("잘 등록되나요?");
		board.setCreateDate(new Date());
		board.setCnt(0L);

		boardRepo.save(board);
	}

	@After
	public void tearDown() throws Exception {
		boardRepo.deleteAll();
	}

	//p252 - spring.jpa.hibernate.ddl-auto=update 로 세팅 후
	@Test
	public void testGetBoard() {
		Board board = boardRepo.findById(1L).get();
		System.out.println(board.toString());
/*
	}

	//p253
	@Test
	public void testUpdateBoard() {
		System.out.println("=== 1번 게시글 조회 ===");
		Board board = boardRepo.findById(1L).get();
*/

		System.out.println("=== 1번 게시글 제목 수정 ===");
		board.setTitle("제목을 수정했습니다.");
		boardRepo.save(board);
	}

	//p255
	/*@Test
	public void testDeleteBoard() {
		boardRepo.deleteById(1L);
	}*/
	
}
