<?php

/**
 * This is the model class for table "zzgameinsttempl".
 *
 * The followings are the available columns in table 'zzgameinsttempl':
 * @property string $gameinsttempl_id
 * @property string $gameinsttempl_game_id
 * @property string $gameinsttempl_game_desc
 * @property integer $gameinst_paramnum1
 * @property integer $gameinst_paramnum2
 * @property integer $gameinst_paramnum3
 * @property integer $gameinst_paramnum4
 * @property integer $gameinst_paramnum5
 * @property integer $gameinst_paramnum6
 * @property integer $gameinst_paramnum7
 * @property integer $gameinst_paramnum8
 * @property integer $gameinst_paramnum9
 * @property integer $gameinst_paramnum10
 * @property string $gameinst_paramdate1
 * @property string $gameinst_paramdate2
 * @property string $gameinst_paramdate3
 * @property string $gameinst_paramdate4
 * @property string $gameinst_paramdate5
 * @property string $gameinst_paramvar1
 * @property string $gameinst_paramvar2
 * @property string $gameinst_paramvar3
 * @property string $gameinst_paramvar4
 * @property string $gameinst_paramvar5
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzgameinst[] $zzgameinsts
 * @property Zzgame $gameinsttemplGame
 */
class Zzgameinsttempl extends ZzgameinsttemplBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzgameinsttempl the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

	
}

//rename file 