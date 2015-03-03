<%@page import="solutions.global.util.Text"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<jsp:useBean id="ui" class="solutions.global.web.commas.CommandUIDecorator" scope="session"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <title>RESTful Web Service Test Tool</title>

        <!-- Grab some style sheets for the built-in tundra theme that Dojo offers for
         styling the page, equipping you with a professional style without
any additional
         effort required. -->        
         <!-- script type="text/javascript">
            djConfig = {
                        baseUrl = "<c:out value="${pageContext.request.contextPath}/common"/>"
            }
         </script-->
         
  
        <link rel="stylesheet" type="text/css" href="http://o.aolcdn.com/dojo/1.1/dojo/resources/dojo.css" />
        <link rel="stylesheet" type="text/css" href="http://o.aolcdn.com/dojo/1.1/dijit/themes/tundra/tundra.css" />        
          <link  rel="stylesheet" type="text/css" href="<c:out value="${pageContext.request.contextPath}/common/css/site.css"/>" />           

        <!-- Load Base and specify that the page should be parsed for dijits after it
          loads --> 
 		 <script
            djConfig="parseOnLoad:true",
            type="text/javascript"
            src="http://o.aolcdn.com/dojo/1.1/dojo/dojo.xd.js">
        </script>     <script type="text/javascript">
            dojo.require("dojo.parser");
            dojo.require("dijit.form.TextBox");
            dojo.require("dijit.form.ValidationTextBox");
            dojo.require("dijit.form.Button");
            dojo.require("dijit.form.ComboBox");     
            
            //dojo.addOnLoad(function(){
                  //build crates
                        //var cratesList = dojo.byId("crates");
                      //alert("cratesList.length="+cratesList.innerHTML);
               //   });
                        
        </script>
        
        <script type="text/javascript">
            function executeFunction()
            {
                  clearOutput();
                  
                   //Get form
                         
                   //convert to JSON
                   //var jsonFormText = escape(dojo.formToJson("functionBuiltForm"));
                   var jsonFormText = dojo.byId("requestJson").value;
                   
                   //alert("jsonFormText:"+jsonFormText);
                   
                   /*if(jsonFormText == "{}")
                   {
                               var requestFunctionTextRecord = dojo.byId("requestFunctionText");
                               if(requestFunctionTextRecord != null && requestFunctionTextRecord != undefined)
                               {
                                     jsonFormText = requestFunctionTextRecord.value;
                               } 
                   }*/
                   
                         
                  // alert("executeFunction="+jsonFormText);
                   var theSelectedFunctionText = getSelectedFunctionText();
                   
                   if(theSelectedFunctionText == null)
                         return;
                   
                         var executeFunctionUrl = "<c:out value="${pageContext.request.contextPath}/controller/commas/"/>"+dojo.byId("commas").value+"."+theSelectedFunctionText;
                        //alert("executeFunctionUrl:"+executeFunctionUrl);

               			dojo.rawXhrPost({
                                     url: executeFunctionUrl,
                                     handleAs: "text",
                                     headers : {"Content-Type":"text/html;charset=ISO-8859-1"},
                                    postData : jsonFormText,
                                     load : function(response, ioArgs) {
                                                //Set some element 
                                                //dojo.byId("outputPanel").innerHTML = escape(response);
                                                      println(response);
                                                return response;
                                          },
                                          //---------------------------------------------------
                                          error: function(response, ioArgs) {
                                                //console.log("error xhrGet", response, ioArgs);
                                                //alert(ioArgs.xhr.responseText);
                                                printError(ioArgs.xhr.responseText);
                                                return response;
                                          }
                                                 
                                    });
            }//-------------------------------------------------
            function println(text)
            {
                  //Set some element 
                  
                  if(text == null || "" == text || text.length == 0)
                	  text = "null";
                  
                  dojo.byId("outputPanel").innerHTML = text;
            }//--------------------------------------------------
            function printError(text)
            {
                  //Set some element 
                              dojo.byId("outputPanel").innerHTML = "<div style='color:red'>"+text+"</div>";
            }//--------------------------------------------------
            
            function displayCommandSelect()
            {
                  clearOutput();
                  clearOutputType();
                  
                  var displayFunctionSelectCrateName = dojo.byId("commas").value;
                  
                  //Clear input form
                  dojo.byId("functionBuiltForm").innerHTML = "";                    
                  
                  var displayCommandSelectUrl = "<c:out value="${pageContext.request.contextPath}/controller/commas"/>/solutions.global.web.commas.WebCommasCatalog.getCommasInfo?"+dojo.byId("commas").value;
                  //get functions
                 //alert("displayCommandSelectUrl="+displayCommandSelectUrl);
                  
                 dojo.xhrGet({
                               url: displayCommandSelectUrl,
                               handleAs: "json",            
                               load : function(response, ioArgs) {
                            	   
                                          //Set some element 
                                          //dojo.byId("outputPanel").innerHTML = escape(response);
                                          var selectHTML = "<label>Command:</label><select id='command' onchange='displayForm();' name='command'><option></option>";
                                          
                                          //alert("response="+dojo.toJson(response));
                                          
                                  	    //alert("i="+dojo.toJson(i));
                                          //var responseObj = dojo.fromJson(response);
                  
                                          for(i in response.commandFacties)
                                          {
                                        
                                                //alert("i="+response.commandFacties[i]);
                                                selectHTML = selectHTML +"<option>"+response.commandFacties[i].simpleName+"</option>";
                                          }
                                          selectHTML = selectHTML + "</select><br/>";
                                          
                                          //alert("selectHTML="+selectHTML);
                                          
                                          dojo.byId("functionSelectCanvas").innerHTML= selectHTML;
                                          
                                          return response;
                                    },
                                    //---------------------------------------------------
                                    error: function(response, ioArgs) {
                                          dojo.byId("outputPanel").innerHTML = ioArgs.xhr.responseText;
                                          return response;
                                    }
                              });
            }//--------------------------------------------------
            function getSelectedFunctionText()
            {
                var selectedFunction = dojo.byId("command");
                  
                  if(selectedFunction.selectedIndex == 0)
                {
                        alert("Please select a command");
                        return null;
                }
                  
                  return selectedFunction.options[selectedFunction.selectedIndex].text;
            }//-----------------
            function clearOutput()
            {
                  dojo.byId("outputPanel").innerHTML = "";
                  
            }//-----------------------------------------
            function clearOutputType()
            {
                  dojo.byId("outputClassTypePanel").innerHTML = "";
            }//---------------------------------------------
            function displayForm()
            {
                  clearOutput();
            
                  var selectedCommasText = getSelectedFunctionText();
                  
                  if( selectedCommasText == null)
                        return;   //Function not selected
                  
                  //build request ex:
                  var getUrl = "<c:out value="${pageContext.request.contextPath}/controller/commas"/>/solutions.global.web.commas.WebCommasCatalog.getJsonCommasSchema?"+dojo.byId("commas").value+"."+selectedCommasText;
                  
                  //alert("getUrl:"+getUrl);
                  //http://localhost:8080/daf-ws/grid/com.medco.fabrix.grid.daf.ws.functions.WebDaffyCrate/getFunctionInputSchema?{crateName:com.medco.fabrix.grid.daf.ws.functions.WebDaffyCrate,functionName:getFunctionInputSchema}
                  dojo.xhrGet({
                                     url: getUrl,
                                     handleAs: "json",            
                                     load : function(response, ioArgs) {
                                                //console.log("success xhrGet", response, ioArgs);
                                                // alert("response="+dojo.toJson(response));
                                                 
                                                 //alert("response.argumentClassInfo.classType="+response.argumentClassInfo.classType);
                                                 
                                                if(response.commandFacts.returnClassInfo != undefined &&  response.commandFacts.returnClassInfo != null)
                                                {
                                                      dojo.byId("outputClassTypePanel").innerHTML = "<b>"+response.commandFacts.returnClassInfo.classSchema.className+"</b>";
                                                }
                                                
                                                //alert("response="+dojo.toJson(response));
                                                if(response.commandFacts.argumentClassInfo == null)
                                                {
                                                      var noArgHMTL = "No input arguments<br/> <input type='button' name='Execute' value='Execute' onclick='executeFunction();'/>";
                                                      
                                                      dojo.byId("functionBuiltForm").innerHTML = noArgHMTL;
                                                      
                                                      return response;
                                                }
                                                
                                                //Set some element 
                                                dojo.byId("functionBuiltForm").innerHTML = "<b>"+response.commandFacts.argumentClassInfo.classSchema.className+"</b><br/>";
                                                
                                                //Loop thru fieldSchemas
                                                var responseHTML ="<p> Date format: <%=Text.DATE_FORMAT%> ex: 01/12/1984 23:00:00:000</p>";
                                                
                                                
                                                if(response.commandFacts.argumentClassInfo != null 
                                                            && response.commandFacts.argumentClassInfo.classType != "primitive")
                                                {
                                                	responseHTML= responseHTML+"<textarea id='requestJson' name='requestJson' rows='4' cols='100'>"+response.jsonInput+"</textarea>"+"<br/>";
                                                    
                                                    
                                                      /*for(i in response.argumentClassInfo.classSchema.fieldSchemas)
                                                      {
                                                            if("serialVersionUID" == response.argumentClassInfo.classSchema.fieldSchemas[i].fieldName)
                                                                  continue; //skip
                                                            
                                                            responseHTML= responseHTML+"<label>"
                                                                                            +response.argumentClassInfo.classSchema.fieldSchemas[i].fieldName
                                                                                                            +"</label>";
                                                            
                                                            responseHTML= responseHTML+"<input name='"+response.argumentClassInfo.classSchema.fieldSchemas[i].fieldName+"'/>"
                                                             +"("+response.argumentClassInfo.classSchema.fieldSchemas[i].className+")<br/>";
                                                      }*/
                                                }
                                                else
                                                {
                                                      responseHTML= responseHTML+"<input id='requestFunctionText'/><br/>";
                                                }
                                                
                                                //Add form button
                                                responseHTML= responseHTML+"<input type='button' name='Execute' value='Execute' onclick='executeFunction();'/>";
                                                
                                                dojo.byId("functionBuiltForm").innerHTML = dojo.byId("functionBuiltForm").innerHTML+responseHTML;
                                          
                                                
                                                return response;
                                          },
                                          //---------------------------------------------------
                                          error: function(response, ioArgs) {
                                                console.log("error xhrGet", response, ioArgs);
                                                
                                                //Set some element 
                                                dojo.byId("functionFormPanel").innerHTML = ioArgs.xhr.responseText;                                                
                                                return response;
                                          }
                                                 
                                    });
               }//------------------------------------------------
               
               /*function decorateField(var classInfo)
               {
            	   if(classInfo != null 
                           && classInfo.classType != "primitive")
	               {
	                     for(i in classInfo.classSchema.fieldSchemas)
	                     {
	                           if("serialVersionUID" == classInfo.classSchema.fieldSchemas[i].fieldName)
	                                 continue; //skip
	                           
	                           responseHTML= responseHTML+"<label>"
	                                                           +response.argumentClassInfo.classSchema.fieldSchemas[i].fieldName
	                                                                           +"</label>";
	                           
	                           responseHTML= responseHTML+"<input name='"+response.argumentClassInfo.classSchema.fieldSchemas[i].fieldName+"'/>"
	                            +"("+response.argumentClassInfo.classSchema.fieldSchemas[i].className+")<br/>";
	                     }
	               }
	               else
	               {
	                     responseHTML= responseHTML+"<input id='requestFunctionText'/><br/>";
	               }
	           }*/
	           //----------------------------------------------------------
            
        </script>
    <head>

        <!-- Specify that the built-in tundra theme should be applied to
