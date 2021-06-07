package com.sts.callout;


import java.math.BigDecimal;
//import java.math.BigInteger;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MOrderLine;
import org.compiere.util.CLogger;

public class CalloutInvoice implements IColumnCallout {
	CLogger log= CLogger.getCLogger(CalloutOderline.class);

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		
		
		if(mTab.getValue("C_OrderLine_ID")!=null) {
			int c_orderline_id=(int)mTab.getValue("C_OrderLine_ID");
			MOrderLine md= new MOrderLine(null, c_orderline_id, null);
			

			
			mTab.setValue("M_Product_ID", md.getM_Product_ID());
			mTab.setValue("LineNetAmt", md.getLineNetAmt());
			mTab.setValue("C_Tax_ID", md.getC_Tax_ID());
			BigDecimal soluong= (BigDecimal)md.getQtyOrdered();
			
			
			BigDecimal pricrList=(BigDecimal)md.getPriceList();
			mTab.setValue("PriceActual", pricrList);
			
			
		//	mTab.setValue("PriceList",md.getPriceList());
			mTab.setValue("QtyInvoiced", soluong);
			
			
		}
		
		if(mTab.getValue("LineNetAmt")!=null&&mTab.getValue("C_Tax_ID")!=null) {
			
		BigDecimal LineNetAmt=(BigDecimal)mTab.getValue("LineNetAmt");
		
			Double thue=0.1;
			mTab.setValue("TaxAmt", BigDecimal.valueOf(thue).multiply(LineNetAmt));
			mTab.setValue("LineTotalAmt",LineNetAmt.add(BigDecimal.valueOf(thue).multiply(LineNetAmt)));
	
	}
		

		return null;
	}

}
