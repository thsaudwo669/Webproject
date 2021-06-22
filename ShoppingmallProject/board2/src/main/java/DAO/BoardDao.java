package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.BoardDto;

public class BoardDao {
	
	private Connection conn;
	private ResultSet rs; 
	
	public BoardDao() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root" , "12341234");
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static BoardDao instance = new BoardDao();
	

	public static BoardDao getinstance() {
		return instance;
	}
	
	
	public String getDate() {
		String SQL = "SELECT NOW()"; 
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery(); 
			

			if( rs.next() ) {
				return rs.getString(1); 
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
			return ""; // db ����
	}
	
	
	public int getnext() {
		String SQL = "SELECT MAX(board_id) from Board"; 
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				return rs.getInt(1) + 1 ;		
			}
			return 1; 
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return -1; 
	}
	
	
	public int write( BoardDto dto ) {
		
		String SQL = "insert into Board values(?,?,?,?,?,?,?,?)";
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt( 1 , getnext() ); 
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContents() );
			pstmt.setString(4, dto.getUserID() );
			pstmt.setString(5, getDate() );	 
			pstmt.setInt(6, dto.getAvailable() ) ;
			pstmt.setString(7, dto.getFile() );
			pstmt.setInt(8, 0 );

			pstmt.executeUpdate();
			return 1 ;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}
	
	
	public ArrayList<BoardDto> getboardlist( int pagenumber ) {
		
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		try {
			String SQL = "select * from board where board_id< ? ORDER BY board_id DESC LIMIT 10";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  getnext() - (pagenumber-1)*10 );
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) { 
				BoardDto dto = new BoardDto();
				dto.setID( rs.getInt(1));
				dto.setTitle( rs.getString(2));
				dto.setContents( rs.getString(3));
				dto.setUserID( rs.getString(4));
				dto.setDate( rs.getString(5));
				dto.setAvailable( rs.getInt(6));
				dto.setFile( rs.getString(7));
				
				list.add(dto);
			}
			return list;
		}
		catch (Exception e) {
			// TODO: handle exception
		}	
		return null;
	}
	
	public boolean nextpage( int pagenumber) {
		String SQL = "SELECT * FROM board where board_id < ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt( 1 ,  getnext() - (pagenumber-1) * 10   );
								
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				return true;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	

	
	public BoardDto getboard( int id) {
		
		try {
			String SQL = "select * from board where board_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			BoardDto dto = new BoardDto();
			
			if( rs.next() ) { 
				
				int count = rs.getInt(8) + 1 ; 
		
				dto.setID( rs.getInt(1));
				dto.setTitle( rs.getString(2));
				dto.setContents( rs.getString(3));
				dto.setUserID( rs.getString(4));
				dto.setDate( rs.getString(5));
				dto.setAvailable( rs.getInt(6));
				dto.setFile( rs.getString(7));
				dto.setCount( count );
				
					
				SQL = "update board set board_count = ? where board_id = ?";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, count);
				pstmt.setInt(2, id);
				
				pstmt.executeUpdate();
				
				return dto;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}	
		return null;
	}
	
	
	
//	
//	public int deleteboard( int id) { 
//		
//		try {
//			String SQL = "update board set board_available = 0 where board_id = ? ";
//			PreparedStatement pstmt = conn.prepareStatement(SQL);
//			pstmt.setInt(1, id);
//			
//			pstmt.executeUpdate();
//		
//			return 1;
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//		}	
//		return -1;
//	}
	
	
	
	public int deleteboard( int id) { 
		
		try {
			String SQL = "delete from board where board_id = ? ";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);	
			pstmt.executeUpdate();
			
			
			SQL = "UPDATE board set board_id = board_id-1 where board_id > ?";
					
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			return 1;
		}
		catch (Exception e) {
			// TODO: handle exception
		}	
		return -1;
	}
	
	
	public int updateboard( String title , String contents , String file ,  int id) { 
		
		try {
			String SQL = "update board set board_title = ? , board_contents=? , board_file=? where board_id = ? ";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, title );
			pstmt.setString(2, contents);
			pstmt.setString(3, file);
			pstmt.setInt(4, id);
			
			pstmt.executeUpdate();
		
			return 1;
		}
		catch (Exception e) {
			// TODO: handle exception
		}	
		return -1;
	}
	
	
	public ArrayList<BoardDto> getboardsearch( String key , String keyword) {
		
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();

		try {
			
//			String SQL = "select * from board where key = keyword";
			String SQL = "select * from board where "+key+" like '%"+keyword+"%'";
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
	
			rs = pstmt.executeQuery();
			
			while( rs.next() ) { 
			
				BoardDto dto = new BoardDto();
				
				dto.setID( rs.getInt(1));
				dto.setTitle( rs.getString(2));
				dto.setContents( rs.getString(3));
				dto.setUserID( rs.getString(4));
				dto.setDate( rs.getString(5));
				dto.setAvailable( rs.getInt(6));
				dto.setFile( rs.getString(7));
				
				list.add(dto);
			}
			return list;
		}
		catch (Exception e) {
			// TODO: handle exception
		}	
		return null;
	}


	
	
	




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
