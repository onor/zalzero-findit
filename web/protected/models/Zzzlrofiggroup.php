<?php

/**
 * This is the model class for table "zzzlrofiggroup".
 *
 * The followings are the available columns in table 'zzzlrofiggroup':
 * @property string $zlfiggroup_id
 * @property string $zlfiggroup_name
 * @property integer $zlfiggroup_nooffigrequired
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrofig[] $zzzlrofigs
 */
class Zzzlrofiggroup extends ZzzlrofiggroupBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzzlrofiggroup the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}