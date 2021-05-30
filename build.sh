# you should have maven installed. In case you don't have, run <sudo apt-get install maven>
# resolve dependencies, build app:
mvn compile

# prepare database:
cd ./database
sh ./database_setup.sh
cd ..

# create executable:
mvn clean install
