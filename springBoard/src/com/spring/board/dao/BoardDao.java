package com.spring.board.dao;

import java.util.List;
import java.util.Map;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;

public interface BoardDao {

	public String selectTest() throws Exception;

	public List<BoardVo> selectBoardList(PageVo pageVo) throws Exception;
	
	public List<BoardVo> selectSizeBoardList(PageVo pageVo) throws Exception;

	public BoardVo selectBoard(BoardVo boardVo) throws Exception;

	public int selectBoardCnt() throws Exception;
	
	public int boardTotalSearch(Map<String, Object> typeList) throws Exception;

	public int boardInsert(BoardVo boardVo) throws Exception;

	public int boardModify(BoardVo boardVo) throws Exception;
	
	public List<BoardVo> boardDelete(List<String> list) throws Exception;
	
	public List<BoardVo> selectExcel(List<String> list) throws Exception;
	
	public List<ComCodeVo> codeList() throws Exception;
	
	public List<BoardVo> searchBoardList(Map<String, Object> keyList) throws Exception;

	public List<BoardVo> search2(Map<String, Object> map) throws Exception;
}
