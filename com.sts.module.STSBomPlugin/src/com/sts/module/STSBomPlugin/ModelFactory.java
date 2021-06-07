package com.sts.module.STSBomPlugin;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.MOrder;
import org.compiere.model.MPayment;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import com.sts.callout.CalloutOderline;

public class ModelFactory implements IModelFactory{
	CLogger log= CLogger.getCLogger(CalloutOderline.class);

	@Override
	public Class<?> getClass(String tableName) {
		// TODO Auto-generated method stub
		log.warning("test");
		if(tableName.equals(MOrder.Table_Name))
			return MOrder_New.class;
		log.warning("chay test");
		if(tableName.equals(MPayment.Table_Name))
			return Mpayment_New.class;
		if(tableName.equals(MUser.Table_Name))
			return MAdUser_New.class;
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		// TODO Auto-generated method stubl
		log.warning("test 2");
		if(tableName.equals(MOrder.Table_Name))
			return new MOrder_New(Env.getCtx(), Record_ID, trxName);
		log.warning("test 3");
		if(tableName.equals(MPayment.Table_Name))
			return new Mpayment_New(Env.getCtx(), Record_ID, trxName);
		if(tableName.equals(MUser.Table_Name))
		return new MAdUser_New(Env.getCtx(), Record_ID, trxName);
		
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		// TODO Auto-generated method stub
		if(tableName.equals(MOrder.Table_Name))
			return new MOrder_New(Env.getCtx(), rs, trxName);
		if(tableName.equals(MPayment.Table_Name))
			return new Mpayment_New(Env.getCtx(), rs, trxName);
     	if(tableName.equals(MUser.Table_Name))
		return new MAdUser_New(Env.getCtx(), rs, trxName);
		return null;
	}

}
