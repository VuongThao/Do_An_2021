package com.sts.module.STSBomPlugin;

import java.math.BigDecimal;
import java.sql.ResultSet;

import java.util.Properties;

import org.compiere.model.MBPartner;
import org.compiere.model.MInvoice;
import org.compiere.util.Env;

public class MInvoice_New extends MInvoice{


	public MInvoice_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	public MInvoice_New(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String		m_processMsg = null;
	private BigDecimal myAmount = Env.ZERO;	

	protected boolean beforeSave(boolean newRecord) {
		log.warning("before save");
		if(newRecord)
		{	
			//setDescription("New By " + (new MUser(Env.getCtx(),Env.getAD_User_ID(Env.getCtx()),null)).getName());
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
			int C_BPartner_ID= getC_BPartner_ID();
			int AD_Org_ID= getAD_Org_ID();
			String sql = "select grandtotal from c_invoice"
					+ " where c_bpartner_id="+ C_BPartner_ID+" and ad_org_id="+AD_Org_ID+" and docstatus='CO'";
			
			
			BigDecimal grandTotal = getGrandTotal();
			int cpartner_ID = getC_BPartner_ID();
//		khachhang	
			MBPartner myBP = new MBPartner(getCtx(),cpartner_ID,get_TrxName());
			MOrder_New mo = new MOrder_New(getCtx(), getC_Order_ID(), get_TrxName());
			MHRContract mhr = new MHRContract(getCtx(), mo.get_ValueAsInt("HR_Contract_ID"), get_TrxName());
			BigDecimal creditUsed = Env.ZERO;
			BigDecimal creditAvailable = Env.ZERO;
			//creditUsed = mhr.getSO_CreditUsed().add(grandTotal);
			creditAvailable = mhr.getSO_CreditLimit().subtract(creditUsed);
			myBP.setSO_CreditUsed(creditUsed);
			//myBP.setAvai(creditAvailable);
			myBP.saveEx(get_TrxName());
			
			// hop dong
//			MHRContract myContract = new MHRContract(getCtx(),getH,get_TrxName());
//			creditUsed = myContract.getso_credituser().add(getGrandTotal());
//			creditAvailable = myContract.getlimit_credit().subtract(creditUsed);
//			myContract.setso_credituser(creditUsed);
//		//	myContract.setAvailable_Credit(creditAvailable);
//			myContract.saveEx(get_TrxName());
		}
		return super.afterSave(newRecord,success);
     }
} 