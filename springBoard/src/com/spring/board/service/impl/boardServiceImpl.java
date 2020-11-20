package com.spring.board.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.BoardDao;
import com.spring.board.service.boardService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;

@Service
public class boardServiceImpl implements boardService{
	
	@Autowired
	BoardDao boardDao;
	
	@Override
	public String selectTest() throws Exception {
		// TODO Auto-generated method stub
		return boardDao.selectTest();
	}
	
	@Override
	public List<BoardVo> SelectBoardList(PageVo pageVo) throws Exception {
		// TODO Auto-generated method stub
		
		return boardDao.selectBoardList(pageVo);
	}
	
	@Override
	public int selectBoardCnt() throws Exception {
		// TODO Auto-generated method stub
		return boardDao.selectBoardCnt();
	}
	
	@Override
	public BoardVo selectBoard(String boardType, int boardNum) throws Exception {
		// TODO Auto-generated method stub
		BoardVo boardVo = new BoardVo();
		
		boardVo.setBoardType(boardType);
		boardVo.setBoardNum(boardNum);
		
		return boardDao.selectBoard(boardVo);
	}
	
	@Override
	public int boardInsert(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.boardInsert(boardVo);
	}

	@Override
	public int boardModify(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.boardModify(boardVo);
	}

	@Override
	public List<BoardVo> boardDelete(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.boardDelete(list);
	}
	@Override
	public List<BoardVo> selectExcel(List<String> list) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.selectExcel(list);
	}

	@Override
	public List<BoardVo> SelectSizeBoardList(PageVo pageVo) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.selectSizeBoardList(pageVo);
	}

	@Override
	public List<ComCodeVo> codeList() throws Exception {
		// TODO Auto-generated method stub
		return boardDao.codeList();
	}

	@Override
	public List<BoardVo> searchBoardList(Map<String, Object> keyList) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.searchBoardList(keyList);
	}

	@Override
	public List<BoardVo> search2(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.search2(map);
	}

	@Override
	public int boardTotalSearch(Map<String, Object> typeList) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.boardTotalSearch(typeList);
	}
	
}
