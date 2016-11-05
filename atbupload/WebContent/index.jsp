<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<!--<title> CS 370: Server Down Project 1</title >-->
        
        <style type="text/css">
			
			body{
				background-image:url(http://www.thenation.com/wp-content/uploads/2015/03/server_room_ss_img2.png);
				background-position:center;  
				background-size: cover; 
				background-repeat: no-repeat;
				background-attachment: fixed;
			}
			
			h1 {
			background-color: powderblue;
			color: 000000;
			font-weight: bold;
			font-size: 150%; 
			text-align: center;
			border-style:solid; 
			padding: 1em;
			}
			
			h2{
			font-size: 100%; 
			background-color: powderblue;
			color: 000000;
			font-weight: bold;
			text-align: center;
			border:3px; 
			border-style:solid; 
			padding: 1em;
			}
			
			h3 {
				text-align: center;
				background-color: powderblue;
				color: 000000;
			}
			
			p { 
				text-align: center;
			}
			
	   </style>  
        

	     
	</head>

<!--  background= "${pageContext.request.contextPath}/images/server_room_ss_img2.png -->
	
	<body onload="clearFields()">
	    <h1> CS 370: Server Down Project 1 </h1>
		<h2>Team Server Down: Servlet Project - Uploading a File</h2>

		<h3>By: Serena, Alester, and Ateeb</h3>

		<form name="myform" action="atbupload" method="post" enctype="multipart/form-data">
		<p style="background-color: powderblue; color: 000000">
			<strong>Select File to Upload:</strong>
		</p>
		<p>
			<input id="fileChosen" type="file" name="fileName" onchange="fileChosen_onchange()" multiple="true" />
		</p>
		<p>
			<textarea class="Text_Box_Invisible_Until_Selected" rows=15 cols=45
				id="Text_Box_Area">When a file is selected your text will appear...</textarea>
		</p>
		<br>
		<p>
			<input id="UpLoad_button" type="submit" value="Upload" disabled="true" onclick="clearText()"/>
		</p>
		<p>
			<button id="FileSize" name="btnUpload" type="button" onclick="View_File_Info_OnClick()"> File Information </button>
			<button id="FileHistory" name="btnFileHistory" type="submit" value="showFileHistory"> Upload History </button>
		</p>

 
		<!--  To not let someone press upload without choosing a file  -->
		<script type="text/javascript">

			function fileChosen_onchange() {
				document.getElementById("UpLoad_button").disabled = false;
			}
			
			function clearText(){
				document.getElementById("Text_Box_Area").value = "When a file is selected your text will appear...";
			}

			// doesn't work yet
			function clearFields() {
				document.getElementById("Text_Box_Area").value = "When a file is selected your text will appear...";
				document.getElementById("fileChosen").reset;	
			    var oldInput = document.getElementById(fileChosen);  
				oldInput.parentNode.replaceChild(oldInput.cloneNode(), oldInput);
				document.getElementById("fileChosen").reset;	
			}
		</script>

		<script type="text/javascript">
			function view_File_Before_Upload(evt) {
				    var f = evt.target.files[0]; 
				    if (f) {
				         var reader = new FileReader();
				         reader.onload = function(e) { 
				        var contents_of_file = e.target.result;		           
				        document.getElementById("Text_Box_Area").value = contents_of_file;
				    }
				    reader.readAsText(f);
				    } else {   alert("Cannot View this File...");  }
			}
			document.getElementById('fileChosen').addEventListener('change', view_File_Before_Upload, false);
		</script>

		<!--  If no file was selected when click "File Information" Nothing Inserted popup, else File Info popup  -->
		<script type="text/javascript">
		    function View_File_Info_OnClick() {
		      var file_popup_view = document.getElementById("fileChosen");
			   alert( file_popup_view.value == "" ? nothingInserted() : File_Info() );     
		    }
		</script>


		<!--  Nothing was Inserted Popup  -->
		<script type="text/javascript">
		function nothingInserted() {
			var x = "Nothing Was Selected... \n Please Select a File";
			return(x);
		}
		</script>

		<!--  Want to view file info  -->
		<script type="text/javascript">
		function File_Info(){
		    var name_of_file = document.getElementById('fileChosen').files[0].name;
		    var size_of_file = document.getElementById('fileChosen').files[0].size;
		    var type_of_file = document.getElementById('fileChosen').files[0].type;
		    var last_date_modified = document.getElementById('fileChosen').files[0].lastModifiedDate;
		    //return alert
		    return       ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + "\n"
		    	  				+ "Some File Information..." + "\n" 
		    	  			+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + "\n"
		    				+ "Name of File: " + name_of_file + "\n"
		    				+ "Size of File: " + size_of_file + " Bytes" + "\n"
		    				+ "Type of File: " + type_of_file + "\n"
		    				+ "Last Date Modified: " + last_date_modified
		    		);
		}
		</script>
</body>
</html>