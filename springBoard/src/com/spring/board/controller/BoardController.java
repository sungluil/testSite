package com.spring.board.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.board.HomeController;
import com.spring.board.service.boardService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;
import com.spring.common.CommonUtil;

import net.sf.json.JSONObject;

@Controller
public class BoardController {
	
	@Autowired 
	boardService boardService;
	
	final static int PAGESIZE = 10;
	final static int BLOCKSIZE = 10;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/board/boardList.do", method = RequestMethod.GET)
	public String boardList(Locale locale, Model model
			,@RequestParam(defaultValue = "1") int pageNo) throws Exception{
		
		List<BoardVo> boardList = new ArrayList<BoardVo>();

		//int pageSize = 10;
		//int blockSize = 10;
		int totalCnt = 0;
		
		totalCnt = boardService.selectBoardCnt();
		
		PageVo pageVo = new PageVo(pageNo, PAGESIZE, totalCnt, BLOCKSIZE);
		
		if(pageNo <= 0) {
			pageVo.setPageNo(pageNo);
			return "redirect:/board/boardList.do?pageNo=1";
		}

		boardList = boardService.SelectBoardList(pageVo);
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("codeList", boardService.codeList());
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageVo", pageVo);

		
		return "board/boardList";
	}
	@RequestMapping(value = "/board/boardSizeList.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> boardSizeList(Locale locale, Model model, String size
			,@RequestParam(defaultValue = "1") int pageNo) throws Exception{
		
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		logger.info("size  = "+size);
		int pageSize = Integer.parseInt(size);
		int totalCnt = 0;
		//int blockSize = 10;
		
		totalCnt = boardService.selectBoardCnt();
		
		PageVo pageVo = new PageVo(pageNo, pageSize, totalCnt, BLOCKSIZE);
		logger.info("pageSize  = "+pageVo.getPageSize());

		boardList = boardService.SelectSizeBoardList(pageVo);
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageVo", pageVo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardList", boardList);
		
		return map;
	}
	
	@RequestMapping(value = "/board/{boardType}/{boardNum}/boardView.do", method = RequestMethod.GET)
	public String boardView(Locale locale, Model model
			, @PathVariable("boardType") String boardType
			, @PathVariable("boardNum") int boardNum) throws Exception{
		
		BoardVo boardVo = new BoardVo();
		
		boardVo = boardService.selectBoard(boardType,boardNum);
		
		model.addAttribute("boardType", boardType);
		model.addAttribute("boardNum", boardNum);
		model.addAttribute("board", boardVo);
		
		return "board/boardView";
	}
	
	@RequestMapping(value = "/board/boardWrite.do", method = RequestMethod.GET)
	public String boardWrite(Locale locale, Model model, ComCodeVo comCodeVo) throws Exception{
		
		List<ComCodeVo> codeList = new ArrayList<ComCodeVo>();
		
		codeList = boardService.codeList();
		model.addAttribute("codeList", codeList);
		
		return "board/boardWrite";
	}
	
	@RequestMapping(value = "/board/boardWriteAction.do", method = RequestMethod.POST)
	@ResponseBody
	public String boardWriteAction(Locale locale,BoardVo boardVo) throws Exception{
		
		HashMap<String, String> result = new HashMap<String, String>();
		CommonUtil commonUtil = new CommonUtil();
		
		int resultCnt = boardService.boardInsert(boardVo);
		
		result.put("success", (resultCnt > 0)?"Y":"N");
		String callbackMsg = commonUtil.getJsonCallBackString(" ",result);
		
		System.out.println("callbackMsg::"+callbackMsg);
		
		return callbackMsg;
	}
	
	@RequestMapping("/board/boardModify.do")
	public String modify(BoardVo boardVo, Model model) throws Exception {
		String boardType = boardVo.getBoardType();
		int boardNum = boardVo.getBoardNum();
		boardService.selectBoard(boardType, boardNum);
		model.addAttribute("board", boardService.selectBoard(boardType, boardNum));
		return "board/boardModify";
	}
	@RequestMapping("/board/boardModifyAction.do")
	public String modifyAction(BoardVo boardVo) throws Exception {
		
		boardService.boardModify(boardVo);
		
		return "board/boardList";
	}
	@RequestMapping(value = "/board/boardDelete.do", method = RequestMethod.POST)
	public String boardDelete(String boardNum) throws Exception {
		
		logger.info("boardNum = "+boardNum);
		
		String[] arr = boardNum.split(",");

		List<String> list = new ArrayList<String>();

		for(String arrVal:arr) {
			list.add(arrVal);
		}
		logger.info("list = "+list);
		boardService.boardDelete(list);
		
		
		return "board/boardList";
	}
	
	/**
	 * 엑셀 선택 다운로드
	 */
	@RequestMapping("/board/excelDown.do")
	public void excelDown(HttpServletResponse response, @RequestParam(defaultValue = "1") int pageNo) throws Exception {
		

		int totalCnt = boardService.selectBoardCnt();
		
		PageVo pageVo = new PageVo(pageNo, PAGESIZE, totalCnt, BLOCKSIZE);
		
		List<BoardVo> boardList = boardService.SelectBoardList(pageVo);
		
		//엑셀 워크북 생성
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("게시판");
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		
		//테이블 Header 스타일
		CellStyle headerStyle = wb.createCellStyle();
		/**
		 * 얇은 경계선 스타일
		 */
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		
		//배경색 지정
		//headerStyle.setFillForegroundColor(HSSFColorPredefined.AQUA.getIndex());
		//headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		headerStyle.setFillPattern(FillPatternType.BRICKS);
		
		//데이터 가운데 정렬
		//headerStyle.setAlignment(HorizontalAlignment.LEFT);
		headerStyle.setAlignment(HorizontalAlignment.CENTER); //가운데 정렬
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER); //중앙 정렬
		
		//폰트 설정
		Font fontOfGothic = wb.createFont();
		fontOfGothic.setFontName("나눔고딕");
		
		//데이터용 경계 스타일 테투리만 지정
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		
		//헤더 생성
		row = sheet.createRow(rowNo++);
		cell = row.createCell(0);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Type");
		cell = row.createCell(1);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("No");
		cell = row.createCell(2);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Title");
		cell = row.createCell(3);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Comment");
		cell = row.createCell(4);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Date");
		cell = row.createCell(5);
