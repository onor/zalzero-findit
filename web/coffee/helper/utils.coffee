define [], ()-> #'utils',
	addEventHandler : (node, evtType, func, isCapture) ->
		if window and window.addEventListener
			node.addEventListener evtType, func, isCapture
		else
			node.attachEvent "on" + evtType, func

	removeEventHandler : (node, evtType, func, isCapture) ->
		if window and window.removeEventListener
			node.removeEventListener evtType, func, isCapture
		else
			node.detachEvent "on" + evtType, func
	
	removeClassName : (node, cls) ->
		reg = undefined
		if (node?) and node.className
			reg = new RegExp("(\\s|^)" + cls + "(\\s|$)")
			node.className = node.className.replace(reg, " ")
			
	log : (message) ->
		console.log message
		
	getMiniLoaderHTML: ->
		return """<div id="floatingBarsGs">
		<div class="blockG" id="rotateG_01">
		</div>
		<div class="blockG" id="rotateG_02">
		</div>
		<div class="blockG" id="rotateG_03">
		</div>
		<div class="blockG" id="rotateG_04">
		</div>
		<div class="blockG" id="rotateG_05">
		</div>
		<div class="blockG" id="rotateG_06">
		</div>
		<div class="blockG" id="rotateG_07">
		</div>
		<div class="blockG" id="rotateG_08">
		</div>
		</div>"""