CREATE TABLE DND_BAN_LIST_FOR_VALIDATION(
	BAN VARCHAR2(10),
	cycle_code VARCHAR2(2),
	cycle_year VARCHAR2(4),
	cycle_month VARCHAR2(2),
	BILL_SEQ_NO VARCHAR2(3),
	STATUS VARCHAR2(100),
	"Comment" VARCHAR2(2000),
	BILL_FORMAT VARCHAR2(3),
  PRODUCTION_TYPE VARCHAR2(4),
  BP_HANDLING_STS VARCHAR2(2),
  BILL_LANGUAGE VARCHAR2(2),
	CONSTRAINT PK_BAN PRIMARY KEY(BAN,cycle_code,BILL_SEQ_NO)
) ;

select * from DND_BAN_LIST_FOR_VALIDATION where BILL_FORMAT='M4'
select * from DND_BAN_LIST_FOR_VALIDATION where CYCLE_CODE='19' and CYCLE_MONTH='06' and lower(status)<>'new'

select * from DND_RULE_VALIDATION_STATUS  where BAN='950246554'
UPDATE DND_BAN_LIST_FOR_VALIDATION SET STATUS='New', "Comment"='' where CYCLE_CODE='19'

DESC DND_BAN_LIST_FOR_VALIDATION;
DESC DND_BAN_VALIDATION_RULES;

select regexp_replace('APT   1006',' +',' ') from dual;

select a.BAN||'|'||a."Comment" AS RAW_REPORT from DND_BAN_LIST_FOR_VALIDATION a where a.CYCLE_CODE =12;
--779#Billing779#Billing12345678

drop table DND_RULE_VALIDATION_STATUS;
select * from DND_RULE_VALIDATION_STATUS where BAN='950246544' Status='Failed';
CREATE TABLE DND_RULE_VALIDATION_STATUS(
  TIME_LOG timestamp,
  BAN VARCHAR2(10),
	cycle_code VARCHAR2(2),
	cycle_year VARCHAR2(4),
	cycle_month VARCHAR2(2),
	BILL_SEQ_NO VARCHAR2(3),
  RULE_ID VARCHAR2(20),
  HTML_DATA VARCHAR2(4000),
  RULE_DATA VARCHAR2(2000),
  STATUS VARCHAR2(20)
);


select count(*) from DND_BAN_LIST_FOR_VALIDATION where cycle_code=11 and cycle_month=06 and cycle_year=2018 and production_type='R';
where  cycle_code=09 and lower(status) = 'new';
110275366_09.pdf
update DND_BAN_LIST_FOR_VALIDATION set STATUS='NEW',"Comment"='NA' where cycle_code=09  BAN in (110275366 );
select * from DND_BAN_LIST_FOR_VALIDATION where lower(status) = 'new' and CYCLE_CODE=09;

SELECT * FROM DND_BAN_LIST_FOR_VALIDATION where STATUS not in ('Completed','New')BAN in (110298352 );

update DND_BAN_LIST_FOR_VALIDATION set status='COMPLETED' where CYCLE_CODE=09 and BAN=110298352 and BILL_FORMAT='M3'

update DND_BAN_LIST_FOR_VALIDATION set status='NEW',"Comment"='' where BAN=110298352 and BILL_FORMAT='T3';

DELETE FROM DND_BAN_LIST_FOR_VALIDATION WHERE BAN =110298352 and CYCLE_CODE=09;

SELECT * FROM BILLING_ACCOUNT WHERE ban in (951388695, 503150029) ;

select BAN,bill_seq_no,Cycle_run_month,Cycle_run_year,Cycle_code from bill where 
BAN in (356840043) and 
cycle_run_year=2017 and 
cycle_code=20
and cycle_run_month=12;

09
05
2018
223

select replace('qwe''ty','''','['''||chr(33303)||']') from dual

select chr(33303) from dual

select * from DND_BAN_LIST_FOR_VALIDATION where cycle_code=20 and lower(status)='new'


iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('110298352','09','2018','05','223','NEW','NA','T3',NULL,NULL,NULL);

update DND_BAN_LIST_FOR_VALIDATION set Status='NEW',"Comment"='NA' where BAN in (961511831,961843940);
iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('961511831','20','2018','01','3','NEW','NA','T14',NULL,NULL,NULL);
iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('961843940','20','2018','01','2','NEW','NA','T4',NULL,NULL,NULL);

iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('110298352','09','2018','05','223','NEW','NA','T3',NULL,NULL,NULL);

iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('60098276','89','2018','04','33','NEW','NA','V3',NULL,NULL,NULL);
select * from DND_BAN_LIST_FOR_VALIDATION where BAN='60098276'
select * from DND_RULE_VALIDATION_STATUS where BAN='60098276'
UPDATE DND_BAN_LIST_FOR_VALIDATION set Status='NEW', "Comment"='NA' where BAN='60098276'
iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('110397363','10','2018','05','222','NEW','NA','M3');
INSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('111361125','10','2018','05','222','NEW','NA','M3');

iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('811086216','17','2018','03','83','NEW','NA','M3');
iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('130775363','17','2018','03','215','NEW','NA','M5');
iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('132136784','17','2018','03','215','NEW','NA','M5');

iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('950197147','17','2018','03','34','COMPLETED','SUCCESS','M3');
iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('956166031','17','2018','03','21','COMPLETED','SUCCESS','M3');
iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('398066532','20','2017','12','153','NEW','NA','M4');
iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('356840043','20','2017','12','163','NEW','NA','M4',NULL,NULL,NULL);
pdate DND_BAN_LIST_FOR_VALIDATION set cycle_month=3;

select ban, actv_amt from  charge  where ban=811086216 and cycle_run_year=2018 and cycle_run_month=03 and cycle_code=17 
and ACTV_REASON_CODE='O' and SUBSCRIBER_NO is null
and ACTV_BILL_SEQ_NO=32 and FEATURE_CODE <>'LTPYM ' 

--19
Select sum(actv_amt) from adjustment where ban=811086216 and cycle_run_year=2018 and cycle_run_month=03 and cycle_code=17 and BALANCE_IMPACT_CODE='I' and ADJ_LEVEL_CODE='A';


--27
select mobile(subscriber_no), '\$'||trim(to_char(actv_amt,'999,999,999,999,990.99'))  from  charge where ban=811086216 and cycle_run_year=2018 and cycle_run_month=03 and cycle_code=17 and SUBSCRIBER_NO is not null and actv_reason_code='R' and SERVICE_TYPE='P'

--28
select mobile(subscriber_no), '\$'||trim(to_char(actv_amt,'999,999,999,999,990.99'))  from  charge where ban=811086216 and cycle_run_year=2018 and cycle_run_month=03 and cycle_code=17 and SUBSCRIBER_NO is not null and FTR_REVENUE_CODE='R' and SERVICE_TYPE<>'P'  and soc not like '%DEFVST%'



SELECT SUbscriber_no FROM subscriber WHERE customer_id=811086216 AND sub_status<>'C'

select mobile(subscriber_no), '$('||trim(to_char(actv_amt,'999,999,999,999,990.99'))||')' from Adjustment where ban=811086216 and cycle_run_year=2018 and cycle_run_month=03 and cycle_code=17  and SUBSCRIBER_NO is not null and BALANCE_IMPACT_CODE<>'I'

select trim(to_char(45566.73,'999,999,999,999,990.99')) from dual;
select double(375) from dual

update DND_BAN_LIST_FOR_VALIDATION 
set STATUS='NEW',
"Comment"='NA'
WHERE BAN=132136784;
commit;



select * from bill_subscriber where BAN=950197147 and bill_seq_no=(select bill_seq_no from bill where BAN=111361125
and cycle_run_year=2018 and cycle_run_month=05 and cycle_code=10)


select * from DND_BAN_VALIDATION_RULES
where FORMAT = 'M3'


drop table DND_BAN_VALIDATION_RULES;
CREATE TABLE DND_BAN_VALIDATION_RULES (
	SEQ_NO NUMBER,
	FORMAT VARCHAR2(5),
	RULE VARCHAR2(5),
	QUERY VARCHAR2(2000),
	IS_QUERY VARCHAR2(10),
	TAG VARCHAR2(2000),
	OFFSET VARCHAR2(2000),
	"LENGTH" NUMBER,
	SKIP VARCHAR2(100),
	DESCRIPTION VARCHAR2(100),
  END_TAG VARCHAR2(10),
	CONSTRAINT PK_RULE PRIMARY KEY(SEQ_NO,FORMAT,RULE)
) ;

select * from DND_BAN_VALIDATION_RULES where FORMAT='M3';

Select 
to_char(CYCLE_CLOSE_DATE,'Mon DD, YYYY'), 
to_char(BILL_DUE_DATE,'MM/DD/YY'), 
AMT_FRMT(PREV_BALANCE_AMT),
AMT_FRMT(PYM_RECEIVED_AMT),
AMT_FRMT(TOTAL_DUE_AMT),
AMT_FRMT(CUR_MNTH_ACC_TOT_RC),
AMT_FRMT(CUR_MNTH_ACC_TOT_OC),
AMT_FRMT(UNPAID_RSTR_QFEE+UNPAID_PA_QFEE),
AMT_FRMT(TOTAL_BILLED_ADJUST) from bill where ban=137825584 and cycle_run_year=2018 and cycle_run_month=05 and cycle_code=15


