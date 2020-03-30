package project2.ver02;

import java.util.HashSet;
import java.util.Scanner;



public class Account {
	//멤버변수
	String accountNumber;//계좌번호
	String name;//이름
	int balance;//잔액
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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





	
	
	Scanner scan = new Scanner(System.in);
	HashSet<showAccInfo> set = new HashSet<showAccInfo>();
	
	int numOfAccount=0;
	private showAccInfo[] aAccount;
	
	public Account(int num) {
		aAccount = new showAccInfo[num];
		numOfAccount = 0;
	
	}
	
	public void showMenu() { //메뉴 출력

		
		System.out.println("-----Menu------");
		System.out.println("1.계좌개설");
		System.out.println("2.입금");
		System.out.println("3.출금");
		System.out.println("4.전체 계좌 정보 출력");
		System.out.println("5.프로그램 종료");
		System.out.println("선택 : ");

		while(true) {
			//메뉴출력을 위한 메소드호출


			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			scan.nextLine();

			switch(choice) {
			case MenuChoice.MAKE: //계좌 개절
				makeAccount();
				System.out.println("입력된 정보 출력");
				break;
			case MenuChoice.DEPOSIT: //입금
				depositMoney();
				break;	
			case MenuChoice.WITHDRAW: //출금
				withdrawMoney();
				break;	
			case MenuChoice.INQUIRE: //잔액조회
				 dataAllShow();
				break;
			case MenuChoice.EXIT: //프로그램 종료
				System.out.println("프로그램을 종료합니다.");
				return;
			}
		}
	}
	
	
	//정보 출력용 메소드
	public void dataAllShow() {
		System.out.println("***계좌정보출력***");
		System.out.println("----------------");
		System.out.println("계좌 번호 :"+ accountNumber);
		System.out.println("고객 이름 :"+ name);
		System.out.println("잔고:"+ balance);
		System.out.println("----------------");
		System.out.println("전체 계좌정보 출력이 완료되었습니다."); 
		return;
	}
		


	public void makeAccount() { // 계좌개설을 위한 함수
		String accountNumber;//계좌번호
		String name;//이름
		int balance;//잔액

		System.out.println("***신규계좌개설***");
		System.out.print("계좌번호:"); accountNumber = scan.nextLine();
		System.out.print("고객이름:"); name = scan.nextLine();
		System.out.print("잔고:"); balance = scan.nextInt();
		scan.nextLine();
		overlap(new showAccInfo (accountNumber, name, balance));
		System.out.println("계좌 계설이 완료되었습니다.");
		numOfAccount++;
		showMenu();
	}   



	void depositMoney() {

		
		
	}    // 입    금





	void withdrawMoney() {

		
		
		
	} // 출    금





	public void overlap(showAccInfo sInfo) {
		if(sInfo instanceof showAccInfo) {
			sInfo = (showAccInfo) sInfo;
		}
		if(!set.add(sInfo)) {
			System.out.println("같은 이름이 있습니다.");
			System.out.println("1. 덮어쓰기 / 2. 다시 입력");
			int choice = scan.nextInt();
			scan.nextLine();
			if(choice ==1) {
				set.remove(sInfo);
				set.add(sInfo);
				System.out.println("데이터 입력이 완료되었습니다. ");
				return;
			}
			else if (choice == 2) {
				return;
			}
		}
	}
}
class showAccInfo{
	
	//멤버변수
	String accountNumber;//계좌번호
	String name;//이름
	int balance;//잔액
	
	//생성자
	public showAccInfo(String accountNumber, String name, int balance) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.balance = balance;
	}
	




}  // 전체계좌정보출력
