# Englishbot

![Build](https://github.com/A-Romany/englishbot/actions/workflows/maven.yml/badge.svg)
![Deploy](https://github.com/A-Romany/englishbot/actions/workflows/deploy.yml/badge.svg)
[![codecov](https://codecov.io/gh/A-Romany/englishbot/branch/master/graph/badge.svg?token=GS49GZ6C50)](https://codecov.io/gh/A-Romany/englishbot)

This Ukrainian-speaking [Telegram chat bot](https://t.me/AhEnglishBot) is designed to help you learn English words. 
Add them to your dictionary and start training using the **amazing** Lesson feature.

The master version of the bot is deployed on Heroku app server, you can play with it [here](https://t.me/AhEnglishBot)
###### Note that Heroku server falls asleep if inactive for 30 minutes, and it takes some time to wake it up - usually no more than a few seconds. Please, be patient when you send the first message to the bot


# Requirements

* Java 1.8
* [Maven](http://maven.apache.org) (for building)
* PostgreSQL server

# Build Instructions

This is a [Spring Boot](https://spring.io/projects/spring-boot) project. To run it from the command line, use this command

`mvn spring-boot:run`

This command will build the project, start the application within embedded Tomcat server, start [ngrok](https://ngrok.com/) tunnel to localhost and fire [Telegram WebHook](https://core.telegram.org/bots/api#setwebhook) to the bot which token is placed in `telegram.token` in `application.yml`. 

Once the webhook has successfully fired, you can start chatting.