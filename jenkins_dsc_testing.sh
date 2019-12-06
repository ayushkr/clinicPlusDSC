cd /home/akr2/.jenkins/workspace/dsc1; 
JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 /common/common/apps/netbeans_8.2_akrSized_359MB/java/maven/bin/mvn -f pom.xml "-Dexec.args=-classpath %classpath in.srisrisri.clinic.Starter" -Dexec.executable=/usr/lib/jvm/java-8-openjdk-amd64/bin/java org.codehaus.mojo:exec-maven-plugin:1.2.1:exec &

cd /home/akr2/NetBeansProjects/persisted/Databases/clinicPlus/current;
java -jar SRI_HMS_server.jar &;

