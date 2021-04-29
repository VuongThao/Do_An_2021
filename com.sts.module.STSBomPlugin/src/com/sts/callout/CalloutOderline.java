package com.sts.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.CLogger;

public class CalloutOderline implements IColumnCallout{
	CLogger log= CLogger.getCLogger(CalloutOderline.class);

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		log.warning("den day");
	
		
		
		// TODO Auto-generated method stub
		if(mTab.getValue("QtyOrdered")!=null&& mTab.getValue("PriceList")!=null) {
			BigDecimal soluong=(BigDecimal)mTab.getValue("QtyOrdered");
			BigDecimal dongia=(BigDecimal)mTab.getValue("PriceList");
			
			//log.warning( "soluong" +  mTab.getValue("QtyOrdered"));
			//log.warning( "dongia" +  mTab.getValue("PriceList"));
			//log.warning ("tong"+ soluong.multiply(dongia) );
			mTab.setValue("PriceActual",dongia);
			mTab.setValue("LineNetAmt",soluong.multiply(dongia));
			
		}
	 
		return null;
	}

}
