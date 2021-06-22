package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ReplyDto;

public class ReplyDao {

	private Connection conn;
	private ResultSet rs; 
	
	public ReplyDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root" , "12341234" );
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	private static ReplyDao instance = new ReplyDao();
	
	
	public static ReplyDao getinstance() {
		return instance;
	}
	
	
	public int re_write( ReplyDto replyDto ) {
		String SQL = "insert into reply ( reply_boardid , reply_userid , reply_contents ) " + "values(?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt( 1 , replyDto.getReply_boardid()  );
			pstmt.setString(2, replyDto.getReply_userid() );
			pstmt.setString(3, replyDto.getReply_contents() );
			pstmt.executeUpdate();
			return 1 ;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}

	
	public ArrayList<ReplyDto> getreplylist( int id ){
		
		
		String SQL = "select * from reply where reply_boardid = ? ORDER BY reply_num DESC ";
		
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setInt( 1 , id);
			
			rs = pstmt.executeQuery(); // select
			
			
			ArrayList<ReplyDto> list = new ArrayList<>();
			
			while( rs.next() ) {
				
				ReplyDto replyDto = new ReplyDto();
				replyDto.setReply_userid( rs.getString(3));
				replyDto.setReply_contents( rs.getString(4));
				
				list.add(replyDto);
			}
			return list;
		}
		catch (Exception e) {
			// TODO: handle exception
		} 
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}
