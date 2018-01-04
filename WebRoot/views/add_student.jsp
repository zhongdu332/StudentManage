<%@ page language="java" import="java.util.*,com.stu.model.*;"	pageEncoding="gbk"%>
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
		<title>����ѧ����Ϣ</title>
		<!-- ���빫����ҳͷ�� -->
		<jsp:include page="/views/include_common.jsp"/>
		
		<script type="text/javascript">
		
			//������� ���������false�򲻻��ύ������true�ύ��
			function formCheck(){
			
				//���ѧ������ֵ 
				var stuName = $.trim($('#txtStuName').val());
				if(stuName.length < 1)
				{
					alert('��������Ϊ�գ�');
					return false;
				}
				
				//�������ֵ�Ƿ�����ֵ
				var stuAge = $.trim($('#txtStuAge').val());
				if(/[^\d+]/gi.test(stuAge))
				{
					alert('������д����ֵ��');
					return false;
				}
			
				//���ѧ��ѧ��ֵ 
				var stuNo = $.trim($('#txtStuNo').val());
				if(stuNo.length < 1)
				{
					alert('ѧ�Ų���Ϊ�գ�');
					return false;
				}
				
				//���༶ֵ
				var cno = $.trim($('#txtCno').val());
				if(cno.length < 1)
				{
					alert('�༶����Ϊ�գ�');
					return false;
				}
				
				//����꼶ֵ
				var gno = $.trim($('#txtGno').val());
				if(gno.length < 1)
				{
					alert('�꼶����Ϊ�գ�');
					return false;
				}
				
				//Ĭ�Ϸ���true 
				return true;
			}
		</script>
	</head>

	<body>
		<!-- ���붥�� -->
		<jsp:include page="/views/top.jsp"/>

        <div class="wrapper">
            <div class="container">
                <div class="row">
                    <div class="span2">
                    	<!-- ������˵� -->
                    	<jsp:include page="/views/left.jsp"/>
                    </div>
                    <!--/.span3-->
                    <div class="span10">
                        <div class="content">
                        	<!-- ҳ���Ҳ����� -->
                        	
						<div class="module">
							<div class="module-head">
								<b>����ѧ��</b>
							</div>
							<div class="module-body">
									<form action="student" class="form-horizontal row-fluid" method="post" onsubmit="return formCheck();">
										
										
										<div class="control-group">
											<label class="control-label" for="basicinput"><span class="red">*</span>������</label>
											<div class="controls">
												<input class="span3" type="text" id="txtStuName" name="stuName" placeholder="��д����"/>
											</div>
										</div>

										<div class="control-group">
											<label class="control-label"><span class="red">*</span>�Ա�</label>
											<div class="controls">
												<label class="radio inline">
													<input type="radio" name="stuSex" value="1" checked="checked" />
													��
												</label> 
												<label class="radio inline">
													<input type="radio" name="stuSex" value="0" />
													Ů
												</label>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label" for="basicinput">���䣺</label>
											<div class="controls">
												<input class="span3" type="text" id="txtStuAge" name="stuAge" maxlength="2" placeholder="��д����"/>
											</div>
										</div>

										<div class="control-group">
											<label class="control-label" for="basicinput"><span class="red">*</span>ѧ�ţ�</label>
											<div class="controls">
												<input class="span3" type="text" id="txtStuNo" name="stuNo" placeholder="��дѧ��"/>
											</div>
										</div>

										<div class="control-group">
											<label class="control-label" for="basicinput"><span class="red">*</span>�༶��</label>
											<div class="controls">
												<input class="span3" type="text" id="txtCno" name="cno" placeholder="ѡ��༶"/>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label" for="basicinput"><span class="red">*</span>�꼶��</label>
											<div class="controls">
												<input class="span3" type="text" id="txtGno" name="gno" placeholder="��д�꼶"/>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label" for="basicinput">סַ��</label>
											<div class="controls">
												<input class="span8" type="text" id="txtStuAddr" name="stuAddr" placeholder="��дסַ" />
											</div>
										</div>

										<div class="control-group">
											<div class="controls">
												<input type="hidden" name="cmd" value="add"/>
												<button type="submit" class="btn btn-small btn-info">����</button>
											</div>
										</div>
										
									</form>
							</div>
						</div>
                        </div>
                        <!--/.content-->
                    </div>
                    <!--/.span9-->
                </div>
            </div>
            <!--/.container-->
        </div>
        <!--/.wrapper-->
        
        <!-- ������ҳ����ҳ�� -->
        <jsp:include page="/views/footer.jsp"></jsp:include>
        
        <jsp:include page="/views/message.jsp"></jsp:include>
	</body>

</html>