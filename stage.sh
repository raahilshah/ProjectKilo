cd ProjectKilo/
rm -rf ~/.m2/repository
mvn clean install
cp target/ProjectKiloWebApp.war ../cultureglasses/files/default/ProjectKiloWebApp.war
cd ../cultureglasses/
rm .vagrant/machines/default/virtualbox/synced_folders
vagrant reload --provision
cd ..