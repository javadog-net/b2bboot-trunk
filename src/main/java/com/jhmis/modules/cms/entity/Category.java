/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.TreeEntity;
import com.jhmis.modules.cms.utils.CmsUtils;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Map;

/**
 * 栏目管理Entity
 * @author lydia
 * @version 2019-09-02
 */
public class Category extends TreeEntity<Category> {

	private static final long serialVersionUID = 1L;
	private String modelId;		// 模型ID
	private String child;		// 子级栏目id
	private String arrChildid;		// 所有父级ID（多个id之间逗号分隔）
	private String name;		// 栏目名称
	private String dirName;		// 生成栏目的路径名
	private String categoryMark;		// 栏目标识
	private String oldCategoryMark;  //编辑栏目信息时使用
	private String parentDir;		// 上级目录（完整目录）
	private String isShow;		// 是否在导航显示 1 是 0 否
	private Integer sort;		// 排序
	private String thumb;		// 栏目图片
	private String isTargetBlank;		// 是否在新窗口中打开（0否1是）
	private String tdkKeywords;		// 关键字
	private String tdkTitle;		// 标题
	private String tdkDescp;		// 栏目描述
	private String categoryTemplate;		// 栏目模板
	private String listTemplate;		// 列表页模板
	private String contentTemplate;		// 文章内容模板
	private String mcategoryTemplate;		// 手机栏目模板
	private String mlistTemplate;		// 手机列表页模板
	private String mcontentTemplate;		// 手机文章内容模板
	private String isAnonymous;		// 是否允许匿名评论
	private String allowComment;		// 是否允许评论
	private String modelName;		// 模型名称
	private List<Category> childList = Lists.newArrayList(); 	// 拥有子分类列表
	private String parId;
	private String parentIds;  //所有上级栏目信息
	private String icon;

	private String folder;//静态页面目录
	private  String pageUrl; //页面地址
	private String isExtend; //是否继承上级页面标识
	private String outUrl; //外部链接

	private String sortType; //排序方式（up,down）非数据库字段，只做判断
	private String createDateStr;//n 添加时间
	private Map<String,Object> data;

	private String[] categoryIds; //仅作查询使用

	private int maxpage;//允许生成的最大页数

	public Category(){
		super();
		this.isShow = Global.HIDE;
		this.allowComment = Global.NO;
		this.isAnonymous = Global.NO;
	}

	public Category(String id){
		this();
		this.id = id;
	}
	
	public Category(String id, Site site){
		this();
		this.id = id;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	@Length(min=0, max=100)
	@Override
	public String getName() {
		if("0".equals(id)){
			return "顶级栏目";
		} else {
			return name;
		}
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getArrChildid() {
		return arrChildid;
	}

	public void setArrChildid(String arrChildid) {
		this.arrChildid = arrChildid;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getCategoryMark() {
		return categoryMark;
	}

	public void setCategoryMark(String categoryMark) {
		this.categoryMark = categoryMark;
	}

	public String getParentDir() {
		return parentDir;
	}

	public void setParentDir(String parentDir) {
		this.parentDir = parentDir;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	@Override
	public Integer getSort() {
		return sort;
	}

	@Override
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getIsTargetBlank() {
		return isTargetBlank;
	}

	public void setIsTargetBlank(String isTargetBlank) {
		this.isTargetBlank = isTargetBlank;
	}

	public String getTdkKeywords() {
		return tdkKeywords;
	}

	public void setTdkKeywords(String tdkKeywords) {
		this.tdkKeywords = tdkKeywords;
	}

	public String getTdkTitle() {
		return tdkTitle;
	}

	public void setTdkTitle(String tdkTitle) {
		this.tdkTitle = tdkTitle;
	}

	public String getTdkDescp() {
		return tdkDescp;
	}

	public void setTdkDescp(String tdkDescp) {
		this.tdkDescp = tdkDescp;
	}

	public String getCategoryTemplate() {
		return categoryTemplate;
	}

	public void setCategoryTemplate(String categoryTemplate) {
		this.categoryTemplate = categoryTemplate;
	}

	public String getListTemplate() {
		return listTemplate;
	}

	public void setListTemplate(String listTemplate) {
		this.listTemplate = listTemplate;
	}

	public String getContentTemplate() {
		return contentTemplate;
	}

	public void setContentTemplate(String contentTemplate) {
		this.contentTemplate = contentTemplate;
	}

	public String getMcategoryTemplate() {
		return mcategoryTemplate;
	}

	public void setMcategoryTemplate(String mcategoryTemplate) {
		this.mcategoryTemplate = mcategoryTemplate;
	}

	public String getMlistTemplate() {
		return mlistTemplate;
	}

	public void setMlistTemplate(String mlistTemplate) {
		this.mlistTemplate = mlistTemplate;
	}

	public String getMcontentTemplate() {
		return mcontentTemplate;
	}

	public void setMcontentTemplate(String mcontentTemplate) {
		this.mcontentTemplate = mcontentTemplate;
	}

	public String getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public String getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(String allowComment) {
		this.allowComment = allowComment;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List<Category> getChildList() {
		return childList;
	}

	public void setChildList(List<Category> childList) {
		this.childList = childList;
	}

	public static void sortList(List<Category> list, List<Category> sourcelist, String parentId){
		for (int i=0; i<sourcelist.size(); i++){
			Category e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					Category child = sourcelist.get(j);
					if (child.getParent()!=null && child.getParent().getId()!=null
							&& child.getParent().getId().equals(e.getId())){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}
	
	public String getIds() {
		return (this.getParentIds() !=null ? this.getParentIds().replaceAll(",", " ") : "") 
				+ (this.getId() != null ? this.getId() : "");
	}

	public boolean isRoot(){
		return isRoot(this.id);
	}

	public static boolean isRoot(String id){
		return id != null && id.equals("0");
	}

   	public String getUrl() {
        return CmsUtils.getUrlDynamic(this);
   	}
   	
	public String getImageSrc() {
   		String img = CmsUtils.formatImageSrcToWeb(this.thumb);
   		if(StringUtils.isBlank(img)) return img;
   		img = img.replace("/_thumbs", "");
        return img;
   	}

	public String getOldCategoryMark() {
		return oldCategoryMark;
	}

	public void setOldCategoryMark(String oldCategoryMark) {
		this.oldCategoryMark = oldCategoryMark;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getPageUrl() {
		if(StringUtils.isNotEmpty(outUrl)){
			pageUrl = outUrl;
		}else if(StringUtils.isNotEmpty(dirName)){
			pageUrl=dirName;
		}else {
			pageUrl=getFolder();
		}
		return pageUrl;
	}

	/**
	 * 静态页面路径
	 * @return
	 */
	public String getFolder() {
		if(StringUtils.isNotEmpty(categoryMark)){
			folder = categoryMark;
		}else if(StringUtils.isNotEmpty(dirName)) {
			folder = dirName;
		}else {
			folder = id;
		}
		return folder;
	}

	public String getParId() {
		return parId;
	}

	public void setParId(String parId) {
		this.parId = parId;
	}

	@Override
	public String getParentIds() {
		return parentIds;
	}

	@Override
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getOutUrl() {
		return outUrl;
	}

	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}

	public String getIsExtend() {
		return isExtend;
	}

	public void setIsExtend(String isExtend) {
		this.isExtend = isExtend;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String[] getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	public int getMaxpage() {
		return maxpage;
	}

	public void setMaxpage(int maxpage) {
		this.maxpage = maxpage;
	}
}