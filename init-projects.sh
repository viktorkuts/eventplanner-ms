#!/bin/bash

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=customers-service \
--package-name=com.viktorkuts.customers \
--groupId=com.viktorkuts.customers \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
customers-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=tickets-service \
--package-name=com.viktorkuts.tickets \
--groupId=com.viktorkuts.tickets \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
tickets-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=venues-service \
--package-name=com.viktorkuts.venues \
--groupId=com.viktorkuts.venues \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
venues-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=events-service \
--package-name=com.viktorkuts.events \
--groupId=com.viktorkuts.events \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
events-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api-gateway \
--package-name=com.viktorkuts.apigateway \
--groupId=com.viktorkuts.apigateway \
--dependencies=web,webflux,validation,hateoas \
--version=1.0.0-SNAPSHOT \
api-gateway
