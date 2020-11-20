package com.spring.board.service;

import java.util.List;
import java.util.Map;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;

public interface boardService {

	public String selectTest() throws Exception;

	public List<BoardVo> SelectBoardList(PageVo pageVo) throws Exception;
	
	public List<BoardVo> SelectSizeBoardList(PageVo pageVo) throws Exception;

	public BoardVo selectBoard(String boardType, int boardNum) throws Exception;

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
