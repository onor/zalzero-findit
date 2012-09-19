<?php

/**
 * This is the model class for table "zzzlrogamebet".
 *
 * The followings are the available columns in table 'zzzlrogamebet':
 * @property string $zlgamebet_id
 * @property string $zlgamebet_zlgameround_id
 * @property string $zlgamebet_gameseat_id
 * @property string $zlgamebet_bettime
 * @property integer $zlgamebet_betcoord
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property ZzzlrogameroundBase $zlgamebetZlgameround
 * @property ZzgameseatBase $zlgamebetGameseat
 */
class ZzzlrogamebetBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzzlrogamebetBase the static model class
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
		return 'zzzlrogamebet';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('zlgamebet_id, zlgamebet_zlgameround_id, zlgamebet_gameseat_id, zlgamebet_betcoord', 'required'),
			array('zlgamebet_betcoord', 'numerical', 'integerOnly'=>true),
			array('zlgamebet_id, zlgamebet_zlgameround_id, zlgamebet_gameseat_id', 'length', 'max'=>100),
			array('zlgamebet_bettime, create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('zlgamebet_id, zlgamebet_zlgameround_id, zlgamebet_gameseat_id, zlgamebet_bettime, zlgamebet_betcoord, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zlgamebetZlgameround' => array(self::BELONGS_TO, 'ZzzlrogameroundBase', 'zlgamebet_zlgameround_id'),
			'zlgamebetGameseat' => array(self::BELONGS_TO, 'ZzgameseatBase', 'zlgamebet_gameseat_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'zlgamebet_id' => 'Zlgamebet',
			'zlgamebet_zlgameround_id' => 'Zlgamebet Zlgameround',
			'zlgamebet_gameseat_id' => 'Zlgamebet Gameseat',
			'zlgamebet_bettime' => 'Zlgamebet Bettime',
			'zlgamebet_betcoord' => 'Zlgamebet Betcoord',
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

		$criteria->compare('zlgamebet_id',$this->zlgamebet_id,true);
		$criteria->compare('zlgamebet_zlgameround_id',$this->zlgamebet_zlgameround_id,true);
		$criteria->compare('zlgamebet_gameseat_id',$this->zlgamebet_gameseat_id,true);
		$criteria->compare('zlgamebet_bettime',$this->zlgamebet_bettime,true);
		$criteria->compare('zlgamebet_betcoord',$this->zlgamebet_betcoord);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}