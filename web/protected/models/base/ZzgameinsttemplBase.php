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
 * @property ZzgameinstBase[] $zzgameinsts
 * @property ZzgameBase $gameinsttemplGame
 */
class ZzgameinsttemplBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzgameinsttemplBase the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'zzgameinsttempl';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('gameinsttempl_id, gameinsttempl_game_id, gameinsttempl_game_desc', 'required'),
			array('gameinst_paramnum1, gameinst_paramnum2, gameinst_paramnum3, gameinst_paramnum4, gameinst_paramnum5, gameinst_paramnum6, gameinst_paramnum7, gameinst_paramnum8, gameinst_paramnum9, gameinst_paramnum10', 'numerical', 'integerOnly'=>true),
			array('gameinsttempl_id, gameinsttempl_game_id', 'length', 'max'=>100),
			array('gameinsttempl_game_desc', 'length', 'max'=>300),
			array('gameinst_paramvar1, gameinst_paramvar2, gameinst_paramvar3, gameinst_paramvar4, gameinst_paramvar5', 'length', 'max'=>150),
			array('gameinst_paramdate1, gameinst_paramdate2, gameinst_paramdate3, gameinst_paramdate4, gameinst_paramdate5, create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('gameinsttempl_id, gameinsttempl_game_id, gameinsttempl_game_desc, gameinst_paramnum1, gameinst_paramnum2, gameinst_paramnum3, gameinst_paramnum4, gameinst_paramnum5, gameinst_paramnum6, gameinst_paramnum7, gameinst_paramnum8, gameinst_paramnum9, gameinst_paramnum10, gameinst_paramdate1, gameinst_paramdate2, gameinst_paramdate3, gameinst_paramdate4, gameinst_paramdate5, gameinst_paramvar1, gameinst_paramvar2, gameinst_paramvar3, gameinst_paramvar4, gameinst_paramvar5, create_time, update_time', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
			'zzgameinsts' => array(self::HAS_MANY, 'ZzgameinstBase', 'gameinst_gameinsttempl_id'),
			'gameinsttemplGame' => array(self::BELONGS_TO, 'ZzgameBase', 'gameinsttempl_game_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'gameinsttempl_id' => 'Gameinsttempl',
			'gameinsttempl_game_id' => 'Gameinsttempl Game',
			'gameinsttempl_game_desc' => 'Gameinsttempl Game Desc',
			'gameinst_paramnum1' => 'Gameinst Paramnum1',
			'gameinst_paramnum2' => 'Gameinst Paramnum2',
			'gameinst_paramnum3' => 'Gameinst Paramnum3',
			'gameinst_paramnum4' => 'Gameinst Paramnum4',
			'gameinst_paramnum5' => 'Gameinst Paramnum5',
			'gameinst_paramnum6' => 'Gameinst Paramnum6',
			'gameinst_paramnum7' => 'Gameinst Paramnum7',
			'gameinst_paramnum8' => 'Gameinst Paramnum8',
			'gameinst_paramnum9' => 'Gameinst Paramnum9',
			'gameinst_paramnum10' => 'Gameinst Paramnum10',
			'gameinst_paramdate1' => 'Gameinst Paramdate1',
			'gameinst_paramdate2' => 'Gameinst Paramdate2',
			'gameinst_paramdate3' => 'Gameinst Paramdate3',
			'gameinst_paramdate4' => 'Gameinst Paramdate4',
			'gameinst_paramdate5' => 'Gameinst Paramdate5',
			'gameinst_paramvar1' => 'Gameinst Paramvar1',
			'gameinst_paramvar2' => 'Gameinst Paramvar2',
			'gameinst_paramvar3' => 'Gameinst Paramvar3',
			'gameinst_paramvar4' => 'Gameinst Paramvar4',
			'gameinst_paramvar5' => 'Gameinst Paramvar5',
			'create_time' => 'Create Time',
			'update_time' => 'Update Time',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		// Warning: Please modify the following code to remove attributes that
		// should not be searched.

		$criteria=new CDbCriteria;

		$criteria->compare('gameinsttempl_id',$this->gameinsttempl_id,true);
		$criteria->compare('gameinsttempl_game_id',$this->gameinsttempl_game_id,true);
		$criteria->compare('gameinsttempl_game_desc',$this->gameinsttempl_game_desc,true);
		$criteria->compare('gameinst_paramnum1',$this->gameinst_paramnum1);
		$criteria->compare('gameinst_paramnum2',$this->gameinst_paramnum2);
		$criteria->compare('gameinst_paramnum3',$this->gameinst_paramnum3);
		$criteria->compare('gameinst_paramnum4',$this->gameinst_paramnum4);
		$criteria->compare('gameinst_paramnum5',$this->gameinst_paramnum5);
		$criteria->compare('gameinst_paramnum6',$this->gameinst_paramnum6);
		$criteria->compare('gameinst_paramnum7',$this->gameinst_paramnum7);
		$criteria->compare('gameinst_paramnum8',$this->gameinst_paramnum8);
		$criteria->compare('gameinst_paramnum9',$this->gameinst_paramnum9);
		$criteria->compare('gameinst_paramnum10',$this->gameinst_paramnum10);
		$criteria->compare('gameinst_paramdate1',$this->gameinst_paramdate1,true);
		$criteria->compare('gameinst_paramdate2',$this->gameinst_paramdate2,true);
		$criteria->compare('gameinst_paramdate3',$this->gameinst_paramdate3,true);
		$criteria->compare('gameinst_paramdate4',$this->gameinst_paramdate4,true);
		$criteria->compare('gameinst_paramdate5',$this->gameinst_paramdate5,true);
		$criteria->compare('gameinst_paramvar1',$this->gameinst_paramvar1,true);
		$criteria->compare('gameinst_paramvar2',$this->gameinst_paramvar2,true);
		$criteria->compare('gameinst_paramvar3',$this->gameinst_paramvar3,true);
		$criteria->compare('gameinst_paramvar4',$this->gameinst_paramvar4,true);
		$criteria->compare('gameinst_paramvar5',$this->gameinst_paramvar5,true);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}