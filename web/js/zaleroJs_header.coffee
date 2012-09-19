facebookInit = ->
  if typeof (FB) isnt "undefined"
    FB.getLoginStatus (response) ->
      if response.status and response.status is "connected"
        updateFBInfo response
      else
  else
    console.log "Facebook NOT initialized!"
facebookConnect = ->
  console.log "in facebook connect"
  if typeof (FB) isnt "undefined"
    console.log "in facebook connect FB is undefined"
    FB.login loginSuccess_redirect,
      scope: "email,user_birthday,sms,publish_stream,read_friendlists,friends_online_presence"
loginSuccess_redirect = (response) ->
  console.log "loginSuccess_redirect()"
  if typeof (response) isnt "undefined"
    fbUid = ""
    flag_FBConnected = false
    if typeof (response["session"]) isnt "undefined" and typeof (response["session"]["status"]) isnt "undefined" and response["session"]["status"] is "connected"
      flag_FBConnected = true
      fbUid = response["session"]["uid"]
      console.log "fbuid : " + fbUid
    else if typeof (response["status"]) isnt "undefined" and response["status"] is "connected" and response["authResponse"] and response["authResponse"]["userID"]
      flag_FBConnected = true
      fbUid = response["authResponse"]["userID"]
      console.log "fbuid : " + fbUid
    else if typeof (response[2]) isnt "undefined" and typeof (response[2][0]) isnt "undefined" and typeof (response[2][0]["session"]) isnt "undefined"
      flag_FBConnected = true
      fbUid = response[2][0]["session"]["uid"]
      console.log "fbuid : " + fbUid
    if flag_FBConnected
      updateFBInfo response
      FB.api
        method: "fql.query"
        query: "SELECT uid,name,email,first_name,last_name,birthday_date,sex,pic_square FROM user WHERE uid = me()"
      , (response) ->
        fbUserData = {}
        for i of response[0]
          fbUserData[i] = response[0][i]
        console.log "fbUserData : " + fbUserData
        jQuery.ajax(
          type: "POST"
          url: baseUrl + "/user/verifyUser"
          data:
            user_email: fbUserData["email"]
            user_fbid: fbUserData["uid"]
        ).done (data) ->
          if data is "true"
            jQuery.ajax
              type: "POST"
              url: baseUrl + "/user/fbCheckLoginAjax"
              data:
                user_fbid: fbUid
                user_email: fbUserData["email"]

              success: fbLoginSuccessRedirect
              dataType: "json"
          else
            alert "Hey! You are not authorized to play this game."
updateFBInfo = (response) ->
  if typeof (response) isnt "undefined"
    if response.status and response.status is "connected" and response.authResponse and response.authResponse.userID and response.authResponse.accessToken
      ofbizUser["$user_fbid"] = response["authResponse"]["userID"]
      ofbizUser["accessToken"] = response["authResponse"]["accessToken"]
      ofbizUser["fbStatus"] = 1
      serveFBRequests()
    else return -1  if response[2][0]["status"] is "notConnected"  if typeof (response[2]) isnt "undefined" and typeof (response[2][0]) isnt "undefined" and typeof (response[2][0]["status"]) isnt "undefined"
  0
serveFBRequests = ->
  oloFunctionRequests["muteX"] = 0
  ctr = 0
  for i of oloFunctionRequests
    request = oloFunctionRequests[i]
    if request isnt 0
      if request is 1
        window[i]()
      else eval i  if request is 2
      oloFunctionRequests[i] = 0
fbLoginSuccessRedirect = (data) ->
  if typeof (data) isnt "undefined" and typeof (data["userValid"]) isnt "undefined" and data["userValid"] is true
    console.log "user is already in database so log him in"
    window.location = baseUrl + "/site/fbLogin?user_email=" + data["user_mail"] + "&user_fbid=" + data["user_fbid"]
  else
    flag_fbPullAuthorised = true
    console.log "user is not in database so create the user and log him in"
    loadSignUpFormFields()
loadSignUpFormFields = ->
  fbPullData()
