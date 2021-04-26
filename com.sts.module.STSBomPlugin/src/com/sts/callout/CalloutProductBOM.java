package com.sts.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;

public class CalloutProductBOM  implements IColumnCallout{

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		MProduct product = new MProduct(ctx, (int) mField.getValue(), null);
		mTab.setValue("Value", product.getValue());
		mTab.setValue("Name", product.getName());
		mTab.setValue("Description", product.getDescription());

		// TODO Auto-generated method stub
		return null;
	}
	
	//public String start (Properties  ctx,int WindowNo, )

}
