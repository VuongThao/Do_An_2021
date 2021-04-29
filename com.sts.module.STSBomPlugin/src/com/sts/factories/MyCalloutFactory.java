package com.sts.factories;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;
import org.compiere.model.MOrder;

import com.sts.callout.MyCallout;

public class MyCalloutFactory implements IColumnCalloutFactory{

	@Override
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {
		// TODO Auto-generated method stub
		
		
		List<IColumnCallout> list = new ArrayList <IColumnCallout>();
		
		if(tableName.contentEquals(MOrder.Table_Name)&& columnName.equalsIgnoreCase(MOrder.COLUMNNAME_Description))
			list.add(new MyCallout());
		if(tableName.contentEquals(MOrder.Table_Name)&& columnName.equalsIgnoreCase(MOrder.COLUMNNAME_AD_Org_ID))
			list.add(new MyCallout());
			
		return list !=null ?list.toArray(new IColumnCallout[0]): new IColumnCallout[0];
	}

}
