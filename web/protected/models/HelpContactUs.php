<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



class HelpContactUs extends CFormModel
{
    public $first_name,$last_name,$email,$subject,$body,$verifyCode;
    
    public function rules()
    {
        return array(
                    array('first_name,email,subject,body', 'required'),
                    array('email','email'),
                    array('first_name,last_name,subject','length','max'=>50),
                    array('body','length','max'=>1000),
                    array('verifyCode', 'captcha', 'allowEmpty'=>false),

                    
        );
    }
}