fbPullData = ->
  if flag_fbPullAuthorised is true
    FB.api
      method: "fql.query"
      query: "SELECT uid,name,email,first_name,last_name,birthday_date,sex,pic_square FROM user WHERE uid = me()"
    , (response) ->
      fbUserData = {}
      for i of response[0]
        fbUserData[i] = response[0][i]
      autoFillForm fbUserData

    flag_fbPullAuthorised = false
autoFillForm = (fbData) ->
  formObj = document.createElement("form")
  formObj.name = "register-user-form"
  formObj.method = "POST"
  formObj.action = baseUrl + "/user/createFbUser"
  model = "Zzuser"
  console.log model + "[user_name]"
  if formObj and formObj.action and formObj.method
    if typeof (formObj) isnt "undefined" and typeof (fbData) isnt "undefined"
      name = fbData["first_name"] + " " + fbData["last_name"]
      formObj.appendChild createDocInputElement(model + "[user_name]", name)
      formObj.appendChild createDocInputElement(model + "[user_email]", fbData["email"])
      formObj.appendChild createDocInputElement(model + "[user_handle]", fbData["email"])
      formObj.appendChild createDocInputElement(model + "[user_fname]", fbData["first_name"])
      formObj.appendChild createDocInputElement(model + "[user_lname]", fbData["last_name"])
      formObj.appendChild createDocInputElement(model + "[user_fbid]", fbData["uid"])
      document.body.appendChild formObj
      formObj.submit()
createDocInputElement = (elName, elValue) ->
  el = document.createElement("input")
  el.type = "hidden"
  el.name = elName
  el.value = elValue
  el
showFrndSelector = ->
  $("body").append "<div class=\"wait\"></div>"
  flag_fbPullAuthorised = true
  if flag_fbPullAuthorised is true
    FB.api
      method: "fql.query"
      query: "SELECT uid, name, pic_square, online_presence, username FROM user WHERE uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) order by name"
    , (response) ->
      fbUserData = {}
      for i of response
        fbUserData[i] = response[i]
      console.log fbUserData
      html = ""
      for j of fbUserData
        html += "<div class=\"rep\"><img src=\"" + fbUserData[j].pic_square + "\" /><div>" + fbUserData[j].name + "<br/></div>"
        html += "<input type=\"button\" style=\"display:none\" value=\"" + fbUserData[j].uid + "\" class=\"status\" />"
        html += "<span class=\"" + fbUserData[j].online_presence + "\">" + fbUserData[j].online_presence + "</span>"
        html += "<a class=\"select_button right\">Select</a></div><div class=\"line\"></div>"
      $.ajax(
        type: "POST"
        url: siteUrl + "/user/getFriend"
      ).done (data) ->
        $("body").append data
        $(".friendlist").html html
        popupapperence.play()  if playSound

    flag_fbPullAuthorised = false
valid_email = (email) ->
  email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/
  return true  if email_regex.test(email)
  false
playSound = true
audioBaseUrl = baseUrl.replace("/index.php", "/")
otherbuttonSound = new Audio(audioBaseUrl + "/sound/otherbuttons.wav")
selectbuttonSound = new Audio(audioBaseUrl + "/sound/select_button.wav")
popupapperence = new Audio(audioBaseUrl + "/sound/popupapperence.wav")
closebutton = new Audio(audioBaseUrl + "/sound/closebutton.wav")
ofbizUser = {}
flag_fbPullAuthorised = false
oloFunctionRequests = {}
jQuery ->
  facebookInit()

jQuery("#close").live "click", ->
  closebutton.play()  if playSound

if document["domain"] is "localhost"
  oloFBAppId = "358029084260694"
else if document["domain"] is "zalerio.com" or document["domain"] is "www.zalerio.com"
  oloFBAppId = "264696926970805"
else if document["domain"] is "zl.mobicules.com"
  oloFBAppId = "392619034110411"
else
  oloFBAppId = "264696926970805"
