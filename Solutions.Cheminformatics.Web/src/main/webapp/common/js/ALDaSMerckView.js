function ALDaSMerckView(theAJAX,contextRoot,schemeCacheId,libraryId,nodeId)
{	
	this.contextRoot = contextRoot;
	this.theAJAX = theAJAX;
	this.schemeCacheId = schemeCacheId
    this.importSelected = importSelected;
	this.libraryId = libraryId;
	this.nodeId = nodeId;
	
	//-------------------------------------------------
	function importSelected()
	{
		try
		{
		  // alert('Hello world');
		   
		   var reagentNodeDT = {"reagentSchemeNodeIdentifier":{"schemeCacheId":this.schemeCacheId,"libraryId":"","nodeId":this.nodeId},
				   "structureKeys":[]}
		   
		   var mmForm =document.getElementById("outputPanelForm");
	    	  
	    	  var checkBoxObj = null;

	    	  
	    	  //toggle checkbox
	    	  
	    	  var arrayKeys = new Array();
	    	  
	    	  var foundCount = 0;
	    	  var molecule = null;
	    	  for (var i=0; i < mmForm.elements.length ;i++)
	    	  {
	    		  checkBoxObj = mmForm.elements[i];
		    	  if(checkBoxObj.type == "checkbox" && checkBoxObj.name == "moleculeCheckBoxes" 
		    		  && checkBoxObj.checked == true)
		    	  {
		    		  
		    		  //{"id":"","sourceCode":""}
		    		  
		    		  molecule =  web.fromJson(checkBoxObj.value);
		    		  arrayKeys[foundCount] = {"id": molecule.structureKey,"sourceCode" : molecule.sourceCode};
		    		  
		    		  foundCount++;
		    	  }
	    	  }
	    	
	    	  
	    	  reagentNodeDT.structureKeys = arrayKeys;
	    	  
	    	  //call function
	    	  
	    	 var url = contextRoot+"controller/commas/ALDaSMgr.importReagentNode";
	         
	    	  web.openAJAX(theAJAX,confirmImport, "POST", url, true, web.toJson(reagentNodeDT));
	     }
		catch(err)
		{
			alert("ERROR:"+err);
		}

	   }//-------------------------------------------------
	/**
   	 * Add import button 
   	 */
   this.confirmImport = confirmImport;
    function confirmImport()
    {
  	 
    	
    	var text = web.getResponseText(theAJAX);
     	  
     	  //get max collection
     	  if(text == null)
           {
         	return false;  
           }
           else if("" == text || text.length == 0 || "[]" == text)
           { 	
        	  return false;
           }
     	  
     	  
     	 alert('Records imported:'+text);
     	  
    }//---------------------------------------------------------------------------- 
	   	/**
	   	 * Add import button 
	   	 */
	   this.constructResultsMenuHTML = constructResultsMenuHTML;
	    function constructResultsMenuHTML()
	    {
	  	 //return " &nbsp;<input type='button' value='Import' onclick='aldasMerckRender.importSelected();'/>";
	    	return " &nbsp;";
	    }//---------------------------------------------------------------------------- 
}