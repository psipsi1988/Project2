package project2.ver03;

import java.util.HashSet;
import java.util.Scanner;



public abstract class Account {
	//멤버변수
	private String accNum;//계좌번호
	private String name;//이름
	private int balance;//잔액
	
	public Account() {}
	public Account(String aN, String na, int bal) {
	
		accNum = aN;
		name = na;
		balance = bal;
	
	}
	public void showAccInfo() {
		System.out.println("***계좌정보출력***");
		System.out.println("----------------");
		System.out.println("계좌 번호 :"+ accNum);
		System.out.println("고객 이름 :"+ name);
		System.out.println("통장 잔고 :"+ balance+"원");
		System.out.println("----------------");
		System.out.println("전체 계좌정보 출력이 완료되었습니다."); 
		return;
	}
	
	public String getAccNum() {
		return accNum;
	}
	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	


}  // 전체계좌정보출력
