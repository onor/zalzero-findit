define [], () ->
  chainDeffereds: do ->
    _defCreator = (def, _prevDef, func) ->
      if _prevDef is null
        func null, def
      else
        _prevDef.then (arg) ->
          func arg, def
          
          true
      
      @
      
    (funcs) ->
      _prevDef = null
      
      for func in funcs
        _prevDef = $.Deferred (def) ->
          _defCreator.call @, def, _prevDef, func
      
      true
