package com.sts.process;

import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;

import com.sts.callout.CalloutOderline;
import com.sts.module.STSBomPlugin.MOrder_New;

public class ProcessInvoiceRecording extends SvrProcess {
	CLogger log= CLogger.getCLogger(CalloutOderline.class);
	int p_Order_ID = 0;
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		log.warning("ngay 17-5-2021 ");
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_Order_ID") && para[i].getParameterAsString() != null) {
				p_Order_ID = para[i].getParameterAsInt();
			} else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		MOrder_New mo = new MOrder_New(getCtx(), p_Order_ID, get_TrxName());
		log.warning("id====="+p_Order_ID);
		MInvoice mI = new MInvoice(getCtx(), 0, get_TrxName());
	//		log.warning("test process"+mo.getC_Order_ID());
	//		mI.setC_Order_ID(mo.getC_Order_ID());
		
		mI.setAD_Org_ID(mo.getAD_Org_ID());
		//mI.setDocumentNo(mo.getDocumentNo());
		//mI.setDocStatus(mo.getDocStatus());
		mI.setC_DocType_ID(mo.getDocTypeID());
		//mI.setC_Order_ID(mo.getC_Order_ID());
		//mI.setDateInvoiced(mo.getDateOrdered());
		
		mI.setC_BPartner_ID(mo.getC_BPartner_ID());
		mI.setC_BPartner_Location_ID(mo.getC_BPartner_Location_ID());
		
		mI.setM_PriceList_ID(1000000);
		mI.setGrandTotal(mo.getGrandTotal());
		if(!mI.save()) {
			return null;
		}
//		MOrderLine mol = new MOrderLine(getCtx(), p_Order_ID, get_TrxName());
//		MInvoiceLine miv= new MInvoiceLine(getCtx(),0,get_TrxName());
//		miv.setC_OrderLine_ID(mol.getC_OrderLine_ID());
//		miv.setM_Product_ID(mol.getM_Product_ID());
//		miv.setC_UOM_ID(mol.getC_UOM_ID());
//		miv.setQtyInvoiced(mol.getQtyInvoiced());
//		miv.setPriceList(mol.getPriceList());
//		miv.setC_Tax_ID(mol.getC_Tax_ID());
//		miv.setLineNetAmt(mol.getLineNetAmt());
//		if(!miv.save()) {
//			return null;
//		}
		
		
		return "Complete";
		
	}

}
