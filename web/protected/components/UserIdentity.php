<?php

/**
 * UserIdentity represents the data needed to identity a user.
 * It contains the authentication method that checks if the provided
 * data can identity the user.
 */
class UserIdentity extends CUserIdentity
{
    public $_id;

    /**
    * Authenticates a user.
    * The example implementation makes sure if the username and password
    * are both 'demo'.
    * In practical applications, this should be changed to authenticate
    * against some persistent user identity storage (e.g. database).
    * @return boolean whether authentication succeeds.
    */
    public function authenticate()
    {
        $auth=Yii::app()->authManager;
        $users = Zzuser::model()->findByAttributes(array('user_email'=>$this->username));
        if($users===null){
            $this->errorCode=self::ERROR_USERNAME_INVALID;
        }else{
            if($users->user_password != md5($this->password)){
                $this->errorCode=self::ERROR_PASSWORD_INVALID;
            }else{
                $this->_id = $users->user_id;
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
                
                $this->errorCode=self::ERROR_NONE;
            }
       }
       //$this->setState('fbConnected', false);
       return !$this->errorCode;
    }
    
    public function getId() {
        return $this->_id;
    }
}