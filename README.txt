mvn -N io.takari:maven:wrapper

./mvnw clean install
./mvnw.cmd clean install

mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install org.jacoco:jacoco-maven-plugin:report sonar:sonar

#Docker
# start service
docker compose up -d
# stop service
docker compose stop
# delete service
docker compose down
# access mysql
docker exec -it mysql-container mysql --user=root --password=JA


#SQL
CREATE LOGIN JA WITH PASSWORD='InsomniaRemix524!';
CREATE USER JA FOR LOGIN JA;
CREATE DATABASE MSSQL;
USE MSSQL;
GRANT control ON <table_name> to JA;