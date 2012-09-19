<?php

/**
 * This is the model class for table "zzzlrofig".
 *
 * The followings are the available columns in table 'zzzlrofig':
 * @property string $zlfig_id
 * @property string $zlfig_name
 * @property integer $zlfig_required
 * @property string $zlfig_remark
 * @property integer $zlfig_points
 * @property string $zlfig_zlfiggroup_id
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrofigcoord[] $zzzlrofigcoords
 * @property Zzzlrofiggroup $zlfigZlfiggroup
 * @property Zzzlrofiginst[] $zzzlrofiginsts
 */
class Zzzlrofig extends ZzzlrofigBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzzlrofig the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

}