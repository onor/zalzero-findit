<?php

/**
 * This is the model class for table "zzgame".
 *
 * The followings are the available columns in table 'zzgame':
 * @property string $game_id
 * @property string $game_name
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property ZzgameinstBase[] $zzgameinsts
 * @property ZzgameinsttemplBase[] $zzgameinsttempls
 */
class ZzgameBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzgameBase the static model class
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
		return 'zzgame';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('game_id, game_name', 'required'),
			array('game_id', 'length', 'max'=>100),
			array('game_name', 'length', 'max'=>150),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('game_id, game_name, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zzgameinsts' => array(self::HAS_MANY, 'ZzgameinstBase', 'gameinst_game_id'),
			'zzgameinsttempls' => array(self::HAS_MANY, 'ZzgameinsttemplBase', 'gameinsttempl_game_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'game_id' => 'Game',
			'game_name' => 'Game Name',
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

		$criteria->compare('game_id',$this->game_id,true);
		$criteria->compare('game_name',$this->game_name,true);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}