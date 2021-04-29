package com.sts.process;

import org.adempiere.base.IPaymentProcessorFactory;
import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;

public class ProcessFactory implements IProcessFactory {

	@Override
	public ProcessCall newProcessInstance(String className) {
		// TODO Auto-generated method stub
		if("com.sts.process.ProcessOrder".equals(className))
			return new ProcessOrder();
		
		
		return null;
	}

}
