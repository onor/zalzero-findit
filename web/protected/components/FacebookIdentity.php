<?php

/**
 * UserIdentity represents the data needed to identity a user.
 * It contains the authentication method that checks if the provided
 * data can identity the user.
 */
class FacebookIdentity extends CUserIdentity
{
    public $_id;

    /**
    * Authenticates a Facebook user.
    */
    public function authenticate()
    {
        $auth=Yii::app()->authManager;
        //$this->_id = $this->password;
        //echo "this->_id : " + $this->_id;
        $users = Zzuser::model()->findByAttributes(array('user_fbid'=>$this->password));
        //print_r($users->attributes);
        if($users===null){
           // echo "user not found";
            $this->errorCode=self::ERROR_UNKNOWN_IDENTITY;
        }else{
            $this->_id = $users->user_id;
            $this->setState('user_fbid', $users->user_fbid);
            $this->setState('user_name', $users->user_name);
            $this->setState('user_email',$users->user_email);
            
            $role_name = strtolower(trim($users->userRole->role_name));
                $this->setState('role', $role_name);
                $this->setState('role_id', $users->userRole->role_id);
                $changed = false;
                if($auth->getAuthItem($role_name) === null){
                    $auth->createRole($role_name);
                    $changed = true;
                }
                
                if (!$auth->isAssigned($role_name,$users->user_id)) {
                    $auth->assign($role_name,$users->user_id);
                }

                if ($changed) {
                    $auth->save();
                }
                
                $this->setState('title', $users->user_email);
            
            //$this->setState('fbConnected', true);
            $this->errorCode=self::ERROR_NONE;
        }
        
        return $this->errorCode;
    }
    
    public function getId() {
        return $this->_id;
    }
}