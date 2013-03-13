<?php

class facebookCredetials {

	public $config;

	public function  __construct() {

		$config = new stdClass();

		$config->appId			=	'392619034110411';
		$config->appSecretId	=	'f6cdf2e164dccb995a69f56128e8bafe';
		$config->canvasPage		=	'http://apps.facebook.com/zalzerostaging/';
		$config->canvasUrl		=	'http://staging.zalerio.com/';
		$config->appName		=	'Zalerio';

        $config->practiceFBId	=	'100003951708198';
		
		$this->config = $config;

	}
}
