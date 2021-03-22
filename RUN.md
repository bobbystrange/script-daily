# run script files

### send-mail

```bash
cd `dirname $0`
./gradlew :send-mail:jar
```

### mongo-migrate

```bash
cd `dirname $0`
./gradlew :mongo-migrate:bootJar

VERSION=`../gradlew :mongo-migrate:printVersion -q`

# mongodb://[username:password@]host1[:port1][,...hostN[:portN]][/[defaultauthdb][?options]]

java \
  -Dmongodb.source.url=mongodb://username1:password1@host1:post1/source_db \
  -Dmongodb.target.url=mongodb://username2:password2@host2:post2/target_db \
  -jar ./mongo-migrate/build/libs/mongo-migrate-${VERSION}.jar
```

### elasticsearch-migrate

```bash
cd `dirname $0`
./gradlew :elasticsearch-migrate:bootJar

VERSION=`../gradlew :elasticsearch-migrate:printVersion -q`

java \
  -Delastic.source.host= \
  -Delastic.source.port= \
  -Delastic.target.host= \
  -Delastic.source.port= \
  -jar ./elasticsearch-migrate/build/libs/elasticsearch-migrate-${VERSION}.jar

```


