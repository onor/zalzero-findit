<?php

class facebookCredetials {

	public $config;

	public function  __construct() {

		$config = new stdClass();

		$config->appId			=	'138174016357229';
		$config->appSecretId	=	'15d9ae5012776fb9c876b434b8d199db';
		$config->canvasPage		=	'http://apps.facebook.com/zalzero/';
		$config->canvasUrl		=	'https://localhost/zalerio/';
		$config->appName		=	'Zalerio';
		
		$this->config = $config;

	}
}

