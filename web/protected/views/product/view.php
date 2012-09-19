<?php
$this->breadcrumbs=array(
	'Zzproducts'=>array('index'),
	$model->product_id,
);

$this->menu=array(
	array('label'=>'List Zzproduct', 'url'=>array('index')),
	array('label'=>'Create Zzproduct', 'url'=>array('create')),
	array('label'=>'Update Zzproduct', 'url'=>array('update', 'id'=>$model->product_id)),
	array('label'=>'Delete Zzproduct', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','id'=>$model->product_id),'confirm'=>'Are you sure you want to delete this item?')),
	array('label'=>'Manage Zzproduct', 'url'=>array('admin')),
);
?>

<h1>View Zzproduct #<?php echo $model->product_id; ?></h1>

<?php $this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'product_id',
		'product_sku',
		'product_name',
		'product_brand',
		'product_shortdesc',
		'product_desc',
		'product_price',
	),
)); ?>
