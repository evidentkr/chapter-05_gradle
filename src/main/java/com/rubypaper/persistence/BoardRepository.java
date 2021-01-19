package com.rubypaper.persistence;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.rubypaper.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {
	//p258 쿼리 메서드
	List<Board> findByTitle(String searchKeyword);

	//p262 Like 연산자
	List<Board> findByContentContaining(String searchKeyword);

	//p264 여러조건 검색
	List<Board> findByTitleContainingOrContentContaining(String title, String content);
	
	//p266 결과 정렬 order by
	List<Board> findByTitleContainingOrderBySeqDesc(String searchKeyword);

	//p268 페이징
	List<Board> findByContentContaining(String searchKeyword, Pageable paging);

	//p271 페이징
	Page<Board> findByTitleContaining(String searchKeyword, Pageable paging);
	
	//=============================================================================================
	
	//p275 쿼리어노테이션 위치기반 파라미터 검색
	@Query("SELECT b FROM Board b " + "WHERE b.title like %?1% ORDER BY b.seq DESC")
	List<Board> queryAnnotationTest1(String searchKeyword);
	
	//p277 쿼리어노테이션 이름기반 파라미터 검색
	@Query("SELECT b FROM Board b " + "WHERE b.title like %:searchKeyword%  " + "ORDER BY b.seq DESC")
	List<Board> queryAnnotationTest1_2(@Param("searchKeyword") String searchKeyword);
	
	//p278 특정변수만 조회하기
	@Query("SELECT b.seq, b.title, b.writer, b.createDate " + "FROM Board b " + "WHERE b.title like %?1% " + "ORDER BY b.seq DESC")
	List<Object[]> queryAnnotationTest2(@Param("searchKeyword") String searchKeyword);
	
	//p280 네이티브 쿼리
	@Query(value = "select seq, title, writer, create_date " + "from board where title like '%'||?1||'%' " + "order by seq desc", nativeQuery = true)
	List<Object[]> queryAnnotationTest3(String searchKeyword);
	
	//p282 페이징 및 정렬 처리
	@Query("SELECT b FROM Board b ORDER BY b.seq DESC")
	List<Board> queryAnnotationTest4(Pageable paging);

	//독자적 페이징 및 정렬 처리
	@Query("SELECT b FROM Board b ORDER BY b.seq DESC")
	Page<Board> queryAnnotationTest4_2(Pageable paging);
}
