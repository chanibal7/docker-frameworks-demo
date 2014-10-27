<%@page contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1"%>
<html>
<script>
function TestEncoding()
      {
        var inputString=document.forms["TestEncodingForm"]["inputString"].value;
        var encodedInputString=escape(inputString);
        encodedInputString=encodedInputString.replace("+", "%2B");
        encodedInputString=encodedInputString.replace("/", "%2F");        
        document.forms["TestEncodingForm"]["encodedInputString"].value=encodedInputString;
      }


</script>
	<head>	
            <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
        </head>
<body>

<a href="InputSubmit.do">Click Here</a>

<a href="InputSubmit.cookieservlet?firstname=Ursulika...???+???+?????!+????????+?+????!">Click</a>
<a href="InputSubmit.cookieservlet?firstname=Ursulika...%D1%8D%D1%82%D0%BE+%D0%92%D0%B0%D1%88+%D0%B2%D1%8B%D0%BE%D0%B4!+%D0%A1%D1%82%D0%B0%D1%82%D1%83%D0%B9%D1%82%D0%B5+%D1%81+%D0%BD%D0%B0%D0%BC%D0%B8!">Click</a>

<br/>
<br/>
<br/>
<a href="InputSubmit.quartzservlet">Click here to make Quartz Job Scheduler Call</a> 

<br/>
<br/>
<br/>
<a href="mailPage.jsp">Click here to make java mail api Call</a>
<br/>
<br/>
<br/>
<a href="ThirdPage.jsp">Click here to Third Page</a>

<br/>
<br/>
<br/>
<a href="methodOverload.jsp">Click here to fill form for POJO Method overload servlet</a>
<br>
<form name="mailForm"  action="InputSubmit.do?oper1=value45&operationget=valueget1" method="POST">
		<center>
			Invoking struts action with params
		<br/><br/>
		<input name="test1" type="text" maxlength="50"value=""/><br/>
		<input name="test2" type="text" maxlength="50"value=""/><br/>
		<input name="test3" type="hidden" maxlength="50"value="data123-post123"/><br/>
		<input name="test4" type="text" maxlength="50"value=""/><br/>
		<input name="operation" type="text" maxlength="50"value=""/><br/>
		<input name="operation2" type="text" maxlength="50"value=""/><br/>
		<input name="operation3" type="text" maxlength="50"value=""/><br/>
		<input name="test5" type="text" maxlength="50"value=""/><br/>
		<input name="test6" type="text" maxlength="50"value=""/><br/>
		<input name="test7" type="text" maxlength="50"value=""/><br/>
		<input  type = "submit" name = "button1" value = " Send " />&nbsp;&nbsp;
		</center>
	</form>

<br>
<form name="mailForm"  action="InputSubmit.do" method="GET">
		<center>
			Invoking struts action with params using GET method
		<br/><br/>
		<input name="test1" type="text" maxlength="50"value=""/><br/>
		<input name="test2" type="text" maxlength="50"value=""/><br/>
		<input name="test3" type="hidden" maxlength="50"value="data123-post123"/><br/>
		<input name="test4" type="text" maxlength="50"value=""/><br/>
		<input name="operation" type="text" maxlength="50"value=""/><br/>
		<input name="operation2" type="text" maxlength="50"value=""/><br/>
		<input name="operation3" type="text" maxlength="50"value=""/><br/>
		<input name="test5" type="text" maxlength="50"value=""/><br/>
		<input name="test6" type="text" maxlength="50"value=""/><br/>
		<input name="test7" type="text" maxlength="50"value=""/><br/>
		<input  type = "submit" name = "button1" value = " Send " />&nbsp;&nbsp;
		</center>
	</form>
</body>
</html>


