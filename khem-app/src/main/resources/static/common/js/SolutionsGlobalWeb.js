/**
 * Usage
 * function makeRequest(aUrl)
   {

     var web = SolutionsGlobalWeb();
     
      var theAJAX = web.getAJAX();

      web.openAJAX(theAJAX,hello, "GET", aUrl, true, null);
   }
  
   function hello()
   {    
      var outText = web.getResponseText();

	  var output= document.getElementById("output");

	  output.innerHTML = outText;	    

   }//---------------------------------------------------
   
 */
function SolutionsGlobalWeb(theAJAX)
{	
	this.theAJAX = theAJAX;
	

	this.getValueFromRadioButton = getValueFromRadioButton;
    function getValueFromRadioButton(nodeList)
    {
        for(i=0; i< nodeList.length; i++)
        {
       	 if(nodeList.item(i).checked == true)
            {
       		  return searchForCriteriaRadioButton.item(i).value;
            }
        }
        
    }
	
	/**
	 * 
	 * @param text the JSON string
	 * @returns String version of JavaScript object
	 */
	this.fromJson = fromJson;
	function fromJson(text)
	{
		return eval("(" + text + ")");

		//return eval(text);	
	}
	//----------------------------------------------
	this.toJson = toJson;
	function toJson(jsonObject)
	{
	
		return JSON.stringify(jsonObject, null);
	}
	//----------------------
	this.byId = byId;
	function byId(id)
	{
		return document.getElementById(id);
		
	}
	//-----------------------------------------------------------------------------------------
	this.openAJAX = openAJAX;
	function openAJAX(http_request, aFunction, aHTTPMethod, aURL, aIsAsynchronous, aQueryData )
	{  
	
		if(http_request == undefined)
			return false;
		
		
	   //Set callback function
	   //alert("aURL="+aURL);
	   
	   http_request.onreadystatechange = aFunction;

	
	   // Open HTTP URL 
	   http_request.open(aHTTPMethod, aURL, aIsAsynchronous);
	
	   
	   //Send Data
	   http_request.send(aQueryData);
	
	
	  /* if(aHTTPMethod.toUpperCase() == "POST")
	   {
		   if(http_request == undefined)
				return true;
		   
	      http_request.setRequestHeader('Content-Type','application/x-ww-form-urlencoded');
	   }*/
	
	   return true;
	
	}//-----------------------------------------
	
	//   
	
	// http_request.onreadystatechange = function(){
	
	// do the thing
	
	//  };
	
	this.getAJAX = getAJAX;
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
	
	   this.getResponseText = getResponseText;
	   function getResponseText()
	   { 
		   
		   //alert("getResponseText");
		
			var response = null;

	
			 if(this.theAJAX.readyState == 4)
			 {
		   
				 if(this.theAJAX.status == 400)
				 {
					 alert('Page not found');
		
				      response = this.theAJAX.responseText;
				      
				      //TODO: support redirects
				 }	   
				 else 
			     if(this.theAJAX.status > 400 && this.theAJAX.status <= 500)
			     {
			        alert('There was a problem with the request. theAJAX.status='+this.theAJAX.responseText);
			     }	   
		         else
			     {
	
			      response = this.theAJAX.responseText;
			      
			      if(response == null)
			    	  response = "";
			     }
	
			}//end if
	
	
		 return response;
	
	   }//-----------------------------------------

	   this.makeRequest = makeRequest;
	   function makeRequest(theAJAX,aUrl)
	   {
	
	      openAJAX(this.theAJAX,hello, "GET", aUrl, true, null);
	
	   }

}