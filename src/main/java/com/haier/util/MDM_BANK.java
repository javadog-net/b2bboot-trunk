package com.haier.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import com.haier.mdm.yhcx.MdmgeneralbankinterfaceClientEp;
import com.haier.mdm.yhcx.ProcessResponse.BankTable.BankItem;

public class MDM_BANK {

    /**
     * 功能：银行信息
     * @param bankBranchCode    银行代码
     * @param bankName          银行名称
     * @param region            地区（州、省、县）
     * @param cityStreetRoom    城市
     * @param bankCategory      银行大类
     * @return                  result为SUCCESS成功，msg为成功信息，bank银行字段信息
     *                          result为FAILURE失败，msg为失败信息
     */
    public static Map Query(String bankBranchCode,String bankName,String region,String cityStreetRoom,String bankCategory){
        Holder<com.haier.mdm.yhcx.ProcessResponse.BankTable> bankTable = new Holder<com.haier.mdm.yhcx.ProcessResponse.BankTable>();
        Holder<String> retCode = new Holder<>();
        Holder<String> retMsg = new Holder<>();
        MdmgeneralbankinterfaceClientEp clientEp = new MdmgeneralbankinterfaceClientEp();
        Map<String,Object> map = new HashMap<>();
        try{
            clientEp.getMDMGeneralBankInterfacePt().process(bankBranchCode,bankName,region,cityStreetRoom,bankCategory,bankTable,retCode,retMsg);
            if("S".equals(retCode.value)){
                map.put("result","SUCCESS");
                map.put("bank",bankTable.value.getBankItem());
                List<BankItem> item=bankTable.value.getBankItem();
                if(item!=null && item.size()>0){
                	for(int i=0;i<item.size();i++){
                		BankItem bi=item.get(i);
                		bi.getBankBranchCode();
                		bi.getBankCountry();
                		bi.getBankName();
                		map.put("bankName", bi.getBankName());
                		System.out.println(bi.toString());
                	}
                	
                }
                map.put("retCode",retCode.value);
                map.put("retMsg",retMsg.value);
            }else{
                map.put("result","FAILURE");
            }
            map.put("msg",retMsg.value);
        }catch (Exception e){
            map.put("result","FAILURE");
            map.put("msg","失败："+e.getMessage());
        }
        return map;
    }
    
    
    public static void main(String[] args) {
    	
    	Map map = Query("102241001833", "", "", "", "");
    	System.out.println(map);
	}
}
