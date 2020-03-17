/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.dealer;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 供应商消息列表Entity
 * @author tity
 * @version 2018-08-20
 */
public class DealerMsg extends DataEntity<DealerMsg> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 模板编码
	private String type;		// 消息类型
	private String dealerId;		// 经销商
    private String relId;           //引用id，做订单id或者其他id使用
	private String content;		// 消息内容
	private Date addtime;		// 发送时间
	private String readids;		// 已读成员
	private String accountId;      //已读用户id
    private String isRead;      //是否已读
    private Date readtime;      //读取时间
	public DealerMsg() {
		super();
	}

	public DealerMsg(String id){
		super(id);
	}

	@ExcelField(title="模板编码", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="消息类型", dictType="message_type", align=2, sort=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="经销商", align=2, sort=3)
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    @ExcelField(title="消息内容", align=2, sort=4)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发送时间不能为空")
	@ExcelField(title="发送时间", align=2, sort=5)
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@ExcelField(title="已读成员", align=2, sort=6)
	public String getReadids() {
		return readids;
	}

	public void setReadids(String readids) {
		this.readids = readids;
	}

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getReadtime() {
        return readtime;
    }

    public void setReadtime(Date readtime) {
        this.readtime = readtime;
    }
}