select  COMP_BILL_NAME_LINE1,regexp_replace(ADR_SECONDARY_LN,' +',' '),COMP_BILL_NAME_LINE2,ADR_ATTENTION,ADR_PRIMARY_LN,ADR_CITY,ADR_STATE_CODE,ADR_ZIP  from ban_list where ban=137825584 and cycle_run_year=2018 and cycle_run_month=05 and cycle_code=15 AND BAN_RECORD_TYPE='R'
select * from DND_BAN_VALIDATION_RULES;
DELETE FROM DND_BAN_VALIDATION_RULES WHERE SEQ_NO=1 and FORMAT='M3';

-- 1 to 10
iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
1,
'M3',
'R1',
'Select 
to_char(CYCLE_CLOSE_DATE,''Mon DD, YYYY''), 
to_char(BILL_DUE_DATE,''MM/DD/YY''), 
AMT_FRMT(PREV_BALANCE_AMT),
AMT_FRMT(PYM_RECEIVED_AMT),
AMT_FRMT(TOTAL_DUE_AMT),
AMT_FRMT(CUR_MNTH_ACC_TOT_RC),
AMT_FRMT(CUR_MNTH_ACC_TOT_OC),
AMT_FRMT(UNPAID_RSTR_QFEE+UNPAID_PA_QFEE),
AMT_FRMT(TOTAL_BILLED_ADJUST) from bill where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC',
'QUERY',
'Monthly Statement~Total amount due Amount~Previous balance~Payments received~Total amount due by|New balance - Credit~Recurring~Other~Unpaid Restore from Suspended Charge~Credits and one time charges',
'0~0~0~0~0~0~0~0~0',
50,
'0~0~0~0~0~0~0~0~0',
'Amount 1 to 10',
NULL
);


select * from  bill where 1=0;
-- 11 to 18
iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
45,
'T3',
'R45',
'select  COMP_BILL_NAME_LINE1,COMP_BILL_NAME_LINE2,ADR_ATTENTION,ADR_PRIMARY_LN,ADR_CITY,ADR_STATE_CODE,ADR_ZIP,ADR_ZIP4  from ban_list where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC',
'QUERY',
'---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------',
'1~1~1~1~1~1~1~1',
50,
'0~0~0~0~0~0~0~0',
'Address Validation',
NULL
);

--19
Select sum(actv_amt) from adjustment where ban=811086216 and cycle_run_year=2018 and cycle_run_month=03 and cycle_code=17
and BALANCE_IMPACT_CODE='I' and ADJ_LEVEL_CODE='A';
select * from payment
where ban=811086216 and cycle_run_year=2018 and cycle_run_month=03 and cycle_code=17;
--20
iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
20,
'M3',
'R20',
'Select AMT_FRMT(PYM_RECEIVED_AMT) from bill where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC',
'QUERY',
'Current charges;<b>Payments received;Total',
0,
50,
'1;0;0',
'Payment Recevied 20',
NULL
);


select AMT_FRMT_M4((sum(nvl(actv_amt,0))+ sum(nvl(TAX_CITY_CUST_AMT,0))+ sum(nvl(TAX_COUNTY_CUST_AMT,0))+ sum(nvl(TAX_STATE_CUST_AMT,0))+
sum (nvl(TAX_MIS1_CUST_AMT,0))+ sum(nvl(TAX_MIS2_CUST_AMT,0))+ sum (nvl(TAX_ROAMING_AMT ,0))+ sum(nvl(tax_federal_amt,0))) as ONE_TIME_CHARGE 
From charge where ftr_revenue_code in('O','U') and actv_amt > 0 and balance_impact_code='B' and bill_comment is null 
and ban in (961511831,961843940) and cycle_run_year=2018 and cycle_run_month=01 and cycle_code=20;

delete DND_BAN_VALIDATION_RULES where FORMAT in ('T14','T4')

INSERT INTO DND_BAN_VALIDATION_RULES VALUES
(9,'T4','R9','select AMT_FRMT_M4((sum(nvl(actv_amt,0))+ sum(nvl(TAX_CITY_CUST_AMT,0))+ sum(nvl(TAX_COUNTY_CUST_AMT,0))+ sum(nvl(TAX_STATE_CUST_AMT,0))+
sum (nvl(TAX_MIS1_CUST_AMT,0))+ sum(nvl(TAX_MIS2_CUST_AMT,0))+ sum (nvl(TAX_ROAMING_AMT ,0))+ sum(nvl(tax_federal_amt,0)))) as ONE_TIME_CHARGE 
From charge where ftr_revenue_code in(''O'',''U'') and actv_amt > 0 and balance_impact_code=''B'' and bill_comment is null 
and ban in ($INPUT_BAN) and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC',
'QUERY','ONE-TIME CHARGES','0',50,'0','One TIME Charge on Summary Page',NULL);

