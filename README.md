# Spring Boot Application Google App Engine Standard with Java 11

This sample shows how to deploy a [Spring Boot](https://spring.io/projects/spring-boot)
application to Google App Engine standard.

See the [Quickstart for Java in the App Engine Standard Environment][ae-docs] for more
detailed instructions.

[ae-docs]: https://cloud.google.com/appengine/docs/standard/java11/quickstart

## Deploying

```bash
gcloud auth login

gcloud init

gcloud app deploy
```

To view our app, use command:
```
gcloud app browse
```
Or navigate [here](https://letter-game-2.uc.r.appspot.com/).