inviteUserId = new Array()
siteUrl = baseUrl
jQuery ($) ->
  fb_publish = (friendId, gameId) ->
    opts =
      message: "Find IT game Invitation"
      name: "Find IT"
      link: "http://" + window.location.hostname + "/?invited_game_id=" + gameId
      description: "Join Find IT to play game"

    $.holdReady true
    FB.api "/" + friendId + "/feed", "post", opts, (response) ->
      if not response or response.error
        $.holdReady false
        console.log response
      else
        $.holdReady false
  $("#startButton").click ->
    otherbuttonSound.play()  if playSound
    showFrndSelector()

  $("#findfriend").live "keyup", ->
    findFriend = $("#findfriend").val().toLowerCase()
    $(".rep").css "display", "none"
    $(".line").css "display", "none"
    $("#show_all_friends").css "display", "block"
    $(".rep div").each (i) ->
      unless $(this).text().toLowerCase().indexOf(findFriend) is -1
        $(this).parent(".rep").css "display", "block"
        $(this).parent(".rep").next().css "display", "block"

  $("#show_all_friends").live "click", ->
    $(".rep").css "display", "block"
    $(".line").css "display", "block"
    $("#findfriend").val ""
    $("#show_all_friends").css "display", "none"

  $(".select_button").live "click", ->
    if $(this).hasClass("selected")
      $(this).removeClass "selected"
      $(this).parent(".rep").find(".countme").removeClass "countme"
    else
      selectbuttonSound.play()  if playSound
      $(this).addClass "selected"
      $(this).parent(".rep").find("input[type=button]").addClass "countme"

  InviteFriends = (id) ->
    friend_id = id
    console.log friend_id.length
    todayDate = new Date()
    gameActiveTime = 7 * 24 * 60 * 60 * 1000
    gameEndDate = new Date(todayDate.getTime() + gameActiveTime)
    gameEntrydate = new Date(todayDate.getTime() + (30000))
    gameStartTime = (todayDate.getFullYear() + "-" + (todayDate.getMonth() + 1) + "-" + (todayDate.getDate()) + " " + (todayDate.getHours()) + ":" + (todayDate.getMinutes()))
    gameEndTime = (gameEndDate.getFullYear() + "-" + (gameEndDate.getMonth() + 1) + "-" + (gameEndDate.getDate()) + " " + (gameEndDate.getHours()) + ":" + (gameEndDate.getMinutes()))
    gameEntryTime = (gameEntrydate.getFullYear() + "-" + (gameEntrydate.getMonth() + 1) + "-" + (gameEntrydate.getDate()) + " " + (gameEntrydate.getHours()) + ":" + (gameEntrydate.getMinutes()))
    $.ajax(
      type: "POST"
      url: siteUrl + "/gameinst/create"
      data:
        "Zzgameinst[gameinst_gameinsttempl_id]": "ZalerioTempl1"
        "Zzgameinst[gameinst_game_id]": "ZALERIO_1"
        "Zzgameinst[gameinst_product_id]": "1"
        "Zzgameinst[gameinst_starttime]": gameStartTime
        "Zzgameinst[gameinst_endtime]": gameEndTime
        "Zzgameinst[gameinst_entrytime]": gameEntryTime
        "Zzgameinst[gameinst_capacity]": friend_id.length + 1
        "Zzgameinst[gameinst_threshold]": "2"
        yt0: "Create"
    ).done (data) ->
      gameseat_gameinst_id = data
      $.ajax(
        type: "POST"
        url: siteUrl + "/gameseat/bookSeat"
        data:
          "Zzgameseat[gameseat_gameinst_id]": gameseat_gameinst_id
          "Zzgameseat[gameseat_user_email]": userLoginId
      ).done (data) ->
        selectedUser = {}
        count = 0
        for i of fbUserData
          count++  if fbUserData.hasOwnProperty(i)
        i = 0
        j = 0

        while i < count
          selectedUser[j++] = fbUserData[i]  unless jQuery.inArray(fbUserData[i].uid, friend_id) is -1
          i++
        console.log selectedUser
        $.ajax(
          type: "POST"
          url: siteUrl + "/user/getFbFriendsList"
          data:
            fbUserData: selectedUser
            gameseat_gameinst_id: gameseat_gameinst_id
        ).done (data) ->
          $(".show_popup").remove()
          $(".wait").remove()
          window.location = "http://" + window.location.hostname + window.location.pathname + "?gameinst_id=" + gameseat_gameinst_id

  $("#sendrinvite").live "click", ->
    id = new Array()
    $(".friendlist .rep .status").each ->
      id.push $(this).val()

    arr = []
    while arr.length < 5 and arr.length < id.length
      randomnumber = Math.ceil(Math.random() * id.length - 1)
      found = false
      i = 0

      while i < arr.length
        if arr[i] is id[randomnumber]
          found = true
          break
        i++
      arr[arr.length] = id[randomnumber]  unless found
    InviteFriends arr

  $("#sendinvite").live "click", ->
    otherbuttonSound.play()  if playSound
    id = new Array()
    $(".countme").each ->
      id.push $(this).val()

    if id.length is 0
      alert "Please select 1,2,3,4 or 9 friend"
    else if id.length > 4 and id.length isnt 9
      alert "Please select 1,2,3,4 or 9 friend"
    else
      InviteFriends id

  $(".fblist .rep").live "click", ->
    if $(this).hasClass("selected")
      $(this).removeClass "selected"
      $(this).find(".countme").removeClass "countme"
    else
      $(this).addClass "selected"
      $(this).find("input[type=hidden]").addClass "countme"

  $("#sendFbInvite").live "click", ->
    id = new Array()
    $(".fblist .rep .countme").each ->
      id.push $(this).val()

    if id.length < 1
      alert "Please Select a Friend"
      return
    selectedUser = {}
    count = 0
    for i of fbUserData
      count++  if fbUserData.hasOwnProperty(i)
    i = 0
    j = 0

    while i < count
      unless jQuery.inArray(fbUserData[i].uid, id) is -1
        selectedUser[j++] = fbUserData[i]
        fb_publish fbUserData[i].uid
      i++
    console.log selectedUser
    $.ajax(
      type: "POST"
      url: siteUrl + "/user/getFbFriendsList"
      data:
        fbUserData: selectedUser
    ).done (data) ->
      console.log data
      console.log "data"
      $(".show_popup").remove()
      $(".wait").remove()

  jQuery("#bottomHUDbuttons-acc").css "display", "none"
  showStats = ->
    jQuery(".popup-block").hide()
    jQuery("#popup-stats").show()
    jQuery("#mylevel_stats").remove()
    jQuery("#myfriends_stats").remove()
    jQuery("#mygames_stats").show()
    jQuery("#active-screen").append "<div class='wait'></div>"
    getStats = ->
      jQuery.post baseUrl + "/stat/mygames",
        frndList: window.fbFrndList
      , (data) ->
        if data
          obj = JSON.parse(data)
          jQuery("#popup-stats .div_top .div_img img").attr "src", obj.personalInfo.user_pic_url
          jQuery("#popup-stats .div_top .div_img #stat_userName").html obj.personalInfo.user_name
          jQuery("#popup-stats .div_top #joined_time").html obj.personalInfo.create_time
          jQuery("#frnd_playing").html obj.frnd_stats.on_zl
          jQuery("#frnd_total").html obj.frnd_stats.total - obj.frnd_stats.on_zl
          jQuery("#user-level-span").removeClass().addClass(obj.belt_class).html obj.level
          jQuery("#rip_won").remove()
          jQuery("#rip_lost").remove()
          jQuery("#rip_active").remove()
          jQuery("#rip_active_container").append "<div id=\"rip_active\" class=\"scroll-pane\"></div>"
          jQuery("#rip_win_lost_container").append "<div class=\"won scroll-pane\" id=\"rip_won\"></div><div class=\"lost scroll-pane\" id=\"rip_lost\"></div>"
          jQuery.each obj.gamesStats.won, (index, value) ->
            str = "<div class='rip'>"
            str += "<div class='game_stats_user infiniteCarousel'>"
            str += "<div class='wrapper'><ul>"
            jQuery.each value.user_arr, (index, value) ->
              str += "<li><a href='#'><img width='31' height='31'src='" + obj.usersInfo[index]["user_pic"] + "'><span>" + obj.usersInfo[index]["name"] + "</span></a></li>"

            str += "</ul></div></div><div class=\"rematch\"><a href=\"#\">Rematch</a></div></div>"
            jQuery("#rip_won").append str

          jQuery.each obj.gamesStats.lost, (index, value) ->
            str = "<div class='rip'>"
            str += "<div class='game_stats_user infiniteCarousel'>"
            str += "<div class='wrapper'><ul>"
            jQuery.each value.user_arr, (index, value) ->
              str += "<li><a href='#'><img width='31' height='31'src='" + obj.usersInfo[index]["user_pic"] + "'><span>" + obj.usersInfo[index]["name"] + "</span></a></li>"

            str += "</ul></div></div><div class=\"rematch\"><a href=\"#\">Rematch</a></div></div>"
            jQuery("#rip_lost").append str

          jQuery.each obj.gamesStats.active, (index, value) ->
            str = ""
            str += "<div class='rip'>"
            str += "<div class='game_stats_user infiniteCarousel'>"
            str += "<div class='wrapper'><ul>"
            jQuery.each value.user_arr, (index, value) ->
              str += "<li><a href='#'><img width='31' height='31'src='" + obj.usersInfo[index]["user_pic"] + "'><span>" + obj.usersInfo[index]["name"] + "</span></a></li>"

            str += "</ul></div></div>"
            str += "<div class='round_stats'>" + value.round + "</div>"
            str += "<div class='rank_stats'>" + value.rank + "</div>"
            str += "</div>"
            jQuery("#rip_active").append str
        jQuery ->
          jQuery(".scroll-pane").jScrollPane showArrows: true

        $(".infiniteCarousel").infiniteCarousel()

      jQuery(".wait").remove()
      jQuery(".s_show_popup").show()

    unless window.fbFrndList
      FB.api
        method: "fql.query"
        query: "SELECT uid2 FROM friend WHERE uid1 = me()"
      , (response) ->
        str = ""
        for i of response
          if str is ""
            str = response[i].uid2
          else
            str += "," + response[i].uid2
        window.fbFrndList = str
        getStats()
    else
      getStats()

  showMyLevel = ->
    jQuery("#myfriends_stats").remove()
    jQuery("#mylevel_stats").remove()
    jQuery("#performance_level").remove()
    jQuery("#mygames_stats").hide()
    jQuery("#active-screen").append "<div class='wait'></div>"
    jQuery.post baseUrl + "/stat/mylevel", (data) ->
      jQuery(".wait").remove()
      jQuery("#popup-stats").append data
      jQuery("#learn-performance-level").click ->
        jQuery(".performance_level").show()

      jQuery("#close-performance-level").click ->
        jQuery(".performance_level").hide()

  showHelp = ->
    jQuery(".popup-block").hide()
    jQuery("#help-tutorial").show()
    jQuery("#popup-help").show()
    jQuery(".s_show_popup").show()
    jQuery ->
      jQuery(".rules-container").jScrollPane showArrows: true

    jQuery("#tutorial_startgame").click (event) ->
      event.preventDefault()
      jQuery("#rules-container .jspContainer .jspPane").css "top", "-150px"

    jQuery("#tutorial_taketurn").click (event) ->
      event.preventDefault()
      jQuery("#rules-container .jspContainer .jspPane").css "top", "-930px"

    jQuery("#tutorial_scoring").click (event) ->
      event.preventDefault()
      jQuery("#rules-container .jspContainer .jspPane").css "top", "-2970px"

    jQuery("#tutorial_jokers").click (event) ->
      event.preventDefault()
      jQuery("#rules-container .jspContainer .jspPane").css "top", "-4280px"

  getHelpContactUsForm = ->
    jQuery.post baseUrl + "/help/contactus", {}, (data) ->
      if data
        jQuery("#help-contactus").html data
        jQuery ->
          jQuery("select.styled").customStyle()

  jQuery("#bottomHUDbuttons-stats").click ->
    otherbuttonSound.play()  if playSound
    if jQuery(".s_show_popup").css("display") is "none"
      showStats()
    else
      if jQuery("#popup-stats").css("display") is "none"
        showStats()
      else
        jQuery(".s_show_popup").hide()

  jQuery(".rightHUDPanel .gameMenu ul li.helpButton").click ->
    otherbuttonSound.play()  if playSound
    if jQuery(".s_show_popup").css("display") is "none"
      showHelp()
    else
      if jQuery("#popup-help").css("display") is "none"
        showHelp()
      else
        jQuery(".s_show_popup").hide()

  jQuery("#a-help-tutorial").click ->
    otherbuttonSound.play()  if playSound
    jQuery(this).addClass "active"
    jQuery("#a-help-contactus").removeClass "active"
    jQuery("#help-contactus").hide()
    jQuery("#help-tutorial").show()

  jQuery("#a-help-contactus").click ->
    otherbuttonSound.play()  if playSound
    jQuery(this).addClass "active"
    jQuery("#a-help-tutorial").removeClass "active"
    getHelpContactUsForm()
    jQuery("#help-contactus").show()
    jQuery("#help-tutorial").hide()

  jQuery(".statslink").click (data) ->
    jQuery(".statslink").removeClass "active"
    jQuery(this).addClass "active"
    id = jQuery(this).attr("id")
    showStats()  if id is "mygames_a"
    showMyLevel()  if id is "mylevel_a"

  jQuery("#upload_help_contactus").live "click", ->
    jQuery("#help_contact_form input,select,textarea,submit").removeClass "error"
    first_name = jQuery("#help_contact_form input[name=first_name]").val()
    last_name = jQuery("#help_contact_form input[name=last_name]").val()
    email = jQuery("#help_contact_form input[name=email]").val()
    subject = jQuery("#help_contact_form select[name=subject]").val()
    body = jQuery("#help_contact_form textarea[name=body]").val()
    verifyCode = jQuery("#help_contact_form input[name=verifyCode]").val()
    valid = "1"
    if first_name is "" or not first_name? or first_name.length > 50
      jQuery("#help_contact_form input[name=first_name]").addClass "error"
      valid = "0"
    if last_name.length > 50
      jQuery("#help_contact_form input[name=last_name]").addClass "error"
      valid = "0"
    if not valid_email(email) or email.length > 100
      jQuery("#help_contact_form input[name=email]").addClass "error"
      valid = "0"
    if subject is "" or not subject? or subject.length > 50
      jQuery("#help_contact_form select[name=subject]").addClass "error"
      valid = "0"
    if body is "" or not body? or body.length > 2000
      jQuery("#help_contact_form textarea[name=body]").addClass "error"
      valid = "0"
    if verifyCode is "" or not verifyCode?
      jQuery("#help_contact_form input[name=verifyCode]").addClass "error"
      valid = "0"
    if valid is 1
      jQuery.post baseUrl + "/help/contactus",
        first_name: first_name
        last_name: last_name
        email: email
        subject: subject
        body: body
        verifyCode: verifyCode
      , (data) ->
        obj = JSON.parse(data)
        if obj.status is "1"
          jQuery("#help_contact_form input[name=first_name]").val("").removeClass "error"
          jQuery("#help_contact_form input[name=last_name]").val("").removeClass "error"
          jQuery("#help_contact_form input[name=email]").val("").removeClass "error"
          jQuery("#help_contact_form textarea[name=body]").val("").removeClass "error"
          jQuery("#help_contact_form input[name=verifyCode]").val("").removeClass "error"
        jQuery("#msg-submit-popup").remove()
        jQuery("#help-contactus").append "<div id=\"msg-submit-popup\" class=\"msg-submit\"><p>" + obj.msg + "</p></div>"

  jQuery("#help_contact_form input,select,textarea,submit").live "click", ->
    jQuery("#msg-submit-popup").remove()

  tilepickup = new Audio(audioBaseUrl + "/sound/Tilepickup.wav")
  mousedownFunction = (e) ->
    return  if $(this).attr("draggable") is "false"
    tilepickup.play()  if playSound
    div = $("<div></div>")
    i = 0
    attributes = $(this).get(0).attributes

    while i < attributes.length
      div.attr attributes[i].name, attributes[i].value
      i++
    $(div).addClass "draggableBetsClick"
    $("body").append div
    $(".draggableBetsClick").css
      position: "absolute"
      left: e.pageX - 15
      top: e.pageY - 15

  $(".box-newBet").live "mousedown", mousedownFunction
  $(".draggableBets").live "mousedown", mousedownFunction
  $(".draggableBetsClick").live "mousemove", (e) ->
    $(".draggableBetsClick").remove()

  $(".rematch").live "click", ->
    otherbuttonSound.play()  if playSound

  $(".dismiss").live "click", ->
    otherbuttonSound.play()  if playSound

  countImg = 1
  lastImg = 1
  url = undefined
  unless typeof $(".girlimg").css("backgroundImage") is "undefined"
    i = 1
    while i < 18
      DivElement = document.createElement("div")
      if i is 1
        url = $(".girlimg").css("backgroundImage")
      else
        url = url.replace(i - 1 + ".png", i + ".png")
      $(DivElement).css "backgroundImage", url
      $(DivElement).css "height", 0
      $("body").append DivElement
      i++
    setInterval (animateGirl = ->
      url = $(".girlimg").css("backgroundImage")
      $(".girlimg").css "backgroundImage", url.replace(lastImg + ".png", (++countImg) + ".png")
      if countImg is 17
        lastImg = countImg
        countImg = 1
      else
        lastImg = countImg
    ), 90
  jQuery(".audioButton a").click ->
    if playSound
      playSound = false
      $(this).parent(".audioButton").addClass "mute"
    else
      playSound = true
      $(this).parent(".audioButton").removeClass "mute"