INSERT INTO DND_BAN_VALIDATION_RULES VALUES
(9,'T14','R9','select AMT_FRMT_M4((sum(nvl(actv_amt,0))+ sum(nvl(TAX_CITY_CUST_AMT,0))+ sum(nvl(TAX_COUNTY_CUST_AMT,0))+ sum(nvl(TAX_STATE_CUST_AMT,0))+
sum (nvl(TAX_MIS1_CUST_AMT,0))+ sum(nvl(TAX_MIS2_CUST_AMT,0))+ sum (nvl(TAX_ROAMING_AMT ,0))+ sum(nvl(tax_federal_amt,0)))) as ONE_TIME_CHARGE 
From charge where ftr_revenue_code in(''O'',''U'') and actv_amt > 0 and balance_impact_code=''B'' and bill_comment is null 
and ban in ($INPUT_BAN) and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC',
'QUERY','CARGOS ÚNICOS','0',50,'0','One TIME Charge on Summary Page',NULL);


--27
iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
27,
'M3',
'R27',
'select mobile(subscriber_no), AMT_FRMT(actv_amt)  from  charge where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC and SUBSCRIBER_NO is not null and actv_reason_code=''R'' and SERVICE_TYPE=''P''',
'QUERY',
'<b>Current charges;Account;$INPUT_SUBSCRIBER',
'1',
50,
'1;0;0',
'SUBSCRIBER LEVEL PP Charge 27',
'<b> Total:'
);

--28
iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
28,
'M3',
'R28',
'select mobile(subscriber_no), AMT_FRMT(actv_amt)  from  charge where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC and SUBSCRIBER_NO is not null and FTR_REVENUE_CODE=''R'' and SERVICE_TYPE<>''P''  and soc not like ''%DEFVST%''',
'QUERY',
'<b>Current charges;Account;$INPUT_SUBSCRIBER',
'1',
50,
'1;0;0',
'SUBSCRIBER LEVEL SOC Charge 28',
'<b> Total:'
);


--29
iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
29,
'M3',
'R29',
'select mobile(subscriber_no), AMT_FRMT(actv_amt)  from  charge where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC and SUBSCRIBER_NO is not null and actv_amt>0 and ACTV_BILL_SEQ_NO=$INPUT_BSN and PRSNT_ON_BILL is null and ACTV_REASON_CODE=''O''',
'QUERY',
'<b>Current charges;Account;$INPUT_SUBSCRIBER',
'1',
50,
'1;0;0',
'SUBSCRIBER LEVEL OTHER Charge 29',
'<b> Total:'
);

--30
iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
30,
'M3',
'R30',
'select mobile(subscriber_no), AMT_FRMT(actv_amt)  from  charge where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC and SUBSCRIBER_NO is not null and ACTV_REASON_CODE=''U''',
'QUERY',
'<b>Current charges;Account;$INPUT_SUBSCRIBER',
'1',
50,
'1;0;0',
'SUBSCRIBER LEVEL USAGE Charge 30',
'<b> Total:'
);





select * from DND_BAN_VALIDATION_RULES where RULE='R30'
select mobile(subscriber_no), AMT_FRMT(SUM(actv_amt))  from  charge where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC and SUBSCRIBER_NO is not null and ACTV_REASON_CODE='U' and soc not like '%DEFVST%' group by subscriber_no
--31
iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
31,
'M3',
'R31',
'select mobile(subscriber_no), ''(''||AMT_FRMT(actv_amt)||'')'' from Adjustment where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC  and SUBSCRIBER_NO is not null and BALANCE_IMPACT_CODE<>''I''',
'QUERY',
'<b>Current charges;Account;$INPUT_SUBSCRIBER',
'1',
50,
'1;0;0',
'SUBSCRIBER LEVEL Adjustment 31',
'<b> Total:'
);





iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
4,
'M3',
'R4',
'RuleTest($INPUT_BAN)',
'FUNCTION',
'Account number',
0,
50,
'0',
'Function Test',
NULL
);

iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
5,
'M3',
'R5',
'Select to_char(CYCLE_CLOSE_DATE,''Mon DD, YYYY'') from bill where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC',
'QUERY',
'---Manifest Line--------------',
-1,
50,
'0',
'NEGATIVE TEST',
NULL
);




