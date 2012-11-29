<?php

class DeauthoriseController extends Controller
{
	public function actionIndex()
	{
		//$this->render('index');
		//echo Yii::app()->user->getId();
		Yii::log(Yii::app()->user->getId(), CLogger::LEVEL_ERROR, 'Any category/label will work');
	
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