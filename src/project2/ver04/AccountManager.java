package project2.ver04;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountManager implements MenuChoice{

	static int numAcc = 0;
	Scanner scan = new Scanner(System.in);
	HashSet<Account> accArr;

	public AccountManager(int num) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/project2/ver4/account.obj"));
			accArr = (HashSet<Account>) in.readObject();
			in.close();
		}
		catch(Exception e) {
			accArr = new HashSet<Account>();
		}

	}

	public void showMenu() { 

		while(true) {
			try {

				System.out.println("-----Menu------");
				System.out.println("1.계좌개설");
				System.out.println("2.입금");
				System.out.println("3.출금");
				System.out.println("4.계좌 정보 출력");
				System.out.println("5.프로그램 종료");
				System.out.println("선택 : ");

				int choice = scan.nextInt();
				scan.nextLine();
				if(!(choice>=1 && choice<=5)) {
					MenuSelectException ex = new MenuSelectException();
					throw ex;
				}

				switch(choice) {
				case MAKE: //계좌 개절
					makeAccount();
					break;

				case DEPOSIT: //입금
					System.out.println("***입  금***");
					System.out.println("계좌번호와 입금할 금액을 입력하세요. ");
					System.out.print("계좌번호:");
					String accN2 = scan.next();
					System.out.print("입금액:");
					int money2 = scan.nextInt();
					scan.nextLine();
					if(money2<0) {
						System.out.println("음수의 금액을 입금할 수 없습니다.");
						break;
					}
					if(!(money2%500==0)) {
						int more = (int)(((money2/500)+1)*500)-money2;
						System.out.println("500원 단위로 입금 가능합니다. ");
						//System.out.printf("%d원 더 입금하세요\n", more);
						break;
					}
					depositMoney(accN2, money2);
					break;

				case WITHDRAW: //출금
					System.out.println("***출  금***");
					System.out.println("계좌번호와 입금할 금액을 입력하세요. ");
					System.out.print("계좌번호:");
					String accN3 = scan.next();
					System.out.print("출금액:");
					int money3 = scan.nextInt();
					scan.nextLine();
					if(money3<0) {
						System.out.println("음수의 금액을 입금할 수 없습니다.");
					}
					else {
						withdrawMoney(accN3, money3);
					}
					break;

				case INQUIRE: //잔액조회
					showInfo();
					break;

				case EXIT: //프로그램 종료
					System.out.println("프로그램을 종료합니다.");
					try {
						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/project2/ver04/account.obj"));
						
						out.writeObject(accArr);
						out.close();
						System.exit(0);
					}
					catch(IOException e) {
						e.printStackTrace();
						System.out.println("저장되지않았습니다. 종료하시겠습니까?예 :(1) 아니오 :(0)");
						int num = scan.nextInt();
						scan.nextLine();
						if(num==1)
							System.exit(0);
					}

				default:
					break;
				}
			}
			catch (MenuSelectException e) {
				e.printStackTrace();
			}
			catch (InputMismatchException e) {
				e.printStackTrace();
				scan.nextLine();
			}
			catch(Exception e) {
				System.out.println("알 수 없는 오류가 발생했습니다. ");
				e.printStackTrace();
			}


		}

	}

	private void accAdd(Account acc) {

		if(!accArr.add(acc)) {
			System.out.println("중복된 값이 있습니다.");
			System.out.println("덮어쓰기(1) : 다시입력(0)");
			int num = scan.nextInt();
			scan.nextLine();
			if(num==1) {
				accArr.remove(acc);
				accArr.add(acc);	
				acc.showAccInfo();
				System.out.println("계좌개설이 완료되었습니다.");
			}
		}
		else {
			System.out.println("계좌개설이 완료되었습니다.");
			acc.showAccInfo();

		}
	}
	public void makeAccount() { // 계좌개설을 위한 함수
		System.out.println("***신규계좌개설***");
		System.out.println("-----계좌선택------");
		System.out.println("1. 보통 계좌");
		System.out.println("2. 신용신뢰계좌");
		System.out.println("선택 : ");
		int num = scan.nextInt();
		scan.nextLine();

		System.out.print("계좌번호:");
		String aN = scan.next();
		System.out.print("고객이름:");
		String na = scan.next();
		System.out.print("잔고:");
		int bal = scan.nextInt();

		if(num==1) {
			//일반
			System.out.printf("기본이자%% (정수형태로 입력):");
			int ir = scan.nextInt();
			scan.nextLine();

			NormalAccount normalAccount = new NormalAccount(aN, na, bal, ir);
			accAdd(normalAccount);
		}
		else {
			//신용신뢰고객
			System.out.printf("기본이자%% (정수형태로 입력):");
			int ir = scan.nextInt();
			scan.nextLine();
			System.out.println("신용등급(A, B, C등급):");
			String cr = scan.next();
			if(cr.equals("A") || cr.equals("B") || cr.equals("C")) {
				HighCreditAccount highCreditAccount = new HighCreditAccount(aN, na, bal, ir, cr);
				accAdd(highCreditAccount);
			}
			else {
				System.out.println("신용등급은 A, B, C 중에서 하나를 입력해야 합니다. ");
			}
		}
	}   


	// 입    금
	public void depositMoney(String accN, int money) {

		boolean ex = false;

		for (Account acc : accArr) {
			if(acc.getAccNum().equals(accN)) {
				if(acc instanceof NormalAccount) {
					acc.setBalance(
							(int)(acc.getBalance()
									+(acc.getBalance()*(((NormalAccount)acc).getInterest())/100)
									+money)
							);
					System.out.println("계좌번호:" + acc.getAccNum());
					System.out.println("입금액: " + money);
					System.out.println("잔고:"+ acc.getBalance());
					System.out.println("입금이 완료되었습니다. ");
				}
				else if(acc instanceof HighCreditAccount) {
					acc.setBalance(
							(int)(acc.getBalance()
									+(acc.getBalance()*(((HighCreditAccount)acc).getInterest())/100)
									+(acc.getBalance()*((HighCreditAccount)acc).getCrInterest())
									+money)
							);
					System.out.println("계좌번호:" + acc.getAccNum());
					System.out.println("입금액: " + money);
					System.out.println("잔고:"+ acc.getBalance());
					System.out.println("입금이 완료되었습니다. ");
				}
				ex = true;
			}

		}		
		if(ex==false)
			System.out.println("계좌가 없습니다.");
		System.out.println();
	}



	// 출    금
	public void withdrawMoney(String accN, int money) {

		boolean ex = false;
		for(Account  acc : accArr) {
			if(acc.getAccNum().equals(accN)) {
				if(acc.getBalance() < money) {
					System.out.println("잔고가 부족합니다. 금액 전체를 출금할까요?");
					System.out.println("YES : 금액 전체 출금 처리");
					System.out.println("NO : 출금 취소");
					String cho = scan.next();

					if(cho.toUpperCase().equals("Yes")) {
						System.out.printf("총 금액 %d원이 출금되었습니다. ", acc.getBalance());
						acc.setBalance(acc.getBalance()-acc.getBalance());
					}
					else {
						System.out.println("출금 요청이 취소되었습니다. ");
					}
				}
				else {
					if(money%1000==0) {

						acc.setBalance(acc.getBalance()-money);
						System.out.println("계좌번호: " + acc.getAccNum());
						System.out.println("출금액: " + money);
						System.out.println("출금이 완료되었습니다.");
					}
					else {
						int more = (((money/1000)+1)*1000)-money;
						System.out.println("1000원 단위로 출금할 수 있습니다. ");
						//System.out.println("금액을 수정해주세요. ");
					}
				}
				ex = true;
			}	

		}	
		if(ex==false)
			System.out.println("계좌가 없습니다.");
		System.out.println();
	}




	public void showInfo() {

		for (Account  acc : accArr) {

			acc.showAccInfo();
		}

	}

}
