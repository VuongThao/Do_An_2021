package com.sts.modelvalidator;

import org.compiere.model.MClient;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;

import com.sts.module.STSBomPlugin.MPPProductBomsts;


public class stsPPProductBomValidator implements ModelValidator{
	private int m_AD_Client_ID = -1;
	private int m_AD_User_ID = -1;
	private int m_AD_Role_ID = -1;
	private int m_AD_Org_ID = -1;



	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		// TODO Auto-generated method stub
		if(client!=null)
			m_AD_Client_ID = client.getAD_Client_ID();
		engine.addModelChange(MPPProductBomsts.Table_Name, this);	

	}

	@Override
	public int getAD_Client_ID() {
		// TODO Auto-generated method stub
		return m_AD_Client_ID;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		// TODO Auto-generated method stub
		m_AD_Org_ID = AD_Org_ID;
		m_AD_Role_ID = AD_Role_ID;
		m_AD_User_ID = AD_User_ID;		
		return null;

	}

	@Override
	public String modelChange(PO po, int type) throws Exception {
		// TODO Auto-generated method stub
		if(type == TYPE_BEFORE_NEW)
		
			po.set_ValueOfColumn(MPPProductBomsts.COLUMNNAME_Description, "New By " + (new MUser(Env.getCtx(),Env.getAD_User_ID(Env.getCtx()),null)).getName());	
		if (type == TYPE_BEFORE_CHANGE)
			po.set_ValueOfColumn(MPPProductBomsts.COLUMNNAME_Description, "Update By " + (new MUser(Env.getCtx(),Env.getAD_User_ID(Env.getCtx()),null)).getName());
		return null;

	}

	@Override
	public String docValidate(PO po, int timing) {
		// TODO Auto-generated method stub
		return null;
	}

}
