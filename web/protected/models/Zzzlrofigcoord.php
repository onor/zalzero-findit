<?php

/**
 * This is the model class for table "zzzlrofigcoord".
 *
 * The followings are the available columns in table 'zzzlrofigcoord':
 * @property string $zlfigcoord_id
 * @property string $zlfigcoord_zlfig_id
 * @property integer $zlfigcoord_posx
 * @property integer $zlfigcoord_posy
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrofig $zlfigcoordZlfig
 */
class Zzzlrofigcoord extends ZzzlrofigcoordBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzzlrofigcoord the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}