<?php

/**
 * This is the model class for table "zzzlrofigcoord".
 *
 * The followings are the available columns in table 'zzzlrofigcoord':
 * @property string $zlfigcoord_id
 * @property string $zlfigcoord_zlfig_id
 * @property integer $zlfigcoord_posx
 * @property integer $zlfigcoord_posy
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property ZzzlrofigBase $zlfigcoordZlfig
 */
class ZzzlrofigcoordBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzzlrofigcoordBase the static model class
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
		return 'zzzlrofigcoord';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('zlfigcoord_id, zlfigcoord_zlfig_id, zlfigcoord_posx, zlfigcoord_posy', 'required'),
			array('zlfigcoord_posx, zlfigcoord_posy', 'numerical', 'integerOnly'=>true),
			array('zlfigcoord_id, zlfigcoord_zlfig_id', 'length', 'max'=>100),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('zlfigcoord_id, zlfigcoord_zlfig_id, zlfigcoord_posx, zlfigcoord_posy, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zlfigcoordZlfig' => array(self::BELONGS_TO, 'ZzzlrofigBase', 'zlfigcoord_zlfig_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'zlfigcoord_id' => 'Zlfigcoord',
			'zlfigcoord_zlfig_id' => 'Zlfigcoord Zlfig',
			'zlfigcoord_posx' => 'Zlfigcoord Posx',
			'zlfigcoord_posy' => 'Zlfigcoord Posy',
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

		$criteria->compare('zlfigcoord_id',$this->zlfigcoord_id,true);
		$criteria->compare('zlfigcoord_zlfig_id',$this->zlfigcoord_zlfig_id,true);
		$criteria->compare('zlfigcoord_posx',$this->zlfigcoord_posx);
		$criteria->compare('zlfigcoord_posy',$this->zlfigcoord_posy);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}