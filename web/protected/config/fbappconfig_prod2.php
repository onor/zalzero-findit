<?php

class facebookCredetials {

	public $config;

	public function  __construct() {

		$config = new stdClass();

		$config->appId			=	'416871301709195';
		$config->appSecretId	=	'a8b54e647bc29ee2224b28a522b8d502';
		$config->canvasPage		=	'http://apps.facebook.com/zalerioprod/';
		$config->canvasUrl		=	'http://zalerio.com/';
		$config->appName		=	'Find It';
		
		$this->config = $config;

	}
}