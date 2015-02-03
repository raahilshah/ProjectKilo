#!/bin/bash

# Install project and generate .war
cd ./ProjectKilo/
if [ -e "pom.xml" ]
then
    mvn -U clean install
else
    echo "Install failed" >&2
    exit
fi

# Copy .war to the server project
if [ -e "target/ProjectKiloWebApp.war" ]
then
    cp ./target/ProjectKiloWebApp.war ../cultureglasses/files/default/ProjectKiloWebApp.war
else
    echo ".war file not found" >&2
    exit
fi

# Cleanup and run Vagrant
cd ../cultureglasses/
rm ./.vagrant/machines/default/virtualbox/synced_folders
sudo vagrant reload --provision

cd ..
