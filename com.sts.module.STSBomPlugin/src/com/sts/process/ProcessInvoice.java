package com.sts.process;

import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.model.X_C_Invoice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;

import com.sts.callout.CalloutOderline;

public class ProcessInvoice extends SvrProcess{
	CLogger log= CLogger.getCLogger(CalloutOderline.class);


	int Invoice_ID = 0;
	@Override
	protected void prepare() {
		log.warning("chay process invoice");
		
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("Invoice_ID") && para[i].getParameterAsString() != null) {
				Invoice_ID = para[i].getParameterAsInt();
			} else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
	}

	@Override
	protected String doIt() throws Exception {
	  MInvoice mi= new MInvoice(getCtx(), Invoice_ID, get_TrxName());
		// TODO Auto-generated method stub
//	  log.warning("se co id:"+Invoice_ID);
//	  log.warning("process"+mi.getDocStatus());
	  switch (mi.getDocStatus()) {
	  case X_C_Invoice.DOCSTATUS_Drafted:{
		  mi.setDocStatus(X_C_Invoice.DOCSTATUS_Approved);
		  break;
	  }
	  case X_C_Invoice.DOCSTATUS_Approved:{
		  mi.setDocStatus(X_C_Invoice.DOCSTATUS_Completed);
     	  mi.setProcessed(true);
		  break;
	  }
	  }
	  mi.save();
	  return null;
	}

}
