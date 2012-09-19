#! bin/bash
environment=$1

# if enviroment is local
# copy css,images,js,protected,sound,themes once directory back
if [ ${environment} = dev ]
then
cp -R  web/* ../
cp web/index-dev.php ../index.php
cp web/protected/config/fbappconfig_dev.php ../protected/config/fbappconfig.php
rm -rf ../protected/config/fbappconfig_dev.php
rm -rf ../protected/config/fbappconfig_staging.php
rm -rf ../protected/config/fbappconfig_prod.php
rm -rf ../index-dev.php
chmod -R 777 ../assets
chmod -R 777 ../protected/runtime
rm -rf ../union
mkdir ../union
cp -R union/* ../union/
mv ../union/union_dev.xml ../union/union.xml
rm -rf ../union/union_prod.xml
elif [ ${environment} = staging ]
then
#if environment is dev
# copy css,images,js,protected,sound,themes once directory back
cp -R  web/* ../
cp web/index-dev.php ../index.php
cp web/protected/config/fbappconfig_staging.php ../protected/config/fbappconfig.php
rm -rf ../protected/config/fbappconfig_dev.php
rm -rf ../protected/config/fbappconfig_staging.php
rm -rf ../protected/config/fbappconfig_prod.php
rm -rf ../index-dev.php
chmod -R 777 ../assets
chmod -R 777 ../protected/runtime
rm -rf ../union
mkdir ../union
cp -R union/* ../union/
mv ../union/union_dev.xml ../union/union.xml
rm -rf ../union/union_prod.xml
else
#if environment is production
# copy css,images,js,protected,sound,themes once directory back
cp -R  web/* ../
cp web/index.php ../index.php
cp web/protected/config/fbappconfig_prod.php ../protected/config/fbappconfig.php
rm -rf ../protected/config/fbappconfig_dev.php
rm -rf ../protected/config/fbappconfig_staging.php
rm -rf ../protected/config/fbappconfig_prod.php
rm -rf ../index-dev.php
chmod -R 777 ../assets
chmod -R 777 ../protected/runtime
rm -rf ../union
mkdir ../union
cp -R union/* ../union/
mv ../union/union_prod.xml ../union/union.xml
rm -rf ../union/union_dev.xml
fi
