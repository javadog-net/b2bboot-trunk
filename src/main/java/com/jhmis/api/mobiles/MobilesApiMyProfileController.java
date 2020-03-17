package com.jhmis.api.mobiles;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.JWTUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.service.UploadService;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAddressService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.UploadResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description 提供给移动端的 我的资料相关的接口
 * @Author t.c
 * @Date 2019-10-29
 */
@RestController
@RequestMapping(value = "/api/mobiles/myprofile")
public class MobilesApiMyProfileController extends BaseController {
    @Autowired
    PurchaserAddressService purchaserAddressService;
    @Autowired
    PurchaserAccountService purchaserAccountService;
    @Autowired
    PurchaserService purchaserService;
    @Autowired
    UploadService uploadService;

    /**
     * @Description 查看我的资料  需要传入token
     * @Return com.jhmis.common.json.AjaxJson
     * @Author t.c
     * @Date 2019-10-29
     */
    @RequestMapping(value = "/lookMyProfile")
    public AjaxJson lookMyProfile() {
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount == null) {
            return AjaxJson.fail("401", "请重新登录！");
        }
        purchaserAccount = purchaserAccountService.get(purchaserAccount.getId());
        purchaserAccount.setAvatar(purchaserAccountService.getAvatarById(purchaserAccount.getId()));
        purchaserAccount.setNickName(purchaserAccountService.getNicknameById(purchaserAccount.getId()));
        return AjaxJson.ok(purchaserAccount);
    }

    /**
     * @Description 修改我的资料 需要传入token
     * @Param
     * @Return
     * @Author t.c
     * @Date 2019-10-30
     */
    @RequestMapping(value = "updateMyProfile")
    public AjaxJson updateMyProfile(
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "realname", required = false) String realname,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "avatar", required = false) String avatar) {

        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if (account == null) {
            return AjaxJson.fail("401", "请重新登录！");
        }
        if (StringUtils.isNotBlank(mobile)) {
            account.setMobile(mobile);
        }
        if (StringUtils.isNotBlank(realname)) {
            account.setRealName(realname);
        }
        if (StringUtils.isNotBlank(email)) {
            account.setEmail(email);
        }
        if (StringUtils.isNotBlank(avatar)) {
            account.setAvatar(avatar);
        }
        purchaserAccountService.save(account);
        AjaxJson ret = new AjaxJson();
        // 先清空账户缓存
        PurchaserUtils.clearCache(account);
        // 再放入缓存
        PurchaserUtils.putCache(account);
        ret.put("account", account);
        ret.put("token", JWTUtils.sign(account.getId(), Global.USER_TYPE_PURCHASER));
        ret.put("msg", "修改成功");
        return ret;
    }

    /**
     * @Description 查看企业认证信息
     * @Param 传入token
     * @Return
     * @Author t.c
     * @Date 2019-10-30
     */
    @RequestMapping(value = "/lookCompanyInfo")
    public AjaxJson lookCompanyInfo() {
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if (account == null) {
            return AjaxJson.fail("401", "请重新登录！");
        }
        String purchaserId = account.getPurchaserId();
        Purchaser purchaser = purchaserService.get(purchaserId);
        return AjaxJson.ok(purchaser);
    }




    /**
     * @Description 修改企业认证信息
     * @Param
     * @Return
     * @Author t.c
     * @Date 2019-10-30
     */
    @RequestMapping(value = "/updateCompanyInfo")
    public AjaxJson updateCompanyInfo(
            @RequestParam(value = "contacts", required = false) String contacts,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "companyTel", required = false) String companyTel,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "licenceUrl", required = false) String licenceUrl,
            @RequestParam(value = "depart", required = false) String depart,
            @RequestParam(value = "provinceId", required = false) String provinceId,
            @RequestParam(value = "cityId", required = false) String cityId,
            @RequestParam(value = "districtId", required = false) String districtId,
            @RequestParam(value = "detailAddress", required = false) String detailAddress,
            @RequestParam(value = "industryId", required = false) String industryId,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "companyNum", required = false) String companyNum,
            @RequestParam(value = "companyNature", required = false) String companyNature) {

        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if (account == null) {
            return AjaxJson.fail("401", "请重新登录！");
        }
        Purchaser purchaser = account.getPurchaser();
        if (purchaser != null) {
            if (StringUtils.isNotBlank(contacts)) {
                purchaser.setContacts(contacts);
            }
            if (StringUtils.isNotBlank(mobile)) {
                purchaser.setMobile(mobile);
            }
            if (StringUtils.isNotBlank(companyTel)) {
                purchaser.setCompanyTel(companyTel);
            }
            if (StringUtils.isNotBlank(email)) {
                purchaser.setEmail(email);
            }
            if (StringUtils.isNotBlank(licenceUrl)) {
                purchaser.setLicenceUrl(licenceUrl);
            }
            if (StringUtils.isNotBlank(depart)) {
                purchaser.setDepart(depart);
            }
            if (StringUtils.isNotBlank(provinceId)) {
                purchaser.setProvinceId(provinceId);
            }
            if (StringUtils.isNotBlank(cityId)) {
                purchaser.setCityId(cityId);
            }
            if (StringUtils.isNotBlank(districtId)) {
                purchaser.setDistrictId(districtId);
            }
            if (StringUtils.isNotBlank(detailAddress)) {
                purchaser.setDetailAddress(detailAddress);
            }
            if (StringUtils.isNotBlank(industryId)) {
                purchaser.setIndustryId(industryId);
            }
            if (StringUtils.isNotBlank(companyName)) {
                purchaser.setCompanyName(companyName);
            }
            if (StringUtils.isNotBlank(companyNum)) {
                purchaser.setCompanyNum(companyNum);
            }
            if (StringUtils.isNotBlank(companyNature)) {
                purchaser.setCompanyNature(companyNature);
            }
        } else {
            return AjaxJson.fail("信息有误，请重新登录。");
        }
        purchaserService.save(purchaser);

        return AjaxJson.ok("修改成功！");

    }

    /**
     * @Description 上传头像 传token
     * @Param
     * @Return
     * @Author t.c
     * @Date 2019-10-30
     */
    @ApiOperation(notes = "uploadavatar", httpMethod = "POST", value = "上传头像", consumes = "multipart/*")
    @PostMapping(value = "/uploadavatar")
    public UploadResult uploadAvatar(HttpServletRequest request, @ApiParam(name = "任意", value = "头像上传") MultipartFile file) {
        UploadResult result = new UploadResult();
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount == null) {
            result.setState("上传失败");
            result.setSuccess(false);
            result.setMessage("请您重新登录！");
            return result;
        }
        try {
            AjaxJson ret = uploadService.uploadAvatarsApp(request, null, Global.USER_TYPE_AVATAR, purchaserAccount.getId());
            if (ret.isSuccess()) {
                List<AttInfo> attInfos = (List<AttInfo>) ret.getResult();
                if (attInfos != null && attInfos.size() > 0) {
                    AttInfo info = attInfos.get(0);
                    result.setSuccess(true);
                    result.setState("SUCCESS");
                    result.setExt(info.getAttExt());
                    result.setTitle(info.getAttName());
                    result.setSize(info.getAttSize());
                    result.setUrl(info.getAttUrl());
                    result.setOriginal(info.getAttName());
                } else {
                    result.setState("上传失败");
                    result.setSuccess(false);
                    result.setMessage("上传失败");
                }
            } else {
                result.setState(ret.getMsg());
                result.setSuccess(false);
                result.setMessage(ret.getMsg());
            }
        } catch (Exception e) {
            result.setState(e.getMessage());
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }


    /**
     * @return List<PurchaserAddress> 设置为默认的在前
     * @Description 查看我的地址 传token
     * @Author t.c
     * @Date 2019-10-29
     */
    @RequestMapping(value = "/lookMyAddress")
    public AjaxJson lookMyAddress() {
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount == null) {
            return AjaxJson.fail("401", "请重新登录！");
        }
        List<PurchaserAddress> list=new ArrayList<>();

        try {
            String purchaserId = purchaserAccount.getPurchaserId();
            PurchaserAddress purchaserAddress = new PurchaserAddress();
            purchaserAddress.setPurchaserId(purchaserId);
            list = purchaserAddressService.findList(purchaserAddress);
            if (list == null || list.size() == 0) {
                return AjaxJson.ok("暂时无地址，请新增地址。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.ok(list);
    }

    /**
     * @Description 保存地址 传入token
     * @Param PurchaserAddress 对象
     * String provinceid;省份id
     * String cityid;城市id
     * String countyid;地区id
     * String username;收货人姓名
     * String address;详细地址
     * String companyName 公司地址
     * String getMobilePhone;	电话号
     * String isdefault;是否设置为默认地址 1 是 0或空 否
     * @Author t.c
     * @Date 2019-10-29
     */
    @RequestMapping(value = "/saveMyAddress")
    public AjaxJson saveMyAddress(PurchaserAddress address) {

        if (StringUtils.isBlank(address.getCityId())) {
            return AjaxJson.fail("城市id不能为空！");
        }
        if (StringUtils.isBlank(address.getProvinceId())) {
            return AjaxJson.fail("省份id不能为空！");
        }
        if (StringUtils.isBlank(address.getDistrictId())) {
            return AjaxJson.fail("地区id不能为空！");
        }
        if(StringUtils.isBlank(address.getAreaInfo())){
            return AjaxJson.fail("省份，城市，市区 未传");
        }
        if (StringUtils.isBlank(address.getDetailAddress())) {
            return AjaxJson.fail("详细地址不能为空！");
        }
        if (StringUtils.isBlank(address.getMobilePhone())) {
            return AjaxJson.fail("收货人电话不能为空！");
        }
        if (StringUtils.isBlank(address.getTrueName())) {
            return AjaxJson.fail("收货人姓名不能为空！");
        }
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount == null) {
            return AjaxJson.fail("401", "请重新登录！");
        }
        try {
            String purchaserId = purchaserAccount.getPurchaserId();
            if (address.getIsDefault().equals("1")) {
                PurchaserAddress purchaserAddress = new PurchaserAddress();
                purchaserAddress.setPurchaserId(purchaserId);
                List<PurchaserAddress> list = purchaserAddressService.findList(purchaserAddress);
                if (list != null && list.size() > 0) {
                    for (PurchaserAddress pa : list) {
                        if (pa.getIsDefault().equals("1")) {
                            pa.setIsDefault("0");//修改 之前的 默认值
                            purchaserAddressService.save(pa);
                        }
                    }
                }
            }
            address.setPurchaserId(purchaserId);
            purchaserAddressService.save(address);//保存地址
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.ok("保存成功！");

    }


    /**
     * @Description 保存地址 传入token
     * @Param PurchaserAddress 对象
     * String id ;地址id
     * String provinceid;省份id
     * String cityid;城市id
     * String countyid;地区id
     * String username;收货人姓名
     * String address;详细地址
     * String companyName 公司地址
     * String getMobilePhone;	电话号
     * String isdefault;是否设置为默认地址 1 是 0或空 否
     * @Author t.c
     * @Date 2019-10-29
     */
    @RequestMapping(value = "/updateMyAddress")
    public AjaxJson updateMyAddress(PurchaserAddress address) {
        if(StringUtils.isBlank(address.getId())){
            return AjaxJson.fail("地址id不能为空");
        }
        if (StringUtils.isBlank(address.getCityId())) {
            return AjaxJson.fail("城市id不能为空！");
        }
        if (StringUtils.isBlank(address.getProvinceId())) {
            return AjaxJson.fail("省份id不能为空！");
        }
        if (StringUtils.isBlank(address.getDistrictId())) {
            return AjaxJson.fail("地区id不能为空！");
        }
        if(StringUtils.isBlank(address.getAreaInfo())){
            return AjaxJson.fail("省份，城市，市区 未传");
        }
        if (StringUtils.isBlank(address.getDetailAddress())) {
            return AjaxJson.fail("详细地址不能为空！");
        }
        if (StringUtils.isBlank(address.getMobilePhone())) {
            return AjaxJson.fail("收货人电话不能为空！");
        }
        if (StringUtils.isBlank(address.getTrueName())) {
            return AjaxJson.fail("收货人姓名不能为空！");
        }
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount == null) {
            return AjaxJson.fail("401", "请重新登录！");
        }
        try {
            String purchaserId = purchaserAccount.getPurchaserId();
            if (address.getIsDefault().equals("1")) {
                PurchaserAddress purchaserAddress = new PurchaserAddress();
                purchaserAddress.setPurchaserId(purchaserId);
                List<PurchaserAddress> list = purchaserAddressService.findList(purchaserAddress);
                if (list != null && list.size() > 0) {
                    for (PurchaserAddress pa : list) {
                        if (pa.getIsDefault().equals("1")) {
                            pa.setIsDefault("0");//修改 之前的 默认值
                            purchaserAddressService.save(pa);
                        }
                    }
                }
            }
            address.setPurchaserId(purchaserId);
            purchaserAddressService.save(address);//保存地址
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.ok("修改成功！");

    }



    /**
     * @Description 删除地址 传token
     * @Param 地址id
     * @Return
     * @Author t.c
     * @Date 2019-10-29
     */
    @RequestMapping(value = "/deleteMyAddress")
    public AjaxJson deleteMyAddress(String id) {
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount == null) {
            return AjaxJson.fail("401", "请重新登录！");
        }
        String purchaserId = purchaserAccount.getPurchaserId();
        PurchaserAddress address = new PurchaserAddress();
        address.setId(id);
        address.setPurchaserId(purchaserId);
        try {
            purchaserAddressService.delete(address);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return AjaxJson.ok("删除成功！");
    }

/**
*@Description 修改默认地址
*@Param  id
 *       tab 修改 值 1 或者 0
*@Return
*@Author t.c
*@Date 2019-11-19
*/
@RequestMapping(value="/updateisdefault")
    public AjaxJson updateIsDefault(String id,String tab){
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount == null) {
            return AjaxJson.fail("401", "请重新登录！");
        }
        String purchaserId = purchaserAccount.getPurchaserId();
        PurchaserAddress address = new PurchaserAddress();
        try {
            address= purchaserAddressService.get(id);
            if(null == address || null == address.getId()){
                return AjaxJson.ok("信息有误，请稍后再试！");
            }
            /*if("1".equals(tab)) {//说明要设置默认地址，查询库里是否有默认地址
                PurchaserAddress purchaserAddress = new PurchaserAddress();
                purchaserAddress.setPurchaserId(purchaserId);
                List<PurchaserAddress> listAddress = purchaserAddressService.findList(purchaserAddress);
                if (listAddress != null && listAddress.size() > 0) {
                    for (PurchaserAddress pa : listAddress) {
                        if (pa.getIsDefault().equals("1") && !pa.getId().equals(address.getId())) {
                            pa.setIsDefault("0");//修改 之前的 默认值
                            purchaserAddressService.save(pa);
                        }
                    }
                }

           }*/
            address.setIsDefault(tab);
            purchaserAddressService.save(address);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  AjaxJson.ok("修改成功！");

    }



}
