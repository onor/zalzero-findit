<?php

/**
 * This is the model class for table "zzzlrofiggroup".
 *
 * The followings are the available columns in table 'zzzlrofiggroup':
 * @property string $zlfiggroup_id
 * @property string $zlfiggroup_name
 * @property integer $zlfiggroup_nooffigrequired
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property ZzzlrofigBase[] $zzzlrofigs
 */
class ZzzlrofiggroupBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzzlrofiggroupBase the static model class
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
		return 'zzzlrofiggroup';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('zlfiggroup_id, zlfiggroup_name', 'required'),
			array('zlfiggroup_nooffigrequired', 'numerical', 'integerOnly'=>true),
			array('zlfiggroup_id', 'length', 'max'=>100),
			array('zlfiggroup_name', 'length', 'max'=>150),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('zlfiggroup_id, zlfiggroup_name, zlfiggroup_nooffigrequired, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zzzlrofigs' => array(self::HAS_MANY, 'ZzzlrofigBase', 'zlfig_zlfiggroup_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'zlfiggroup_id' => 'Zlfiggroup',
			'zlfiggroup_name' => 'Zlfiggroup Name',
			'zlfiggroup_nooffigrequired' => 'Zlfiggroup Nooffigrequired',
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

		$criteria->compare('zlfiggroup_id',$this->zlfiggroup_id,true);
		$criteria->compare('zlfiggroup_name',$this->zlfiggroup_name,true);
		$criteria->compare('zlfiggroup_nooffigrequired',$this->zlfiggroup_nooffigrequired);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}