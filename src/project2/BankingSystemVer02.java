package project2;

import project2.ver02.AccountManager;
public class BankingSystemVer02 {

	public static void main(String[] args) {
		AccountManager AM= new AccountManager(50);
		AM.start();
	}
}
