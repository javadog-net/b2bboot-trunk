<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>招投标管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					jh.loading();
					$("input[name='projectContent']").val($('#projectContent').summernote('code'));//取富文本的值
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
				//富文本初始化
			$('#projectContent').summernote({
				height: 300,                
                lang: 'zh-CN'
            });
	        $('#startTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#stopTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/bid/bidInfoDetail"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="bidInfoDetail" action="${ctx}/bid/bidInfoDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">项目名称：</label>
					<div class="col-sm-10">
						<form:input path="projectName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目所属公司的名称：</label>
					<div class="col-sm-10">
						<form:input path="companyName" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目所属公司的类型：</label>
					<div class="col-sm-10">
						<form:select path="companyType" class="form-control ">
							<form:option value="" label="请选择公司类型"/>
							<form:option value="政府" label="政府" />
							<form:option value="商业" label="商业" />
							<form:option value="教育" label="教育" />
							<form:option value="军队" label="军队" />
							
							
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目所属的行业：</label>
					<div class="col-sm-10">
						<form:select path="projectIndustry" class="form-control ">
							<%-- <form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="11" htmlEscape="false"/>
							<option selected="true" value="">请选择行业</option>
							 --%>
							<form:option value="" label="请选择行业"/>
							<form:option value="03fb574a984c4dfc97daa26caccd19e8" label="金融业"/>
							<form:option value="0a7995e408ff42e4918708cad7bbd75b" label="公共管理、社会保障和社会组织"/>
							<form:option value="1bd6dbbbf5804aeaaae8dd147ac3a52d" label="制造业"/>
							<form:option value="2439f6cc92e44487b369ea12941ab65e" label="农、林、牧、副、渔业"/>
							<form:option value="3b7a81cc1e7843a6b2ef5d827ed807cc" label="居民服务、修理和其他服务业"/>
							<form:option value="410db94684b848afaae791256f5821ad" label="水利、环境和公共设施管理业"/>
							<form:option value="5a702e9564de444dba1b0e9dc0978cfe" label="批发和零售业"/>
							<form:option value="5e7bf828512f4b788996e068676650a0" label="国际组织"/>
							<form:option value="6838ddf28b0345f2a99b9be4f7970706" label="租赁和商务服务业"/>
							<form:option value="6ca38513b27046dabb1093dfbdd9d1f0" label="公寓"/>
							<form:option value="7c80dabade8d48e3a94b83a29eda7cd8" label="采矿业"/>
							<form:option value="89d67bf23a4c4a169b5ef804256027f2" label="房地产业"/>
							<form:option value="985ac06e37e54d3da76266cde7e99efb" label="科学研究和技术服务业"/>
							<form:option value="98b5598b8d97441aba23fef199e9fdd8" label="文化、体育和娱乐业"/>
							<form:option value="a159fb1c6e5f49188af4c29b3a96295b" label="建筑业"/>
							<form:option value="ac6e3d7374934b97a8d57363fd25531b" label="卫生和社会工作"/>
							<form:option value="bf1b86f51f344841a7b3566eb4497793" label="信息传输、软件和信息技术服务业"/>
							<form:option value="c8b5c6e770a44495891995910b3b352f" label="住宿和餐饮业"/>
							<form:option value="cd874e036baf4dfa96a7c6ade6fe6dd8" label="电力、热力、燃气及水生产和供应业"/>
							<form:option value="dc83c1ee80714ab9b602cf43844ec845" label="教育"/>
							<form:option value="e379f05895be46109dca40c30a3b7b6f" label="交通运输、仓储和邮政业"/>
							<form:option value="e379f05895be46109dca40c30a3b7b70" label="金属制品、机械和设备修理业"/>
							<form:option value="e379f05895be46109dca40c30a3b7b71" label="其他"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目状态：</label>
					<div class="col-sm-10">
						<form:select path="projectStatus" class="form-control ">
							<form:option value="" label="请选择项目状态"/>
							<form:option value="筹建" label="筹建" />
							<form:option value="在建" label="在建" />
							<form:option value="完工" label="完工" />
							
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目的内容：</label>
					<div class="col-sm-10">
                        <input type="hidden" name="projectContent"/>
						<div id="projectContent">
                          ${fns:unescapeHtml(bidInfoDetail.projectContent)}
                        </div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目的预算：</label>
					<div class="col-sm-10">
						<form:input path="projectBudget" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目的性质：</label>
					<div class="col-sm-10">
						<form:select path="projectProperties" class="form-control ">
							<form:option value="" label="请选择项目的性质"/>
							<form:option value="新建" label="新建" />
							<form:option value="改扩建" label="改扩建" />
							
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">开工时间：</label>
					<div class="col-sm-10">
						<p class="input-group">
							<div class='input-group form_datetime' id='startTime'>
			                    <input type='text'  name="startTime" class="form-control"  value="<fmt:formatDate value="${bidInfoDetail.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">竣工时间：</label>
					<div class="col-sm-10">
						<p class="input-group">
							<div class='input-group form_datetime' id='stopTime'>
			                    <input type='text'  name="stopTime" class="form-control"  value="<fmt:formatDate value="${bidInfoDetail.stopTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
			            </p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">建筑面积：</label>
					<div class="col-sm-10">
						<form:input path="buildArea" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">装修情况：</label>
					<div class="col-sm-10">
						<form:select path="redecorateCondition" class="form-control ">
							<form:option value="" label="请选择项目的装修情况"/>
							<form:option value="精装修" label="精装修" />
							<form:option value="简装修" label="简装修" />
							<form:option value="毛坯" label="毛坯" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">装修标准：</label>
					<div class="col-sm-10">
						<form:textarea path="redecorateStand" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所属地区：</label>
					<div class="col-sm-10">
				<div class=" input-group" style=" width: 100%;">
					  <form:input path="belongArea" htmlEscape="false"  class="" data-toggle="city-picker" style="height: 34px;"/>
				</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目所需要的产品：</label>
					<div class="col-sm-10">
						<form:input path="projectNeedProduct" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目的地址：</label>
					<div class="col-sm-10">
						<form:input path="projectAddress" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目所属公司联系人1：</label>
					<div class="col-sm-10">
						<form:input path="bidConcat1" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">项目所属公司联系人1手机号：</label>
					<div class="col-sm-10">
						<form:input path="bidConcat1Phone" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<c:if test="${bidInfoDetail.bidConcat1Position !=null &&bidInfoDetail.bidConcat1Position!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目所属公司联系人1职位：</label>
						<div class="col-sm-10">
							<form:input path="bidConcat1Position" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidConcat1Tel !=null &&bidInfoDetail.bidConcat1Tel!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目所属公司联系人1固话：</label>
						<div class="col-sm-10">
							<form:input path="bidConcat1Tel" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidConcat2 !=null &&bidInfoDetail.bidConcat2!='' }" >
				<div class="form-group">
					<label class="col-sm-2 control-label">项目所属公司联系人2：</label>
					<div class="col-sm-10">
						<form:input path="bidConcat2" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidConcat2Phone !=null &&bidInfoDetail.bidConcat2Phone!='' }" >
				<div class="form-group">
					<label class="col-sm-2 control-label">项目所属公司联系人2手机号：</label>
					<div class="col-sm-10">
						<form:input path="bidConcat2Phone" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidConcat2Position !=null &&bidInfoDetail.bidConcat2Position!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目所属公司联系人2职位：</label>
						<div class="col-sm-10">
							<form:input path="bidConcat2Position" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidConcat2Tel !=null &&bidInfoDetail.bidConcat2Tel!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目所属公司联系人2固话：</label>
						<div class="col-sm-10">
							<form:input path="bidConcat2Tel" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidConcat3 !=null &&bidInfoDetail.bidConcat3!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目所属公司联系人3：</label>
						<div class="col-sm-10">
							<form:input path="bidConcat3" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidConcat3Phone !=null &&bidInfoDetail.bidConcat3Phone!='' }" >
				<div class="form-group">
					<label class="col-sm-2 control-label">项目所属公司联系人3手机号：</label>
					<div class="col-sm-10">
						<form:input path="bidConcat3Phone" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidConcat3Position !=null &&bidInfoDetail.bidConcat3Position!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目所属公司联系人3职位：</label>
						<div class="col-sm-10">
							<form:input path="bidConcat3Position" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidConcat3Tel !=null &&bidInfoDetail.bidConcat3Tel!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目所属公司联系人3固话：</label>
						<div class="col-sm-10">
							<form:input path="bidConcat3Tel" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>  
				<!--项目设计公司1  -->
				<c:if test="${bidInfoDetail.bidDesignConcat1 !=null &&bidInfoDetail.bidDesignConcat1!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目设计公司联系人1：</label>
						<div class="col-sm-10">
							<form:input path="bidDesignConcat1" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidDesignConcat1Phone !=null &&bidInfoDetail.bidDesignConcat1Phone!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目设计公司联系人1手机号：</label>
						<div class="col-sm-10">
							<form:input path="bidDesignConcat1Phone" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidDesignConcatPositon !=null &&bidInfoDetail.bidDesignConcatPositon!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目设计公司联系人1职位：</label>
						<div class="col-sm-10">
							<form:input path="bidDesignConcatPositon" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidDesignConcat1Tel !=null &&bidInfoDetail.bidDesignConcat1Tel!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目设计公司联系人1固话：</label>
						<div class="col-sm-10">
							<form:input path="bidDesignConcat1Tel" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				
				<!--项目 设计公司 2 -->
				<c:if test="${bidInfoDetail.bidDesignConcat2 !=null &&bidInfoDetail.bidDesignConcat2!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目设计公司联系人2：</label>
						<div class="col-sm-10">
							<form:input path="bidDesignConcat2" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidDesignConcat2Phone !=null &&bidInfoDetail.bidDesignConcat2Phone!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目设计公司联系人2手机号：</label>
						<div class="col-sm-10">
							<form:input path="bidDesignConcat2Phone" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidDesignConcat2Position !=null &&bidInfoDetail.bidDesignConcat2Position!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目设计公司联系人2职位：</label>
						<div class="col-sm-10">
							<form:input path="bidDesignConcat2Position" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidDesignConcat2Tel !=null &&bidInfoDetail.bidDesignConcat2Tel!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目设计公司联系人2固话：</label>
						<div class="col-sm-10">
							<form:input path="bidDesignConcat2Tel" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				
				<!-- 项目施工公司 -->
				
				<c:if test="${bidInfoDetail.bidBuildConcat1 !=null &&bidInfoDetail.bidBuildConcat1!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目施工公司联系人1：</label>
						<div class="col-sm-10">
							<form:input path="bidBuildConcat1" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidBuildConcat1Phone !=null &&bidInfoDetail.bidBuildConcat1Phone!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目施工公司联系人1手机号：</label>
						<div class="col-sm-10">
							<form:input path="bidBuildConcat1Phone" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidBuildConcat1Positon !=null &&bidInfoDetail.bidBuildConcat1Positon!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目施工公司联系人1职位：</label>
						<div class="col-sm-10">
							<form:input path="bidBuildConcat1Positon" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				<c:if test="${bidInfoDetail.bidBuildConcat1Tel !=null &&bidInfoDetail.bidBuildConcat1Tel!='' }" >
					<div class="form-group">
						<label class="col-sm-2 control-label">项目施工公司联系人1固话：</label>
						<div class="col-sm-10">
							<form:input path="bidBuildConcat1Tel" htmlEscape="false"    class="form-control "/>
						</div>
					</div>
				</c:if>
				
		<c:if test="${fns:hasPermission('bid:bidInfoDetail:list') || isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
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