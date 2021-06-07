package com.sts.module.STSBomPlugin;

import java.sql.ResultSet;
import java.util.Properties;

public class MHRContract extends X_HR_Contract {

	public MHRContract(Properties ctx, int HR_Contract_ID, String trxName) {
		super(ctx, HR_Contract_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	public MHRContract(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
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
		//	log.warning("after save");
			return false;
		}
		return super.afterSave(newRecord,success);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
