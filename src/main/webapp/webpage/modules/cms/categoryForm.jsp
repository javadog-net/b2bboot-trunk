<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>栏目管理</title>
    <meta name="decorator" content="default"/>
    <!-- SUMMERNOTE -->
    <%@include file="/webpage/include/summernote.jsp" %>
    <style>
        .column {
            position: absolute;
            top: 40%;
            float: left;
            width: 10%;
            font-size: 16px;
            font-weight: bolder;
            margin-left: 30px;
        }

        .border-hf {
            position: relative;
            border-bottom: 3px solid #2089c9;
            padding: 30px 60px 30px 30px;
            margin-bottom: 10px;
        }

        .right-column {
            width: 90%;
            float: right;
        }

        .col-sm-10 span label {
            margin: 0 10px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#name").focus();
            $("#inputForm").validate({
                rules: {
                    name: {remote: "${ctx}/cms/category/checkCategoryName?oldCategoryName=" + encodeURIComponent('${category.name}')},
                    categoryMark: {remote: "${ctx}/cms/category/checkCategoryMark?oldCategoryMark=" + encodeURIComponent('${category.categoryMark}')}
                },
                messages: {
                    name: {remote: "栏目名称已存在"},
                    categoryMark: {remote: "栏目标识已存在"}
                },
                submitHandler: function (form) {
                    form.submit();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>

<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="category" action="${ctx}/cms/category/save" method="post"
                               class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>
                        <div class="border-hf">
                            <div class="column">栏目：</div>
                            <div class="right-column">

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">上级栏目:</label>
                                    <div class="col-sm-10">
                                        <sys:treeselect id="category" name="parent.id" value="${category.parent.id}"
                                                        labelName="parent.name" labelValue="${category.parent.name}"
                                                        title="栏目" url="/cms/category/treeData" extId="${category.id}"
                                                        cssClass="form-control required"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">栏目模型:</label>
                                    <div class="col-sm-10">
                                        <form:select path="modelId" value="${category.modelId}" class="form-control ">
                                            <form:options items="${modelList}" itemLabel="name" itemValue="id"
                                                          htmlEscape="false"></form:options>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><font color="red">*</font>栏目名称:</label>
                                    <div class="col-sm-10">
                                        <form:input path="name" htmlEscape="false" maxlength="50"
                                                    class="form-control required"/>
                                    </div>
                                </div>
								<div class="form-group">
									<label class="col-sm-2 control-label">栏目icon:</label>
									<div class="col-sm-10">
										<form:input path="icon" htmlEscape="false" maxlength="50"
													class="form-control "/>
									</div>
								</div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">缩略图:</label>
                                    <div class="col-sm-10">
                                        <form:hidden path="thumb" htmlEscape="false" maxlength="255"
                                                     class="form-control input-xlarge"/>
                                        <sys:webupload input="thumb" type="file" multiple="false" maxWidth="480"
                                                       maxHeight="480"/>
                                        <span><font color="red" size="1">图片建议尺寸 650px * 480px</font></span>
                                    </div>
                                </div>
                                <c:if test="${category.parent.id !='0'}">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">是否继承上级栏目标识:</label>
                                        <div class="col-sm-10">
                                            <form:radiobuttons path="isExtend" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">页面标识:</label>
                                    <div class="col-sm-10">
                                        <form:hidden path="oldCategoryMark"
                                                     value="${category.categoryMark}"></form:hidden>
                                        <form:input path="categoryMark" htmlEscape="false" maxlength="200"
                                                    class="form-control"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">导航中显示:</label>
                                    <div class="col-sm-10">
                                        <form:radiobuttons path="isShow" items="${fns:getDictList('yes_no')}"
                                                           itemLabel="label" itemValue="value" htmlEscape="false"
                                                           class="i-checks required"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">外部链接:</label>
                                    <div class="col-sm-10">
                                        <form:input path="outUrl" htmlEscape="false" maxlength="255"
                                                    class="form-control"></form:input>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">描述:</label>
                                    <div class="col-sm-10">
                                        <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200"
                                                       class="form-control input-xxlarge"/>
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                        </div>
                        <div class="border-hf">
                            <div class="column">SEO：</div>
                            <div class="right-column">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">SEO关键字:</label>
                                    <div class="col-sm-10">
                                        <form:input path="tdkKeywords" htmlEscape="false" class="form-control "/>
                                        <span class="help-inline">填写描述及关键字，有助于搜索引擎优化</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">SEO标题:</label>
                                    <div class="col-sm-10">
                                        <form:input path="tdkTitle" htmlEscape="false" class="form-control "/>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">SEO描述：</label>
                                    <div class="col-sm-10">
                                        <form:input path="tdkDescp" htmlEscape="false" class="form-control "/>
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                        </div>
                        <div class="border-hf">
                            <div class="column">模板：</div>
                            <div class="right-column">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">栏目模板：</label>
                                    <div class="col-sm-10">
                                        <form:select path="categoryTemplate" value="${category.categoryTemplate}"
                                                     class="form-control ">
                                            <form:option value="">--请选择--</form:option>
                                            <form:options items="${indexList}" htmlEscape="false"></form:options>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">列表页模板：</label>
                                    <div class="col-sm-10">
                                        <form:select path="listTemplate" value="${category.listTemplate}"
                                                     class="form-control ">
                                            <form:option value="">--请选择--</form:option>
                                            <form:options items="${list}" htmlEscape="false"></form:options>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">内容模板：</label>
                                    <div class="col-sm-10">
                                        <form:select path="contentTemplate" value="${category.contentTemplate}"
                                                     class="form-control ">
                                            <form:option value="">--请选择--</form:option>
                                            <form:options items="${contentList}" htmlEscape="false"></form:options>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">手机栏目模板：</label>
                                    <div class="col-sm-10">
                                        <form:input path="mcategoryTemplate" htmlEscape="false" class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">手机列表页模板：</label>
                                    <div class="col-sm-10">
                                        <form:input path="mlistTemplate" htmlEscape="false" class="form-control "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">手机文章内容模板：</label>
                                    <div class="col-sm-10">
                                        <form:input path="mcontentTemplate" htmlEscape="false" class="form-control "/>
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                        </div>
                        <div class="border-hf" style="border-bottom: none">
                            <div class="column">评论：</div>
                            <div class="right-column">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否允许匿名评论：</label>
                                    <div class="col-sm-10">
                                        <form:radiobuttons path="isAnonymous" items="${fns:getDictList('yes_no')}"
                                                           itemLabel="label" itemValue="value" htmlEscape="false"
                                                           class="i-checks "/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否允许评论：</label>
                                    <div class="col-sm-10">
                                        <form:radiobuttons path="allowComment" items="${fns:getDictList('yes_no')}"
                                                           itemLabel="label" itemValue="value" htmlEscape="false"
                                                           class="i-checks "/>
                                    </div>
                                </div>
                            </div>
                            <div style="clear: both"></div>
                        </div>
                        <div style="clear: both"></div>
                        <c:if test="${fns:hasPermission('cms:navigation:edit') || isAdd}">
                            <div class="col-lg-3"></div>
                            <div class="col-lg-6">
                                <div class="form-group text-center">
                                    <div>
                                        <shiro:hasPermission name="cms:category:edit"><input id="btnSubmit"
                                                                                             class="btn btn-primary"
                                                                                             type="submit"
                                                                                             value="保 存"/>&nbsp;</shiro:hasPermission>
                                        <input id="btnCancel" class="btn" type="button" value="返 回"
                                               onclick="history.go(-1)"/>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>