package kh.mclass.semim.board.model.dto;

import java.util.List;

public class BoardReplyDto {

//	이름                     널?       유형             
//	---------------------- -------- -------------- 
//	BOARD_REPLY_ID         NOT NULL NUMBER         
//	BOARD_ID               NOT NULL NUMBER         
//	BOARD_REPLY_WRITER     NOT NULL VARCHAR2(20)   
//	BOARD_REPLY_CONTENT    NOT NULL VARCHAR2(4000) 
//	BOARD_REPLY_WRITE_TIME NOT NULL TIMESTAMP(6)   
//	BOARD_REPLY_LOG_IP              VARCHAR2(15)   
//	BOARD_REPLY_LEVEL      NOT NULL NUMBER         
//	BOARD_REPLY_REF        NOT NULL NUMBER         
//  BOARD_REPLY_STEP       NOT NULL NUMBER(3)   

	private Integer boardReplyId;
	private Integer boardId;
	private String boardReplyWriter;
	private String boardReplyContent;
	private String boardReplyWriteTime; // TIMESTAMP // to_cahr 이용
	private String boardReplyLogIp; // 필요없음
	private Integer boardReplyLevel;
	private Integer boardReplyRef;
	private Integer boardReplyStep;

	@Override
	public String toString() {
		return "BoardReplyDto [boardReplyId=" + boardReplyId + ", boardId=" + boardId + ", boardReplyWriter="
				+ boardReplyWriter + ", boardReplyContent=" + boardReplyContent + ", boardReplyWriteTime="
				+ boardReplyWriteTime + ", boardReplyLogIp=" + boardReplyLogIp + ", boardReplyLevel=" + boardReplyLevel
				+ ", boardReplyRef=" + boardReplyRef + ", boardReplyStep=" + boardReplyStep + "]";
	}



	public BoardReplyDto() {
		super();
	}

	

	public BoardReplyDto(Integer boardReplyId, Integer boardId, String boardReplyWriter, String boardReplyContent,
			String boardReplyWriteTime, String boardReplyLogIp, Integer boardReplyLevel, Integer boardReplyRef,
			Integer boardReplyStep) {
		super();
		this.boardReplyId = boardReplyId;
		this.boardId = boardId;
		this.boardReplyWriter = boardReplyWriter;
		this.boardReplyContent = boardReplyContent;
		this.boardReplyWriteTime = boardReplyWriteTime;
		this.boardReplyLogIp = boardReplyLogIp;
		this.boardReplyLevel = boardReplyLevel;
		this.boardReplyRef = boardReplyRef;
		this.boardReplyStep = boardReplyStep;
	}

	public Integer getBoardReplyId() {
		return boardReplyId;
	}

	public Integer getBoardId() {
		return boardId;
	}

	public String getBoardReplyWriter() {
		return boardReplyWriter;
	}

	public String getBoardReplyContent() {
		return boardReplyContent;
	}

	public String getBoardReplyWriteTime() {
		return boardReplyWriteTime;
	}

	public String getBoardReplyLogIp() {
		return boardReplyLogIp;
	}

	public Integer getBoardReplyLevel() {
		return boardReplyLevel;
	}

	public Integer getBoardReplyRef() {
		return boardReplyRef;
	}

	public Integer getBoardReplyStep() {
		return boardReplyStep;
	}



}
