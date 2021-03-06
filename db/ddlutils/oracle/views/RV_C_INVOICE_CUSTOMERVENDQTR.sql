CREATE OR REPLACE VIEW RV_C_INVOICE_CUSTOMERVENDQTR
(AD_CLIENT_ID, AD_ORG_ID, C_BPARTNER_ID, VENDOR_ID, DATEINVOICED, 
 LINENETAMT, LINELISTAMT, LINELIMITAMT, LINEDISCOUNTAMT, LINEDISCOUNT, 
 LINEOVERLIMITAMT, LINEOVERLIMIT, QTYINVOICED)
AS 
SELECT il.AD_Client_ID, il.AD_Org_ID,
	il.C_BPartner_ID, po.C_BPartner_ID AS Vendor_ID,
	firstOf(il.DateInvoiced, 'Q') AS DateInvoiced,	--	DD Day, DY Week, MM Month
	SUM(LineNetAmt) AS LineNetAmt,
	SUM(LineListAmt) AS LineListAmt,
	SUM(LineLimitAmt) AS LineLimitAmt,
	SUM(LineDiscountAmt) AS LineDiscountAmt,
	CASE WHEN SUM(LineListAmt)=0 THEN 0 ELSE
	  currencyRound((SUM(LineListAmt)-SUM(LineNetAmt))/SUM(LineListAmt)*100,i.C_Currency_ID,'N') END AS LineDiscount,
	SUM(LineOverLimitAmt) AS LineOverLimitAmt,
	CASE WHEN SUM(LineNetAmt)=0 THEN 0 ELSE
	  100-currencyRound((SUM(LineNetAmt)-SUM(LineOverLimitAmt))/SUM(LineNetAmt)*100,i.C_Currency_ID,'N') END AS LineOverLimit,
	SUM(QtyInvoiced) AS QtyInvoiced
FROM RV_C_InvoiceLine il
	INNER JOIN C_Invoice i ON (i.C_Invoice_ID=il.C_Invoice_ID)
	INNER JOIN M_Product_PO po ON (il.M_Product_ID=po.M_Product_ID)
WHERE il.IsSOTrx='Y'
GROUP BY il.AD_Client_ID, il.AD_Org_ID, il.C_BPartner_ID, po.C_BPartner_ID,
	firstOf(il.DateInvoiced, 'Q'), i.C_Currency_ID;



