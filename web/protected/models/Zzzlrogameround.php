<?php

/**
 * This is the model class for table "zzzlrogameround".
 *
 * The followings are the available columns in table 'zzzlrogameround':
 * @property string $zlgameround_id
 * @property string $zlgameround_gameinst_id
 * @property string $zlgameround_roundname
 * @property string $zlgameround_timestart
 * @property string $zlgameround_timeend
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrogamescore[] $zzzlrogamescores
 * @property Zzgameinst $zlgameroundGameinst
 * @property Zzzlrogamebet[] $zzzlrogamebets
 */
class Zzzlrogameround extends ZzzlrogameroundBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzzlrogameround the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}