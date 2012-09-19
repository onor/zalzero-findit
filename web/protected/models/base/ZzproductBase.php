<?php

/**
 * This is the model class for table "zzproduct".
 *
 * The followings are the available columns in table 'zzproduct':
 * @property string $product_id
 * @property string $product_sku
 * @property string $product_name
 * @property string $product_brand
 * @property string $product_shortdesc
 * @property string $product_imagename
 * @property string $product_desc
 * @property string $product_price
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property ZzgameinstBase[] $zzgameinsts
 */
class ZzproductBase extends ActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return ZzproductBase the static model class
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
		return 'zzproduct';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('product_id, product_name, product_brand', 'required'),
			array('product_id', 'length', 'max'=>100),
			array('product_sku, product_name, product_brand', 'length', 'max'=>150),
			array('product_shortdesc, product_imagename', 'length', 'max'=>200),
			array('product_desc', 'length', 'max'=>500),
			array('product_price', 'length', 'max'=>19),
			array('create_time, update_time', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('product_id, product_sku, product_name, product_brand, product_shortdesc, product_imagename, product_desc, product_price, create_time, update_time', 'safe', 'on'=>'search'),
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
			'zzgameinsts' => array(self::HAS_MANY, 'ZzgameinstBase', 'gameinst_product_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'product_id' => 'Product',
			'product_sku' => 'Product Sku',
			'product_name' => 'Product Name',
			'product_brand' => 'Product Brand',
			'product_shortdesc' => 'Product Shortdesc',
			'product_imagename' => 'Product Imagename',
			'product_desc' => 'Product Desc',
			'product_price' => 'Product Price',
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

		$criteria->compare('product_id',$this->product_id,true);
		$criteria->compare('product_sku',$this->product_sku,true);
		$criteria->compare('product_name',$this->product_name,true);
		$criteria->compare('product_brand',$this->product_brand,true);
		$criteria->compare('product_shortdesc',$this->product_shortdesc,true);
		$criteria->compare('product_imagename',$this->product_imagename,true);
		$criteria->compare('product_desc',$this->product_desc,true);
		$criteria->compare('product_price',$this->product_price,true);
		$criteria->compare('create_time',$this->create_time,true);
		$criteria->compare('update_time',$this->update_time,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}