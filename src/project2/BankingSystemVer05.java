package project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import project2.ver01.Account;
import project2.ver01.MenuChoice;

public class BankingSystemVer05 implements MenuChoice{
	static Scanner scan = new Scanner(System.in);
	static int numAcc=0;

	public static Connection con;
	public static Statement stmt;
	public static ResultSet rs;
	public static PreparedStatement psmt;

	public static void showMenu() {

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin://@localhost:1521:orcl", "kosmo", "1234");
			System.out.println("오라클 DB 연결성공");
		}
		catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("알 수 없는 예외발생");
		}

		System.out.println("");
		System.out.println("-----Menu------");
		System.out.println("1. 계좌 	개설");
		System.out.println("2. 입	금");
		System.out.println("3. 출	금");
		System.out.println("4. 계좌정보출력");
		System.out.println("5. 프로그램 종료");
		System.out.println("선택:");
	}

	public static void makeAccount(Account[] accArr, String aN, String na, int bal) {
		Account acc = new Account(aN, na, bal);
		accArr[numAcc++] = acc;
		System.out.println("계좌 개설이 완료되었습니다. ");
	}


	public static void depositMoney(String accN, int money) {

		try {

			String sql = "update banking_tb set balance = balance + ?"
					+ "where accNum = ?";
			psmt = con.prepareStatement(sql);

			psmt.setInt(1, money);
			psmt.setString(2, accN);
			//psmt.executeUpdate();


			if(psmt.executeUpdate()!=0) {
				System.out.println("계좌번호: " + accN);
				System.out.println("입금액: " + money);
				System.out.println("입금이 완료되었습니다.");
			}
			else {
				System.out.println("계좌번호가 잘못되었습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	public static void withdrawMoney(String accN, int money) {

		try {

			String sql = "update banking_tb set balance = balance - ?"
					+ "where accNum = ?";
			psmt = con.prepareStatement(sql);

			psmt.setInt(1, money);
			psmt.setString(2, accN);
			//psmt.executeUpdate();



			if(psmt.executeUpdate()!=0) {
				System.out.println("계좌번호: "+ accN);
				System.out.println("출금액: "+ money);
				System.out.println("출금이 완료되었습니다. ");
			}
			else {
				System.out.println("계좌번호가 잘못되었습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}





	/*
	public static void showAccInfo(Account[] accArr) {
		for (int i=0; i<numAcc; i++) {
			System.out.println("-------------");
			System.out.println("계좌번호: "+accArr[i].getAccNum());
			System.out.println("고객이름: "+accArr[i].getName());
			System.out.println("잔고: "+accArr[i].getBalance()+"원");
			System.out.println("-------------");

		}
	}
	 */

	public static void showAccInfo() {

		try {

			String sql = 
					"select * from banking_tb";
			stmt = con.createStatement();
			rs= stmt.executeQuery(sql);
			while(rs.next()) {
				String accNum = rs.getString(1);
				String name = rs.getString(2);
				int balance = rs.getInt(3);

				System.out.printf("%s, %s, %s%n", accNum, name, balance);

				System.out.println("**귀하가 요청하는 정보를 찾았습니다.**");

			}




		}
		catch(SQLException e) {
			e.printStackTrace();
		}




	}










	public static void main(String[] args) {


		Account[] account = new Account[50];

		while(true) {
			showMenu();

			int choice = scan.nextInt();

			switch(choice) {
			case MAKE:
				try {

					String query = "INSERT INTO banking_tb VALUES (?,?,? )";

					psmt = con.prepareStatement(query);

					String accN1, na1;
					int bal1;
					System.out.print("계좌번호:"); accN1 = scan.next();
					System.out.print("이름:");  na1 = scan.next();
					System.out.print("잔고:");  bal1 = scan.nextInt();
					scan.nextLine();
					makeAccount(account, accN1, na1, bal1);


					psmt.setString(1,  accN1);
					psmt.setString(2,  na1);
					psmt.setInt(3,  bal1);

					psmt.executeUpdate();


				}

				catch(Exception e) {
					e.printStackTrace();
				}

				break;

			case DEPOSIT:
				System.out.println("****입   금****");
				System.out.println("계좌번호와 입금할 금액을 입력하세요");
				System.out.print("계좌번호:"); String accN2 = scan.next();
				System.out.print("입금액:"); int money2 = scan.nextInt();
				scan.nextLine();

				depositMoney(accN2, money2);
				break;

			case WITHDRAW:
				System.out.println("****출  금****");
				System.out.println("계좌번호와 출금할 금액을 입력하세요");
				System.out.print("계좌번호:"); String accN3 = scan.next();
				System.out.print("출금액:"); int money3 = scan.nextInt();
				scan.nextLine();
				withdrawMoney(accN3, money3);
				break;

			case INQUIRE:
				System.out.println("***계좌정보출력***");
				showAccInfo();
				//showAccInfo(account);
				System.out.println("전체계좌정보 출력이 완료되었습니다. ");
				break;

			case EXIT:
				System.out.println("프로그램을 종료합니다. ");
				System.exit(0);

			default:
				break;

			}
		}

	}
}
