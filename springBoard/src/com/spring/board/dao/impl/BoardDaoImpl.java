package com.spring.board.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.dao.BoardDao;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;

@Repository
public class BoardDaoImpl implements BoardDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public String selectTest() throws Exception {
		// TODO Auto-generated method stub
		
		String a = sqlSession.selectOne("board.boardList");
		
		return a;
	}
	/**
	 * 
	 * */
	@Override
	public List<BoardVo> selectBoardList(PageVo pageVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.boardList",pageVo);
	}
	
	@Override
	public int selectBoardCnt() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.boardTotal");
	}
	
	@Override
	public BoardVo selectBoard(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.boardView", boardVo);
	}
	
	@Override
	public int boardInsert(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.boardInsert", boardVo);
	}
	@Override
	public int boardModify(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("board.boardModify", boardVo);
	}
	@Override
	public List<BoardVo> boardDelete(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.boardDeleteAll",list);
	}
	@Override
	public List<BoardVo> selectExcel(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.excelSelect",list);
	}
	@Override
	public List<BoardVo> selectSizeBoardList(PageVo pageVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.boardSizeList",pageVo);
	}
	@Override
	public List<ComCodeVo> codeList() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.codeList");
	}
	@Override
	public List<BoardVo> searchBoardList(Map<String, Object> keyList) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.boardSearchList",keyList);
	}
	@Override
	public List<BoardVo> search2(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.boardSearch2", map);
		
	}
	@Override
	public int boardTotalSearch(Map<String, Object> typeList) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.boardTotalSearch", typeList);
	}

	
}
