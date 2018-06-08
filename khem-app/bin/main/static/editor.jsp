
<html>
<head>
<!--  -link type="text/css" rel="stylesheet"
	href="/asap-merck/common/css/global.css" -->
</head>
<body>
	<div class="globalWrapper">
		<div class="headerContainer">
			<div class="headerTop">
				<div class="logo">
					<a href="/" title="Merck & Co., Inc."> <img
						alt="Merck & Co., Inc."
						src="//a248.e.akamai.net/7/248/430/20091230155215/www.merck.com/images/global/logo_Merck_no_be_well.jpg">
					</a>
				</div>
			</div>
			<!-- ****************** JDRAW  **************************   -->
			<div>
			<form action="javascript:void(0);">
<table border="1">
    <tr>
        <td colspan="2">
            <applet
              code     = "com.symyx.draw.JDrawEditor"
              name     = "JDrawEditor"
              id       = "myJDrawApplet"
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
        </td>
    </tr>
</table>

<button onClick="transferMolecule();">Transfer</button>

</form>


<!-- If you want to test the ability to detect a change to the structure:
<script>JDrawEditor.setOnStructureChangedJS("alert('OnStructureChanged event fired: javascript executed');");</script>
-->

<!-- Define JavaScript functions associated with the four buttons -->
<script>
      function getMolString()
      {
        var applet = document.getElementById("myJDrawApplet");
        return applet.getMolString();
      }

      function setMolString(molstring)
      {
        var applet = document.getElementById("myJDrawApplet");
        applet.setMolString(molstring);
      }

      function getChimeString()
      {
        var applet = document.getElementById("myJDrawApplet");
        return applet.getChimeString();
      }

      function setChimeString(molstring)
      {
        var applet = document.getElementById("myJDrawApplet");
        applet.setChimeString(molstring);
      }

      function getRxnString()
      {
        var applet = document.getElementById("myJDrawApplet");
        return applet.getRxnString();
      }

      function clearMolecule()
      {
        document.getElementById("myJDrawApplet").clearMolecule();
      }

      function transferMolecule()
      {
    	  var searchApplet = opener.document.getElementById("myJDrawApplet");
    	  
    	  searchApplet.setMolString(this.getMolString());
    	  
    	  window.close();
      }
</script>
			</div>
		</div>
		
		
		<script>
		 var searchApplet = opener.document.getElementById("myJDrawApplet");
   	  
		 setMolString(searchApplet.getMolString());
	   	  
	   	 </script>
</body>
</html>