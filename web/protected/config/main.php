<?php

// uncomment the following to define a path alias
// Yii::setPathOfAlias('local','path/to/local-folder');

// This is the main Web application configuration. Any writable
// CWebApplication properties can be configured here.
require_once( dirname(__FILE__) . '/../components/helpers.php');
require_once( dirname(__FILE__) . '/../config/fbappconfig.php');
ob_start('My_OB');
function My_OB($str, $flags)
{
    //remove UTF-8 BOM
    $str = preg_replace("/\xef\xbb\xbf/","",$str);
    
    return $str;
}
return array(
	'basePath'=>dirname(__FILE__).DIRECTORY_SEPARATOR.'..',
	'name'=>'Zalerio Game',

	// preloading 'log' component
	'preload'=>array('log'),

	// autoloading model and component classes
	'import'=>array(
		'application.models.*',
            'application.models.base.*',
             'application.components.*',
            'application.extensions.KEmail.KEmail',
            'application.controllers.*',	
	),

	'modules'=>array(
		// uncomment the following to enable the Gii tool
		
		'gii'=>array(
			'class'=>'system.gii.GiiModule',
			'password'=>'standard',
		 	// If removed, Gii defaults to localhost only. Edit carefully to taste.
			'ipFilters'=>array('127.0.0.1','::1'),
                        'generatorPaths'=>array(
                            'application.gii',   // a path alias
                        ),
		),
		
	),

	// application components
	'components'=>array(
		'user'=>array(
			'allowAutoLogin' => false
		),
		// uncomment the following to enable URLs in path-format
		
		'urlManager'=>array(
			'urlFormat'=>'path',
                        'showScriptName'=>false,
			'rules'=>array(
				'<controller:\w+>/<id:\d+>'=>'<controller>/view',
				'<controller:\w+>/<action:\w+>/<id:\d+>'=>'<controller>/<action>',
				'<controller:\w+>/<action:\w+>'=>'<controller>/<action>',
			),
		),
             
                'email'=>array(
                    'class'=>'KEmail',
                    'host_name'=>'localhost', //Hostname or IP of smtp server
                ),
		
                /*
		'db'=>array(
			'connectionString' => 'sqlite:'.dirname(__FILE__).'/../data/testdrive.db',
		),
                 * 
                 */
		// uncomment the following to use a MySQL database
		
		'db'=>array(
			'connectionString' => 'pgsql:host=localhost;port=5432;dbname=zalzero',
			//'emulatePrepare' => true,
			'username' => 'zalzero',
			'password' => 'Cytzmlk1',
			'charset' => 'utf8',
		),
                
                'authManager'=>array(
                    'class'=>'CDbAuthManager',
                    'connectionID'=>'db',
                                'itemTable'=>'authitem',
                            // The assignmentTable name (default:authassignment)
                                'assignmentTable'=>'authassignment',
                            // The itemChildTable name (default:authitemchild)
                                'itemChildTable'=>'authitemchild',
                ),
		
		'errorHandler'=>array(
			// use 'site/error' action to display errors
                        'errorAction'=>'site/error',
                ),
		'log'=>array(
			'class'=>'CLogRouter',
			'routes'=>array(
				array(
					'class'=>'CFileLogRoute',
					'levels'=>'error, warning',
				),
				// uncomment the following to show log messages on web pages
				
				array(
					'class'=>'CWebLogRoute',
				),
				
			),
		),
	),

	// application-level parameters that can be accessed
	// using Yii::app()->params['paramName']
	'params'=>array(
		// this is used in contact page
		'adminEmail'=>'webmaster@example.com',
	),
);
