package com.sts.module.STSBomPlugin;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MBPartner;
import org.compiere.model.MPayment;
import org.compiere.util.Env;

public class Mpayment_New extends MPayment {
	
	
	
	public Mpayment_New(Properties ctx, int C_Payment_ID, String trxName) {
		super(ctx, C_Payment_ID, trxName);
	}

	public Mpayment_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	protected boolean beforeSave(boolean newRecord) {
		log.warning("before save");
		if(newRecord)
		{	
			
		}
		
		return super.beforeSave(newRecord);
	}
	protected boolean afterSave(boolean newRecord, boolean success) {
		log.warning("after save");
		if (!success)
		{
		
			return false;
		}
		
		if (is_ValueChanged("DocStatus") && getDocStatus().equals("CO"))
		{  
			
			//Mpayment_New pay = new Mpayment_New(getCtx(), getC_Payment_ID(), get_TrxName());
			int C_Payment_ID= getC_Payment_ID();
			int HR_Contract_ID= getHR_Contract_ID();
//			String sql = "select payamt from c_payment"
//					+ " WHERE c_payment_id="+C_Payment_ID+" and hr_contract_id="+HR_Contract_ID;
//     		System.out.println("sql: "+sql);
     		
     		BigDecimal payamt = getPayAmt();
     		
     	    
     	
			int cpartner_ID = getC_BPartner_ID();
//			update Bpartner 
			MBPartner myBP = new MBPartner(getCtx(),cpartner_ID,get_TrxName());
			
			BigDecimal creditUsed = Env.ZERO;
			BigDecimal creditAvailable = Env.ZERO;
			log.warning("payment tinh toan:payment"+payamt);
			creditUsed = myBP.getSO_CreditUsed().subtract(payamt);
			//myBP.setSO_CreditUsed(Math.abs(creditUsed));
			log.warning("hien ra man hinh abc"+creditUsed);
			creditAvailable = myBP.getSO_CreditLimit().subtract(creditUsed);
			log.warning("creditAvailable"+creditAvailable);
			log.warning ("vuong thi thao");
			myBP.setSO_CreditUsed(creditUsed);
		//	myBP.setSO(creditAvailable);
			myBP.saveEx(get_TrxName());
			
			//update hr_contract.
			
     		MHRContract mhr = new MHRContract(getCtx(),HR_Contract_ID , get_TrxName());
			creditUsed = mhr.getso_credituser().subtract(payamt);
			log.warning("ket qua cuoi cung payment: creditUserd"+creditUsed);
			creditAvailable = mhr.getlimit_credit().subtract(creditUsed);
			mhr.setso_credituser(creditUsed);
			log.warning("setso_credituser"+creditUsed);
			mhr.setavailable_credit(creditAvailable);
			log.warning(" set _creditAvailable"+creditAvailable);
			mhr.saveEx(get_TrxName());
		}
		return super.afterSave(newRecord,success);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
