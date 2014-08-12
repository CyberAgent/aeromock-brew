%~d0
cd %~p0
cmd /k java -classpath ".\lib\aeromock-server\*;.\lib\aeromock-cli\*;.\lib\templates\aeromock-freemarker\*;lib\templates\aeromock-handlebars-java\*;lib\templates\aeromock-jade4j\*;lib\templates\aeromock-velocity\*;lib\templates\aeromock-groovy-template\*;lib\templates\aeromock-thymeleaf\*" ^
  -Xmx512m ^
  -Xms64m ^
  -XX:MaxPermSize=256m ^
  -XX:PermSize=64m ^
  ameba.aeromock.cli.AeromockCli %*
