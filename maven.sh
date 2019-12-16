PWD1=/common/clinicPlusFolder/clinicPlusDSC
cd $PWD1; 
echo "cd to "$PWD1
JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 /common/common/apps/netbeans_8.2_akrSized_359MB/java/maven/bin/mvn -f pom.xml "-Dexec.args=-classpath %classpath in.srisrisri.clinic.Starter" -Dexec.executable=/usr/lib/jvm/java-8-openjdk-amd64/bin/java org.codehaus.mojo:exec-maven-plugin:1.2.1:exec &




