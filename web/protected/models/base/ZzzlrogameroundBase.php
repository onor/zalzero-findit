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
 * @property ZzzlrogamescoreBase[] $zzzlrogamescores
 * @property ZzgameinstBase $zlgameroundGameinst
 * @property ZzzlrogamebetBase[] $zzzlrogamebets
 */
class ZzzlrogameroundBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzzlrogameroundBase the static model class
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
		return 'zzzlrogameround';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('zlgameround_id, zlgameround_gameinst_id', 'required'),
			array('zlgameround_id, zlgameround_gameinst_id', 'length', 'max'=>100),
			array('zlgameround_roundname', 'length', 'max'=>20),
			array('zlgameround_timestart, zlgameround_timeend, create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('zlgameround_id, zlgameround_gameinst_id, zlgameround_roundname, zlgameround_timestart, zlgameround_timeend, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zzzlrogamescores' => array(self::HAS_MANY, 'ZzzlrogamescoreBase', 'zlgamescore_zlgameround_id'),
			'zlgameroundGameinst' => array(self::BELONGS_TO, 'ZzgameinstBase', 'zlgameround_gameinst_id'),
			'zzzlrogamebets' => array(self::HAS_MANY, 'ZzzlrogamebetBase', 'zlgamebet_zlgameround_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'zlgameround_id' => 'Zlgameround',
			'zlgameround_gameinst_id' => 'Zlgameround Gameinst',
			'zlgameround_roundname' => 'Zlgameround Roundname',
			'zlgameround_timestart' => 'Zlgameround Timestart',
			'zlgameround_timeend' => 'Zlgameround Timeend',
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

		$criteria->compare('zlgameround_id',$this->zlgameround_id,true);
		$criteria->compare('zlgameround_gameinst_id',$this->zlgameround_gameinst_id,true);
		$criteria->compare('zlgameround_roundname',$this->zlgameround_roundname,true);
		$criteria->compare('zlgameround_timestart',$this->zlgameround_timestart,true);
		$criteria->compare('zlgameround_timeend',$this->zlgameround_timeend,true);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}