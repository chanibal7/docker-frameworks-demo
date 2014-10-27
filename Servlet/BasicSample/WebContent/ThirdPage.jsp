<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/doclibcomman.js"></script>
<script language="javascript" type="text/javascript">

function alertWindow(){
 
	if(win != null && !win.closed){
		win.focus();
	}else{
		var cBuilderOverWriteButton = 'Alert window';
		
		win = window.open("","Alert window","width=350,height=120,scrollbars=no,toolbar=no,menubar=no,resizable=yes,top=350, left=450");
		
		win.document.write("<body>");
		win.document.write("<form>");
		win.document.write("<center><p> Hello world</p>");
	
	        win.document.write("</p>");
	        win.document.write(cBuilderOverWriteButton);
	        win.document.write("&nbsp;&nbsp;");
		win.document.write("</p></center>");
		win.document.write("</form>");
		win.document.write("</body></html>");
	}
}
 
</script>
 
</head>
 
 
<body>

 <ul id="nav" class="nowrap">
  <li id="toolsDIV" name="toolsDIV" >
	<a id="linkTolls" name ="linkTolls" href="javascript:void(0);"  onclick="javascript:alertWindow();">Alert Window</a></li>
 </ul>


<h3> Simple alert window test</h3>
</body>
</html>
        

