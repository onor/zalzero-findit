<?php

class HelpController extends Controller
{
    
        public function actions()
        {
            return array(
                'captcha'=>array(
                'class'=>'CCaptchaAction',
                 'transparent'=>true,
                    'foreColor'=>0x4482BE
                    ),
                );
        }
        
        /*** This function will generate and validate the Help-contacts us form */
	public function actionContactus()
	{ 
                // check if its an ajax request
		if (!yii::app()->request->isAjaxRequest)
                {
                    Yii::app()->end();
                }
                 // check if we have to generate the contact us form or to validate the submitted value and send mail to support
                if (isset($_POST['email']))
                {
                    // generate the model which will contain all values submitted and will validate after
                    $model = new HelpContactUs();
                    // assign the submitted values to model
                    $model->attributes = array('first_name'=> $_POST['first_name'],
                                                'last_name'=>$_POST['last_name'],
                                                'email'=>$_POST['email'],
                                                'subject'=>$_POST['subject'],
                                                'body'=>$_POST['body'],    
                                                'verifyCode'=>$_POST['verifyCode']);
                    // this array will be having the message and status of request and will be sent back to client as response
                    $msgArr = array();
                    $msg = "";
                   // validate the submitted values
                    if($model->validate())
                    {
                        
                        $from = $_POST['email'];
                        $subject = $_POST['subject'];
                        $body = $_POST['body'];
                        $to = DESK_SUPPORT_MAIL;
                        // create a object that will send mail
                        $mail_obj = new sendEmail();
                        // send mail to support using send Grid
                         $mail_status = $mail_obj->notifyemail(array("to" => "$to", "subject" => "$subject", "from" => "$from", "body" => "$body", "fromname"=>$_POST['first_name'].' '.$_POST['last_name']));
                         // check if the mail has been sent successfully
                        if($mail_status['message'] =='success')
                        {
                            // shorten the name that will be displayed in the message popup
                            $name = getDisplayName($_POST['first_name'], $_POST['last_name']);
                            // Generate response message
                            $msgArr['msg'] = "Dear ".$name.", Thank you for your message! You will hear back from us shortly...";
                            $msgArr['status'] = 1;
                            echo CJSON::encode($msgArr);
                            Yii::app()->end();
                        }        
                        else
                        {
                            // if there is any error in sending mail
                            $msgArr['msg'] = "There was some error. Please try again.";
                            $msgArr['status'] = 0;
                            echo CJSON::encode($msgArr);
                            Yii::app()->end();
                        }
                    }
                    else
                    {
                        // if validation fails then set the message 
                        foreach($model->getErrors() as $key=>$arr)
                        {
                            // this will check if validation failed for name
                            if($key == 'first_name')
                            {
                                $msg = "Name should not be blank.";
                                break;
                            }
                            // this will check if validation failed for email
                            if($key == 'email')
                            {
                                $msg = "Email should be valid.";break;
                            }
                            // this will check if validation failed for subject
                            if($key == 'subject')
                            {
                                $msg = "Subject should not be blank.";break;
                            }
                            // this will check if validation failed for captcha
                            if($key == 'verifyCode')
                            {
                                $msg = "Please enter correct verification code.";break;
                            }
                            
                            if($key == 'body')
                            {
                                $msg = "Message should not be more than 1000 characters.";break;
                            }
                        }
                    }
                    if(empty($msg))
                    {
                        "There was some error. Please try again.";
                    }
                    $msgArr['msg'] = $msg;
                    $msgArr['status']=0;
                    echo CJSON::encode($msgArr);
                    Yii::app()->end();
                }
                
                // else this will render the contact us form and will be sent to client     
		$this->renderPartial('contactus');
                Yii::app()->end();
	}


	public function actionIndex()
	{
		$this->render('index');
	}
        
        
        
        // this function will submit user's rating, suggestions, likes and dislikes about game 
        public function actionRatezalerio()
	{ 
		// checks if its an ajax request
                if (!yii::app()->request->isAjaxRequest)
                {
                    Yii::app()->end();
                }
                
                // check if request contains rating_level value
                if (isset($_POST['rating_level']))
                {
                    // create a rating model that will validate the submitted values 
                    $model = new Rating();
                    // assign values to rating model
                    $model->attributes = array('rating_level'=> $_POST['rating_level'],
                                               'comment_improvement'=>$_POST['comment_improvement'],
                                               'comment_like'=>$_POST['comment_like'],
                                               'email'=>$_POST['email'],
                                                'name'=>$_POST['name']);
                    // this array will be having the message and status of request and will be sent back to client as response
                    $msgArr = array();
                    $msg = "";
                    // check if all required valid values are there
                    if($model->validate() && in_array($_POST['rating_level'], $GLOBALS['rating_level']))
                    {
                        // send mail to desk.com
                        $from = $_POST['email'];
                        $body = '<b>Game Rating : </b>'.$_POST['rating_level'].'<br> <b>Improvement Suggestions : </b>'.$_POST['comment_improvement'].'<br><br><b> Liked about game : </b>'.$_POST['comment_like'];
                        $to = DESK_SUPPORT_MAIL;
                        $mail_obj = new sendEmail();
                        $mail_status = $mail_obj->notifyemail(array("to" => "$to", "subject" => RATING_FEATURE_SUBJECT, "from" => "$from", "body" => "$body","fromname"=>$_POST['name']));
                         // check if mail has been sent successfully 
                        if($mail_status['message'] =='success')
                        {
                            $msgArr['msg'] = "Thank you for your suggestions.";
                            $msgArr['status'] = 1;
                            echo CJSON::encode($msgArr);
                            Yii::app()->end();
                        }        
                        else
                        {   
                            // if there was some error in sending mail
                            $msgArr['msg'] = "There was some error. Please try again.";
                            $msgArr['status'] = 0;
                            echo CJSON::encode($msgArr);
                            Yii::app()->end();
                        }
                    }
                    else
                    {
                       // if user has not set the rating_level 
                       $msg = "Please rate the game first.";
                    }
                    
                    $msgArr['msg'] = $msg;
                    $msgArr['status']=0;
                    echo CJSON::encode($msgArr);
                    Yii::app()->end();
                }
                
                    
		
	}
        
       
}
