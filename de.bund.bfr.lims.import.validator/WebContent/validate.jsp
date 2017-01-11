<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Einsendebogen Portal</title>
        <script src="js/dropzone.js"></script>
        <link rel="stylesheet" href="https://rawgit.com/enyo/dropzone/master/dist/dropzone.css">
    </head>
    <body>
    
        <script>
	        // myDropzone is the configuration for the element that has an id attribute
	        // with the value my-dropzone (or myDropzone)
	        Dropzone.options.myDropzone = {
	          init: function() {
	            this.on("success", function(file, responseText) {
	            	console.log(responseText.length);
                    if (responseText.length > 2) {
                        addText(file.previewTemplate, responseText);
                        //file.previewTemplate.appendChild(document.createTextNode(responseText));
                        file.previewElement.classList.add("dz-error");
                        file.previewElement.querySelector("[data-dz-errormessage]").textContent = "error";//responseText;
                    }  
	            	/*
                    file.previewTemplate.appendChild(document.createTextNode(responseText));    
                    var str = responseText.valueOf();
                    file.previewTemplate.appendChild(document.createTextNode("<br>"+str));   
                    if (0 == str.length) file.previewTemplate.appendChild(document.createTextNode("ss"));   
                    */
                    /*
	            	var str = responseText.valueOf();
                    if (!responseText || 0 === str.length) {
                        file.previewTemplate.appendChild(document.createTextNode(responseText));                        
                    }
                    else {
                        file.previewTemplate.appendChild(document.createTextNode(responseText));                        
                        file.previewElement.classList.add("dz-error");
                        file.previewElement.querySelector("[data-dz-errormessage]").textContent = responseText;
                    }
                    */
	            });
	            //this.on("complete", function(file) {
	            //    this.removeFile(file);
	            //});
	          }
	        };
	        
	        function addText(node,text){     
	            var t=text.split(/\s*<br ?\/?>\s*/i),
	                i;
	            if(t[0].length>0){         
	              node.appendChild(document.createTextNode(t[0]));
	            }
	            for(i=1;i<t.length;i++){
	               node.appendChild(document.createElement('BR'));
	               if(t[i].length>0){
	                 node.appendChild(document.createTextNode(t[i]));
	               }
	            } 
	        } 	        
        </script>
    
    
        <section>
            <div id="dropzone">
                <form method="post" action="result" enctype="multipart/form-data" class="dropzone needsclick" id="my-dropzone">

	            <div class="dz-message needsclick">
	                W�hle deinen Einsendebogen oder ziehe ihn hierauf<br />
	            </div>

                </form>
            </div>
        </section>
    </body>
</html>