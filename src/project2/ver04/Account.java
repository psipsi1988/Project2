package project2.ver04;

import java.io.Serializable;
import java.util.Scanner;



public abstract class Account implements Serializable{
	//멤버변수
	private String accNum;//계좌번호
	private String name;//이름
	private int balance;//잔액
	
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
		return;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result+((accNum==null)? 0 : accNum.hashCode()); 
		return result;
	}
		
	@Override
	public boolean equals(Object obj) {
		Account acc = (Account) obj;
		if(acc.accNum.equals(this.accNum)) {
			return true;
		}
		else {
			return false;
		}
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