Insert into DND_BAN_VALIDATION_RULES values (1,'M4','R1','Select to_char(PRD_CVRG_STRT_DATE,''Mon DD, YYYY'')as PRD_CVRG_STRT_DATE,
to_char(PRD_CVRG_END_DATE,''Mon DD, YYYY'')as PRD_CVRG_END_DATE,
BAN,TOTAL_DUE_AMT,PREV_BALANCE_AMT,to_char(BILL_DUE_DATE,''Mon DD, YYYY'') as BILL_DUE_DATE,
''Here is your statement for ''||to_char(cycle_close_date,''Month'')as CYCLE_CLOSE_DATE
from bill where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC','QUERY','Bill period~Bill period~Account~TOTAL DUE~Thanks for~Please detach this portion~Here is your statement for',
'0~0~0~1~3~5~0',50,'0~0~0~0~0~0~0','Amount',NULL);

Insert into DND_BAN_VALIDATION_RULES values (2,'M4','R2','SELECT SUM(ACTV_AMT) as ACTV_AMT FROM PAYMENT_ACTIVITY WHERE BAN=$INPUT_BAN AND ACTV_BILL_SEQ_NO IN(
SELECT BILL_SEQ_NO FROM BILL WHERE BAN=$INPUT_BAN AND cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC)','QUERY','Thanks for',
'0',50,'0','Amount',NULL);

Insert into DND_BAN_VALIDATION_RULES values (3,'M4','R3','SELECT
(CASE WHEN FIRST_NAME IS NULL THEN (SELECT ''Hello,'' FROM BAN_LIST WHERE  cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC AND BAN=$INPUT_BAN)
ELSE (SELECT ''Hi ''||INITCAP(FIRST_NAME)||'','' FROM BAN_LIST WHERE  cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC AND BAN=$INPUT_BAN)
END) AS FIRST_NAME FROM BAN_LIST WHERE  cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC AND BAN=$INPUT_BAN','QUERY','Thanks for ',
'1',50,'0','Amount',NULL);