$.fn.infiniteCarousel = ->
  repeat = (str, num) ->
    new Array(num + 1).join str
  @each ->
    gotoPage = (page) ->
      dir = (if page < currentPage then -1 else 1)
      n = Math.abs(currentPage - page)
      left = singleWidth * dir * visible * n
      $wrapper.filter(":not(:animated)").animate
        scrollLeft: "+=" + left
      , 100, ->
        if page is 0
          $wrapper.scrollLeft singleWidth * visible * pages
          page = pages
        else if page > pages
          $wrapper.scrollLeft singleWidth * visible
          page = 1
        currentPage = page

      false
    $wrapper = $("> div", this).css("overflow", "hidden")
    $slider = $wrapper.find("> ul")
    $items = $slider.find("> li")
    $single = $items.filter(":first")
    singleWidth = $single.outerWidth()
    visible = parseInt($wrapper.innerWidth() / singleWidth)
    currentPage = 1
    pages = Math.ceil($items.length / visible)
    console.log $wrapper.innerWidth() / singleWidth
    unless ($items.length % visible) is 0
      $slider.append repeat("<li class=\"empty\" />", visible - ($items.length % visible))
      $items = $slider.find("> li")
    $items.filter(":first").before $items.slice(-visible).clone().addClass("cloned")
    $items.filter(":last").after $items.slice(0, visible).clone().addClass("cloned")
    $items = $slider.find("> li")
    $wrapper.scrollLeft singleWidth * visible
    $wrapper.after "<a class=\"arrow back\">&lt;</a><a class=\"arrow forward\">&gt;</a>"
    $("a.back", this).click ->
      gotoPage currentPage - 1

    $("a.forward", this).click ->
      gotoPage currentPage + 1

    $(this).bind "goto", (event, page) ->
      gotoPage page

