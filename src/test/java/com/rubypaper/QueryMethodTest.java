package com.rubypaper;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;
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

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryMethodTest {
    @Autowired
    private BoardRepository boardRepo;

    // 테스트 하기 전 데이터 인서트
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

    //p260 쿼리메서드 테스트 - 일반검색
    @Test
    public void testFindByTitle() {
        List<Board> boardList = boardRepo.findByTitle("테스트 제목 10");

        System.out.println("검색 결과");
        for (Board board : boardList) {
            System.out.println("---> " + board.toString());
        }
    }

    //p262 Like 연산자
    @Test
    public void testByContentContaining() {
        List<Board> boardList = boardRepo.findByContentContaining("7");

        System.out.println("검색 결과");
        for (Board board : boardList) {
            System.out.println("---> " + board.toString());
        }
    }

    //p264 여러조건 검색
    @Test
    public void testFindByTitleContainingOrContentContaining() {
        List<Board> boardList =
                boardRepo.findByTitleContainingOrContentContaining("7", "7");

        System.out.println("검색 결과");
        for (Board board : boardList) {
            System.out.println("---> " + board.toString());
        }
    }

    //p266 결과 정렬 order by
    @Test
    public void testFindByTitleContainingOrderBySeqDesc() {
        List<Board> boardList =
                boardRepo.findByTitleContainingOrderBySeqDesc("1");

        System.out.println("검색 결과");
        for (Board board : boardList) {
            System.out.println("---> " + board.toString());
        }
    }

    //p268 페이징
    @Test
    public void testFindByTitleContaining() {
        Pageable paging = PageRequest.of(0, 3, Sort.Direction.DESC, "seq");
        // 타이틀에 제목이 들어간 글 중 0페이지에서 5개
        List<Board> boardList = boardRepo.findByContentContaining("내용", paging);

        System.out.println("검색 결과");
        for (Board board : boardList) {
            System.out.println("---> " + board.toString());
        }
    }

    //p271 페이징 -  위보다 더 나아보임
    @Test
    public void testFindByTitleContaining_2() {
        Pageable paging = PageRequest.of(0, 3, Sort.Direction.DESC, "seq");

        Page<Board> pageInfo = boardRepo.findByTitleContaining("제목", paging);

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