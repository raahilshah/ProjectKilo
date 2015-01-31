# Install project and generate .war
cd ProjectKilo/
mvn clean install

# Move .war to the server project
cp target/ProjectKiloWebApp.war ../cultureglasses/files/default/ProjectKiloWebApp.war

# Cleanup and run Vagrant
cd ../cultureglasses/
rm .vagrant/machines/default/virtualbox/synced_folders
sudo vagrant reload --provision

cd ..
