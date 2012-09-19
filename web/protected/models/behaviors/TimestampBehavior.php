<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of TimestampBehavior
 *
 * @author Shekhar
 */
class TimestampBehavior extends CActiveRecordBehavior{
    public function beforeValidate($event) {
        if($this->owner->isNewRecord){
            $pkkey = $this->owner->tableSchema->primaryKey;
            $pkval = $this->owner->getPrimaryKey();
            
                if($pkval == null || $pkval == ''){
                    $cr = new CDbCriteria();
                    $cr->select = $pkkey;
                    $cr->order = "create_time desc";
                    $pkRec = $this->owner->model()->find($cr);
                    $newPrimaryKey = 1;
                    if($temp = intval($pkRec[$pkkey])){
                        while(1){
                            $newPrimaryKey =  $temp +  1;
                            $oldPkRec = $this->owner->model()->findByPk($newPrimaryKey);
                            if(empty($oldPkRec)){
                                break;
                            }else{
                                $temp++;
                            }
                        }
                    }
                    $this->owner->setPrimaryKey($newPrimaryKey);
                }
        }
        parent::beforeValidate($event);
    }
    public function beforeSave($event) {
        parent::beforeSave($event);
        if($this->owner->isNewRecord){
                //$this->owner->model()::
                
                
            $this->owner->create_time = new CDbExpression('now()');
        }
        
        $this->owner->update_time = new CDbExpression('now()');
    }
}

?>
