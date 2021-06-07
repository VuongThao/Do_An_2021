package com.sts.module.STSBomPlugin;



import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.adempiere.base.Core;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.BPartnerNoBillToAddressException;
import org.adempiere.exceptions.BPartnerNoShipToAddressException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.ITaxProvider;
import org.adempiere.process.SalesOrderRateInquiryProcess;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MMailText;
import org.compiere.model.MOrder;
import org.compiere.model.MOrg;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;


public class MOrder_New extends MOrder{
	private static final long serialVersionUID = 1L;
	private String		m_processMsg = null;
	private BigDecimal myAmount = Env.ZERO;	
	
    public static final String COLUMNNAME_Crm_Quote_ID = "Crm_Quote_ID";
//	public void setCrm_Quote_ID (int Crm_Quote_ID)
//	{
//		if (Crm_Quote_ID < 1) 
//			set_ValueNoCheck (COLUMNNAME_Crm_Quote_ID, null);
//		else 
//			set_ValueNoCheck (COLUMNNAME_Crm_Quote_ID, Integer.valueOf(Crm_Quote_ID));
//	}
//	
	/** Get Crm_Quote.
		@return Crm_Quote	  */
//	public int getCrm_Quote_ID () 
//	{
//		Integer ii = (Integer)get_Value(COLUMNNAME_Crm_Quote_ID);
//		if (ii == null)
//			 return 0;
//		return ii.intValue();
//	}
	public MOrder_New(Properties ctx, int C_Payment_ID, String trxName) {
		super(ctx, C_Payment_ID, trxName);
	}
	public MOrder_New(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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
		//myAmount = getGrandTotal();
//		/*tampt add de cap nhat so du cong no khach hang va tai xe*/
		if (is_ValueChanged("DocStatus") && getDocStatus().equals("CO"))
		{
//			//DienNN added on Jul 05, 2019
//			int myQuoteID = getCrm_Quote_ID();
//			int aduser_ID = getAD_User_ID();
			int C_Order_ID=getC_Order_ID();
			String sql = "select grandtotal from c_order where c_order_id = " + C_Order_ID;
     		System.out.println("sql: "+sql);

//			//update BP
			BigDecimal grandTotal = getGrandTotal();
			int cpartner_ID = getC_BPartner_ID();
//			
			MBPartner myBP = new MBPartner(getCtx(),cpartner_ID,get_TrxName());
			BigDecimal creditUsed = Env.ZERO;
			BigDecimal creditAvailable = Env.ZERO;
			creditUsed = myBP.getSO_CreditUsed().add(grandTotal);
			log.warning(" exxx11 hien ra man hinh"+creditUsed);
			creditAvailable = myBP.getSO_CreditLimit().subtract(creditUsed);
			log.warning("creditAvailable"+creditAvailable);
			myBP.setSO_CreditUsed(creditUsed);
		//	myBP.setSO(creditAvailable);
			myBP.saveEx(get_TrxName());
//			
		//update contract
     		MOrder_New mo = new MOrder_New(getCtx(), getC_Order_ID(), get_TrxName());
     		MHRContract mhr = new MHRContract(getCtx(), mo.get_ValueAsInt("HR_Contract_ID"), get_TrxName());
			creditUsed = mhr.getso_credituser().add(grandTotal);
			creditAvailable = mhr.getlimit_credit().subtract(creditUsed);
			mhr.setso_credituser(creditUsed);
			log.warning("setso_credituser"+creditUsed);
			mhr.setavailable_credit(creditAvailable);
			log.warning(" set _creditAvailable"+creditAvailable);
			mhr.saveEx(get_TrxName());
			
//			//cap nhat han muc cong no tai xe
//			MUser myDriver = new MUser(getCtx(),getAD_User_ID(),get_TrxName());
//			int driveUsed = 0;
//			int driverAvailable = 0;
			//credit log
//			MCreditLog myLog = new MCreditLog(getCtx(),0,get_TrxName());
//			myLog.setAD_Table_ID(259);//C_Order
//			myLog.setRecord_ID(getC_Order_ID());
//			myLog.setAvailable_Old(myDriver.getAvailable_Credit());
//			myLog.setC_BPartner_ID(myBP.getC_BPartner_ID());
//			myLog.setAD_User_ID(getAD_User_ID());
//			myLog.setHR_Contract_ID(myContract.getHR_Contract_ID());
//			
//			driveUsed = myDriver.getCredit_Used() + grandTotal.intValue();
//			driverAvailable = myDriver.getLimit_Credit().intValue() - driveUsed;
//			myLog.setAvailable_New(new BigDecimal(driverAvailable));
//			
//			myDriver.setCredit_Used(driveUsed);
//			myDriver.setAvailable_Credit(new BigDecimal(driverAvailable));
//			
//			myLog.saveEx(get_TrxName());
//			myDriver.saveEx(get_TrxName());
//			
//			boolean inforok = createADInfo();
			
		//}
//		if (is_ValueChanged("DocStatus") && getDocStatus().equals("VO"))
//		{
//			BigDecimal grandTotal = getGrandTotal();
//			int cpartner_ID = getC_BPartner_ID();
//			int aduser_ID = getAD_User_ID();
//			int corderid = getC_Order_ID();
//			//String isdropship = get_ValueOfColumn("IsDropShip") ;
//			Boolean isdropship  = isDropShip();
//			
//			if (isdropship)//don hang void tu mobile
//				{
//				//update BP
//				grandTotal = getGrandTotal();
//				cpartner_ID = getC_BPartner_ID();
//				aduser_ID = getAD_User_ID();
//				MBPartner myBP = new MBPartner(getCtx(),cpartner_ID,get_TrxName());
//				BigDecimal creditUsed = Env.ZERO;
//				BigDecimal creditAvailable = Env.ZERO;
//				creditUsed = myBP.getSO_CreditUsed().subtract(grandTotal);
//				creditAvailable = myBP.getSO_CreditLimit().subtract(creditUsed);
//				myBP.setSO_CreditUsed(creditUsed);
//				myBP.setAvailable_Credit(creditAvailable);
//				myBP.saveEx(get_TrxName());
//				
//				//update contract
//				MHRContract myContract = new MHRContract(getCtx(),getHR_Contract_ID(),get_TrxName());
//				creditUsed = myContract.getSO_CreditUsed().subtract(grandTotal);
//				creditAvailable = myContract.getLimit_Credit().subtract(creditUsed);
//				myContract.setSO_CreditUsed(creditUsed);
//				myContract.setAvailable_Credit(creditAvailable);
//				myContract.saveEx(get_TrxName());
//				
//				//cap nhat han muc cong no tai xe
//				
//				
//				
//				
//				
//		}
		//end tampt
		
		/*tampt added*/
//		if (getLink_Order_ID()!=0 && getDocStatus().equals("DR") && getGrandTotal().compareTo(Env.ZERO)>0)	
//		{
//			int ref_id = getLink_Order_ID();
//			BigDecimal grandTotal = getGrandTotal();
//			
//			if (ref_id>0 && grandTotal.compareTo(Env.ZERO)> 0)
//			{
//				int v_nextID = 0;
//				v_nextID = DB.getSQLValue(get_TrxName(), "select pvoil_order_process_sq.nextval from dual");
//				
//				String sql = "insert into pvoil_order_process(PVOIL_ORDER_PROCESS_ID,"
//						+ " AD_CLIENT_ID,AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,UPDATED,UPDATEDBY,"              
//						+ " C_BPARTNER_ID,PROCESSED,PROCESSED_OK,DESCRIPTION,AD_USER_ID,HR_CONTRACT_ID)"
//						+ " select "+v_nextID+",AD_CLIENT_ID,AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,UPDATED,UPDATEDBY,"              
//						+ " C_BPARTNER_ID,'N',0,'Huy don hang',AD_USER_ID,HR_CONTRACT_ID from C_Order "
//						+ " where C_Order_ID=" + getC_Order_ID();
//					int no = DB.executeUpdateEx(sql, get_TrxName());
//					if (log.isLoggable(Level.FINE)) log.fine("Description -> #" + no);
//				sql = "insert into pvoil_order_process_dtl(PVOIL_ORDER_PROCESS_DTL_ID,"
//						+ " PVOIL_ORDER_PROCESS_ID,AD_CLIENT_ID,AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,"
//						+ " UPDATED,UPDATEDBY,C_ORDER_ID,DOCSTATUS,GRANDTOTAL)"
//						+ " select PVOIL_ORDER_PROCESS_DTL_SQ.NEXTVAL,"+v_nextID+",AD_CLIENT_ID,AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,"
//						+ " UPDATED,UPDATEDBY,C_ORDER_ID,DOCSTATUS,GRANDTOTAL"
//						+" FROM c_order where c_order_id in ("+getC_Order_ID()+","+ref_id+")";
//				no = DB.executeUpdateEx(sql, get_TrxName());
//				if (log.isLoggable(Level.FINE)) log.fine("Description -> #" + no);
//			}
//		}
//		//end tampt
//		
		log.warning("trang thai"+getDocStatus());
		if (is_ValueChanged("DocStatus") && (getDocStatus().equals("CO")||getDocStatus().equals("VO")))
		{
			int myorder = getC_Order_ID();
			if (myorder!=0)
			{	
				Boolean sendOK = sendIndividualMail(getC_Order_ID());
				//log.warning("id "+ getC_Order_ID());
				//Boolean sendOKm = sendIndividualMailM(getC_Order_ID());
			}

		}
		}
		return super.afterSave(newRecord,success);
	}
		
	
	
