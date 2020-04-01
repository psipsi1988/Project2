package project2.ver04;

public class NormalAccount extends Account{

	private int interest;
	public NormalAccount(String aN, String na, int bal, int ir) {
		super(aN, na, bal);
		this.interest = ir;
	}
	
	
	@Override
	public void showAccInfo() {
		super.showAccInfo();
		System.out.println("기본 이자>" + interest+"%");
		System.out.println("----------------");
	}


	public int getInterest() {
		return interest;
	}


	public void setInterest(int interest) {
		this.interest = interest;
	}

}
