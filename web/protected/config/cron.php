<?php

require_once( dirname(__FILE__) . '/../config/fbappconfig.php');
require_once( dirname(__FILE__) . '/../components/helpers.php');

return array(
        'basePath'=>dirname(__FILE__).DIRECTORY_SEPARATOR.'..',
        'name'=>'Cron',
        'preload'=>array('log'),
         'import'=>array(
                'application.components.*',
                'application.models.*',
                'application.models.base.*',
        ),
        // application components
        'components'=>array(
                'db'=>array(
//                      'class'=>'CDbConnection',
                        'connectionString' => 'pgsql:host=localhost;port=5432;dbname=zalzero',
                        'emulatePrepare' => true,
                        'username' => 'zalzero',
                        'password' => 'Cytzmlk1',
                        'charset' => 'utf8',

                ),
                'log'=>array(
                        'class'=>'CLogRouter',
                        'routes'=>array(
                                array(
                                        'class'=>'CFileLogRoute',
                                        'logFile'=>'cron.log',
                                        'levels'=>'error, warning',
                                ),
                                array(
                                        'class'=>'CFileLogRoute',
                                        'logFile'=>'cron_trace.log',
                                        'levels'=>'trace',
                                ),
                        ),
                ),
'functions'=>array(
                        'class'=>'application.extensions.functions.Functions',
                ),
        ),
);
