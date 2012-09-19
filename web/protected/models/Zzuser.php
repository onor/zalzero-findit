<?php

/**
 * This is the model class for table "zzuser".
 *
 * The followings are the available columns in table 'zzuser':
 * @property string $user_id
 * @property string $user_name
 * @property string $user_email
 * @property string $user_fbid
 * @property string $user_password
 * @property string $user_handle
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzgameseat[] $zzgameseats
 */
class Zzuser extends ZzuserBase
{
    /*  var $invitation_code; // Invitation code property
        var $decode_key = '36343cb7ac8fe16281aca9bdae86277df86cbd87';
      */  
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzuser the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

        /**
         * Add invition code rule
         * writen by pankaj anupam
         * @return array
         */
       /* public function rules()
	{
		$rules_array = parent::rules();
                $rules_array[] = array('invitation_code', 'required');
                $rules_array[] = array('invitation_code', 'validateInvitationCode');
                return $rules_array;
	}*/
        
        /**
         * Add invition code label
         * writen by pankaj anupam
         * @return string 
         */        
        public function attributeLabels()
	{
                $label_array = parent::attributeLabels();
                $label_array['invitation_code'] = 'Invitation Code';
		return $label_array;
            
	}
       /* 
        public function validateInvitationCode($attribute,$params){
            if(trim($this->invitation_code) != sha1($this->decode_key.trim($this->user_email))){                
                $this->addError('invitation_code','Incorrect Invitation Code.');
            }
        } */

}