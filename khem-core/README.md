Testing
`
Install Joelib library

	mvn install:install-file -Dfile=libs/JOELib2nyla.jar -DartifactId=JOELib2nyla  -Dpackaging=jar  -DgroupId=joelib2 -Dversion=0.1
	mvn install:install-file -Dfile=itext-0.94.jar -DartifactId=itext  -Dpackaging=jar  -DgroupId=com.lowagie -Dversion=0.94


Setup Eclipse PRoject

	gradle eclipse

Build without tests


	gradle install -x test


Start GemFire

	start locator  --dir=runtime/locator --bind-address=localhost --J=-Dgemfire.jmx-manager-hostname-for-clients=localhost --J=-Dgemfire.jmx-manager-bind-address=localhost --J=-Dgemfire.http-service-bind-address=localhost --http-service-port=0  --name=locator  --port=10334 --J=-Dgemfire.jmx-manager-port=11099



	start server --name=server --dir=runtime/server --bind-address=localhost  --server-bind-address=localhost --locators=localhost[10334] 
	
	
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/ant.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/bin2hex.pl
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/com-sun-javadoc.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/com-sun-javadoc.jar.zip
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/com-sun-tools-doclets-Taglet.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/crimson.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/itext-0.94.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/j3dcore.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/j3dutils.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/jaxp.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/jdom.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/Jmol.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/junit-3.8.1.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/sgt.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/vecmath.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/weka.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/lib/xerces.jar
	y
	deploy --jar=/Projects/solutions/Cheminformatics/dev/khem/khem-joelib/build/libs/khem-joelib-0.0.1-SNAPSHOT.jar
	y
	deploy --jar=/Projects/solutions/nyla/dev/nyla.solutions.core/build/libs/nyla.solutions.core-1.1.4-SNAPSHOT.jar
	y
	deploy --jar=/Projects/solutions/gedi/dev/gedi-geode/gedi-geode-extensions-core/target/gedi-geode-extensions-core-1.1.4-SNAPSHOT.jar
	y


	configure pdx --disk-store=BACKUP --read-serialized=true --auto-serializable-classes=.*

	create region --name=molecules --type=PARTITION

	stop server --name=server

	start server --name=server --dir=runtime/server --bind-address=localhost  --server-bind-address=localhost --locators=Gregorys-MBP[10334] --classpath="/Projects/solutions/Cheminformatics/dev/khem/khem-functions/libs/*"

	deploy --jar= /Projects/solutions/Cheminformatics/dev/khem/khem-functions/build/libs/khem-functions-0.0.1-SNAPSHOT.jar




	cd /devtools/integration/scdf/

	java -jar spring-cloud-dataflow-server-local-1.3.1.RELEASE.jar --spring.cloud.deployer.local.freeDiskSpacePercentage=0


app register --name khem-streams --type sink --uri file:///Projects/solutions/Cheminformatics/dev/khem/khem-streams/build/libs/khem-streams-0.0.1-SNAPSHOT.jar


file --directory=/Projects/solutions/Cheminformatics/dev/khem/khem-core/src/test/resources/input/data --filename-pattern=test.sdf --mode=ref | khem-streams


stream create definition file --directory=/Projects/solutions/Cheminformatics/dev/khem/khem-core/src/test/resources/input/data --filename-pattern=test.sdf --mode=ref | khem-streams

deployer.*.local.inheritLogging=true
