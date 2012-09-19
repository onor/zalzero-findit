<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class Rating extends CFormModel
{
    public $rating_level,$comment_improvement,$comment_like,$email,$name;
    
    public function rules()
    {
        return array(
                    array('rating_level,email,name', 'required'),
                    array('email','email'),
                    array('comment_improvement','length','max'=>1000),
                    array('comment_like','length','max'=>1000),
                    array('name','length','max'=>50)
         
        );
    }
}
