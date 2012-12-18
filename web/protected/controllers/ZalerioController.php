<?php

class ZalerioController extends Controller
{
	public function actionIndex()
	{
		
	}
	
	public function actionPrivacypolicy(){
		$this->layout = false;
		if(Yii::app()->request->isAjaxRequest){
			$this->render('ajax_privacypolicy');
		}else{
			$this->render('privacypolicy');
		}
		exit;
	}
	
	public function actionTos(){
		$this->layout = false;
		if(Yii::app()->request->isAjaxRequest){
			$this->render('ajax_tos');
		}else{
			$this->render('tos');
		}
		
		exit;
	}
	
 	public function filters()
	{
		return array( 'accessControl');
	}
	
 	public function accessRules()
    {
        	return array(
        			array('allow',
        				  'actions'=>array('index','privacypolicy','tos'),
        				  'users'=>array('*'),
        			),
        	);
    }
}