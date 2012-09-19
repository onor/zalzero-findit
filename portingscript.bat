xcopy web\* ..\ /S /R /Q
del ..\index.php
ren ..\index-dev.php index.php
ren ..\protected\config\fbappconfig_dev.php fbappconfig.php
del ..\protected\config\fbappconfig_staging.php
del ..\protected\config\fbappconfig_prod.php
del ..\union
mkdir ..\union
xcopy union ..\union /S /R /Q
ren ..\union\union_dev.xml union.xml
del ..\union\union_prod.xml 