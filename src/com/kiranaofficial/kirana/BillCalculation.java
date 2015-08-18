package com.kiranaofficial.kirana;

public class BillCalculation {
	public double getProductSubTotal(double rate, int quantity, double taxBracket) {
		double productCost = (rate * quantity);
		double productTaxOnCost = (productCost * (taxBracket/100));
		
		double productSubTotal = productCost + productTaxOnCost;
		return productSubTotal;
	}
	
	public double getBillGrandTotal(double totalCostProducts, double vat, double serviceCharge, double serviceTax) {
		double totalVat = (totalCostProducts * (vat/100));
		double totalServiceCharge = (totalCostProducts * (serviceCharge/100));
		double totalServiceTax = (totalCostProducts * (serviceTax/100));
		
		double grandTotal = totalCostProducts + totalVat + totalServiceCharge + totalServiceTax;
		return grandTotal;
	}
}
