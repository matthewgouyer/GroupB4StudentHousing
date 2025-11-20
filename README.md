CS-157A: Group B4 Student Housing Database Application

1. Prerequisites

MySQL Server & MySQL Workbench


JDK 21.0.1 (build 21.0.1+12): 
Builds & Download links:
    Windows	64-bit	zip (sha256) 188M: https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_windows-x64_bin.zip
    
    Mac/AArch64	64-bit	tar.gz (sha256) 184M: https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_macos-aarch64_bin.tar.gz
    
    Mac/x64	64-bit	tar.gz (sha256) 186M: https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_macos-x64_bin.tar.gz
    
    Linux/AArch64	64-bit	tar.gz (sha256) 187M: https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_linux-aarch64_bin.tar.gz
    
    Linux/x64	64-bit	tar.gz (sha256) 189M: https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_linux-x64_bin.tar.gz

Maven

MySQL Connector/J: Included as a dependency in pom.xml.

2. Database Setup

Open MySQL Workbench.

RUN SQL script.

Execute the entire script against your local MySQL server. This creates the schema and populates it with test data.

3. Application Setup 

Locate the template file in the project root: app.properties.template.

Copy this file and paste it into the src/main/resources directory.

Rename the copy in the src/main/resources directory to app.properties.

Enter Your Credentials (url should not need to be changed)

4. Run App

So far you can just test the connection. 
