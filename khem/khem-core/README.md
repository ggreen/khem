
Install Joelib library

	mvn install:install-file -Dfile=libs/joelib2.jar -DartifactId=joelib2  -Dpackaging=jar  -DgroupId=joelib2 -Dversion=0.1
	
	
Setup Eclipse PRoject
	
	gradle eclipse

Build without tests


	gradle install -x test