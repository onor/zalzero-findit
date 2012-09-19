<?php
$this->breadcrumbs=array(
	'Zzproducts',
);

$this->menu=array(
	array('label'=>'Create Zzproduct', 'url'=>array('create')),
	array('label'=>'Manage Zzproduct', 'url'=>array('admin')),
);
?>

<h1>Zzproducts</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>
