<%@ page language="java" import="java.util.*,com.stu.model.*;"	pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath %>">
		<title>学生查询</title>
		<!-- 引入公共网页头部 -->
		<jsp:include page="/views/include_common.jsp"/>
		<script type="text/javascript">
			function pageClick(pageIndex){
				window.location.href = 'student?cmd=stulist&currentPage=' + pageIndex 
										+ '&stuName=' + $.trim($('txtStuName').val()) 
										+ '&stuNo=' + $.trim($('txtStuNo').val());
			}
			
			
			//删除学生信息
			function deleteStudent(_stuid){
				if(confirm('确认删除学生信息吗？删除学生信息会同时删除成绩信息。')){
					var param = { cmd : 'deletestudent' , stuid : _stuid };
					$.post('student',param,function(data){
						if(data && data.msg){
							alert(data.msg);
							if(data.code == 1){
								window.location.reload();//重新加载当前页面
							}
						}
					},'json');
				}
			}
			
		</script>
	</head>

	<body>
		<!-- 引入顶部 -->
		<jsp:include page="/views/top.jsp"/>

        <div class="wrapper">
            <div class="container">
                <div class="row">
                    <div class="span2">
                    	<!-- 引入左菜单 -->
                    	<jsp:include page="/views/left.jsp"/>
                    </div>
                    <!--/.span3-->
                    <div class="span10">
                        <div class="content">
                        	<!-- 页面右侧内容 -->
                        	<div class="module">
							<div class="module-head">
								<b>学生查询</b>
							</div>
							<div class="module-body table">
								<div class="dataTables_length">
									<form action="student" method="get" class="form-horizontal row-fluid">
										<div class="control-group" style="height:35px;">
											<input type="hidden" name="cmd" value="stulist"/>
											<label class="control-label" style="width:65px;"><span class="red">*</span>姓名：</label>
											<input class="span3" style="float:left;" type="text" id="txtStuName" name="stuName" value="${requestScope.stuName}" placeholder="填写姓名" autocomplete="off"/>
											<label class="control-label"  style="width:65px;"><span class="red">*</span>学号：</label>
											<input class="span3" style="float:left;" type="text" id="txtStuNo" name="stuNo" value="${requestScope.stuNo}" placeholder="填写学号" autocomplete="off"/>
											<button type="submit left" class="btn btn-small btn-info icon-search" style="height:auto;margin-left:10px;">查询</button>
										</div>
									</form>
								</div>
								<table cellpadding="0" cellspacing="0" border="0" class="datatable-1 table table-bordered table-striped	 display" width="100%">
									<thead>
										<tr>
											<th>标识ID</th>
											<th>姓名</th>
											<th>学号</th>
											<th>班级编号</th>
											<th>年级编号</th>
											<th>年龄</th>
											<th>住址</th>
											<th>性别</th>
											<th>操作</th>
										</tr>
									</thead>
									<!-- 迭代数据 -->
									<tbody>
						                  <c:forEach var="li" items="${requestScope.pageBean.pageData}">
						                      <tr>
										        <td>${li.stuid}</td>
										        <td>${li.stuName}</td>
										        <td>${li.stuNo}</td>
										        <td>${li.cno}</td>
										        <td>${li.gno}</td>
										        <td>${li.stuAge}</td>
										        <td>${li.stuAddr}</td>
										        <td>${li.stuSex}</td>
										        <td><a href="javascript:deleteStudent(${li.stuid});">删除</a></td>
						                      </tr>
						                  </c:forEach>
						          </tbody>
						          <tfoot>
							          <tr>
							              <td colspan="9" align="center">
							              		每页${requestScope.pageBean.pageSize}条 , 当前${requestScope.pageBean.currentPage}
							                  /${requestScope.pageBean.totalPage}
							                  	页     &nbsp;&nbsp;
							                  <a href="javascript:pageClick(1)">首页</a>
							                  <a href="javascript:pageClick(${requestScope.pageBean.prevPage})">上一页 </a>
							                  <a href="javascript:pageClick(${requestScope.pageBean.nextPage})">下一页 </a>
							                  <a href="javascript:pageClick(${requestScope.pageBean.lastPage})">末页</a>
							              </td>
							          </tr>
						          </tfoot>
								</table>
							</div>
						</div><!--/.module-->
                        </div>
                        <!--/.content-->
                    </div>
                    <!--/.span9-->
                </div>
            </div>
            <!--/.container-->
        </div>
        <!--/.wrapper-->
        
        <!-- 引入网页公共页脚 -->
        <jsp:include page="/views/footer.jsp"></jsp:include>
        
	</body>
</html>