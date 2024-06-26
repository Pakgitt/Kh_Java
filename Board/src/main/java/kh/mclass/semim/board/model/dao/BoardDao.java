package kh.mclass.semim.board.model.dao;

import static jdbc.common.JdbcTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kh.mclass.semim.board.model.dto.BoardDetailDto;
import kh.mclass.semim.board.model.dto.BoardDto;
import kh.mclass.semim.board.model.dto.BoardInsertDto;
import kh.mclass.semim.board.model.dto.BoardListDto;
import kh.mclass.semim.board.model.dto.BoardReadDto;
import kh.mclass.semim.board.model.dto.BoardReplyListDto;
import kh.mclass.semim.board.model.dto.BoardReplyWriteDto;
import kh.mclass.semim.board.model.dto.FileReadDto;
import kh.mclass.semim.board.model.dto.FileWriteDto;

//이름           널?       유형             
//------------ -------- -------------- 
//BOARD_ID     NOT NULL NUMBER         
//SUBJECT      NOT NULL VARCHAR2(120)  
//CONTENT      NOT NULL VARCHAR2(4000) 
//WRITE_TIME   NOT NULL TIMESTAMP(6)   
//LOG_IP                VARCHAR2(15)   
//BOARD_WRITER NOT NULL VARCHAR2(20)   
//HIT          NOT NULL NUMBER    
public class BoardDao {
	
//	 select list - all
	public List<FileReadDto> selectFileList(Connection conn, Integer boardId) {
		List<FileReadDto> result = null;
		String sql = "select BOARD_ID, BOARD_FILE_ID, SAVED_FILE_PATH_NAME, ORIGINAL_FILENAME"
				+ " from board_file where board_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			// ?처리
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			// ResultSet 처리
			if (rs.next()) {
				result = new ArrayList<FileReadDto>();
				do {
					FileReadDto dto = new FileReadDto(rs.getInt("BOARD_ID"), rs.getString("BOARD_FILE_ID"),
							rs.getString("SAVED_FILE_PATH"), rs.getString("ORIGINAL_FILENAME"));
					result.add(dto);
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close(rs);
		close(pstmt);
		return result;
	}

	// select total count
	public int selectTotalCount(Connection conn) {
		int result = 0;
		String sql = "SELECT COUNT(*) CNT FROM BOARD";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// ResultSet 처리
			if (rs.next()) {
				result = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close(rs);
		close(pstmt);
		return result;
	}

	// update - readCount 조회수
	public int updateReadCount(Connection conn, Integer boardId) {
		int result = 0;
		String sql = "update board set hit=hit+1 where board_id=?"; // TODO
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 처리
			pstmt.setInt(1, boardId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(pstmt);
		return result;
	}

//	 select list - all
	public List<BoardListDto> selectPageList(Connection conn, int start, int end) {
		List<BoardListDto> result = null;
		String sql = "select t2.* from (select t1.*, rownum rn"
				+ " from (select * from board order by board_id desc) t1) t2 where rn between ? and ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			// ?처리
			// 한페이지당 글 수 * (현재페이지-1)+1
			pstmt.setInt(1, start);
			// 한페이지당 글 수 * (현재페이지)
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			// ResultSet 처리
			if (rs.next()) {
				result = new ArrayList<BoardListDto>();
				do {
					BoardListDto dto = new BoardListDto(rs.getInt("BOARD_ID"), rs.getString("SUBJECT"),
							rs.getString("BOARD_WRITER"), rs.getString("WRITE_TIME"), rs.getInt("HIT"));
					result.add(dto);
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close(rs);
		close(pstmt);
		return result;
	}

	// select one
	public List<BoardListDto> selectAllList(Connection conn) {
		List<BoardListDto> result = null;
		String sql = "SELECT BOARD_ID, SUBJECT, BOARD_WRITER, WRITE_TIME, HIT FROM BOARD";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// ResultSet 처리
			if (rs.next()) {
				result = new ArrayList<BoardListDto>();
				do {
					BoardListDto dto = new BoardListDto(rs.getInt("BOARD_ID"), rs.getString("SUBJECT"),
							rs.getString("BOARD_WRITER"), rs.getString("WRITE_TIME"), rs.getInt("HIT"));
					result.add(dto);
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close(rs);
		close(pstmt);
		return result;
	}

	// select one
	public BoardReadDto selectOne(Connection conn, Integer boardId) {
		BoardReadDto result = null;
		String sql = "SELECT BOARD_ID, SUBJECT, CONTENT, WRITE_TIME, LOG_IP, BOARD_WRITER, HIT FROM Board WHERE BOARD_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			// ? 처리
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			// ResultSet 처리
			if (rs.next()) {
				result = new BoardReadDto(
						rs.getInt("BOARD_ID"), rs.getString("SUBJECT"), rs.getString("CONTENT"),
						rs.getString("WRITE_TIME"), rs.getString("LOG_IP"), rs.getString("BOARD_WRITER"),
						rs.getInt("HIT")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		close(rs);
		close(pstmt);
		return result;
	}

	// select
	public int getSequenceNum(Connection conn) {
		int result = 0;
		String sql = "SELECT SEQ_BOARD_ID.nextval FROM DUAL";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 처리
			rs = pstmt.executeQuery();
			// ResetSet처리
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(rs);
		close(pstmt);
		return result;
	}

	// insert
	public int insert(Connection conn, BoardInsertDto dto) {
		System.out.println("boardDao Insert() param : " + dto);
		int result = 0;
//		INSERT INTO BOARD (BOARD_ID, SUBJECT, CONTENT, WRITE_TIME, LOG_IP, BOARD_WRITER, HIT) VALUES (SEQ_BOARD_ID.nextval, ?, ?, DEFAULT, DEFAULT, ?, DEFAULT)
//		String sql = "INSERT INTO BOARD (BOARD_ID, SUBJECT, CONTENT, WRITE_TIME, LOG_IP, BOARD_WRITER, HIT)"
//				+ " VALUES (?, ?, ?, default, ?, ?, default)";
		String sql = "INSERT ALL ";
			   sql+="	INTO BOARD (BOARD_ID,SUBJECT,CONTENT,WRITE_TIME,LOG_IP,BOARD_WRITER, HIT) ";
			   sql+="		VALUES (SEQ_BOARD_ID.nextval, ?, ?, default, ?, ?, default) ";
		if(dto.getFileList()!= null && dto.getFileList().size()>0) {
			for(FileWriteDto filedto :dto.getFileList()) {
				sql+="	INTO BOARD_FILE (BOARD_ID, BOARD_FILE_ID, SAVED_FILE_PATH_NAME, ORIGINAL_FILENAME) ";
				sql+="		VALUES (SEQ_BOARD_ID.nextval, ?, ?, ?) ";
			}
		}
				sql+="	SELECT * FROM DUAL ";
				System.out.println("sql: "+ sql);
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			// ? 처리
//			pstmt.setInt(1, boare=);
			int i = 1;
			pstmt.setString(i++, dto.getSubject());
			pstmt.setString(i++, dto.getContent());
			pstmt.setString(i++, null); // TODO pstmt.setString(3, dto.getLogIp());
			pstmt.setString(i++, dto.getBoardWriter());
			if(dto.getFileList() != null && dto.getFileList().size() > 0) {
				int fileId = 1;
				for(FileWriteDto filedto : dto.getFileList()) {
					pstmt.setInt(i++, fileId++);
					pstmt.setString(i++, filedto.getFilePath());
					pstmt.setString(i++, filedto.getOriginalFileName());
				}
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		close(pstmt);
		System.out.println("boardDao Insert() return : " + result);
		return result;
	}

	// update
	public int update(Connection conn, BoardDto dto) {
		int result = 0;
		String sql = "UPDATE BOARD SET SUBJECT = ?, CONTENT = ?, LOG_IP = ? WHERE BOARD_ID = ? "; // TODO
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		close(pstmt);
		return result;
	}

	// delete
	public int delete(Connection conn, Integer boardId) {
		int result = 0;
		String sql = "DELETE FROM BOARD WHERE BOARD_ID=?";
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			// ? 처리
			pstmt.setInt(1, boardId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		close(pstmt);
		return result;
	}

	// -------board reply 댓글
	// select list - board reply : board_id
	public List<BoardReplyListDto> selectBoardReplyList(Connection conn, Integer boardId) {
		List<BoardReplyListDto> result = null;
		String sql = "select BOARD_REPLY_ID,  BOARD_REPLY_WRITER,BOARD_REPLY_CONTENT, " + " BOARD_REPLY_WRITE_TIME, "
				+ " BOARD_REPLY_LEVEL,BOARD_REPLY_REF, BOARD_REPLY_STEP "
				+ " from board_reply where board_id=? order by board_reply_ref desc,  board_reply_step";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 처리
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			// ResetSet처리
			if (rs.next()) {
				result = new ArrayList<BoardReplyListDto>();
				do {
					BoardReplyListDto dto = new BoardReplyListDto(rs.getInt("BOARD_REPLY_ID"),
							rs.getString("BOARD_REPLY_WRITER"), rs.getString("BOARD_REPLY_CONTENT"),
							rs.getString("BOARD_REPLY_WRITE_TIME"), rs.getInt("BOARD_REPLY_LEVEL"),
							rs.getInt("BOARD_REPLY_REF"), rs.getInt("BOARD_REPLY_STEP"));
					result.add(dto);
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(rs);
		close(pstmt);
		return result;
	}

	// insert - reply 댓글 원본글
	public int insertReply(Connection conn, BoardReplyWriteDto dto) {
		int result = 0;
		String sql = " INSERT INTO BOARD_REPLY VALUES"
				+ "        ( (SELECT NVL(MAX(BOARD_REPLY_ID),0)+1 FROM BOARD_REPLY) , ?, "
				+ "            ?, ? , default , null, "
				+ "            DEFAULT , (SELECT NVL(MAX(BOARD_REPLY_ID),0)+1 FROM BOARD_REPLY), DEFAULT )";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 처리
			pstmt.setInt(1, dto.getBoardId());
			pstmt.setString(2, dto.getBoardReplyWriter());
			pstmt.setString(3, dto.getBoardReplyContent());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		close(pstmt);
		return result;
	}

	// insert - reply 댓글 대댓글
	public int insertRReply(Connection conn, BoardReplyWriteDto dto) {
		int result = 0; // 1 정상, 0비정상
		String sql = " INSERT INTO BOARD_REPLY VALUES ( (SELECT NVL(MAX(BOARD_REPLY_ID),0)+1 FROM BOARD_REPLY), ?,"
				+ "            ?, ? , default , null, "
				+ "            (SELECT BOARD_REPLY_LEVEL+1 FROM BOARD_REPLY WHERE BOARD_REPLY_ID = ? )  , "
				+ "            (SELECT BOARD_REPLY_REF     FROM BOARD_REPLY WHERE BOARD_REPLY_ID = ? )  , "
				+ "            (SELECT BOARD_REPLY_STEP+1  FROM BOARD_REPLY WHERE BOARD_REPLY_ID = ? )  )";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 처리
			pstmt.setInt(1, dto.getBoardId());
			pstmt.setString(2, dto.getBoardReplyWriter());
			pstmt.setString(3, dto.getBoardReplyContent());
			pstmt.setInt(4, dto.getBoardReplyId());
			pstmt.setInt(5, dto.getBoardReplyId());
			pstmt.setInt(6, dto.getBoardReplyId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(pstmt);
		return result;
	}

	// update - reply Step
	public int updateReplyStep(Connection conn, Integer boardReplyId) {
		int result = -1; // 0~n 정상이므로 비정상인경우-1
		String sql = "UPDATE BOARD_REPLY SET BOARD_REPLY_STEP = BOARD_REPLY_STEP+1  WHERE "
				+ "            BOARD_REPLY_REF = ( SELECT BOARD_REPLY_REF FROM BOARD_REPLY WHERE BOARD_REPLY_ID = ?)"
				+ "            AND "
				+ "            BOARD_REPLY_STEP > ( SELECT BOARD_REPLY_STEP FROM BOARD_REPLY WHERE BOARD_REPLY_ID = ? )";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 처리
			pstmt.setInt(1, boardReplyId);
			pstmt.setInt(2, boardReplyId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(pstmt);
		return result;
	}
}
