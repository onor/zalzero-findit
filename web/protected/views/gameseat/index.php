<?php
$this->breadcrumbs=array(
	'Zzgameseats',
);

$this->menu=array(
	array('label'=>'Create Zzgameseat', 'url'=>array('create')),
	array('label'=>'Manage Zzgameseat', 'url'=>array('admin')),
);
?>

<h1>Zzgameseats</h1>

<?php $this->widget('zii.widgets.CListView', array(
	'dataProvider'=>$dataProvider,
	'itemView'=>'_view',
)); ?>
