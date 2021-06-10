package com.sts.process;

import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.model.X_C_Order;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.EmailSrv;

import com.sts.module.STSBomPlugin.MOrder_New;

public class ProcessOrder extends SvrProcess {

	int Order_ID = 0;

	@Override
	protected void prepare() {
		log.warning("Ngày 31/5/2021");
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("Order_ID") && para[i].getParameterAsString() != null) {
				Order_ID = para[i].getParameterAsInt();
			} else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

	}

	@Override
	protected String doIt() throws Exception {
		MOrder_New mo = new MOrder_New(getCtx(), Order_ID, get_TrxName());
		log.warning("cung xem id nhe:"+Order_ID);
		switch (mo.getDocStatus()) {
		case X_C_Order.DOCSTATUS_Drafted: {
			mo.setDocStatus(X_C_Order.DOCSTATUS_Approved);
			break;
		}
		case X_C_Order.DOCSTATUS_Approved: {
			mo.setDocStatus(X_C_Order.DOCSTATUS_Completed);
			//mo.setProcessed(true);
		
			break;
		}
		}
		mo.save();

		// TODO Auto-generated method stub
		return null;
	}

}
