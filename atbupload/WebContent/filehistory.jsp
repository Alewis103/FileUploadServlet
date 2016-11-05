<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Server File Upload History</title>
    </head>
    <body>
    <h1>Files:</h1>
         <p>${message}</p>
	<p> <button name="back_button" type="button" onclick=Last_Page() > Back </button> </p>  
	
	
	
	<script type="text/javascript">
	function Last_Page(){
		window.history.back();
	    //window.location.reload();
	}
	</script>
    </body>
</html>