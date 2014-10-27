<%@page contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1"%>

<html>
<script>
function verify()
      {
        
      }

</script>
 
	<head>	
            <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
         
        </head>
<body>
	<form name="mailForm"  action="inputSubmit.mailservlet" method="post" onsubmit="return checkForm()">
		<center>
			Sending mail using javax.mail.Transport.send() method
		<br/><br/>
    
		<FONT SIZE=4><b>&nbsp;Please enter a valid mail id:</b></FONT>&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="emailId" type="text" maxlength="50"value="" onblur="verify();"/><br/>
		<FONT SIZE=4><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Enter the Subject:</b></FONT>&nbsp;&nbsp;&nbsp;&nbsp;
		<input Name="subject" Type="text" maxlength="50" Value="" /><br/>
		<FONT SIZE=4><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Enter the Body Content:</b></FONT>&nbsp;&nbsp;&nbsp;&nbsp;
		<input Name="content" Type="textarea" rows="3" cols="50" Value="" /><br/>
		<input  type = "submit" name = "button1" value = " Send " />&nbsp;&nbsp;<input  type = "reset" name = "button2" value = " clear " />
		</center>
	</form>
	<a href="index.jsp">back to Index page</a>
	<a href="ThirdPage.jsp">Go to Third Page</a>
</body>
</html>
