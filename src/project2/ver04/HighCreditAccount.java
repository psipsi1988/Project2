package project2.ver04;

public class HighCreditAccount extends Account implements CustomSpecialRate{

	private int interest;
	private String creditRate;
	private double crInterest;
	
	public HighCreditAccount(String aN, String na, int bal, int ir, String cr) {
		super(aN, na, bal);
		this.interest = ir;
		this.creditRate = cr;
	}
	
	public double getCrInterest() {
		switch(this.creditRate) {
		case "A":
			crInterest = A;
			break;
		case "B":
			crInterest = B;
			break;
		case "C":
			crInterest = C;
			break;
		default : 
			break;
		}
		return crInterest;
	}
	
	
	@Override
	public void showAccInfo() {
		super.showAccInfo();
		System.out.println("기본 이자>" + interest+"%");
		System.out.println("신용 등급>" + creditRate);
		System.out.println("----------------");
	}
	
	
	
	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}

	public String getCreditRate() {
		return creditRate;
	}

	public void setCreditRate(String creditRate) {
		this.creditRate = creditRate;
	}
	
	
}
