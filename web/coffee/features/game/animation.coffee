define [], ()->
	panelMoveMagnitudeCodes =
      ONE: "1"
      ONE_VIEW: "One screen at a time"
      START: "Be ginning of the list"
      END: "End of the list"
    window.panelMoveMagnitudeCodes = panelMoveMagnitudeCodes;
	UserPanelMov : (->
      UserPanelMov = (jUserPanelCenterNav, USER_PANEL_PLAYER_DIV_WIDTH, USER_PANEL_PLAYER_DIV_WIDTH_PADDING, USER_PANEL_VIEW_LIMIT, currentNoOfUsers) ->
        @jUserPanelCenterNav = jUserPanelCenterNav
        @USER_PANEL_PLAYER_DIV_WIDTH = (if USER_PANEL_PLAYER_DIV_WIDTH? then USER_PANEL_PLAYER_DIV_WIDTH else 145)
        @USER_PANEL_PLAYER_DIV_WIDTH_PADDING = (if USER_PANEL_PLAYER_DIV_WIDTH_PADDING? then USER_PANEL_PLAYER_DIV_WIDTH_PADDING else 0)
        @USER_PANEL_VIEW_LIMIT = (if USER_PANEL_VIEW_LIMIT? then USER_PANEL_VIEW_LIMIT else 1)
        @currentNoOfUsers = (if currentNoOfUsers? then currentNoOfUsers else 0)
        @currentPos = 0
      UserPanelMov::moveUserPanelDivNow = ->
        startPx = undefined
        _this = this
        startPx = @currentPos * (@USER_PANEL_PLAYER_DIV_WIDTH + @USER_PANEL_PLAYER_DIV_WIDTH_PADDING)
        console.log "[currentNoOfUsers:", @currentNoOfUsers, ",currentPos:", @currentPos, ",USER_PANEL_PLAYER_DIV_WIDTH:", @USER_PANEL_PLAYER_DIV_WIDTH, "USER_PANEL_PLAYER_DIV_WIDTH_PADDING:", @USER_PANEL_PLAYER_DIV_WIDTH_PADDING, "startPx:", startPx, " : Element : ", @jUserPanelCenterNav if isDevEnvironment
        if @jUserPanelCenterNav?
          console.log "step 1 for move" if isDevEnvironment
          if @jUserPanelCenterNav.animate?
            console.log "step 2 for move" if isDevEnvironment
            @jUserPanelCenterNav.animate
              left: "-" + startPx + "px"
            , "fast", ->
              {}
          else if (@jUserPanelCenterNav.style?) and (@jUserPanelCenterNav.style.offsetLeft?)
            console.log "setting left prop" if isDevEnvironment
            @jUserPanelCenterNav.style.offsetLeft = startPx + "px"

#      UserPanelMov::updateCount = (cnt) ->
#        @currentNoOfUsers = cnt

      UserPanelMov::move = (panelMoveDirection, panelMoveMagnitude) ->
        console.log "moveUserPanelFn() - " + panelMoveDirection + "," + panelMoveMagnitude if isDevEnvironment
        magnitude = 0
        flag_NO_EXTREME_END = false
        panelMoveDirection = (if panelMoveDirection isnt "LEFT" then "RIGHT" else "LEFT")
        switch panelMoveMagnitude
          when panelMoveMagnitudeCodes.ONE
            magnitude = 1
            flag_NO_EXTREME_END = true
          when panelMoveMagnitudeCodes.ONE_VIEW
            magnitude = @USER_PANEL_VIEW_LIMIT
            flag_NO_EXTREME_END = true
        if flag_NO_EXTREME_END is true
          if panelMoveDirection is "LEFT"
            if @currentNoOfUsers <= @USER_PANEL_VIEW_LIMIT or (@currentPos - magnitude) < 1
              panelMoveMagnitude = panelMoveMagnitudeCodes.START
            else if (@currentPos - magnitude) >= (@currentNoOfUsers - @USER_PANEL_VIEW_LIMIT)
              panelMoveMagnitude = panelMoveMagnitudeCodes.END
            else
              @currentPos = @currentPos - magnitude
          else
            if (@currentNoOfUsers <= @USER_PANEL_VIEW_LIMIT) or ((@currentPos + magnitude) < 1)
              panelMoveMagnitude = panelMoveMagnitudeCodes.START
            else if (@currentPos + magnitude) >= (@currentNoOfUsers - @USER_PANEL_VIEW_LIMIT)
              panelMoveMagnitude = panelMoveMagnitudeCodes.END
            else
              @currentPos = @currentPos + magnitude
        switch panelMoveMagnitude
          when panelMoveMagnitudeCodes.END
            @currentPos = @currentNoOfUsers - @USER_PANEL_VIEW_LIMIT
          when panelMoveMagnitudeCodes.START
            @currentPos = 0
        @moveUserPanelDivNow()

      UserPanelMov
    )()