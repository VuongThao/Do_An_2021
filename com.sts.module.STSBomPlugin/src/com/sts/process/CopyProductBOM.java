package com.sts.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.DBException;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.sts.module.STSBomPlugin.MPPProductBomlinests;
import com.sts.module.STSBomPlugin.MPPProductBomsts;
import com.sts.module.STSBomPlugin.X_PP_Product_Bom_Sts;
import com.sts.module.STSBomPlugin.X_PP_Product_Bomline_Sts;

public class CopyProductBOM extends SvrProcess {
	private int M_PP_Product_Bom_sts_ID=0;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("M_PP_Product_Bom_sts_ID"))
				M_PP_Product_Bom_sts_ID = ((BigDecimal)para[i].getParameter()).intValue();			
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

		
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception {
		MPPProductBomsts ppBom = new MPPProductBomsts(getCtx(),M_PP_Product_Bom_sts_ID,get_TrxName());
		copyBOM(getCtx(), M_PP_Product_Bom_sts_ID);		
		return ppBom.getName();

	}	
	private void copyBOM(Properties ctx, int m_PP_Product_Bom_sts_ID) {
		String sql = " SELECT * FROM pp_product_bomline_sts WHERE pp_product_bom_sts_id = "+m_PP_Product_Bom_sts_ID;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				MPPProductBomlinests from = new MPPProductBomlinests(getCtx(), rs.getInt("pp_product_bomline_sts_id"), get_TrxName());
				MPPProductBomlinests to = new MPPProductBomlinests(getCtx(), 0, get_TrxName());
				PO.copyValues(from, to);
				to.setPP_Product_Bom_Sts_ID(getRecord_ID());
				to.saveEx();
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		}	


}
