package com.sts.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MOrder;
import org.compiere.util.CLogger;

public class MyCallout implements IColumnCallout {
	
	CLogger log= CLogger.getCLogger(MyCallout.class);

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		log.warning("ColumnName"+mField.getColumnName());
		log.warning("NewVakue"+value.toString());
		log.warning("OldValue: "+oldValue.toString());
		
		mTab.setValue(MOrder.COLUMNNAME_DocumentNo, "Test");
		
		
		return null;
	}

}
