<?php

/**
 * This is the model class for table "zzzlrofiginstcoord".
 *
 * The followings are the available columns in table 'zzzlrofiginstcoord':
 * @property string $zlfiginstcoord_id
 * @property string $zlfiginstcoord_zlfiginst_id
 * @property integer $zlfiginstcoord_posx
 * @property integer $zlfiginstcoord_posy
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrofiginst $zlfiginstcoordZlfiginst
 */
class Zzzlrofiginstcoord extends ZzzlrofiginstcoordBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzzlrofiginstcoord the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}