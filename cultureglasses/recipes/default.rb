#
# Cookbook Name:: cultureglasses
# Recipe:: default
#
# Copyright (C) 2015 YOUR_NAME
#
# All rights reserved - Do Not Redistribute
#

include_recipe 'apt'
include_recipe 'tomcat'

cookbook_file "/var/lib/tomcat6/webapps/sample.war" do
  source "sample.war"
  mode 00744
  owner 'root'
  group 'root'
end

cookbook_file "/var/lib/tomcat6/webapps/Calendar.war" do
  source "Calendar.war"
  mode 00744
  owner 'root'
  group 'root'
end