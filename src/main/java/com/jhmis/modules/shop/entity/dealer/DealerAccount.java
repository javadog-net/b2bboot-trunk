/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.dealer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jhmis.common.utils.Collections3;
import com.jhmis.common.utils.excel.fieldtype.RoleListType;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * 供应商账号管理Entity
 * @author tity
 * @version 2018-07-22
 */
@ApiModel(value="DealerAccount",description="供应商子账户对象")
public class DealerAccount extends DataEntity<DealerAccount> {
	
	private static final long serialVersionUID = 1L;
	private String dealerId;		// 供应商ID
	private String loginName;		// 登录名
	private String passwd;		// 登录密码
	private String realName;		// 姓名
	private String mobile;		// 手机号
	private String departName;		// 所在部门
	private String isAdmin;		// 是否是供应商主账号  0 否 1  是
	private String email;		// 邮箱
	private String isClosed;		// 子账号状态（0 正常 1 停用）
	private Date lastLoginDate;		// 最后登录时间
	private String createById;		// 创建者
	private String updateById;		// 更新者

    //只作查询
    private String dealerName;      //供应商名
    private String logoUrl;          //供应商头像
    private String newPassword;    //新密码
    private Dealer dealer;          //供应商对象
	private List<DealerRole> roleList = Lists.newArrayList(); // 拥有角色列表
    private List<String> roleIdList = Lists.newArrayList(); // 拥有角色id列表
	public DealerAccount() {
		super();
	}

	public DealerAccount(String id){
		super(id);
	}

	@ExcelField(title="供应商ID", align=2, sort=1)
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

    @Length(min=1, max=100, message="登录名长度必须介于 1 和 64 之间")
	@ExcelField(title="登录名", align=2, sort=2)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@JsonIgnore
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

    @Length(min=1, max=100, message="姓名长度必须介于 1 和 64 之间")
	@ExcelField(title="姓名", align=2, sort=3)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

    @Pattern(regexp="^1\\d{10}$",message="手机格式不正确")
	@ExcelField(title="手机号", align=2, sort=4)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="所在部门", align=2, sort=5)
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	@ExcelField(title="是否主账号", dictType="yes_no", align=2, sort=6)
	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

    @Email(message="邮箱格式不正确")
    @Length(min=0, max=200, message="邮箱长度必须介于 1 和 200 之间")
	@ExcelField(title="邮箱", align=2, sort=7)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="账号状态", dictType="yes_no", align=2, sort=8)
	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后登录时间", align=2, sort=9)
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@ExcelField(title="创建者", align=2, sort=10)
	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}
	
	@ExcelField(title="更新者", align=2, sort=11)
	public String getUpdateById() {
		return updateById;
	}

	public void setUpdateById(String updateById) {
		this.updateById = updateById;
	}

	@JsonIgnore
	public List<DealerRole> getRoleList() {
		return roleList;
	}

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setRoleList(List<DealerRole> roleList) {
		this.roleList = roleList;
	}

	public List<String> getRoleIdList() {
		//List<String> roleIdList = Lists.newArrayList();
		for (DealerRole role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
	    this.roleIdList = roleIdList;
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			DealerRole role = new DealerRole();
			role.setId(roleId);
			roleList.add(role);
		}
	}

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    /**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}
}