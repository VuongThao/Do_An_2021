package com.sts.process;

import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.EmailSrv;

public class ProcessOrder extends SvrProcess {
	
	int Order_ID=0;
	

	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("Order_ID") && para[i].getParameterAsString() != null) {
				Order_ID = para[i].getParameterAsInt();
			}  else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		MOrder mo= new MOrder(getCtx(), Order_ID, get_TrxName());
		mo.setDocStatus("AP");
		mo.save();
		
		// TODO Auto-generated method stub
		return null;
	}

}
