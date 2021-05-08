package com.sts.module.STSBomPlugin;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.MOrder;
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
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		// TODO Auto-generated method stubl
		log.warning("test 2");
		if(tableName.equals(MOrder.Table_Name))
			return new MOrder_New(Env.getCtx(), Record_ID, trxName);
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		// TODO Auto-generated method stub
		if(tableName.equals(MOrder.Table_Name))
			return new MOrder_New(Env.getCtx(), rs, trxName);
		return null;
	}

}
