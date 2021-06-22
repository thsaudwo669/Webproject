package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ProductDto;
import DTO.ReplyDto;

public class ProductDao {

	private Connection conn;
	private ResultSet rs; 
	
	public ProductDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root" , "12341234" );
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
		private static ProductDao instance = new ProductDao();
	
	
	public static ProductDao getinstance() {
		return instance;
	}
	
	
	public int productadd(  ProductDto dto    ) {
		
		String SQL = "insert into product( product_name , product_price , product_manufacturer , product_category , product_stock , product_conditions , product_file , product_activation , product_salesrate)" + "values(?,?,?,?,?,?,?,?,?)";
				
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1, dto.getProduct_name());
			pstmt.setInt(2, dto.getProduct_price());
			pstmt.setString(3, dto.getProduct_manufacturer());
			pstmt.setString(4, dto.getProduct_category());
			pstmt.setInt(5, dto.getProduct_stock());
			pstmt.setInt(6, dto.getProduct_conditions());
			pstmt.setString(7, dto.getProduct_file());
			pstmt.setInt(8, dto.getProduct_activation());
			pstmt.setInt(9, dto.getProduct_salesrate());
			
			pstmt.executeUpdate();
			return 1; 
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
		
	}
	
 
	public ArrayList<ProductDto> productalllist(){
		
		ArrayList<ProductDto> list = new ArrayList<ProductDto>();
		
		try {
			String SQL = "select * from product";
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				ProductDto dto = new ProductDto(
						rs.getString(2),
						rs.getInt(3), 
						rs.getString(4),
						rs.getString(5),
						rs.getInt(6), 
						rs.getInt(7),
						rs.getString(8),
						rs.getInt(9),
						rs.getInt(10));
				
				dto.setProduct_id( rs.getInt(1) ); // ��ǰ ��ȣ
				list.add(dto);
			}
			
			return list;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
