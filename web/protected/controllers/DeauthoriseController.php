<?php

class DeauthoriseController extends Controller
{
	public function actionIndex()
	{
		//$this->render('index');
		echo Yii::app()->user->getId();
	
	}

	
/* 	public function filters()
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
    } */
}