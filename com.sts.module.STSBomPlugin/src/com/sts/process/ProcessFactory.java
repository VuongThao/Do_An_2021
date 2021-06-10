package com.sts.process;

//import org.adempiere.base.IPaymentProcessorFactory;
import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;

public class ProcessFactory implements IProcessFactory {

	@Override
	public ProcessCall newProcessInstance(String className) {
		// TODO Auto-generated method stub
		if("com.sts.process.ProcessOrder".equals(className))
			return new ProcessOrder();
		if("com.sts.process.ProcessInvoice".equals(className))
			return new ProcessInvoice();
		if("com.sts.process.ProcessInvoiceRecording".equals(className))
			return new ProcessInvoiceRecording();
//		if("com.sts.process.ProcessButtonInvoice".equals(className))
//			return new ProcessButtonInvoice();
		
		
		return null;
	}

}
