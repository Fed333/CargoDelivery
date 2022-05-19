echo Rebuild application ...
mvn clean install
echo End of rebuilding.

echo Starting server ... 
touch log/startup_log.txt
nohup java -jar target/CargoDelivery-1.0-SNAPSHOT.jar > log/startup_log.txt &
echo Server launched finished. See logs below.

tail -f log/startup_log.txt
