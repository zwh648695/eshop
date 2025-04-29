<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>註冊會員</title>

	<style>
	    .error { 
	    	color: red; 
	    	font-size: 14px; 
	    }
	    
	    .required-star {
	        color: red;
	        font-weight: bold;
	    }
	    
	    /* ✅ 欄位紅框樣式 */
		input.invalid {
			border: 1px solid red;
			background-color: #ffecec;
		}
		
		input.invalid::placeholder {
			color: red;
			font-style: italic;
		}
	</style>
	
</head>
<body>

	<h2>註冊新帳號</h2>
	<hr>

	<!-- 顯示全域錯誤訊息 -->
	<s:if test="hasActionErrors()">
	    <div class="error">
	        <s:actionerror />
	    </div>
	</s:if>

	<s:form action="register" method="post" theme="simple">
        <table text-align="center">

            <tr>
                <td>帳號：<span class="required-star" title="此欄位為必填">*</span></td>
                <td>
                    <s:textfield name="username" required="true" />
                </td>
                <td>
                    <s:if test="fieldErrors.containsKey('username')">
                        <span class="error">
                        	<s:fielderror fieldName="username" />
                        </span>
                    </s:if>
                </td>
            </tr>

            <tr>
                <td>密碼：<span class="required-star" title="此欄位為必填">*</span></td>
                <td>
                    <s:password name="password" id="password" required="true" />
                </td>
                <td>
                    <s:if test="fieldErrors.containsKey('password')">
                        <span class="error">
                        	<s:fielderror fieldName="password" />
                        </span>
                    </s:if>
                </td>
            </tr>

            <tr>
                <td>E-Mail：<span class="required-star" title="此欄位為必填">*</span></td>
                <td>
                    <s:textfield name="email" id="email" required="true" />
                </td>
                <td>
                    <s:if test="fieldErrors.containsKey('email')">
                        <span class="error">
                        	<s:fielderror fieldName="email" />
                        </span>
                    </s:if>
                </td>
            </tr>

            <tr>
                <td>姓名：</td>
                <td>
                    <s:textfield name="fullName" />
                </td>
                <td>
                    <s:if test="fieldErrors.containsKey('fullName')">
                        <span class="error">
                        	<s:fielderror fieldName="fullName" />
                        </span>
                    </s:if>
                </td>
            </tr>

            <tr>
                <td>電話：</td>
                <td>
                    <s:textfield name="phone" id="phone" />
                </td>
                <td>
                    <s:if test="fieldErrors.containsKey('phone')">
                        <span class="error">
                        	<s:fielderror fieldName="phone" />
                        </span>
                    </s:if>
                </td>
            </tr>

            <tr>
                <td>地址：</td>
                <td>
                    <s:textarea name="address" rows="3" cols="20" />
                </td>
            </tr>

            <tr>
                <td colspan="3" style="padding-top: 5px;">
                    <div>
                        <s:submit value="送出註冊" />
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        		已有帳號？<a href="login" style="text-decoration:none;">登入</a>
                    </div>
                </td>
            </tr>


        </table>
    </s:form>
    
 	<!-- <script src="${pageContext.request.contextPath}/public/script.js"></script> -->
 	<script src="<c:url value='/public/js/script.js' />"></script>
	

</body>
</html>