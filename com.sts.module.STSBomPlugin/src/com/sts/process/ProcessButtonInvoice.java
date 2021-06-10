package com.sts.process;

import java.util.logging.Level;

import org.compiere.model.X_C_Order;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.sts.module.STSBomPlugin.MOrder_New;

public class ProcessButtonInvoice extends SvrProcess {
 
	int ButtonInv;
	@Override
	
	protected void prepare() {
		// TODO Auto-generated method stub
     log.warning("chay process invoice");
		
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {	
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("Invoice_ID") && para[i].getParameterAsString() != null) {
				ButtonInv = para[i].getParameterAsInt();
			} else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		MOrder_New me= new MOrder_New(getCtx(), ButtonInv, get_TrxName());
		switch(me.getInvoiceRule()) {
		case X_C_Order.INVOICERULE_AfterDelivery:{
		   me.setInvoiceRule(X_C_Order.INVOICERULE_AfterOrderDelivered);
		   me.setProcessed(true);
		   break;
		}
		case X_C_Order.INVOICERULE_AfterOrderDelivered:{
			
			break;
		}
		}
		
		return null;
	}

}
