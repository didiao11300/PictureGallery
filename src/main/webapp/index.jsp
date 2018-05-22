<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
	<title>Tags</title>
	
		<style>
			  h1 {font-style: italic; text-align: center;}
			  h1:HOVER {font-style: italic; color: blue;}
			 
			  .fullImage{width: 100%; height: 100%}
			  .thumbsArea{text-align: center}
			  .tagButton{padding-top: 5px; padding-bottom: 5px; background-color:#768d87; color: white; font-size: 15px; color:#ffffff; font-family:Arial; font-weight: bold; border-radius:5px; border:1px solid #566963; text-shadow:0px -1px 0px #2b665e;}
			  .imageInfo{background-color: #66FF66; font-weight: bold; font-size: 1.2em; text-align: center; font-variant: small-caps;}
		</style>
		
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script type="text/javascript"><!--		
		  
			function getTags() {
				$.ajax(
			  	{
					url: "servlets/tags",
				 	dataType: "json",
				 	success: function(data) {
						var tags = data; // već je JSON zbog dataType gore...
						var html = "";
						var len = tags.length;
						
						if(len == 0) {
							html = "No results...";
						} else {
							for(var i = 0; i < len; i++) {
								html += "<button onclick=getImagesForTag('" + tags[i] + "') class=\"tagButton\">" + tags[i] + "</button>";
							}
						}
						
						$("#imageInfo").html("");
						$("#tagArea").html(html);
					}
			 	}
				);
			}
		
			function getImagesForTag(tagValue) {
				$.ajax(
				{
					url: "servlets/images",
					data: {
						tag: tagValue
					},
					dataType: "json",
					success: function(data) {
						var images = data; // već je JSON zbog dataType gore...
						var html = "";
						var len = images.length;
						
						if(len != 0) {
							for(var i = 0; i < len; i++) {
								html += "<img src=\"servlets/image?name=" + images[i] +
										"\" onclick=\"getFullInfo('" + images[i] + "');\">";
								if(i % 2 == 1) {
									html += "<br>";
								}
							}
						}
						document.getElementById("fullImage").innerHTML = "";
						$("#imageInfo").html("");
						$("#thumbsArea").html(html);
					}
				}
				);
			}
			
			function getFullImage(name) {
				document.getElementById("fullImage").innerHTML = "<img src=\"servlets/fullImage?name=" + name +
						"\" class=\"fullImage\">";
			}
			
			function getFullInfo(imgName) {
				$.ajax(
			  	{
					url: "servlets/imageInfo",
					data: {
						name: imgName
					},
				 	dataType: "json",
				 	success: function(data) {
						var html = "";
						
						getFullImage(imgName);
						html += data[0].info + "<br>" + data[0].tags + "<br>";
						$("#imageInfo").html(html);
					}
			 	}
				);
			}		
		
		  onload = getTags();
		  
		//--></script>
	</head>
	<body>
		<h1>Game of thrones pictures gallery</h1>
		<div id="tagArea" class="tagArea">&nbsp;</div><br>
		<div id="thumbsArea" class="thumbsArea">&nbsp;</div><br>
		<div id="fullImage">&nbsp;</div>
		<div id="imageInfo" class="imageInfo">&nbsp;</div>
	</body>
</html>