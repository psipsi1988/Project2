package project2.ver03;

import java.util.InputMismatchException;
import java.util.Scanner;
import project2.ver03.Account;

public class AccountManager implements MenuChoice{

	static int numAcc = 0;
	Scanner scan = new Scanner(System.in);
	Account[] accArr;

	public AccountManager(int num) {
		accArr = new Account[num];

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
					System.exit(0);

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
			accArr[numAcc++] = normalAccount;
			System.out.printf("계좌번호: %s\n"
					+"고객이름: %s\n"
					+ "잔고: %d%n"
					+ "기본이자: %d", 
					normalAccount.getAccNum(), normalAccount.getName(),
					normalAccount.getBalance(), normalAccount.getInterest()
					);
			System.out.println("%");
			System.out.println("계좌 개설이 완료되었습니다.");
		}
		else {
			//신용신뢰고객
			System.out.printf("기본이자%% (정수형태로 입력):");
			int ir = scan.nextInt();
			scan.nextLine();
			System.out.println("신용등급(A, B, C등급):");
			String cr = scan.next();
			scan.nextLine();

			HighCreditAccount highCreditAccount = new HighCreditAccount(aN, na, bal, ir, cr);
			accArr[numAcc++] = highCreditAccount;
			System.out.printf("계좌번호: %s\n"
					+"고객이름: %s\n"
					+ "잔고: %d%n"
					+ "기본이자:%d"
					+ "%%  "
					+ "\n신용등급:%s", 
					highCreditAccount.getAccNum(), highCreditAccount.getName(),
					highCreditAccount.getBalance(), highCreditAccount.getInterest(), 
					highCreditAccount.getCreditRate() 
					);
			System.out.println("");
			System.out.println("계좌 개설이 완료되었습니다.");
		}
	}   


	// 입    금
	public void depositMoney(String accN, int money) {

		for (int i=0; i<numAcc; i++) {
			if(accArr[i].getAccNum().equals(accN)) {
				if(accArr[i] instanceof NormalAccount) {
					accArr[i].setBalance(
							(int)(accArr[i].getBalance()
									+(accArr[i].getBalance()*(((NormalAccount)accArr[i]).getInterest())/100)
									+money)
							);
					System.out.println("계좌번호:" + accArr[i].getAccNum());
					System.out.println("입금액: " + money);
					System.out.println("잔고:"+ accArr[i].getBalance());
					System.out.println("입금이 완료되었습니다. ");
				}
				else if(accArr[i] instanceof HighCreditAccount) {
					accArr[i].setBalance(
							(int)(accArr[i].getBalance()
									+(accArr[i].getBalance()*(((HighCreditAccount)accArr[i]).getInterest())/100)
									+(accArr[i].getBalance()*((HighCreditAccount)accArr[i]).getCrInterest())
									+money)
							);
					System.out.println("계좌번호:" + accArr[i].getAccNum());
					System.out.println("입금액: " + money);
					System.out.println("잔고:"+ accArr[i].getBalance());
					System.out.println("입금이 완료되었습니다. ");
				}
				else {
					System.out.println("계좌가 없습니다. ");
				}

			}
		}
	}   

	// 출    금
	public void withdrawMoney(String accN, int money) {

		for(int i = 0; i<numAcc; i++) {
			if(accArr[i].getAccNum().equals(accN)) {
				if(accArr[i].getBalance() < money) {
					System.out.println("잔고가 부족합니다. 금액 전체를 출금할까요?");
					System.out.println("YES : 금액 전체 출금 처리");
					System.out.println("NO : 출금 취소");
					String cho = scan.next();

					if(cho.toUpperCase().equals("Yes")) {
						System.out.printf("총 금액 %d원이 출금되었습니다. ", accArr[i].getBalance());
						accArr[i].setBalance(accArr[i].getBalance()-accArr[i].getBalance());
					}
					else {
						System.out.println("출금 요청이 취소되었습니다. ");
					}
				}
				else {
					if(money%1000==0) {

						accArr[i].setBalance(accArr[i].getBalance()-money);
						System.out.println("계좌번호: " + accArr[i].getAccNum());
						System.out.println("출금액: " + money);
						System.out.println("출금이 완료되었습니다.");
					}
					else {
						int more = (((money/1000)+1)*1000)-money;
						System.out.println("1000원 단위로 출금할 수 있습니다. ");
						//System.out.println("금액을 수정해주세요. ");
					}
				}
			}	
			else {
				System.out.println("계좌가 없습니다.");

			}	
		}
	}



	public void showInfo() {

		for (int i=0; i<numAcc; i++) {

			accArr[i].showAccInfo();
		}

	}

}
