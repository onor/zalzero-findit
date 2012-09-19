<?php

/**
 * This is the model class for table "zzzlrofiginst".
 *
 * The followings are the available columns in table 'zzzlrofiginst':
 * @property string $zlfiginst_id
 * @property string $zlfiginst_zlfig_id
 * @property integer $zlfiginst_valid
 * @property integer $zlfiginst_noofcolumns
 * @property integer $zlfiginst_noofrows
 * @property string $zlfiginst_orientation
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrofiginstcoord[] $zzzlrofiginstcoords
 * @property Zzzlrofig $zlfiginstZlfig
 * @property Zzzlrogameinstfig[] $zzzlrogameinstfigs
 */
class Zzzlrofiginst extends ZzzlrofiginstBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzzlrofiginst the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}