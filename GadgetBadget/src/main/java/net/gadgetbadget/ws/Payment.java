package net.gadgetbadget.ws;

public class Payment {
	
	private int payment_ID;
	private String payment_type;
	private float amount;
	private String date;
	
	public Payment() {
				
	}

	public Payment(int payment_ID, String payment_type, float amount, String date) {
		super();
		this.payment_ID = payment_ID;
		this.payment_type = payment_type;
		this.amount = amount;
		this.date = date;
	}

	public int getPayment_ID() {
		return payment_ID;
	}

	public void setPayment_ID(int payment_ID) {
		this.payment_ID = payment_ID;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + payment_ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (payment_ID != other.payment_ID)
			return false;
		return true;
	}
}
