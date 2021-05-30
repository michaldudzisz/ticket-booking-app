# This is path to maven repository location default on Ubuntu Linux.
# Please change it, if it is different on your machine.
MAVEN_REPOSITORY_PATH="~/.m2/repository"

java -cp $MAVEN_REPOSITORY_PATH/com/h2database/h2/1.4.200/h2-1.4.200.jar org.h2.tools.RunScript \
-user sa -password password -url jdbc:h2:./cinema -script ./schema.sql

java -cp $MAVEN_REPOSITORY_PATH/com/h2database/h2/1.4.200/h2-1.4.200.jar org.h2.tools.RunScript \
-user sa -password password -url jdbc:h2:./cinema -script ./data.sql