//		cell.setCellStyle(bodyStyle);
//		cell.setCellValue("Writer");
		
		//데이터 생성
		for(BoardVo vo:boardList) {
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getComCodeVo().getCodeName());
			cell = row.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getBoardNum());
			cell = row.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getBoardTitle());
			cell = row.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getBoardComment());
			cell = row.createCell(4);
			cell.setCellStyle(bodyStyle);
			
			//0에서10까지의 자릿수만 나오게 변경 예)2020-01-21
			if(vo.getCreateTime().length() > 8 ) {
				cell.setCellValue(vo.getCreateTime().substring(0, 8));				
			}
//			cell = row.createCell(5);
//			cell.setCellStyle(bodyStyle);
//			cell.setCellValue(vo.getCreator());
		}
		
		//컨텐츠 타입과 파일명 지정
		response.setContentType("ms-vnd/excel");
		
		//저장될이름에 날짜붙여서 저장시키기위해 date사용
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SS");
		Date date = new Date();
		String today = sdf.format(date);

		response.setHeader("Content-Disposition", "attachment;filename=test-"+ today +".xls");
		
		//엑셀 출력
		wb.write(response.getOutputStream());
		wb.close();

	}
	/**
	 * 엑셀 선택 다운로드
	 */
	@RequestMapping("/board/excelSelectDown.do")
	public void excelSelectDown(HttpServletResponse response, String delchk) throws Exception {
				
		logger.info("delchk = "+delchk);
		
		String[] arr = delchk.split(",");

		List<String> list = new ArrayList<String>();
		for(String arrVal:arr) {
			list.add(arrVal);
		}
		
		logger.info("list = "+list);
		List<BoardVo> boardList = boardService.selectExcel(list);

		
		//엑셀 워크북 생성
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("게시판");
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		
		//테이블 Header 스타일
		CellStyle headerStyle = wb.createCellStyle();
		/**
		 * 얇은 경계선 스타일
		 */
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		
		//배경색 지정
		//headerStyle.setFillForegroundColor(HSSFColorPredefined.AQUA.getIndex());
		//headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		headerStyle.setFillPattern(FillPatternType.BRICKS);
		
		//데이터 가운데 정렬
		//headerStyle.setAlignment(HorizontalAlignment.LEFT);
		headerStyle.setAlignment(HorizontalAlignment.CENTER); //가운데 정렬
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER); //중앙 정렬
		
		//폰트 설정
		Font fontOfGothic = wb.createFont();
		fontOfGothic.setFontName("나눔고딕");
		
		//데이터용 경계 스타일 테투리만 지정
		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		
		//헤더 생성
		row = sheet.createRow(rowNo++);
		cell = row.createCell(0);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Type");
		cell = row.createCell(1);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("No");
		cell = row.createCell(2);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Title");
		cell = row.createCell(3);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("Comment");
		cell = row.createCell(4);
		cell.setCellStyle(bodyStyle);
		cell.setCellValue("CreateTime");
