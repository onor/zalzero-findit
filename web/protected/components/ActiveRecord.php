<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of ActiveRecord
 *
 * @author Shekhar
 */
class ActiveRecord extends CActiveRecord{
    public function behaviors() {
        return array('TimestampBehavior' => array(
            'class' => 'application.models.behaviors.TimestampBehavior', )
        );
    }
}

?>
