<?php

class facebookCredetials {

	public $config;

	public function  __construct() {

		$config = new stdClass();

		$config->appId			=	'264696926970805';
		$config->appSecretId	=	'81a310ca60b02f37ed5ff43f30c35701';
		$config->canvasPage		=	'http://apps.facebook.com/zalerio/';
		$config->canvasUrl		=	'http://zzprod2.zalerio.com/';
		$config->appName		=	'Zalerio';
		
		$this->config = $config;

	}
}