<div class="wide form">

<?php $form=$this->beginWidget('CActiveForm', array(
	'action'=>Yii::app()->createUrl($this->route),
	'method'=>'get',
)); ?>

	<div class="row">
		<?php echo $form->label($model,'gameinst_id'); ?>
		<?php echo $form->textField($model,'gameinst_id'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_game_id'); ?>
		<?php echo $form->textField($model,'gameinst_game_id'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_product_id'); ?>
		<?php echo $form->textField($model,'gameinst_product_id'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_starttime'); ?>
		<?php echo $form->textField($model,'gameinst_starttime'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_endtime'); ?>
		<?php echo $form->textField($model,'gameinst_endtime'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_entrytime'); ?>
		<?php echo $form->textField($model,'gameinst_entrytime'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_capacity'); ?>
		<?php echo $form->textField($model,'gameinst_capacity'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_threshold'); ?>
		<?php echo $form->textField($model,'gameinst_threshold'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum1'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum1'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum2'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum2'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum3'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum3'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum4'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum4'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum5'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum5'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum6'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum6'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum7'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum7'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum8'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum8'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum9'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum9'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramnum10'); ?>
		<?php echo $form->textField($model,'gameinst_paramnum10'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramdate1'); ?>
		<?php echo $form->textField($model,'gameinst_paramdate1'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramdate2'); ?>
		<?php echo $form->textField($model,'gameinst_paramdate2'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramdate3'); ?>
		<?php echo $form->textField($model,'gameinst_paramdate3'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramdate4'); ?>
		<?php echo $form->textField($model,'gameinst_paramdate4'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramdate5'); ?>
		<?php echo $form->textField($model,'gameinst_paramdate5'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramvar1'); ?>
		<?php echo $form->textField($model,'gameinst_paramvar1',array('size'=>60,'maxlength'=>150)); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramvar2'); ?>
		<?php echo $form->textField($model,'gameinst_paramvar2',array('size'=>60,'maxlength'=>150)); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramvar3'); ?>
		<?php echo $form->textField($model,'gameinst_paramvar3',array('size'=>60,'maxlength'=>150)); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramvar4'); ?>
		<?php echo $form->textField($model,'gameinst_paramvar4',array('size'=>60,'maxlength'=>150)); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'gameinst_paramvar5'); ?>
		<?php echo $form->textField($model,'gameinst_paramvar5',array('size'=>60,'maxlength'=>150)); ?>
	</div>

	<div class="row buttons">
		<?php echo CHtml::submitButton('Search'); ?>
	</div>

<?php $this->endWidget(); ?>

</div><!-- search-form -->