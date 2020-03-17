/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.purchaser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jhmis.common.utils.Collections3;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * 采购商账号Entity
 * @author tity
 * @version 2018-07-22
 */
public class PurchaserAccount extends DataEntity<PurchaserAccount> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserId;		// 供应商ID
	private String loginName;		// 登录名
	private String passwd;		// 密码
	private String realName;		// 姓名
	private String mobile;		// 手机号
	private String avatar; //头像
	private String nickName; //昵称
	private String departName;		// 所在部门
	private String isAdmin;		// 是否是供应商主账号  0 否 1  是
	private String email;		// 邮箱
	private String isClosed;		// 子账号状态（0 正常 1 停用）
	private Date lastLoginDate;		// 最后登录时间
	private String createById;		// 创建者
	private String updateById;		// 更新者
    private String haierUserId;     //用户中心id
	// 采购商登录
	private String loginNum;          //88码

	private String code;            // 推荐码

	// 新增getter和setter方法
	@ExcelField(title="登录名", align=2, sort=2)
	public String getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(String loginNum) {
		this.loginNum = loginNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	//只作查询
	private String purchaserName;//公司名称
    private String logoUrl;
    private String newPassword;    //新密码
    private Purchaser purchaser;          //采购商对象
  private String detailAddress;
  private String wxindustryname;
  private String proId;
  private String cityId;
    public String getProId() {
	return proId;
}

public void setProId(String proId) {
	this.proId = proId;
}

public String getCityId() {
	return cityId;
}

public void setCityId(String cityId) {
	this.cityId = cityId;
}

	public String getWxindustryname() {
	return wxindustryname;
}

public void setWxindustryname(String wxindustryname) {
	this.wxindustryname = wxindustryname;
}

	public String getDetailAddress() {
	return detailAddress;
}

public void setDetailAddress(String detailAddress) {
	this.detailAddress = detailAddress;
}

	private List<PurchaserRole> roleList = Lists.newArrayList(); // 拥有角色列表
    private List<String> roleIdList = Lists.newArrayList(); // 拥有角色id列表
	public PurchaserAccount() {
		super();
	}

	public PurchaserAccount(String id){
		super(id);
	}

	@ExcelField(title="供应商ID", align=2, sort=1)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="登录名", align=2, sort=2)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@ExcelField(title="密码", align=2, sort=3)
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	@ExcelField(title="姓名", align=2, sort=4)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@ExcelField(title="手机号", align=2, sort=5)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@ExcelField(title="所在部门", align=2, sort=6)
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	@ExcelField(title="是否是供应商主账号  0 否 1  是", align=2, sort=7)
	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	@ExcelField(title="邮箱", align=2, sort=8)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="子账号状态（0 正常 1 停用）", dictType="enable", align=2, sort=10)
	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后登录时间", align=2, sort=11)
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@ExcelField(title="创建者", align=2, sort=12)
	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}
	
	@ExcelField(title="更新者", align=2, sort=14)
	public String getUpdateById() {
		return updateById;
	}

	public void setUpdateById(String updateById) {
		this.updateById = updateById;
	}

    public String getHaierUserId() {
        return haierUserId;
    }

    public void setHaierUserId(String haierUserId) {
        this.haierUserId = haierUserId;
    }

    public String getPurchaserName() {
		return purchaserName;
	}

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Purchaser getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Purchaser purchaser) {
        this.purchaser = purchaser;
    }

    @JsonIgnore
    public List<PurchaserRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<PurchaserRole> roleList) {
        this.roleList = roleList;
    }

    public List<String> getRoleIdList() {
        //List<String> roleIdList = Lists.newArrayList();
        for (PurchaserRole role : roleList) {
            roleIdList.add(role.getId());
        }
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
        roleList = Lists.newArrayList();
        for (String roleId : roleIdList) {
            PurchaserRole role = new PurchaserRole();
            role.setId(roleId);
            roleList.add(role);
        }
    }

    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     */
    public String getRoleNames() {
        return Collections3.extractToString(roleList, "name", ",");
    }
}