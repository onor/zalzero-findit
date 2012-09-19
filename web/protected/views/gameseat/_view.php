<div class="view">

	<b><?php echo CHtml::encode($data->getAttributeLabel('gameseat_id')); ?>:</b>
	<?php echo CHtml::link(CHtml::encode($data->gameseat_id), array('view', 'id'=>$data->gameseat_id)); ?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('gameseat_user_id')); ?>:</b>
	<?php echo CHtml::encode($data->gameseat_user_id); ?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('gameseat_gameinst_id')); ?>:</b>
	<?php echo CHtml::encode($data->gameseat_gameinst_id); ?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('gameseat_createdat')); ?>:</b>
	<?php echo CHtml::encode($data->gameseat_createdat); ?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('create_time')); ?>:</b>
	<?php echo CHtml::encode($data->create_time); ?>
	<br />

	<b><?php echo CHtml::encode($data->getAttributeLabel('update_time')); ?>:</b>
	<?php echo CHtml::encode($data->update_time); ?>
	<br />


</div>