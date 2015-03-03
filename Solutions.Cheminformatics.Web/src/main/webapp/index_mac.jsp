<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@page import="solutions.global.util.Text"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<head>
      <title>Merck ASAP Material Inventory Tool</title>
      <link type="text/css" rel="stylesheet" href="/asap-merck/common/css/site.css"/>     
	  <!-- link rel="stylesheet" type="text/css" href="http://o.aolcdn.com/dojo/1.1/dojo/resources/dojo.css" />
        <link rel="stylesheet" type="text/css" href="http://o.aolcdn.com/dojo/1.1/dijit/themes/tundra/tundra.css" />             
   
        <script
            djConfig="parseOnLoad:false",
            type="text/javascript"
            src="http://o.aolcdn.com/dojo/1.1/dojo/dojo.xd.js">
        </script>            
        
        <script type="text/javascript">
            dojo.require("dojo.parser");
            dojo.require("dijit.form.DateTextBox");
            dojo.require("dijit.form.ComboBox");
            dojo.require("dijit.form.ValidationTextBox");
            dojo.require("dijit.form.Button");
            dojo.require("dijit.form.Form");
            dojo.require("dijit.form.MultiSelect");
        </script-->
       <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
       
       <script>
       function getAJAX()
   	   {
   	
   	     var http_request = false;
   	
  
   	      if(window.XMLHttpRequest) //Mozilla, Safari
   	      {
   	
   	      http_request =  new XMLHttpRequest();
   	
   	      if (http_request.overrideMimeType) 
   	      {
   	
   	          http_request.overrideMimeType("text/xml"); 
   	
   	      }
   	
   	   }
   	
   	   else if( window.ActiveXObject)  //IE
   	   {
   	
   	      try 
   	      {
   	    	http_request = new ActiveXObject("Msxml2.XMLHTTP");
   	    	//Microsoft.XMLHTTP
            //http_request = new ActiveXObject("Msxml2.XMLHTTP");
   	
   	      }
   	
   	      catch(e)
   	      {
   	         try
   	         {
   	
   	            http_request = new ActiveXObject("Microsoft.XMLHTTP");
   	
   	         }
   	         catch (e) {}
   	
   	      }
   	
   	   }
   	
   	
   	
   	   //unable to create XMLHTTP
   	
   	   if (!http_request) 
   	
   	   {
   	
   	      alert("Giving up ( Cannot create an XMLHTTP instance");
   	
   	      return false;
   	
   	   }
   	
   	
   	
   	   return http_request;   
   	
   	}//-------------------------------------------------------------
       </script>
       <script
            type="text/javascript" src="common/js/MaterialMgmtView.js">
        </script>    
       <script
            type="text/javascript" src="common/js/SolutionsGlobalWeb.js">
        </script>   
          <script
            type="text/javascript" src="common/js/json2.js">
        </script>      
       <script type="text/javascript">
       
       var theAJAX = getAJAX();
       
       var web = new SolutionsGlobalWeb(theAJAX);
       var mmRender = new MaterialMgmtView("/asap-merck/","<c:out value="${pageContext.session.id}"/>",theAJAX,web);
       
         
        </script>
        
