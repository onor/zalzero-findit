<?php

/**
 * This is the model class for table "zzzlrogameinstfig".
 *
 * The followings are the available columns in table 'zzzlrogameinstfig':
 * @property string $zlgameinstfig_id
 * @property string $zlgameinstfig_zlfiginst_id
 * @property string $zlgameinstfig_gameinst_id
 * @property integer $zlgameinstfig_posleft
 * @property integer $zlgameinstfig_postop
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrofiginst $zlgameinstfigZlfiginst
 * @property Zzgameinst $zlgameinstfigGameinst
 */
class Zzzlrogameinstfig extends ZzzlrogameinstfigBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzzlrogameinstfig the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}