everything in the
          body of the page. (Dijit relies heavily on CSS so including the appropriate
          theme is crucial.)-->
        <body class="tundra">
		<div class="formPanel">
		
            <h1>RESTful Web Service Testing Tool:</h1>

            <form id="catalogForm">

                <!-- Weave some widgets into the page by supplying the tags and
including
                  a dojoType attribute so the parser can find them and swap
them out -->

                <div class="grouping">
                              <label>Services:</label>
                              <select name="commas" dojoType="dijit.form.ComboBox" id="commas" onchange="displayCommandSelect"  autoComplete="true">
                                    <c:forEach var="crate" items="${ui.commasInfos}">
                                          <option><c:out value="${crate.name}"></c:out></option>
                                    </c:forEach>
                              </select>
                              <br/>
                              <div id="functionSelectCanvas">
                              </div>
                                                                  
                    <!--  button dojoType="dijit.form.Button"
                     onClick="displayForm();">Build Form</button>
                  </div-->
            </form>
            
             <h2>Request</h2>
              <div id="functionFormPanel" class="grouping">
                  <form id="functionBuiltForm">
                        </form>
              </div>
                  
                  <br/>
      
                  <h2>Response</h2> 
                  <div id="outputClassTypePanel"></div>           
                  <div id="outputPanel" class="grouping">
                  </div>
         
         </div>
<!-- 
   Config.properties.gemfire.cache-xml-file=<%=solutions.global.util.Config.getProperty("gemfire.cache-xml-file","")%>
   Config.properties.gemfirePropertyFile=<%=solutions.global.util.Config.getProperty("gemfirePropertyFile","")%>
 -->
    </body>
</html>

