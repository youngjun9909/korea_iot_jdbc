package korea_iot_jdbc.main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import korea_iot_jdbc.dao.UserDAO;
import korea_iot_jdbc.entity.User;

public class UserManager {
	public void run () throws SQLException{
		Scanner sc = new Scanner(System.in);
		
		UserDAO userDao = new UserDAO();
		
		while(true) {
			System.out.println("1. 사용자 단건 조회");
			System.out.println("2. 사용자 전체 조회");
			System.out.println("3. 사용자 추가");
			System.out.println("4. 사용자 정보 수정");
			System.out.println("5. 사용자 삭제");
			System.out.println("6. 프로그램 종료");
			System.out.println("메뉴 선택: ");
			
			String selectedMenu = sc.nextLine();
			
			switch(selectedMenu) {
			case "1":
				viewUserById(sc, userDao);
				break;
			case "2":
				viewAllUsers(userDao);
				break;
			case"3":
				addUser(sc, userDao);
				break;
			case "4":
				updateUser(sc, userDao);
				break;
			case "5":
				deleteUser(sc, userDao);
				break;
			case "6":
				System.out.println("프로그램을 종료합니다.");
				sc.close();
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	// 사용자 삭제 (DELETE)
	private void deleteUser(Scanner sc, UserDAO userDao) throws SQLException {
		System.out.println("삭제할 사용자 ID 입력: ");
		int id = Integer.parseInt(sc.nextLine());
		userDao.deleteUser(id);
		System.out.println("사용자 정보가 삭제되었습니다.");
		
	}
	
	// 사용자 수정 (UPDATE)
	private void updateUser(Scanner sc, UserDAO userDao) throws SQLException {
		System.out.println("수정할 사용자 ID 입력: ");
		int id = Integer.parseInt(sc.nextLine());
		
		User user = userDao.getUserById(id);
		
		if(user == null) {
			System.out.println("해당 ID의 사용자를 찾을 수 없습니다.");
			return;
		}
		System.out.print("사용자의 새 이름 (변경하지 않으려면 Enter)");
		String name = sc.nextLine();
		System.out.print("사용자의 새 이메일 (변경하지 않으려면 Enter)");
		String email = sc.nextLine();
		
		if(!name.isEmpty()) {
			// 비워져있지 않으면 - 변경할 경우
			user.setName(name);
		}
		if(!email.isEmpty()) {
			// 비워져있지 않으면 - 변경할 경우
			user.setEmail(email);
		}
		
		userDao.updateUser(user);
		System.out.println("사용자 정보가 업데이트되었습니다.");
	}
	
	// 사용자 추가 (CREATE)
	private void addUser(Scanner sc, UserDAO userDao) throws SQLException {
		System.out.println("새로운 새용자 이름 입력: ");
		String name = sc.nextLine();
		
		System.out.println("새로운 새용자 이메일 입력: ");
		String email = sc.nextLine();
		
		// DB의 auto_increment에 값을 전달해야 할 때는 -1의 값을 전달 
		//>> 아직 DB에 저장되지 않음을 의미하는 값
		User newUser = new User(-1, name, email);
		userDao.addUser(newUser);
		System.out.println("사용자가 성공적으로 추가되었습니다");
		
	}
	
	// 사용자 전체 조회 (READ)
	private void viewAllUsers(UserDAO userDao) throws SQLException {
		List<User> users = userDao.findAll();
		
		if(users.isEmpty()) {
			System.out.println("등록된 사용자가 없습니다.");
		}else {
			users.forEach(user -> System.out.println(user.toString()));
		}
	}
	
	// 사용자 단건 조회 (READ)
	private void viewUserById(Scanner sc, UserDAO userDao) throws SQLException {
		System.out.print("조회할 사용자 ID를 입력: ");
		int id = Integer.parseInt(sc.nextLine());
		
		User user = userDao.getUserById(id);
		
		if(user != null) {
			System.out.println("User Id: " + user.getId());
			System.out.println("User Name: " + user.getName());
			System.out.println("User Email: " + user.getEmail());
		}else {
			System.out.println("사용자를 찾을 수 없습니다.");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
