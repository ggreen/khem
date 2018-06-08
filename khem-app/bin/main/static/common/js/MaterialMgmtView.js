 
function MaterialMgmtView(contextRoot,sessionID,mmAJAX,web,menuBuilderFunction)
{
	this.mmAJAX = mmAJAX;
    this.web = web;
    this.sessionID = sessionID;
    this.contextRoot = contextRoot;
    
    this.moleculeObjects = {};
    this.containerObjects = {};
    this.materialCriteria = {};
    this.menuBuilderFunction = menuBuilderFunction;
    
    //----------------------------------------------
    this.format = format;
    function format(text)
    {
  	  if(text == undefined)
  		  return "";
  	  else if(text == "null")
  		  return "";
  	  
  	  return text;
    }//-------------------------------------------------
    this.printProgress = printProgress;
    function printProgress()
    {
  	   //Set some element     
       hideHighlightMoleculeRow();
        web.byId("outputPanel").innerHTML = "<img src='"+contextRoot+"common/images/progress.gif'/>";
    }//---------------------------------------------

    this.printError = printError;
    function printError(text)
    {
          //Set some element 
                      web.byId("outputPanel").innerHTML = "<div style='color:red'>"+text+"</div>";
    }//--------------------------------------------------   
    this.println = println;
    function println(text)
    {
  	//Set some element 
        
        if(text == null || "" == text || text.length == 0)
      	  text = "No data found";
        
        web.byId("outputPanel").innerHTML = text;
    }//---------------------------------------------
    
	this.contextRoot = contextRoot;
	/**
	 * Search for containers
	 * @returns
	 */
	this.searchFor=searchFor;
	function searchFor()
    {	
        	  this.printProgress();
        	  
        	    var operationValue = document.mmForm.searchOperation.options[document.mmForm.searchOperation.selectedIndex].value;
        	  
        	    mmRender.materialCriteria = {"locations":[{"site":"","building":"","floor":"","room":"","subLocation":"","name":""}],
        	    		                "pageCriteria":{"id":sessionID,"beginIndex":"1","size":"300","savePagination":"true"},
        	    		                "structureCriteria":{"pageCriteria":{"id":sessionID,"beginIndex":"1","size":"300","savePagination":"true",
        	    		                "className":"com.merck.mrl.asap.integration.services.data.Molecule"},
        	    	 "molString": this.getMolString()

        	    		 ,"sources":["ACD","MCIDB","MERCKACD"],"structureKeys":[{"id":"","molKey":"","sourceCode":"","isActive":""}],"casNum":"","name":"",
        	    		 "operation": operationValue}};
                 //Get form
                 var renderResultsData = web.toJson(mmRender.materialCriteria);
                       

                 //var executeFunctionUrl = "<c:out value="${pageContext.request.contextPath}/controller/commas/"/>"+dojo.byId("searchForCriteria").value;
                 var searchForCriteriaRadioButton = document.mmForm.searchForCriteria;
                 var searchForCriteria = null;
                 
                 for(i=0; i< searchForCriteriaRadioButton.length; i++)
                 {
                	 if(searchForCriteriaRadioButton.item(i).checked == true)
                     {
                		 searchForCriteria = searchForCriteriaRadioButton.item(i).value;
                		 break;
                     }
                 }
                 
                 var executeFunctionUrl = contextRoot+"controller/commas/"+searchForCriteria;

             	 /*dojo.rawXhrPost({
                                   url: executeFunctionUrl,
                                   handleAs: "text",
                                   headers : {"Content-Type":"text/html;charset=ISO-8859-1"},
                                  postData : jsonFormText,
                                   load : this.renderResults,
                                   error: this.renderError
                                               
                                  });
                   */

                 if(executeFunctionUrl.indexOf("findContainers") > -1)
                 {
                	 web.openAJAX(mmAJAX,this.renderContainerResults, "POST", executeFunctionUrl, true, renderResultsData);	 
                 }
                 else if(executeFunctionUrl.indexOf("findMolecules") > -1)
                 {
                	 web.openAJAX(mmAJAX,this.renderMoleculeResults, "POST", executeFunctionUrl, true, renderResultsData);	
                 }
                 else
                 {
                	 alert("render results for "+executeFunctionUrl )
                 }
                 
                 
          
                
       }//-------------------------------------------------  
	   /**
	    * 
	    */
	   this.transferMolecule = transferMolecule;
	   function transferMolecule()
       {
     	  	var jdrawEditorApplet = document.getElementById("jdrawEditorApplet");
     	    var JDrawRendererApplet = document.getElementById("jdrawRendererApplet");
     	    
     	   JDrawRendererApplet.setMolString(jdrawEditorApplet.getMolString());
     	      
       	  	// var editorWindow = window.open("<c:url value="/editor.jsp"/>","editor","menubar=no,scrollbars=yes,toolbar=no,location=no,height=800,width=850");
            var editorPanel = document.getElementById("jdrawEditorPanel");
            
            editorPanel.style.display = "none";
            
	       	 var jDrawRenderPanel = document.getElementById("jDrawRenderPanel");
	         jDrawRenderPanel.style.display = "inline";
	            
       }//--------------------------------------------------
	   this.openEditor = openEditor;
      function openEditor()
      {
          var jDrawRenderPanel = document.getElementById("jDrawRenderPanel");
          jDrawRenderPanel.style.display = "none";
          
    	  // var editorWindow = window.open("<c:url value="/editor.jsp"/>","editor","menubar=no,scrollbars=yes,toolbar=no,location=no,height=800,width=850");
         var editorPanel = document.getElementById("jdrawEditorPanel");
         
         editorPanel.style.display = "inline";
      }//-------------------------------------------------
      
      this.selectAllMolecules= selectAllMolecules;
      function selectAllMolecules()
      {
    	  
    	  var mmForm =document.getElementById("outputPanelForm");
    	  
    	  var checkBoxObj = null;
    	  
    	  var selectAllChkBoxChkBox = document.getElementById("selectAllChkBox");
    	  
    	  //toggle checkbox
    	  /*if(selectAllChkBoxChkBox.checked == true)
    		  selectAllChkBoxChkBox.checked = false;
    	  else
    		  selectAllChkBoxChkBox.checked = true;
    	  */
    	  
    	  for (var i=0; i < mmForm.elements.length ;i++)
    	  {
    		  checkBoxObj = mmForm.elements[i];
	    	  if(checkBoxObj.type == "checkbox" && checkBoxObj.name == "moleculeCheckBoxes")
	    	  {
	    	    	checkBoxObj.checked = selectAllChkBoxChkBox.checked;
	    	  }
    	  }
    	  
    	  //.moleculeCheckBoxes
      }//-------------------------------------------------
       this.renderMoleculeResults = renderMoleculeResults;
       function  renderMoleculeResults() 
       {
    	   var text = web.getResponseText(mmAJAX);

           
           if(text == null)
           {
         	return false;  
           }
           else if("" == text || text.length == 0 || "[]" == text)
            {
        	     println("No data found");
        	   return false;
            }
                                   
            var responseHTML = "<div id='moleculeRowsTablePanel'><div id='moleculeSummaryPanel'></div><table class='resultTable'><thead><tr>"+
            			"<th><input type='checkbox' id='selectAllChkBox' name='selectAllChkBox' onclick='mmRender.selectAllMolecules();' /></th>"+
                          "<th>Source</th>"+
                         //"<th>Structure</th>" +
                         "<th>Key</th><th>NAME</th><th>Weight</th><th>Formula</th></tr></thead><tbody>";
                         
            mmRender.moleculeObjects = web.fromJson(text);
       
         var rowClass= "";
         var index = 0;
         
         for(i in mmRender.moleculeObjects.collection)
         {
        	 //even
        	 if(index % 2 == 0)
        		 rowClass = "even";
        	 else
        		 rowClass = "odd";
        	 
             index++
        	 
             responseHTML= responseHTML+"<tr class='"+rowClass+"'  onmouseover=\"mmRender.highlightMoleculeRow(this,'hovered',"+i+")\" onmouseout=\"mmRender.changeClass(this,'"+rowClass+"')\">"
             +"<td><input type='checkbox' name='moleculeCheckBoxes' value='"+web.toJson(mmRender.moleculeObjects.collection[i])+"'/></td>"
             +"<td>"+mmRender.format(mmRender.moleculeObjects.collection[i].sourceCode)+"</td>"
        	 
             +"<td>"+mmRender.format(mmRender.moleculeObjects.collection[i].structureKey)+"</td>"
        	 +"<td>"+mmRender.format(mmRender.moleculeObjects.collection[i].name)+"</td>"
        	 +"<td>"+mmRender.format(mmRender.moleculeObjects.collection[i].weight)+"</td>"
        	 +"<td>"+mmRender.format(mmRender.moleculeObjects.collection[i].formula)+"</td>"
        	  +"</tr>";
        }

         responseHTML= responseHTML+"<!--${NEW_MOLEROW_PLACE_HOLDER}--></tbody></table></div>";
         
         //if( mmRender.moleculeObjects.last != "true")
         //{
        	 responseHTML= responseHTML+"<div id='more'><a href='#' onclick='mmRender.nextMolecules();return false;'>More</a> </div>";
         //}
         
         println(responseHTML);
         
         //Render counts
         window.setTimeout("mmRender.getPaginationCount()",100);
         
          return true;
       }//-------------------------------------------------
       this.renderMoleculeNextResults = renderMoleculeNextResults;
       function renderMoleculeNextResults()
       {
     	  var text = web.getResponseText(mmAJAX);
     	  
     	  //get max collection
     	  if(text == null)
           {
         	return false;  
           }
           else if("" == text || text.length == 0 || "[]" == text)
           {
         	  var moreElement = document.getElementById("more");
         	  
         	  if(moreElement != undefined && moreElement !=  null)
         		  moreElement.innerHTML  = "";
         	  
        	       return false;
           }
     	  
           
           var nextMoleculeResults = web.fromJson(text);
           
           if(nextMoleculeResults.empty == "true")
           {
         	  var moreElement = document.getElementById("more");
         	  
         	  if(moreElement != undefined && moreElement !=  null)
         		  moreElement.innerHTML  = "";
         	  
         	  return false;
           }
           
		   
		   //Has Results
		   
		    var moreElement = document.getElementById("more");
			moreElement.innerHTML = "<a href='#' onclick='mmRender.nextMolecules();return false;'>More</a>";
		   
		   
     	  var index =  mmRender.moleculeObjects.collection.length;
     	  
		  
		  
		  
     	  var rowHTML ="";
     	  for(i in nextMoleculeResults.collection)
           {
     		  //add to saved results
     		 mmRender.moleculeObjects.collection[index] = nextMoleculeResults.collection[i];
     		  
     		  rowHTML = rowHTML + renderMoleculeRow(nextMoleculeResults.collection[i],index);
     		  
     		  index = index + 1;
           }
     	  
     	 rowHTML = rowHTML + "<!--${NEW_MOLEROW_PLACE_HOLDER}-->";
     	 
     	//get moleculeRowsTbody
     	  
     	 //Add new row in NEW_MOLEROW_PLACE_HOLDER location
     	  var moleculeRowsTablePanel = document.getElementById("moleculeRowsTablePanel");
     	  var moleculeRowsTablePanelHTML = moleculeRowsTablePanel.innerHTML.replace( "<!--${NEW_MOLEROW_PLACE_HOLDER}-->", rowHTML);

     	  //Append addition results
     	 moleculeRowsTablePanel.innerHTML = moleculeRowsTablePanelHTML;
     	 
     	 
         //Render counts
         getPaginationCount();
     	  
     	  return true;
       }//------------------------    
       /**
        * Render a molecule row
        * @param molecule the molecule
        * @param index the row index count
        */
       this.renderMoleculeRow = renderMoleculeRow;
       function renderMoleculeRow(molecule,index)
       {
    	   var rowClass ="odd";
    	   
    	   //even
      	 if(index % 2 == 0)
      		 rowClass = "even";
      	 else
      		rowClass ="odd";
      	 
    	  var moleculeRowHTML = "<tr class='"+rowClass+"'  onmouseover=\"mmRender.highlightMoleculeRow(this,'hovered',"+index+")\" onmouseout=\"mmRender.changeClass(this,'"+rowClass+"')\">"
      	 +"<td>"+format(molecule.sourceCode)+"</td>"      	 
         +"<td>"+format(molecule.structureKey)+"</td>"
      	 +"<td>"+format(molecule.name)+"</td>"
      	 +"<td>"+format(molecule.weight)+"</td>"
      	 +"<td>"+format(molecule.formula)+"</td>"
      	  +"</tr>";
    	   
    	   return moleculeRowHTML;
       }//----------------------

	  this.showProgressBar = showProgressBar;
	  function showProgressBar(panelId)
	  {
	     var panelElement = document.getElementById(panelId);
         
		 
         if(panelElement != undefined && panelElement !=  null)
         		  panelElement.innerHTML  = "<img src='"+contextRoot+"common/images/progress.gif'/>";
	  }//------------------------------------------------------
	  
	  this.showAdvanceOptions = showAdvanceOptions;
	  function showAdvanceOptions()
	  {
	     var panelElement = document.getElementById("advanceSearchOptionsPanel");
         
	     if(panelElement.style.display == "inline")
	      {
	    	 panelElement.style.display = "none";
	      }
	     else
	     {
	    	 panelElement.style.display = "inline";	 
	     }
	     
	  }//------------------------------------------------------
       this.nextMolecules = nextMolecules;
       function nextMolecules()
       {
    	   
    	   showProgressBar("more");
    	   
    	   mmRender.moleculeObjects.pageCriteria.beginIndex =  (mmRender.moleculeObjects.collection.length *  1) + 1;
    	   
    	   
            //Get form
            var renderResultsData = web.toJson(mmRender.moleculeObjects.pageCriteria);
                  
                        
            var executeFunctionUrl = this.contextRoot+"controller/commas/solutions.global.web.commas.iteration.GetPagingCommand.getPaging";
     
           	 web.openAJAX(mmAJAX,this.renderMoleculeNextResults, "POST", executeFunctionUrl, true, renderResultsData);	
      
      }//--------------------------------------------------	  
       /**
        * Get the pagination count
        */
       this.getPaginationCount = getPaginationCount;
       function getPaginationCount()
       {
    	  // moleculeObjects.pageCriteria
    	  //TODO: this.printProgress();    	   
    	   
            //Get form
    	   
    	   if(mmRender.materialCriteria.pageCriteria ==  undefined)
    		   return false;
    	   
            var pageCriteriaJSON = web.toJson(mmRender.materialCriteria.pageCriteria);
                  
            
            renderMoleculeSummaryCount("-1");
            
            
            var executeFunctionUrl = contextRoot+"controller/commas/solutions.global.web.commas.iteration.GetPagingCommand.count";
     
           	 web.openAJAX(mmAJAX,renderPaginationCount, "POST", executeFunctionUrl, true, pageCriteriaJSON);	
      
      }//--------------------------------------------------
   
      this.changeClass = changeClass;
      function changeClass(row, theClass)
      {
    	  row.className = theClass;
      }//---------------------------------------------------
	  
	  this.hideHighlightMoleculeRow =  hideHighlightMoleculeRow;
	  function hideHighlightMoleculeRow()
	  {
	      //"jdrawPopupPanel"
	      var panel = web.byId("jdrawPopupPanel");
		  
		  if(panel != undefined && panel != null)
		      panel.style.display = "none";
		  
	  }//-----------------------------
      this.highlightMoleculeRow = highlightMoleculeRow;
      function highlightMoleculeRow(row,theClass, molIndex)
      {  
    	  var panel = web.byId("jdrawPopupPanel");
    	  panel.style.display = "inline";
    	  
    	  var topPx = 300;
    	  panel.style.top =  ""+topPx+"px"; //150
    	  
    	  var applet = document.getElementById("jdrawPopupApplet");
    	 
    	  try
    	  {
	    	  //Set the molecule string
	    	  applet.setMolString(mmRender.moleculeObjects.collection[molIndex].molString);
    	  }
    	  catch(err)
    	  {}
    	  
    	 // Change the highlight color of the row 
    	 changeClass(row,theClass);
     }//-------------------------------------------------
      this.highlightContainerRow = highlightContainerRow;
      function highlightContainerRow(row,theClass, containerIndex)
      {
    	  //web.byId("jdrawPopupPanel").style.display = "inline";
    	  
    	  var panel = web.byId("jdrawPopupPanel");
    	  panel.style.display = "inline";
    	  
    	  //var topPx = molIndex * 50 + 400;
    	  var topPx = 300;
    	  
    	  panel.style.top =  ""+topPx+"px"; //150
    	  
    	  var applet = document.getElementById("jdrawPopupApplet");
    	  
         
    	  try
    	  {
	    	  //alert(moleculeObjects[molIndex].molString);
	    	 applet.setMolString(mmRender.containerObjects.collection[containerIndex].molString);
    	  }
    	  catch(err)
    	  {
    		  
    	  }
    	  
    	  //jdrawPopupApplet
    	  
   	     changeClass(row,theClass);
     }//-------------------------------------------------
      /**
       * Display the container HTML
       * @returns {Boolean}
       */
      this.renderContainerResults = renderContainerResults;
      function  renderContainerResults() 
      {
    	  var text = web.getResponseText(mmAJAX);
    	  
    	  //output.innerHTML = outText;	    
    	  
          
          if(text == null)
          {
        	return false;  
          }
          else if("" == text || text.length == 0 || "[]" == text)
           {
       	     println("No data found");
       	   return false;
           }
                                  
           var responseHTML = "<div id='containerRowsTablePanel'><div id='containerSummaryPanel'></div><table class='resultTable'><thead><tr>"+
                         "<th>Source</th><th>Structure Key</th><th>BARCODE</th><th>Amounts</th><th>Vendor</th><th>Location</th><th>Location Type</th></tr></thead><tbody>";
                         
      //[{"structKey":"MDL1","name":"1","purityhigh":"0.0","purityhighLow":"0.0"}]
                                  

        mmRender.containerObjects = web.fromJson(text);
      
        var rowClass= "";
        var index = 1;
        
        for(i in  mmRender.containerObjects.collection)
        {
       	 //even
       	 if(index % 2 == 0)
       		 rowClass = "even";
       	 else
       		 rowClass = "odd";
       	 
            index++
       	 
            //onmouseover=\"mmRender.highlightMoleculeRow(this,'hovered',"+index+")\" onmouseout=\"mmRender.changeClass(this,'"+rowClass+"')\"
            responseHTML= responseHTML+"<tr class='"+rowClass+"'  onmouseover=\"mmRender.highlightContainerRow(this,'hovered',"+i+")\" onmouseout=\"mmRender.changeClass(this,'"+rowClass+"')\">"
          	 +"<td>"+mmRender.format( mmRender.containerObjects.collection[i].source)+"</td>"
            +"<td>"+mmRender.format( mmRender.containerObjects.collection[i].structKey)+"</td>"
       	 //+"<td><img src='"+contextRoot+"controller?cmd=com.merck.mrl.asap.integration.services.web.MoleculeImageCommand&structKey="+containerObjects.collection[i].structKey+"&structSource="+containerObjects.collection[i].structSource+"'/></td>"
       	 +"<td>"+mmRender.format( mmRender.containerObjects.collection[i].barCode)+"</td>"
       	 +"<td>"+mmRender.format( mmRender.containerObjects.collection[i].originalAmount)
       	 +" "+mmRender.format( mmRender.containerObjects.collection[i].originalAmountUnits)+"</td>"
       	 +"<td>"+mmRender.format( mmRender.containerObjects.collection[i].vendorName)+"</td>"
       	 +"<td>"+mmRender.format( mmRender.containerObjects.collection[i].site)+"->"
       	     + mmRender.containerObjects.collection[i].building
       	     +"->"+mmRender.format( mmRender.containerObjects.collection[i].floor)
       	     +"->"+ mmRender.containerObjects.collection[i].room
       	     +"->"+ mmRender.containerObjects.collection[i].subLocation
       	  +"</td>"
       	  +"<td>"+ mmRender.containerObjects.collection[i].locationType+"</td>"
       	  +"</tr>";
       }
        

        responseHTML= responseHTML+"<!--${NEW_CONTAINER_ROW_PLACE_HOLDER}--></tbody></table><div>";
        
        println(responseHTML);
        
        
        //Render counts
        //getContainerPaginationCount();
                       
         return true;
    }
   //---------------------------------------------------
      
    this.renderError  = renderError;
    function renderError(response, ioArgs) {
          //console.log("error xhrGet", response, ioArgs);
          this.printError(ioArgs.xhr.responseText);
          return response;
    }   
      //---------------------------------------------------
      this.getMolString = getMolString;
      function getMolString()
      {
        var applet = document.getElementById("jdrawRendererApplet");
        return applet.getMolString();
      }
      //------------------------------------------------
      this.getEditorMolString = getEditorMolString;
      function getEditorMolString()
      {
        var applet = document.getElementById("jdrawEditorApplet");
        return applet.getMolString();
      }
      //-----------------------------------------------------
      this.setEditorMolString = setEditorMolString;
      function setEditorMolString(molstring)
      {
        var applet = document.getElementById("jdrawEditorApplet");
        applet.setMolString(molstring);
      }//----------------------------------------------------
     
      this.renderMoleculeSummaryCount = this.renderMoleculeSummaryCount;
      function renderMoleculeSummaryCount(countText)
      {
    	  var moleculeSummaryPanel = document.getElementById("moleculeSummaryPanel");
     	 
     	 if(moleculeSummaryPanel  == null || moleculeSummaryPanel == undefined)
     		 return false;
     	 
     	 if(mmRender.moleculeObjects.collection == undefined)
     		 return false;
     	 
     	 var summaryCountHTML = "Showing "+mmRender.moleculeObjects.collection.length+" results of About: ";
     	 if(countText == "-1")
     	 {
     		 
     		summaryCountHTML = summaryCountHTML  + "<img src='"+contextRoot+"common/images/progress.gif'/>";
         }
     	 else
     	 {
     		summaryCountHTML = summaryCountHTML  + countText;
     		 
     	 }
         
     	
     	
     	 
     	 if( countText == "\""+mmRender.moleculeObjects.collection.length+"\"")
     	 {
     		 //hide more
     		 document.getElementById("more").style.display = "none";
     	 }
     	 
     	 // Add Menu options
      	summaryCountHTML += mmRender.menuBuilderFunction();
      	
     	moleculeSummaryPanel.innerHTML = summaryCountHTML;
    	  
      }//----------------------------------------------------------------------------

      this.renderPaginationCount = renderPaginationCount;
      function renderPaginationCount()
      {
    	  var countText = web.getResponseText(mmAJAX);
    	  
    	  //get max collection
    	  if(countText == null)
          {
        	return false;  
          }
          else if("" == countText || countText.length == 0 || "[]" == countText)
          { 	  
       	       return false;
          }
    	  
    	  renderMoleculeSummaryCount(countText);
         
    	  return true;
      }//------------------------ 
}