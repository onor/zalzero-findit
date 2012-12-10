<?php

class DeauthoriseController extends Controller
{
	public function actionIndex()
	{
		
		list($encoded_sig, $payload) = explode('.', $_REQUEST["signed_request"], 2);
		$signedRequestData = json_decode(base64_decode(strtr($payload, '-_', '+/')), true);
		

		Yii::log($signedRequestData["user_id"], CLogger::LEVEL_ERROR, 'user leving the game');
		
		$check_for_user_existence = Zzuser::model()->findByAttributes( array('user_fbid'=>$signedRequestData["user_id"]) );
		
		if(isset($check_for_user_existence)) {
			$check_for_user_existence->zzuser_status = 'invited';
			$check_for_user_existence->save();
			
			Yii::log($signedRequestData["user_id"], CLogger::LEVEL_ERROR, 'User leave the game: Status changed to invited ');
		}
		
		foreach($signedRequestData as $key=>$val){
			Yii::log($key.'=>'.$val, CLogger::LEVEL_ERROR, 'Any category/label will work');
		}
	}

	public function actionUnsubscribe()
	{	
		if( isset($_REQUEST['user_id']) ) {
			
			$usr = Zzuser::model()->findByAttributes( array('user_fbid'=>$_REQUEST['user_id']) );
			
			if(isset($usr)) {

				$usr->zzuser_subscribe_status = FALSE;
				$usr->save();
						
				echo '<h1>You have been successfully unsubscribed!</h1>';

				echo '<p>You have requested to be removed from our mailing list, which has been successful, <br />You will not receive any future correspondence from us.</p>';
				
			}
		}
	}
	
 	public function filters()
	{
		return array( 'accessControl');
	}
	
 	public function accessRules()
    {
        	return array(
        			array('allow',
        				  'actions'=>array('index','unsubscribe'),
        				  'users'=>array('*'),
        			),
        	);
    }
    
    
}