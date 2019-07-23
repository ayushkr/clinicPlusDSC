cd /home/akr/Desktop/clinicplus/netbeansProject; JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 /home/common/apps/netbeans_8.2_akrSized_359MB/java/maven/bin/mvn -f /home/akr/Desktop/clinicplus/netbeansProject/pom.xml "-Dexec.args=-classpath %classpath in.srisrisri.clinic.Starter" -Dexec.executable=/usr/lib/jvm/java-8-openjdk-amd64/bin/java org.codehaus.mojo:exec-maven-plugin:1.2.1:exec &

cd /home/common/clinicPlus/netbeansProject/database/current;
java -jar /home/common/clinicPlus/database/current/SRI_HMS_server.jar 
;