jQuery(document).ready ->
  setTimeout showFrndSelector, 3000  if gameInstId is "0"  unless typeof gameInstId is "undefined"

(($) ->
  $.fn.extend customStyle: (options) ->
    if not $.browser.msie or ($.browser.msie and $.browser.version > 6)
      @each ->
        currentSelected = $(this).find(":selected")
        $(this).after("<span class=\"customStyleSelectBox\"><span class=\"customStyleSelectBoxInner\">" + currentSelected.text() + "</span></span>").css
          position: "absolute"
          opacity: 0
          fontSize: $(this).next().css("font-size")

        selectBoxSpan = $(this).next()
        selectBoxWidth = parseInt($(this).width()) - parseInt(selectBoxSpan.css("padding-left")) - parseInt(selectBoxSpan.css("padding-right"))
        selectBoxSpanInner = selectBoxSpan.find(":first-child")
        selectBoxSpan.css display: "inline-block"
        selectBoxSpanInner.css
          width: selectBoxWidth
          display: "inline-block"

        selectBoxHeight = parseInt(selectBoxSpan.height()) + parseInt(selectBoxSpan.css("padding-top")) + parseInt(selectBoxSpan.css("padding-bottom"))
        $(this).height(selectBoxHeight).change ->
          selectBoxSpanInner.text($(this).find(":selected").text()).parent().addClass "changed"
) jQuery