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
 * @property string $product_desc
 * @property string $product_price
 * @property string $create_time
 * @property string $update_time
 *
 * The followings are the available model relations:
 * @property Zzgameinst[] $zzgameinsts
 */
class Zzproduct extends ZzproductBase
{
	/**
	 * Returns the static model of the specified AR class.
	 * @param string $className active record class name.
	 * @return Zzproduct the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
}