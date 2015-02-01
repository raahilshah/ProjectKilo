cd ProjectKilo/
mvn clean package
cp target/ProjectKiloWebApp.war ../cultureglasses/files/default/ProjectKiloWebApp.war
cd ../cultureglasses/
rm .vagrant/machines/default/virtualbox/synced_folders
vagrant reload --provision
cd ..