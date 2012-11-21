#!/bin/sh
java -Xms128M -Xmx512M -XX:MaxPermSize=512m -Dfile.encoding=UTF-8 -cp lib/union.jar:lib/jooq/jooq-2.0.0.jar:lib/stax-api-1.0.1.jar:lib/wstx-asl-3.2.6.jar:lib/postgresql-9.0-801.jdbc4.jar:lib/joda-time-2.1.jar net.user1.union.core.UnionMain start &