//		cell = row.createCell(5);
//		cell.setCellStyle(bodyStyle);
//		cell.setCellValue("Writer");
		
		//데이터 생성
		for(BoardVo vo:boardList) {
			row = sheet.createRow(rowNo++);
			cell = row.createCell(0);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getComCodeVo().getCodeName());
			cell = row.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getBoardNum());
			cell = row.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getBoardTitle());
			cell = row.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getBoardComment());
			cell = row.createCell(4);
			cell.setCellStyle(bodyStyle);
//			cell.setCellValue(vo.getCreateTime());

			
			//0에서10까지의 자릿수만 나오게 변경 예)2020-01-21
			if(vo.getCreateTime().length() > 10 ) {
				cell.setCellValue(vo.getCreateTime().substring(0, 4)+"-"+vo.getCreateTime().substring(4, 6)+"-"+vo.getCreateTime().substring(6, 8));				
			}
//			cell = row.createCell(5);
//			cell.setCellStyle(bodyStyle);
//			cell.setCellValue(vo.getCreator());
		}
		
		//컨텐츠 타입과 파일명 지정
		response.setContentType("ms-vnd/excel");
		
		//저장될이름에 날짜붙여서 저장시키기위해 date사용
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SS");
		Date date = new Date();
		String today = sdf.format(date);
		
		response.setHeader("Content-Disposition", "attachment;filename=test-"+ today +".xls");
		
		//엑셀 출력
		wb.write(response.getOutputStream());
		wb.close();
		
	}
	@RequestMapping(value = "/board/boardSearch.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchBoardList(String boardType, PageVo pageVo,BoardVo boardVo, Model model) throws Exception{

		int page = 1;

		if(pageVo.getPageNo() == 0){
			pageVo.setPageNo(page);;
		}
		logger.info("boardType = "+boardType);
		String[] str=boardType.split(",");
		
		List<String> List = new ArrayList<String>();
		for(int i=0;i<str.length;i++) {
			List.add(str[i]);
		}
		System.out.println("List = "+List);
		
		Map<String, Object> typeList = new HashMap<String, Object>();
		//typeList.put("typeList1", List);
		System.out.println("typeList : "+typeList);
		int totalCnt = boardService.boardTotalSearch(typeList);
		
		//System.out.println("totalCnt = "+totalCnt);
		
		Map<String, Object> keyList = new HashMap<String, Object>();
		keyList.put("keyList1", List);
		keyList.put("pageNo", page);
		//System.out.println("keyList = "+keyList);
		
		
		List<BoardVo> boardList = boardService.searchBoardList(keyList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardList", boardList);
		

		return map;
		
	}
	/*
    @RequestMapping(value = "/board/search2.do", method = RequestMethod.POST)
	public String search2 (String search, String keyword, Model model,@RequestParam(defaultValue = "1") int pageNo) throws Exception {
		
    	
    	int totalCnt = boardService.selectBoardCnt();
    	
    	System.out.println(search+keyword);
    	PageVo pageVo = new PageVo(pageNo, PAGESIZE, totalCnt, BLOCKSIZE);
		Map<String, Object> searchList = new HashMap<String, Object>();
		searchList.put("search", search);
		searchList.put("keyword", keyword);
		searchList.put("pageNo", pageNo);
		System.out.println(searchList);
		
		List<BoardVo> boardList = boardService.search2(searchList);

		model.addAttribute("boardList", boardList);
		model.addAttribute("codeList", boardService.codeList());
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageVo", pageVo);
    	
    	return "board/boardList";
	}
	*/
    @RequestMapping(value = "/board/boardSearch2.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> search2 (String search, String keyword,Model model,@RequestParam(defaultValue = "1") int pageNo) throws Exception {
    	

    	int totalCnt = boardService.selectBoardCnt();
    	System.out.println(search+keyword);
    	PageVo pageVo = new PageVo(pageNo, PAGESIZE, totalCnt, BLOCKSIZE);
    	Map<String, Object> searchList = new HashMap<String, Object>();
    	searchList.put("search", search);
    	searchList.put("keyword", keyword);
    	searchList.put("pageNo", pageNo);
    	System.out.println(searchList);
    	
    	List<BoardVo> boardList = boardService.search2(searchList);
    	
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardList", boardList);
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageVo", pageVo);
    	
    	return map;
    }
	
	@RequestMapping(value = "/board/modalWindow.do")
	public String modal() {
		return "board/modalWindow";
	}
	@RequestMapping(value = "/board/popup.do")
	public String popup() {
		return "board/popup";
	}
	
	@RequestMapping(value = "/board/index.do")
	public String homePage(Model model, String namePage) {
		
		if(namePage == null) {
			namePage = "boardList.do";
		}

		System.out.println(namePage);
		model.addAttribute("namePage", namePage);
		
		return "index";
	}
	
	
	
	
	
}
