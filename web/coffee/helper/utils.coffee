define [], ()->
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