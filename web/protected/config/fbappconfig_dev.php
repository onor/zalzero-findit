<?php

class facebookCredetials {

	public $config;

	public function  __construct() {

		$config = new stdClass();

		$config->appId			=	'419756618072782';
		$config->appSecretId	=	'0d702ed8e96cc385896b75b692e2d1b9';
		$config->canvasPage		=	'http://apps.facebook.com/zalzero/';
		$config->canvasUrl		=	'https://localhost/zalerio/';
		$config->appName		=	'Zalerio';
		
		$this->config = $config;

	}
}