	//send email to contract manage
	private Boolean sendIndividualMail (int p_order_id) 
			{
				/** What to send			*/
				 int				m_R_MailText_ID = -1;
				/**	Mail Text				*/
				 MMailText		m_MailText = null;
				/**	From (sender)			*/
				 int				m_AD_User_ID = -1;
				/** Client Info				*/
				 MClient			m_client = null;
				/**	From					*/
				 MUser			m_from = null;
				/** Recipient List to prevent duplicate mails	*/
				 ArrayList<Integer>	m_list = new ArrayList<Integer>();
				 int 			m_counter = 0;
				 int 			m_errors = 0;
				 m_client = MClient.get (getCtx());
					if (m_client.getAD_Client_ID() == 0)
						return(false);
					if (m_client.getSMTPHost() == null || m_client.getSMTPHost().length() == 0)
						return(false);
				MBPartner mybp = new MBPartner(getCtx(),getC_BPartner_ID(),get_TrxName());
				/*
				if (!mybp.isSendEMail())
					return(true);
				*/
				m_R_MailText_ID = MSysConfig.getIntValue("PVoil_Mail_OrderConrform", 0, getAD_Client_ID());
				m_MailText = new MMailText (getCtx(), m_R_MailText_ID, get_TrxName());
				
				
				int c_order_id = getC_Order_ID();	
				
			//	MHRContract mycontract = new MHRContract(getCtx(),contractID,get_TrxName());
				MOrder morder = new MOrder(getCtx(), c_order_id, get_TrxName());
				
				if (mybp.getEMail()==null || !morder.isSendEMail())
				{
					return false;
				}
				
				StringBuilder message = new StringBuilder(m_MailText.getMailText(true));
				String myStr = message.toString();
				String myStrsubject = m_MailText.getMailHeader().toString();
				String v_partnername = null;
				String v_documentno = null;
				String v_grandtotal = null;
//				String v_product = null;
//				String v_quantity = null;
//				String v_amount = null;
//     			String v_taxamt = null;
//				String v_orgaddress = null;
//				String v_parentorg = null;
//				String v_orgphone = null;
//				String v_orgfax = null;
//				String v_parentorgemail = null;
//				String v_parentorgaddress = null;
				
				/*03-aug-2018*/
//				String v_car_license = null;
//				String v_priceentered = null;
				/*end 03-aug-2018*/
				if (p_order_id!=0)
				{
					//String sql = "select * from pvoil_order_v where c_order_id = ?";
					String sql = "select C_BPartner_ID,DocumentNo,GrandTotal from C_Order where c_order_id = ?";
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					try
					{
						pstmt = DB.prepareStatement(sql, get_TrxName());
						pstmt.setInt(1, p_order_id);
						rs = pstmt.executeQuery();
						while (rs.next())
						{
							 v_partnername = DB.getSQLValueString(get_TrxName(), "select name from c_bpartner where c_bpartner_id = ?", rs.getInt(1));
							 v_documentno = rs.getString(2);
							 v_grandtotal = rs.getString(3);
//							 v_product = rs.getString(5);
//							 v_quantity = rs.getString(6);
//							 //v_amount = rs.getString(7);
//							 v_amount = DB.getSQLValueString(get_TrxName(), "select replace(TO_CHAR(?, '999,999,999'),',','.') from dual ",myAmount);
//							 v_taxamt = rs.getString(8);
//							 v_orgaddress = rs.getString(9);
//							 v_parentorg = rs.getString(10);
//							 v_orgphone = rs.getString(11);
//							 v_orgfax = rs.getString(12);
//							 v_parentorgemail = rs.getString(13);
//							 v_parentorgaddress = rs.getString(14);
//							 /*03-aug-2018*/
//							 v_car_license = rs.getString(15);
//							 BigDecimal myPrice = Env.ZERO;
//							 myPrice = rs.getBigDecimal(16);
//							 v_priceentered = DB.getSQLValueString(get_TrxName(), "select replace(TO_CHAR(?, '999,999,999'),',','.') from dual ",myPrice);
//							 /*end 03-aug-2018*/
						}
					}
					catch (SQLException ex)
					{
						log.log(Level.SEVERE, sql, ex);
					}
					//	Clean Up
					finally
					{
						DB.close(rs, pstmt);
						rs = null;
						pstmt = null;
					}
				}
				if (v_partnername!=null)
					myStr = myStr.replace("@C_BPartner@", v_partnername);
				myStrsubject = myStrsubject.replace("@C_BPartner@", v_partnername);
				log.warning("khách hàng"+v_partnername);
				
				
				if(v_documentno!=null)
					myStr=myStr.replace("@DocumentNo@", v_documentno);
				   myStrsubject = myStrsubject.replace("@DocumentNo@", v_documentno);
				
//				if (v_drivername!=null)
//				{
//					myStr = myStr.replace("@drivername@", v_drivername);
//					myStrsubject = myStrsubject.replace("@drivername@", v_drivername);
//				}
				
//				if (getDocStatus().equals("VO"))
//					myStrsubject = myStrsubject.replace("@trxtype@", Msg.getMsg(getCtx(), "pvoil_so_void"));	
//				else
//					myStrsubject = myStrsubject.replace("@trxtype@", Msg.getMsg(getCtx(), "pvoil_so_trx"));
//				
				if (v_grandtotal!=null)
				{
					myStr = myStr.replace("@GrandTotal@", v_grandtotal);
					myStrsubject = myStrsubject.replace("@GrandTotal@", v_grandtotal);
				}
				
				
				
			//	log.warning("tien"+v_grandtotal);
//				if (v_product!=null)
//					myStr = myStr.replace("@m_product_id@", v_product);
//				
//				log.warning("sp"+v_grandtotal);
//				if (v_quantity!=null)
//					myStr = myStr.replace("@qtyordered@", v_quantity);
//				if (v_amount!=null)
//				{
//					myStr = myStr.replace("@linenetamt@", v_amount);
//					myStrsubject = myStrsubject.replace("@linenetamt@", v_amount);
//				}
//				if (v_taxamt!=null)
//				myStr = myStr.replace("@taxamt@", v_taxamt);
//				if (v_orgaddress!=null)
//					myStr = myStr.replace("@orgaddress@", v_orgaddress);
//				if (v_parentorg!=null)
//					myStr = myStr.replace("@parentorg@", v_parentorg);
//				if (v_orgphone!=null)
//					myStr = myStr.replace("@parentorgphone@", v_orgphone);
//				if (v_orgfax!=null)
//					myStr = myStr.replace("@parentorgfax@", v_orgfax);
//				if (v_parentorgemail!=null)
//					myStr = myStr.replace("@parentorgemail@", v_parentorgemail);
//				if (v_parentorgaddress!=null)
//					myStr = myStr.replace("@parentorgaddress@", v_parentorgaddress);
//				
//				/*03-aug-2018*/	
//				if (v_car_license!=null)
//					myStr = myStr.replace("@car_license@", v_car_license);
//				if (v_priceentered!=null)
//					myStr = myStr.replace("@priceentered@", v_priceentered);
				/*end 03-aug-2018*/
				
				//EMail email = m_client.createEMail(null, to, myStrsubject, myStr);
				EMail email = m_client.createEMail(mybp.getEMail(), myStrsubject, myStr);
				if (m_MailText.isHtml())
					email.setMessageHTML(myStrsubject, myStr);
				else
				{
					email.setSubject (myStrsubject);
					email.setMessageText (myStr);
				}
				if (!email.isValid() && !email.isValid(true))
				{
					log.warning("NOT VALID - " + email);
					return Boolean.FALSE;
				}
				boolean OK = EMail.SENT_OK.equals(email.send());
				log.warning("gui thanh cong"+OK);
				return new Boolean(OK);
			}	//	sendIndividualMail
	
	//send to manager of drive

}

