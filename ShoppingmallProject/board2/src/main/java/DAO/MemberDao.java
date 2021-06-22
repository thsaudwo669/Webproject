package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.BoardDto;
import DTO.MemberDto;

public class MemberDao {
	
	private Connection conn;
	private ResultSet rs; 
	
	public MemberDao() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root" , "12341234" );
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	private static MemberDao instance = new MemberDao();
	
	
	public static MemberDao getinstance() {
		return instance;
	}
	
	
	public int signup( MemberDto dto ) {
		
		String SQL = "insert into member values(?,?,?,?,?,?)";
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString( 1 , dto.getMember_id() ); 
			pstmt.setString(2, dto.getMember_password());
			pstmt.setString(3, dto.getMember_name() );
			pstmt.setString(4, dto.getMember_phone() );
			pstmt.setString(5, dto.getMember_email() );	
			pstmt.setString(6, dto.getMember_address() ) ;

			pstmt.executeUpdate();
			return 1 ; // ȸ������ ���� 
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return -1; 
	}
	
	
	public int login( String id , String password ) {
		
		String SQL  ="select * from member where member_id =? and member_password = ?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString( 1 , id ); 
			pstmt.setString(2, password );
			
			rs = pstmt.executeQuery();
			
			if( rs.next()) {
				return 1 ;	
			}	
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	

}
