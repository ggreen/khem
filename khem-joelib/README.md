

	mvn install:install-file -Dfile=lib/weka.jar -DartifactId=weka  -Dpackaging=jar  -DgroupId=weka -Dversion=0.1
	mvn install:install-file -Dfile=lib/com-sun-javadoc.jar -DartifactId=javadoc  -Dpackaging=jar  -DgroupId=javadoc -Dversion=1.3
	mvn install:install-file -Dfile=lib/com-sun-tools-doclets-Taglet.jar  -DartifactId=com-sun-tools-doclets-Taglet  -Dpackaging=jar  -DgroupId=com-sun-tools-doclets-Taglet -Dversion=1.3
	
	 cp build/libs/khem-joelib-0.0.1-SNAPSHOT.jar ../khem-functions/libs/