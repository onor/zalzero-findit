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
 * @property ZzzlrogameroundBase $zlgamescoreZlgameround
 * @property ZzgameseatBase $zlgamescoreGameseat
 */
class ZzzlrogamescoreBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzzlrogamescoreBase the static model class
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
		return 'zzzlrogamescore';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('zlgamescore_id, zlgamescore_zlgameround_id, zlgamescore_gameseat_id', 'required'),
			array('zlgamescore_scored', 'numerical', 'integerOnly'=>true),
			array('zlgamescore_id, zlgamescore_zlgameround_id, zlgamescore_gameseat_id', 'length', 'max'=>100),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('zlgamescore_id, zlgamescore_zlgameround_id, zlgamescore_gameseat_id, zlgamescore_scored, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zlgamescoreZlgameround' => array(self::BELONGS_TO, 'ZzzlrogameroundBase', 'zlgamescore_zlgameround_id'),
			'zlgamescoreGameseat' => array(self::BELONGS_TO, 'ZzgameseatBase', 'zlgamescore_gameseat_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'zlgamescore_id' => 'Zlgamescore',
			'zlgamescore_zlgameround_id' => 'Zlgamescore Zlgameround',
			'zlgamescore_gameseat_id' => 'Zlgamescore Gameseat',
			'zlgamescore_scored' => 'Zlgamescore Scored',
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

		$criteria->compare('zlgamescore_id',$this->zlgamescore_id,true);
		$criteria->compare('zlgamescore_zlgameround_id',$this->zlgamescore_zlgameround_id,true);
		$criteria->compare('zlgamescore_gameseat_id',$this->zlgamescore_gameseat_id,true);
		$criteria->compare('zlgamescore_scored',$this->zlgamescore_scored);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}