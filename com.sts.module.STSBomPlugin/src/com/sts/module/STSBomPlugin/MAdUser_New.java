package com.sts.module.STSBomPlugin;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.MMailText;
import org.compiere.model.MOrder;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;

public class MAdUser_New extends MUser{

	// TODO Auto-generated constructor stub
	private static final long serialVersionUID = 1L;
	private String		m_processMsg = null;
	private BigDecimal myAmount = Env.ZERO;	

	/**
	 * 
	 */
	// public static final String COLUMNNAME_Crm_Quote_ID = "Crm_Quote_ID";

//	}
	 public MAdUser_New(Properties ctx, int C_Oder_ID, String trxName) {
			super(ctx, C_Oder_ID, trxName);
		}
	 public MAdUser_New(Properties ctx, ResultSet rs, String trxName) {
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
		log.warning("mail reset");
		String abc= getEMail();
		if (abc!=null)
		{
			int ad_user_id = getAD_User_ID();
			if (ad_user_id!=0)
			{	
				Boolean sendOK = sendIndividualMail(getAD_User_ID());
				//log.warning("id "+ getC_Order_ID());
				//Boolean sendOKm = sendIndividualMailM(getC_Order_ID());
				log.warning("mail reset123");
			}

		}
		return super.afterSave(newRecord,success);
	}
	private Boolean sendIndividualMail(int ad_User_ID) {
		// TODO Auto-generated method stub
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
//			if (m_client.getAD_Client_ID() == 0)
//				return(false);
		 log.warning("mail reset1234");
			if (m_client.getSMTPHost() == null || m_client.getSMTPHost().length() == 0)
				return(false);
			m_R_MailText_ID = MSysConfig.getIntValue("PVOIl_Reset_Password ", 0, getAD_Client_ID());
			m_MailText = new MMailText (getCtx(), m_R_MailText_ID, get_TrxName());
			
			int c_ad_user_id= getAD_User_ID();
			log.warning("c_ad_user_id"+c_ad_user_id);
			//MUser User=new MUser(getCtx(), c_ad_user_id, get_TrxName());
			
			String abc= getEMail();
			log.warning("abc"+abc);
			log.warning("chieu dai"+abc.length());
			if(abc==null && abc.length()>1) {
				log.warning("abc1"+abc);
				return false;
				
			}
			StringBuilder message = new StringBuilder(m_MailText.getMailText(true));
			//log.warning("message"+message);
			String myStr = message.toString();
			//log.warning("myStr "+myStr);
			String myStrsubject = m_MailText.getMailHeader().toString();
			String v_name = null;
			String v_pass = null;
			log.warning("5678"+c_ad_user_id);
			//if(c_ad_user_id!=0) {
				//String sql="select Name, Password from AD_User where AD_User_ID=?";
			//	getName();
			//	System.out.print(sql);
			//	PreparedStatement pstmt = null;
			//	ResultSet rs = null;
				/*
				try
				{
					//pstmt = DB.prepareStatement(sql, get_TrxName());
					pstmt.setInt(1, c_ad_user_id);
					rs = pstmt.executeQuery();
					while (rs.next())
					{
						v_name =getName();
						v_pass = getPassword();
						
//						
					}
				}
				catch (SQLException ex)
				{
					//log.log(Level.SEVERE, sql, ex);
				}
				//	Clean Up
				finally
				{
					DB.close(rs, pstmt);
					rs = null;
					pstmt = null;
				}
			}*/
			//if (v_name!=null)
				myStr = myStr.replace("@Name@", getName());
			myStrsubject = myStrsubject.replace("@Name@",getName());
			log.warning("khách hàng"+getName());

			//if(v_pass!=null)
				myStr=myStr.replace("@Password@", getPassword());
			   myStrsubject = myStrsubject.replace("@Password@", getPassword());
			   EMail email = m_client.createEMail(getEMail(), myStrsubject, myStr);
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
	}
}
