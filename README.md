# CS-157A Group B4: Student Housing Database Application

## Setup & Run
**Database:** Run `groupb4-studenthousing.sql` in MySQL Workbench to create the schema and sample data. **Application:** Copy `app.properties.template` to `src/main/resources/app.properties` and fill in your MySQL credentials. Then run `MainApp.java` (requires JDK 21, Maven, and MySQL Connector/J 8.0.33 which is included in `pom.xml`).

## How It Was Built
We created an `app.properties` file to store JDBC connection info (URL, user, password) and built `DBConnector.java` to load the MySQL driver and establish connections. `MainApp.java` provides a console menu using Scanner for input and PreparedStatements for all SQL operations (SELECT, INSERT, UPDATE, DELETE) on Students, Rooms, and MaintenanceTickets tables. A transactional workflow demonstrates COMMIT and ROLLBACK when assigning students to rooms. See video demo for screenshots of the application in action.

## Downloads
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- [JDK 21 - Windows x64](https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_windows-x64_bin.zip)
- [JDK 21 - Mac AArch64](https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_macos-aarch64_bin.tar.gz)
- [JDK 21 - Mac x64](https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_macos-x64_bin.tar.gz)
- [JDK 21 - Linux x64](https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_linux-x64_bin.tar.gz)
