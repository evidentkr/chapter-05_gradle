package com.rubypaper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryAnnotationTest {
	@Autowired
	private BoardRepository boardRepo;

	@Before
	public void dataPrepare() {
		for (int i = 1; i <= 20; i++) {
			Board board = new Board();
			board.setTitle("테스트 제목 " + i);
			board.setWriter("테스터");
			board.setContent("테스트 내용 " + i);
			board.setCreateDate(new Date());
			board.setCnt(0L);
			boardRepo.save(board);
		}
	}

	@After
	public void tearDown() throws Exception {
		boardRepo.deleteAll();
	}

	//p275 쿼리어노테이션 위치기반 파라미터 검색
	@Test
	public void testQueryAnnotationTest1() {
	    List<Board> boardList = boardRepo.queryAnnotationTest1("테스트 제목 1");

	    System.out.println("검색 결과");
	    for (Board board : boardList) {
		System.out.println("---> " + board.toString());
	    }
	}

	//p277 쿼리어노테이션 이름기반 파라미터 검색
	@Test
	public void testQueryAnnotationTest1_2() {
		List<Board> boardList = boardRepo.queryAnnotationTest1_2("테스트 제목 1");

		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("---> " + board.toString());
		}
	}
	
	//p278 특정변수만 조회
	@Test
	public void testQueryAnnotationTest2() {
		List<Object[]> boardList = boardRepo.queryAnnotationTest2("테스트 제목 1");

		System.out.println("검색 결과");
		for (Object[] row : boardList) {
			System.out.println("---> " + Arrays.toString(row));
		}
	}
	
	//280 네이티브 쿼리
	@Test
	public void testQueryAnnotationTest3() {
		List<Object[]> boardList = boardRepo.queryAnnotationTest3("테스트 제목 1");

		System.out.println("검색 결과");
		for (Object[] row : boardList) {
			System.out.println("---> " + Arrays.toString(row));
		}
	}
	
	//282 페이징 및 정렬 처리
	@Test
	public void testQueryAnnotationTest4() {
		Pageable paging = PageRequest.of(0, 3, Sort.Direction.DESC, "seq");

		List<Board> boardList = boardRepo.queryAnnotationTest4(paging);
				
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("---> " + board.toString());
		}
	}

	//독자적 페이징 및 정렬 처리
	@Test
	public void testQueryAnnotationTest4_2() {
		Pageable paging = PageRequest.of(0, 3, Sort.Direction.DESC, "seq");

		Page<Board> pageInfo = boardRepo.queryAnnotationTest4_2(paging);

		System.out.println("PAGE SIZE : " + pageInfo.getSize());
		System.out.println("TOTAL PAGES : " + pageInfo.getTotalPages());
		System.out.println("TOTAL COUNT : " + pageInfo.getTotalElements());
		System.out.println("NEXT : " + pageInfo.nextPageable());

		List<Board> boardList = pageInfo.getContent();

		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("---> " + board.toString());
		}
	}
}