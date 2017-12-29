<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@page import="nyla.solutions.global.util.Text"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<head>
      <title>KHEM - Knowledge Handling Every Molecule </title>
      <link type="text/css" rel="stylesheet" href="<c:url value="/common/css/site.css"/>"/>    
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
        
        <script
            type="text/javascript" src="common/js/ALDaSMerckView.js">
        </script>   
                
       <script type="text/javascript">
       
       var theAJAX = getAJAX();
       
       var web = new SolutionsGlobalWeb(theAJAX);
       var aldasMerckRender = new ALDaSMerckView(theAJAX,"<c:url value=""/>","<c:out value="${param.schemeCacheId}"/>","","<c:out value="${param.nodeId}"/>");
       var mmRender = new MaterialMgmtView("<c:url value=""/>","<c:out value="${pageContext.session.id}"/>",theAJAX,web,aldasMerckRender.constructResultsMenuHTML);
       
       
         
        </script>
        
</head>
<body class="tundra">
	<div class="globalWrapper">
		<div class="headerContainer">
			<div class="headerTop">
				<div class="logo">
					<img src="<c:url value="/common/images/emc.png"/>"/>
					<img src="<c:url value="/common/images/pivotal.png"/>"/>						
				</div>
			</div>
			<!-- ****************** JDRAW  **************************   -->
			<div>
			<div><a href="<c:url value="/views/commas/catalog.jsp"/>">WS Test Tool</a></div>
			<!--  dojotype="dijit.form.Form" -->
			<form id="mmForm" action="javascript:void(0);"  name="mmForm">
			<div id="jDrawRenderPanel">
			<table border="0" valign="top" class="input">
			    <tr>
			        <td colspan="2">
			       
			        <!--   code     = "com.symyx.draw.JDrawEditor" 
			         code     = "com.symyx.draw.JDrawRenderer"
			          code     = "com.merck.mrl.chem.ui.JavaDrawRender"
			        
			        -->
			        <div class="structureView">
			        
			          <!-- IE: com.symyx.draw.JDrawRenderer and com.merck.mrl.chem.ui.JavaDrawRender  Mac  
			          			khem.solutions.cheminformatics.draw.KhemJDrawRender-->
			            <applet
			              code     = "com.symyx.draw.JDrawRenderer"    
			              name     = "jdrawRendererApplet"
			              id       = "jdrawRendererApplet"
			              width    = "300"
			              height   = "300"
			              hspace   = "0"
			              vspace   = "0"
			              align    = "middle"
			              
			              archive  = "/khem.solutions.cheminformatics.web/jdraw/CsInline.jar,/khem.solutions.cheminformatics.web/jdraw/jdrawcore.jar,/khem.solutions.cheminformatics.web/jdraw/jdrawapplet.jar,/khem.solutions.cheminformatics.web/jdraw/khem.solutions.cheminformatics-1.0.jar">
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
			         		    <option>FLEXMATCH_EXACT</option>
			         		    <option>FLEXMATCH_SALTS</option>
			         		</select>
			         		
			         		<a href="javascript:mmRender.showAdvanceOptions()">Advanced Options</a>
			         </div>
			         <div id="advanceSearchOptionsPanel" class="advanceSearchOptions">
			         
			         <div id="orStructure">
			         	OR <br/>
			         	
						      <!--  applet
						              code     = "com.symyx.draw.JDrawEditor"
						              name     = "JDrawOREditor"
						              id       = "jdrawOREditorApplet"
						              width    = "800"
						              height   = "500"
						              hspace   = "0"
						              vspace   = "0"
						              align    = "middle"
						              archive  = "<c:url value="/jdraw/CsInline.jar"/>,<c:url value="/jdraw/jdrawcore.jar"/>,<c:url value="/jdraw/jdrawapplet.jar"/>,<c:url value="/jdraw/plugin.jar"/>,<c:url value="/jdraw/merck.asap.integration.jar"/>">
						
						
									  <!-- To avoid possible DirectDraw issues -->
									  <param name="java_arguments" value="-Xmx256m -Dsun.java2d.noddraw=true"/>
						
						
									  <!-- If you want to hide the atom tool bar:
									  <param name="showAtomsToolBar" value="off">
						
									  <!-- If you want to disable atoms coloring:
									  <param name="colorAtomsByType" value="off"> -->
									  			
						              <param name="labelHeight" value="0.45"/> 
						              <param name="showAtomsToolBar" value="false"/>
						            </applet-->
			         	</div>
			         	
			         	
			         	<div id="notStructure">
			         	NOT <br/>
			         	
						      <!-- applet
						              code     = "com.symyx.draw.JDrawEditor"
						              name     = "JDrawNOTEditor"
						              id       = "jdrawNOTEditorApplet"
						              width    = "800"
						              height   = "500"
						              hspace   = "0"
						              vspace   = "0"
						              align    = "middle"
						              archive  = "<c:url value="/jdraw/CsInline.jar"/>,<c:url value="/jdraw/jdrawcore.jar"/>,<c:url value="/jdraw/jdrawapplet.jar"/>,<c:url value="/jdraw/plugin.jar"/>">
						
						
									  <!-- To avoid possible DirectDraw issues -->
									  <param name="java_arguments" value="-Xmx256m -Dsun.java2d.noddraw=true"/>
						
									  <!-- If you want to disable atoms coloring:
									  <param name="colorAtomsByType" value="off"> -->
									  			
						              <param name="labelHeight" value="0.45"/> 
						              <param name="showAtomsToolBar" value="false"/>
						            </applet -->
			         	</div>
			         
			            <div>
				             <input type="checkbox" name="inventoryTypeCode" value="CC"/> CC
				             <input type="checkbox" name="inventoryTypeCode" value="STOREROOM"/> STOREROOM
				             <input type="checkbox" name="inventoryTypeCode" value="STOREROOM"/> LABS
				             <input type="checkbox" name="inventoryTypeCode" value="STOREROOM"/> MBBC (FRONTIER)
				         </div>
			            
			            <div>
			              <input type="checkbox" name="structureSources" value="CC"/> MCIDB
			              <input type="checkbox" name="structureSources" value="CC"/> ACD
			               <input type="checkbox" name="structureSources" value="CC"/> MERCKACD
			      
			            </div>

 						<div>
	 						<table>
	 							<thead>
	 								<tr>
		 								<th><a title="Paste list of 16-digit L-, MFDC or MERK identifiers" href="#" onclick="return false;">L/MFCD/MERK#</a></th>
		 								<th><a title="Paste list of ACD CAS numbers" href="#" onclick="return false;">CAS Numbers</a></th>
		 								<th><a title="Paste list of COSMIC Barcodes or Local CC Sublocation identifiers" href="#" onclick="return false;">Barcodes</a></th>
	 								</tr>
	 								</thead>
	 							<tbody>
	 								<tr>
	 									<td><textarea rows="3" name="structurekeys" cols="16"></textarea></td>
	 									<td><textarea rows="3" name="structurekeys" cols="16"></textarea></td>
	 									<td><textarea rows="3" name="structurekeys" cols="16"></textarea></td>
	 								</tr>
	 							</tbody>
	 						</table>
 	
 						</div>			            
			         </div>
			         <br/>
			            <button name="search" onclick="mmRender.searchFor();">Search</button><input id="searchForCriteria" type="radio" name="searchForCriteria" value="CompoundMgmt.findMolecules" checked="true"> Molecules <input id="searchForCriteria" type="radio" name="searchForCriteria" value="CompoundMgmt.findContainers"> Containers <!-- input type="radio" name="searchForCriteria" value="Order"> Orders-->
			        </td>
			      	<td>
			  	</td>     
		 </tr>
    </table>
    </div><!-- End JDraw Render -->
    
    <!--  START Pop Over -->
    <div id="jdrawPopupPanel" style="display:none; z-index:10; position: fixed;left:710px; top:150px;border-style:solid;border-color:#B8B8B8;">
    	              <!--  com.symyx.draw.JDrawRenderer -->
			    <applet
			              code     = "com.symyx.draw.JDrawRenderer"
			              name     = "jdrawPopupApplet"
			              id       = "jdrawPopupApplet"
			              width    = "300"
			              height   = "300"
			              hspace   = "0"
			              vspace   = "0"
			              align    = "middle"
			              
			              archive  = "<c:url value="/jdraw/CsInline.jar"/>,<c:url value="/jdraw/jdrawcore.jar"/>,<c:url value="/jdraw/jdrawapplet.jar"/>,<c:url value="/jdraw/plugin.jar"/>">
				          <param name="OnMouseLeftClickJS" value="mmRender.openEditor();">
						  <!-- To avoid possible DirectDraw issues -->
						  <param name="java_arguments" value="-Xmx256m -Dsun.java2d.noddraw=true">
			
					    </applet>
    </div>
    <!--  END Pop Over -->
    <div id="jdrawEditorPanel" style="display:none">
      <applet
              code     = "com.symyx.draw.JDrawEditor"
              name     = "jdrawEditorApplet"
              id       = "jdrawEditorApplet"
              width    = "800"
              height   = "500"
              hspace   = "0"
              vspace   = "0"
              align    = "middle"
              archive  = "<c:url value="/jdraw/CsInline.jar"/>,<c:url value="/jdraw/jdrawcore.jar"/>,<c:url value="/jdraw/jdrawapplet.jar"/>,<c:url value="/jdraw/plugin.jar"/>">


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
	<form id="outputPanelForm" name="outputPanelForm">
			
		<div id="outputPanel">
			
		
		</div>
	</form>
	
   </div>
		
</body>
</html>