Insert into DND_BAN_VALIDATION_RULES values (4,'M4','R4','SELECT
(CASE WHEN BA.AUTO_GEN_PYM_TYPE=''R'' THEN (SELECT ''Autopay is scheduled for '' FROM BILLING_ACCOUNT BA WHERE BA.BAN=$INPUT_BAN)
ELSE (SELECT NULL FROM BILLING_ACCOUNT BA WHERE BA.BAN=$INPUT_BAN)
END) AS AUTO_GEN_PYM_TYPE, TO_CHAR(BI.BILL_DUE_DATE,''Mon DD, YYYY'') as BILL_DUE_DATE FROM BILLING_ACCOUNT BA,BILL BI WHERE BI.BAN=BA.BAN 
AND cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC AND ba.BAN=$INPUT_BAN AND ba.auto_gen_pym_type=''R''','QUERY','TOTAL DUE~TOTAL DUE',
'2~2',50,'0~0','Amount',NULL);

iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
1,
'M5',
'R1',
'Select to_char(CYCLE_CLOSE_DATE,''Mon DD, YYYY''),to_char(BILL_DUE_DATE,''MM/DD/YY''),to_char(BILL_DUE_DATE,''MM/DD/YY''),PREV_BALANCE_AMT,PYM_RECEIVED_AMT,TOTAL_DUE_AMT,CUR_MNTH_ACC_TOT_RC,CUR_MNTH_ACC_TOT_OC,UNPAID_RSTR_QFEE+UNPAID_PA_QFEE,TOTAL_BILLED_ADJUST from bill where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC',
'QUERY',
'Monthly Statement~Total amount due by~Total amount due Amount~Previous balance~Payments received~Total amount due by~Recurring~Other~Unpaid Restore from Suspended Charge~Credits and one time charges',
'0~0~0~0~0~0~0~0~0~0',
50,
'0~0~0~0~0~0~0~0~0~0',
'Amount 1 to 10',
NULL
);

-- 11 to 18
iNSERT INTO DND_BAN_VALIDATION_RULES VALUES
(
11,
'M5',
'R11',
'select  COMP_BILL_NAME_LINE1,COMP_BILL_NAME_LINE2,ADR_ATTENTION,ADR_PRIMARY_LN,ADR_CITY,ADR_STATE_CODE,ADR_ZIP,ADR_ZIP4  from ban_list where ban=$INPUT_BAN and cycle_run_year=$INPUT_CY and cycle_run_month=$INPUT_CM and cycle_code=$INPUT_CC',
'QUERY',
'---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------~---Manifest Line--------------',
'1~1~1~1~1~1~1~1',
50,
'0~0~0~0~0~0~0~0',
'Address 11 to 18',
NULL
);


INSERT INTO DND_BAN_VALIDATION_RULES VALUES
(4,'M4','R4','select
(CASE WHEN PAST_DUE_AMT IS NULL and (PREV_BALANCE_AMT is NUll OR (NVL(PREV_BALANCE_AMT,0)-NVL(PYM_RECEIVED_AMT,0))=0) THEN NULL 
WHEN PAST_DUE_AMT>0 THEN ''PAST DUE BALANCE'' 
WHEN ((NVL(prev_balance_amt,0)- NVL(pym_received_amt,0))=0 and (NVL(TOTAL_BILLED_CHARGE,0)-NVL(TOTAL_BILLED_ADJUST,0)+NVL(DEPOSIT_REQ_AMT,0)+NVLWHEN Total_billed_adjust is Not Null and (NVL(pym_received_amt,0)+NVL(Total_billed_adjust,0))=prev_balance_amt THEN ''BALANCE''  
WHEN NVL(BALANCE_M4($INPUT_BAN,$INPUT_CC,$INPUT_CM,$INPUT_CY),0)=0 THEN ''BALANCE'' 
WHEN ((NVL(past_due_amt,0)- NVL(pym_received_amt,0)+NVL(total_billed_charge,0)-NVL(TOTAL_BILLED_ADJUST,0)+NVL(deposit_req_amt,0)+NVL(unpaid_rstr_qfee,0)+NVL(unpaid_pa_qfee,0))<0) THEN ''BALANCE FORWARD''
ELSE NULL END)AS DUE_AMT_INDICATOR,AMT_FRMT_M4_3(PAST_DUE_AMT,TOTAL_BILLED_ADJUST,PREV_BALANCE_AMT,PYM_RECEIVED_AMT) as AMOUNT FROM BILL BI,BAN_LIST BL
where BI.ban in ($INPUT_BAN) and BI.cycle_run_year=$INPUT_CY and BI.cycle_run_month=$INPUT_CM and BI.cycle_code=$INPUT_CC and BI.ban=BL.BAN and BI.cycle_run_month=BL.cycle_run_month 
and BI.cycle_run_year=BL.cycle_run_year and BI.cycle_code=BL.cycle_code','QUERY','BALANCE|BALANCE FORWARD|UNPAID BALANCE|PAST DUE BALANCE','1',50,'0','BALANCE Validation',NULL);

INSERT INTO DND_BAN_VALIDATION_RULES VALUES
(4,
'M14',
'R4',
'select
(CASE WHEN PAST_DUE_AMT IS NULL and (PREV_BALANCE_AMT is NUll OR (NVL(PREV_BALANCE_AMT,0)-NVL(PYM_RECEIVED_AMT,0))=0) THEN NULL 
WHEN PAST_DUE_AMT>0 THEN ''BALANCE PENDIENTE'' 
WHEN ((NVL(prev_balance_amt,0)- NVL(pym_received_amt,0))=0 and (NVL(TOTAL_BILLED_CHARGE,0)-NVL(TOTAL_BILLED_ADJUST,0)+NVL(DEPOSIT_REQ_AMT,0)+NVL(UNPAID_RSTR_QFEE,0)+NVL(UNPAID_PA_QFEE,0))>0) THEN ''BALANCE ADEUDADO'' 
WHEN Total_billed_adjust is Not Null and (NVL(pym_received_amt,0)+NVL(Total_billed_adjust,0))=prev_balance_amt THEN ''SALDO''  
WHEN ((NVL(past_due_amt,0)- NVL(pym_received_amt,0)+NVL(total_billed_charge,0)-NVL(TOTAL_BILLED_ADJUST,0)+NVL(deposit_req_amt,0)+NVL(unpaid_rstr_qfee,0)+NVL(unpaid_pa_qfee,0))<0) THEN ''BALANCE TRANSFERIDO''
ELSE NULL END)AS DUE_AMT_INDICATOR,AMT_FRMT_M4_3(PAST_DUE_AMT,TOTAL_BILLED_ADJUST,PREV_BALANCE_AMT,PYM_RECEIVED_AMT) as AMOUNT FROM BILL BI,BAN_LIST BL
where BI.ban in ($INPUT_BAN) and BI.cycle_run_year=$INPUT_CY and BI.cycle_run_month=$INPUT_CM and BI.cycle_code=$INPUT_CC and BI.ban=BL.BAN and BI.cycle_run_month=BL.cycle_run_month 
and BI.cycle_run_year=BL.cycle_run_year and BI.cycle_code=BL.cycle_code',
'QUERY',
'SALDO|BALANCE TRANSFERIDO|BALANCE ADEUDADO|BALANCE PENDIENTE',
'1~1',
50,
'0~0',
'BALANCE Validation',
NULL);

select
(CASE WHEN PAST_DUE_AMT IS NULL and (PREV_BALANCE_AMT is NUll OR (NVL(PREV_BALANCE_AMT,0)-NVL(PYM_RECEIVED_AMT,0))=0) THEN NULL 
WHEN PAST_DUE_AMT>0 THEN 'BALANCE PENDIENTE' 
WHEN ((NVL(prev_balance_amt,0)- NVL(pym_received_amt,0))=0 and (NVL(TOTAL_BILLED_CHARGE,0)-NVL(TOTAL_BILLED_ADJUST,0)+NVL(DEPOSIT_REQ_AMT,0)+NVL(UNPAID_RSTR_QFEE,0)+NVL(UNPAID_PA_QFEE,0))>0) THEN 'BALANCE ADEUDADO' 
WHEN Total_billed_adjust is Not Null and (NVL(pym_received_amt,0)+NVL(Total_billed_adjust,0))=prev_balance_amt THEN 'SALDO'  
WHEN ((NVL(past_due_amt,0)- NVL(pym_received_amt,0)+NVL(total_billed_charge,0)-NVL(TOTAL_BILLED_ADJUST,0)+NVL(deposit_req_amt,0)+NVL(unpaid_rstr_qfee,0)+NVL(unpaid_pa_qfee,0))<0) THEN 'BALANCE TRANSFERIDO'
ELSE NULL END)AS DUE_AMT_INDICATOR,AMT_FRMT_M4_3(PAST_DUE_AMT,TOTAL_BILLED_ADJUST,PREV_BALANCE_AMT,PYM_RECEIVED_AMT) as AMOUNT FROM BILL BI,BAN_LIST BL
where BI.ban in (961549656) and BI.cycle_run_year=2017 and BI.cycle_run_month=12 and BI.cycle_code=20 and BI.ban=BL.BAN and BI.cycle_run_month=BL.cycle_run_month 
and BI.cycle_run_year=BL.cycle_run_year and BI.cycle_code=BL.cycle_code

iNSERT INTO DND_BAN_LIST_FOR_VALIDATION VALUES ('961549656','20','2017','12','1','NEW','NA','M14',NULL,NULL,NULL);


delete from DND_BAN_VALIDATION_RULES where FORMAT='M14';

CREATE FUNCTION AMT_FRMT_M4_3 (PAST_DUE_AMT NUMBER, TOTAL_BILLED_ADJUST NUMBER, PREV_BALANCE_AMT NUMBER, PYM_RECEIVED_AMT NUMBER) RETURN VARCHAR2 AS RET_VAL VARCHAR2(20) ;
BEGIN
IF PAST_DUE_AMT  IS NULL and TOTAL_BILLED_ADJUST is NUll and (NVL(PREV_BALANCE_AMT,0)-NVL(PYM_RECEIVED_AMT,0))=0 THEN select NULL INTO RET_VAL from DUAL;
ELSIF PAST_DUE_AMT < 0 THEN SELECT '-\'||REPLACE(trim(to_char(PAST_DUE_AMT,'$9,99,99,99,990.99')),'-','') INTO RET_VAL FROM DUAL;
ELSIF PAST_DUE_AMT > 0 THEN SELECT '\'||trim(to_char(PAST_DUE_AMT,'$9,99,99,99,990.99')) INTO RET_VAL FROM DUAL;
ELSE SELECT '$0.00' INTO RET_VAL FROM DUAL;
END IF;
RETURN RET_VAL;
END; 
/

CREATE OR REPLACE FUNCTION RuleTest (BAN NUMBER) RETURN VARCHAR2 AS
ret_val VARCHAR2(50);
BEGIN
SELECT BAN INTO RET_VAL FROM DUAL;
RETURN ret_val;
END;

select RuleTest(1234) from dual


CREATE OR REPLACE FUNCTION Mobile(MSISDN NUMBER) RETURN VARCHAR2 AS
ret_val varchar(15);
BEGIN
SELECT '('||SUBSTR(to_char(MSISDN),1,3)||') '||SUBSTR(to_char(MSISDN),4,3)||'-'||SUBSTR(to_char(MSISDN),7) INTO ret_Val FROM DUAL;
return ret_val;
end;

CREATE OR REPLACE FUNCTION AMT_FRMT (AMT NUMBER) RETURN VARCHAR2 AS RET_VAL VARCHAR2(20);
BEGIN
IF AMT IS NULL  THEN
SELECT NULL INTO RET_VAL FROM DUAL;
ELSIF AMT < 0 THEN
SELECT '\(\'||REPLACE(trim(to_char(AMT,'$999,999,990.99')),'-','')||'\)' INTO RET_VAL FROM DUAL;
ELSE
SELECT '\'||trim(to_char(AMT,'$999,999,990.99')) INTO RET_VAL FROM DUAL;
END IF;
RETURN RET_VAL;
END;


DROP FUNCTION AMT_FRMT_M4;
CREATE FUNCTION AMT_FRMT_M4 (AMT NUMBER) RETURN VARCHAR2 AS RET_VAL VARCHAR2(20) ;
BEGIN
IF AMT IS NULL  THEN
SELECT NULL INTO RET_VAL FROM DUAL;
ELSIF AMT < 0 THEN
SELECT '-\'||REPLACE(trim(to_char(AMT,'$9,99,99,99,990.99')),'-','') INTO RET_VAL FROM DUAL;
ELSE
SELECT '\'||trim(to_char(AMT,'$9,99,99,99,990.99')) INTO RET_VAL FROM DUAL;
END IF;
RETURN RET_VAL;
END; 
/



SELECT AMT_FRMT(-0.59) FROM DUAL;

SELECT NULL FROM DUAL;
select Mobile(9937501468) from dual;

select to_char(1123) from dual

select  BL.BAN ||' '|| TO_CHAR(BL.BILL_SEQ_NO,'FM009') ||' '|| TO_CHAR(BL.CYCLE_CODE,'FM09')||' S P01OL1'
from DND_BAN_LIST_FOR_VALIDATION  BL
where cycle_code = 01
and cycle_month = 02
and cycle_year = 2018
and lower(status) = 'new'

SELECT * FROM DND_BAN_VALIDATION_RULES;
SELECT * FROM DND_BAN_LIST_FOR_VALIDATION;




DROP TABLE DND_TABLE_DEFINITION
CREATE TABLE DND_TABLE_DEFINITION (
	ID VARCHAR2(20),
	START_TABLE VARCHAR2(100),
	IGNORE VARCHAR2(1000),
	CHECK_SUM_ITEM VARCHAR2(100),
	CYCLIC NUMBER,
	CYCLE_END VARCHAR2(200),
	IGNORE_START VARCHAR2(200),
	IGNORE_END VARCHAR2(200),
	TAGS VARCHAR2(500),
	REVERSE_TABLES_ENDS VARCHAR2(500),
  BILL_FORMAT VARCHAR2(20)
) ;

select * from DND_TABLE_DEFINITION;


DELETE FROM DND_TABLE_DEFINITION WHERE ID='T1'
INSERT INTO DND_TABLE_DEFINITION VALUES ('T1','Balance','Balance forward - Credit|Unpaid balance','Total amount due by|New balance - Credit|No payment required',0,NULL,NULL,NULL,NULL,NULL,'M3');
INSERT INTO DND_TABLE_DEFINITION VALUES ('T2','Charges based on the following address|Service charge taxes based on this address','Subtotal:|Included|Balance of remaining paymentsDescription|will not reflect transactions made in proximity','Total:',1,'Usage charge details|Usage details',NULL,NULL,NULL,NULL,'M3');

delete dnd_table_definition where ID in ('T3','T4');

insert into DND_TABLE_DEFINITION VALUES ('T3', 'DETAILED CHARGES', null, 'REGULAR CHARGES|Balance', 1, null,null,null,'PLANS|EQUIPMENT|SERVICES|ONE-TIME CHARGES', 'TAX BREAKDOWN|USAGE CHARGE DETAILS','M4');

INSERT INTO DND_TABLE_DEFINITION VALUES ('T11','Saldo','Saldo remitido|Unpaid balance','Importe total a pagar en',0,NULL,NULL,NULL,NULL,NULL,'M5');
INSERT INTO DND_TABLE_DEFINITION VALUES ('T12','Cargos de acuerdo a la siguiente direcci&#243;n:','Subtotal:|Saldo de los pagos restantesDescripci|El saldo no mostrar&#225; transacciones realizadas cerca de la fecha','Total:',1,'Detalles del cargo por uso|Detalles del uso',NULL,NULL,NULL,NULL,'M5');


drop table DND_REGEX_DEFINITION
CREATE TABLE DND_REGEX_DEFINITION (
	REGEXID VARCHAR2(20),
	REGEX VARCHAR2(1000),
  BILL_FORMAT VARCHAR2(20)
) ;

INSERT INTO DND_REGEX_DEFINITION VALUES('R1','-[\d,]+\.\d+','M3');

INSERT INTO DND_REGEX_DEFINITION VALUES('REG1','\$([456789]\d\d|[\d,]+\d\d\d)','M3');

truncate table DND_REGEX_DEFINITION;
select * from DND_REGEX_DEFINITION;


DELETE FROM DND_TABLE_DEFINITION WHERE ID='T4'

PDATE DND_BAN_LIST_FOR_VALIDATION SET STATUS='NEW' WHERE BAN =110573765;