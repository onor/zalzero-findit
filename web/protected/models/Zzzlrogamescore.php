<?php

/**
 * This is the model class for table "zzzlrogamescore".
 *
 * The followings are the available columns in table 'zzzlrogamescore':
 * @property string $zlgamescore_id
 * @property string $zlgamescore_zlgameround_id
 * @property string $zlgamescore_gameseat_id
 * @property integer $zlgamescore_scored
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzzlrogameround $zlgamescoreZlgameround
 * @property Zzgameseat $zlgamescoreGameseat
 */
class Zzzlrogamescore extends ZzzlrogamescoreBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzzlrogamescore the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}