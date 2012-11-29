<?php

class DeauthoriseController extends Controller
{
	public function actionIndex()
	{
		//$this->render('index');
		
		
		list($encoded_sig, $payload) = explode('.', $_REQUEST["signed_request"], 2);
		$signedRequestData = json_decode(base64_decode(strtr($payload, '-_', '+/')), true);
		
		//echo Yii::app()->user->getId();
		Yii::log(Yii::app()->user->getId().'gdfgdf', CLogger::LEVEL_ERROR, 'Any category/label will work');


foreach($signedRequestData as $val){
	Yii::log($key.'=>'.$val, CLogger::LEVEL_ERROR, 'Any category/label will work');
}
		
Yii::log($_REQUEST["signed_request"], CLogger::LEVEL_ERROR, 'Any category/label will work');
	/* 	
		list($encoded_sig, $payload) = explode('.', $_REQUEST["signed_request"], 2);
		$signedRequestData = json_decode(base64_decode(strtr($payload, '-_', '+/')), true);
		
		Yii::log($signedRequestData, CLogger::LEVEL_ERROR, 'Any category/label will work');
		
		Yii::log(Yii::app()->user->getId(), CLogger::LEVEL_ERROR, 'Any category/label will work'); */
	
	}

	
 public function filters()
	{
		// return the filter configuration for this controller, e.g.:
		return array( 'accessControl');
	}
	
 	public function accessRules()
    {
        	return array(
        			array('allow',
        					'users'=>array('*'),
        			),
        	);
    }
}