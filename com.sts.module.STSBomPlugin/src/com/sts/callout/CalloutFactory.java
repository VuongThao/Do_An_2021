package com.sts.callout;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.util.CLogger;

public class CalloutFactory implements IColumnCalloutFactory {
	CLogger log= CLogger.getCLogger(CalloutOderline.class);
	@Override
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {
		// TODO Auto-generated method stub
		log.warning("chay callout");
		List<IColumnCallout> list = new  ArrayList<IColumnCallout>();
		if(tableName.equals(MOrderLine.Table_Name)&& columnName.equals(MOrderLine.COLUMNNAME_QtyOrdered))
			list.add(new CalloutOderline());
		return list !=null ? list.toArray(new IColumnCallout[0]): new IColumnCallout[0];
	}

}
