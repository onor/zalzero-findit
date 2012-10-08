<?php

// change the following paths if necessary
$yii=dirname(__FILE__).'yii/framework/yii.php';
$config=dirname(__FILE__).'web/protected/config/cron.php';
// remove the following lines when in production mode
defined('YII_DEBUG') or define('YII_DEBUG',FALSE);
// specify how many levels of call stack should be shown in each log message
defined('YII_TRACE_LEVEL') or define('YII_TRACE_LEVEL',1);

require_once($yii);
//Yii::createWebApplication($config)->run();
Yii::createConsoleApplication($config)->run();