</head>
<body class="tundra">
	<div class="globalWrapper">
		<div class="headerContainer">
			<div class="headerTop">
				<div class="logo">
					<img
						alt="Merck & Co., Inc."
						src="//a248.e.akamai.net/7/248/430/20091230155215/www.merck.com/images/global/logo_Merck_no_be_well.jpg"/>
				</div>
			</div>
			<!-- ****************** JDRAW  **************************   -->
			<div>
			<div><a href="<c:url value="/views/commas/catalog.jsp"/>">WS Test Tool</a></div>
			<!--  dojotype="dijit.form.Form" -->
			<form action="javascript:void(0);"  name="mmForm">
			<div id="jDrawRenderPanel">
			<table border="0" valign="top" class="input">
			    <tr>
			        <td colspan="2">
			        <!--   code     = "com.symyx.draw.JDrawEditor" 
			         code     = "com.symyx.draw.JDrawRenderer"
			          code     = "com.merck.mrl.chem.ui.JavaDrawRender"
			        
			        -->
			        <div class="structureView">
			            <applet
			              code     = "com.merck.mrl.chem.ui.JavaDrawRender"
			              name     = "jdrawRendererApplet"
			              id       = "jdrawRendererApplet"
			              width    = "300"
			              height   = "300"
			              hspace   = "0"
			              vspace   = "0"
			              align    = "middle"
			              
			              archive  = "/asap-merck/jdraw/CsInline.jar,/asap-merck/jdraw/jdrawcore.jar,/asap-merck/jdraw/jdrawapplet.jar,/asap-merck/jdraw/plugin.jar,/asap-merck/jdraw/merck.asap.integration.jar">
				          <param name="OnMouseLeftClickJS" value="mmRender.openEditor();">
						  <!-- To avoid possible DirectDraw issues -->
						  <param name="java_arguments" value="-Xmx256m -Dsun.java2d.noddraw=true">
			
						  <!-- If you want to test the ability to detect a change to the structure:
						  <param name="OnStructureChangedJS" value="alert('OnStructureChanged event fired: javascript executed');"> -->
			
						  <!-- If you want to hide the atom tool bar:
						  <param name="showAtomsToolBar" value="off">
			
						  <!-- If you want to disable atoms coloring:
						  <param name="colorAtomsByType" value="off"> -->
						  
						  
						  <!-- param name="moleculeURL" value="/asap-merck/jdraw/test.mol"-->
					
						  <!-- For other parameters, such as labelHeight, bondLabelSize, hydrogenDisplayMode, polAtomDisplayMode, absStereoLabelText,
						       see the javadoc for JDrawApplet -->
			            </applet>
			            <br/>
			         </div>
			         
			         <div><a href="javascript:mmRender.openEditor()">Edit</a> </a>   
			         		<select name="searchOperation">
			         		    <option>SSS</option>
			         		    <option>MOLNEMAKEY</option>
			         		    <option>FLEXMATCH_EXACT</option>
			         		    <option>FLEXMATCH_SALTS</option>
			         		</select>
			         </div>
			         <br/>
			            <button name="search" onclick="mmRender.searchFor();">Search</button><input id="searchForCriteria" type="radio" name="searchForCriteria" value="CompoundMgmt.findMolecules" checked="true"> Molecules <input id="searchForCriteria" type="radio" name="searchForCriteria" value="CompoundMgmt.findContainers"> Containers <!-- input type="radio" name="searchForCriteria" value="Order"> Orders-->
			        </td>
			      	<td>
			      	<!-- 
			      	 <p>
			     	  <label for="structureKey">Struct Key</label>
			      	  <input name="structureKey" type="text"/>   
			      	</p>
			      	<p>
			      	  <label for="location">Location</label>
			      	 <select name="location" multiple="true" dojotype="dijit.form.ComboBox" autocomplete="true">
			                        <option></option>
			                        <option>RY-34A-1-112</option>
						            <option>RY-34A-1-113</option>
						            <option>RY-34A-1-114</option>
			                        </select>
			      	 </p>
			      	  <br/>
			      	   -->
			    	   	  
			  	</td>     
		 </tr>
    </table>
    </div><!-- End JDraw Render -->
    
    <!--  START Pop Over -->
    <div id="jdrawPopupPanel" style="display:none; z-index:10; position: fixed;left:710px; top:150px;border-style:solid;border-color:#B8B8B8;">
    	              <!--  com.symyx.draw.JDrawRenderer -->
			    <applet
			              code     = "com.merck.mrl.chem.ui.JavaDrawRender"
			              name     = "jdrawPopupApplet"
			              id       = "jdrawPopupApplet"
			              width    = "300"
			              height   = "300"
			              hspace   = "0"
			              vspace   = "0"
			              align    = "middle"
			              
			              archive  = "/asap-merck/jdraw/CsInline.jar,/asap-merck/jdraw/jdrawcore.jar,/asap-merck/jdraw/jdrawapplet.jar,/asap-merck/jdraw/plugin.jar,/asap-merck/jdraw/merck.asap.integration.jar">
				          <param name="OnMouseLeftClickJS" value="mmRender.openEditor();">
						  <!-- To avoid possible DirectDraw issues -->
						  <param name="java_arguments" value="-Xmx256m -Dsun.java2d.noddraw=true">
			
						  <!-- If you want to test the ability to detect a change to the structure:
						  <param name="OnStructureChangedJS" value="alert('OnStructureChanged event fired: javascript executed');"> -->
			
						  <!-- If you want to hide the atom tool bar:
						  <param name="showAtomsToolBar" value="off">
			
						  <!-- If you want to disable atoms coloring:
						  <param name="colorAtomsByType" value="off"> -->
						  
						  
						  <!-- param name="moleculeURL" value="/asap-merck/jdraw/test.mol"-->
					
						  <!-- For other parameters, such as labelHeight, bondLabelSize, hydrogenDisplayMode, polAtomDisplayMode, absStereoLabelText,
						       see the javadoc for JDrawApplet -->
			            </applet>
    </div>
    <!--  END Pop Over -->
    <div id="jdrawEditorPanel" style="display:none">
      <applet
              code     = "com.symyx.draw.JDrawEditor"
              name     = "JDrawEditor"
              id       = "jdrawEditorApplet"
              width    = "800"
              height   = "500"
              hspace   = "0"
              vspace   = "0"
              align    = "middle"
              archive  = "/asap-merck/jdraw/CsInline.jar,/asap-merck/jdraw/jdrawcore.jar,/asap-merck/jdraw/jdrawapplet.jar">


			  <!-- To avoid possible DirectDraw issues -->
			  <param name="java_arguments" value="-Xmx256m -Dsun.java2d.noddraw=true">

			  <!-- If you want to test the ability to detect a change to the structure:
			  <param name="OnStructureChangedJS" value="alert('OnStructureChanged event fired: javascript executed');"> -->

			  <!-- If you want to hide the atom tool bar:
			  <param name="showAtomsToolBar" value="off">

			  <!-- If you want to disable atoms coloring:
			  <param name="colorAtomsByType" value="off"> -->
			  
			  
			  <!-- param name="moleculeURL" value="/asap-merck/jdraw/test.mol"-->


              <param name="labelHeight" value="0.45"> 
              <param name="showAtomsToolBar" value="false">

			  <!-- For other parameters, such as labelHeight, bondLabelSize, hydrogenDisplayMode, polAtomDisplayMode, absStereoLabelText,
			       see the javadoc for JDrawApplet -->
            </applet>
            <div>
            <br/>
            <button onClick="mmRender.transferMolecule();">Transfer</button>
           		 <div>
                        <textarea name="molData" cols="80" rows="15"></textarea><br/>
                        <button onclick="javascript:molData.value = mmRender.getEditorMolString();">View Mol</button>
			            <button onclick="javascript:mmRender.setEditorMolString(molData.value);">Draw Mol</button>
			            <br />
		     	 </div>
            </div>
    </div>
<br>

</form>


<div id="outputPanel">
<!-- table class="resultTable">
	<thead>
		<tr>
			<th>Structure</th>
			<th>Barcode</th>
			<th>Location</th>
			<th>Sub Loc.</th>
			<th>Vendor</th>
			<th>Amount</th>
			<th>Status</th>
		</tr>
	</thead>
	<tbody>
		<tr class="odd">
			<td>L-02323</td>
			<td>BC23923</td>
			<td>RY-34A-1-118</td>
			<td>RA-1</td>
			<td>Fisher</td>
			<td>1 ML</td>
			<td>INUSE</td>
		</tr>
		<tr class="even">
			<td>MDL02323</td>
			<td>BC34923</td>
			<td>RY-34B-1-118</td>
			<td>RA-3</td>
			<td>Fisher</td>
			<td>3 ML</td>
			<td>INUSE</td>			
		</tr>	
	</tbody>
</table-->

</div>

<!-- If you want to test the ability to detect a change to the structure:
<script>JDrawEditor.setOnStructureChangedJS("alert('OnStructureChanged event fired: javascript executed');");</script>
-->

	</div>
		</div>
		
</body>
</html>