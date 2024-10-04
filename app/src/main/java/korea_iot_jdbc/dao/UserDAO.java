package korea_iot_jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import korea_iot_jdbc.db.DBConnection;
import korea_iot_jdbc.entity.User;

// DAO (Data Access Object)
// : DB와 같은 영구 저장소에 접근하는 객체
// : 데이터 처리 로직과 DB 작업을 분리하는 역할

// DAO 패턴
// : DB와의 CRUD 작업을 처리
	
// UserDAO 클래스 정의
public class UserDAO {
	// 아이디를 기준으로 사용자 정보를 불러오는 메서드
	public User getUserById(int id) throws SQLException {
		// DBConnection을 통해 DB 연결을 가져옴
		Connection connection = DBConnection.getConnection();
		
		// 실행항 SQL문 작성 id를 조건으로 사용자를 조회
		// : 동적 파라미터 
		String sql = "SELECT * FROM user WHERE id = ?";
		
		// SQL 쿼리를 실행하기 위해 PreparedStatement 객체를 생성
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// 실행 객체에 파라미터 값을 설정
		statement.setInt(1, id);
		
		// SQL 쿼리를 실행하고 결과를 ResultSet으로 변수에 저장
		ResultSet resultSet = statement.executeQuery();
		
		User user = null; // User 객체를 null로 초기화
		if(resultSet.next()) { // 결과 집합에 다음 행이 있으면 true를 반환
			user = new User(resultSet.getInt("id")
					, resultSet.getString("name")
					, resultSet.getString("email"));
		}
		
		resultSet.close();


		
		return user;
	}
	
	// 모든 사용자를 조회하는 메서드
	public List<User> findAll() throws SQLException{
		// DB연결을 가져옴
		Connection connection = DBConnection.getConnection();
		
		// 모든 사용자 정보를 조회하는 SQL 쿼리 작성
		String sql = "SELECT * FROM user";
		
		// PreparedStatement 객체를 생성
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// 쿼리를 실행하고 결과를 ResultSet으로 전달받음
		ResultSet resultSet = statement.executeQuery();
		
		// 유저 객체를 저장할 리스트생성
		List<User> users = new ArrayList<User>();
		
		//  결과 집합에서 다음 행이 이쓴ㄴ 동안 반복
		while (resultSet.next()) {
			User user = new User(
					resultSet.getInt("id"),
					resultSet.getString("name"),
					resultSet.getString("email")
					);
			
			// 리스트에 User 객체를 추가
			users.add(user);
					
		}
		resultSet.close();
		statement.close();
		connection.close();
		
		return users;
	}
	
	public void addUser (User user) throws SQLException {
		Connection connection = DBConnection.getConnection();
		String sql = "INSERT INTO user (name, email) VALUES (?, ?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, user.getName());
		statement.setString(2, user.getEmail());
		
		statement.executeUpdate();
		
		statement.close();
		connection.close();
	}
	
	public void updateUser (User user) throws SQLException {
		Connection connection = DBConnection.getConnection();
		
		// 업데이트할 이름과 이메일이 있는지 확인
		boolean updateName = user.getName() != null & !user.getName().isEmpty();
		boolean updateEmail = user.getEmail() != null & !user.getEmail().isEmpty();
		
		// sql쿼리
		// cf) StringBuilder
		// >>> 자바에서 가변 문자열을 만드는 클래스 
		StringBuilder sql = new StringBuilder("UPDATE user SET ");
		
		if (updateName) {
			sql.append("name = ?, ");
		}
		
		if (updateEmail) {
			sql.append("email = ?, ");
		}
		
		// 마지막 콤마를 제거
		sql.deleteCharAt(sql.length() - 2);
		
		// where 절 추가
		sql.append("WHERE id = ?");
		
		PreparedStatement statement = connection.prepareStatement(sql.toString());
		
		int parameterIndex = 1;
		if(updateName) {
			statement.setString(parameterIndex++, user.getName());
		}
		if(updateEmail) {
			statement.setString(parameterIndex++, user.getEmail());
		}
		
		statement.setInt(parameterIndex, user.getId());
		statement.executeUpdate();
		
		statement.close();
		connection.close();
	}
	
	
	public void deleteUser (int id) throws SQLException {
		
		try (Connection connection = DBConnection.getConnection()){
			String sql = "DELETE FROM user WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			
			statement.close();
			connection.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
