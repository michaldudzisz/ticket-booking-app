java -cp C:\Users\micha\.m2\repository\com\h2database\h2\1.4.200\h2-1.4.200.jar org.h2.tools.RunScript -user sa -password password -url jdbc:h2:./cinema -script ./schema.sql

java -cp C:\Users\micha\.m2\repository\com\h2database\h2\1.4.200\h2-1.4.200.jar org.h2.tools.RunScript -user sa -password password -url jdbc:h2:./cinema -script ./data.sql


