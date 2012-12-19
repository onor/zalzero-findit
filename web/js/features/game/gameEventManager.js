// Generated by CoffeeScript 1.4.0

define(["../../config/config", "../../config/version", "../../helper/confirmBox", "../../config/globals", "./rightHudController", "./leftHudController", "./gamePlayController", "../../popup/resignPopup", "../../popup/showInviteStatus", "../../popup/showFinalScore", "../../popup/showFrndSelector", "../../helper/utils", "../../popup/roundScorePopup", "drag-drop", "../../helper/sound"], function(config, version, confirmBox, globals, rightHudController, leftHudController, gamePlayController, resignPopup, showInviteStatus, showFinalScore, showFrndSelector, utils, roundScorePopup, dragNdrop, sound) {
  var zzUnionConnection;
  return zzUnionConnection = (function() {
    var UPC, addZzListeners, askClientData, clientAddedListener, clientAttrUpdateListener, clientRemovedListener, clientSnapshotListener, closeListener, init, joinedRoomListener, messageListener, msgManager, onLoginResult, onLogoutResult, orbiter, readyListener, resetGameVariables, roomAttrUpdateListener, roomSnapshotListener, sendDeclinedToServer, sendUpcMessageToServer, zzListeners;
    zzUnionConnection = function() {
      window.UPC = UPC;
      return init();
    };
    UPC = net.user1.orbiter.UPC;
    orbiter = null;
    msgManager = null;
    init = function() {
      orbiter = new net.user1.orbiter.Orbiter();
      msgManager = orbiter.getMessageManager();
      addZzListeners();
      utils.log("defining UPC");
      utils.log("ololisteners :" + zzListeners);
      utils.log("UPC definition completed!");
      if (!orbiter.getSystem().isJavaScriptCompatible()) {
        oloGame.displayChatMessage("systemChatMessage", "Your browser is not supported.");
        return;
      }
      if (orbiter) {
        orbiter.addEventListener(net.user1.orbiter.OrbiterEvent.READY, readyListener, this);
        orbiter.addEventListener(net.user1.orbiter.OrbiterEvent.CLOSE, closeListener, this);
      }
      return jQuery(function() {
        return orbiter.connect(config.unionConnection.url, config.unionConnection.port);
      });
    };
    roomAttrUpdateListener = function(e, roomId, attrKey, attrVal) {
      if (zzGlobals.roomVars[attrKey]) {
        zzGlobals.roomVars[attrKey] = attrVal;
        utils.log("Triggered room:", attrKey, attrVal);
        return jDocument.trigger("room:" + attrKey, attrVal);
      }
    };
    closeListener = function(e) {
      var el, _this;
      _this = this;
      jDocument.trigger(zzEvents.CONNECTION_CLOSE);
      el = document.getElementById("loadingGame");
      if (el && el.style) {
        el.style.display = "block";
      }
      return window.setTimeout((function() {
        return document.location.reload(true);
      }), 5000);
    };
    onLoginResult = function(clientID, userID, arg2) {
      var unionClientId;
      unionClientId = clientID;
      return zzGlobals.currentUserDBId = userID;
    };
    roomSnapshotListener = function(requestID, roomID, occupantCount, observerCount, roomAttrsStr) {
      var argLen, i, len, roomAttrKey, roomAttrVal, roomAttrsSplit, userFBVOs, userVO;
      argLen = arguments.length;
      i = 5;
      while (i < argLen) {
        userVO = new zzGlobals.UserVO(arguments[i], arguments[i + 3]);
        jDocument.trigger(zzEvents.RECEIVE_USERVO, [userVO, false]);
        i += 5;
      }
      if ((typeof getGameInstWithFBFriends !== "undefined" && getGameInstWithFBFriends !== null) && zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID]) {
        userFBVOs = getGameInstWithFBFriends([zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID]]);
      }
      roomAttrsSplit = roomAttrsStr.split("|");
      len = roomAttrsSplit.length;
      roomAttrKey = null;
      roomAttrVal = null;
      i = 0;
      while (i < len) {
        if (roomAttrsSplit[i] && roomAttrsSplit[i + 1]) {
          roomAttrKey = roomAttrsSplit[i];
          roomAttrVal = roomAttrsSplit[i + 1];
          if (zzGlobals.roomVars[roomAttrKey]) {
            if (!(roomAttrKey === zzGlobals.roomCodes.WINNER_ID && roomAttrVal === "-1")) {
              utils.log("Triggered via ss room:", roomAttrKey, roomAttrVal);
              zzGlobals.roomVars[roomAttrKey] = {};
              zzGlobals.roomVars[roomAttrKey] = roomAttrVal;
              jDocument.trigger("room:" + roomAttrKey, roomAttrVal);
            }
          }
        }
        i += 2;
      }
      return true;
    };
    clientSnapshotListener = function(requestID, clientID, userID, a4, clientAttrsStr) {
      var clientAttrKey, clientAttrVal, clientAttrsSplit, i, len;
      if (config.isDevEnvironment) {
        utils.log("ClientSnapshotListener ", clientAttrsStr);
      }
      if (userLoginId === userID) {
        clientAttrsSplit = clientAttrsStr.split("|");
        len = clientAttrsSplit.length;
        clientAttrKey = null;
        clientAttrVal = null;
        i = 0;
        while (i < len) {
          if (clientAttrsSplit[i] && clientAttrsSplit[i + 1]) {
            clientAttrKey = clientAttrsSplit[i];
            clientAttrVal = clientAttrsSplit[i + 1];
            if (clientVars[clientAttrKey]) {
              clientVars[clientAttrKey] = clientAttrVal;
              jDocument.trigger("client:" + clientAttrKey, clientAttrVal);
            }
          }
          i += 2;
        }
        return true;
      }
    };
    joinedRoomListener = function(rId) {
      var roomID;
      roomID = rId;
      resetGameVariables();
      return jDocument.trigger(zzEvents.JOINED_ROOM);
    };
    resetGameVariables = function() {
      var flag_roundDrawn;
      flag_roundDrawn = false;
      jQuery("#userTopPicHUDMain").css("left", "0");
      jQuery("#scroll_carousel .selected").removeClass("selected");
      jQuery("#gameBetPanel").css("display", 'block');
      return jQuery("#right_hud_" + gameInstId).addClass("selected");
    };
    clientAddedListener = function(roomID, clientID, userID) {
      var userVO;
      userVO = new zzGlobals.UserVO(clientID, arguments[3]);
      if (config.isDevEnvironment) {
        utils.log("user added and triggered : [UserVO:", userVO, ",clientId:" + clientID + ",args:" + arguments[3] + "]");
      }
      jDocument.trigger(zzEvents.CLIENT_JOINED, userVO);
      return jDocument.trigger(zzEvents.RECEIVE_USERVO, [userVO, true]);
    };
    clientRemovedListener = function(roomID, clientID) {
      var userName;
      userName = clientID;
      if (clientID && zzGlobals.userVOsIndex[clientID]) {
        return jDocument.trigger(zzEvents.REMOVE_USERVO, clientID);
      }
    };
    messageListener = function(messageName, broadcastType, fromClientID, roomID, message) {
      return jDocument.trigger(zzEvents.SERVER_MESSAGE, [messageName, broadcastType, fromClientID, roomID, message, orbiter.getClientID()]);
    };
    clientAttrUpdateListener = function(roomId, clientId, userId, attrKey, attrVal) {
      if (zzGlobals.currentUserDBId === userId) {
        if (zzGlobals.clientVars[attrKey]) {
          zzGlobals.clientVars[attrKey] = attrVal;
          return jDocument.trigger("client:" + attrKey, attrVal);
        }
      }
    };
    askClientData = function(e) {
      utils.log("askfordata");
      return msgManager.sendUPC(UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|AGD", "UID|" + zzGlobals.currentUserDBId);
    };
    readyListener = function(e) {
      msgManager.sendUPC(UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|LI", "UI|" + userLoginId, "GI|" + gameInstId);
      return jDocument.bind(zzEvents.SEND_UPC_MESSAGE, sendUpcMessageToServer);
    };
    window.gameChangeListener = function(e, gameInstIdTemp) {
      var flag_roundBetsDrawn, flag_roundDrawn;
      zzGlobals.inviteStatus = 0;
      utils.log("gameInstIdTemp", gameInstIdTemp);
      if (typeof e === 'string') {
        gameInstIdTemp = e;
      }
      if (typeof gameInstIdTemp === 'undefined') {
        eval("gameInstIdTemp = gameInstId");
      } else {
        eval("gameInstId = gameInstIdTemp");
      }
      zzGlobals.roomVars.FR = -1;
      flag_roundDrawn = false;
      flag_roundBetsDrawn = false;
      return msgManager.sendUPC(UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|CG", "UI|" + userLoginId, "GI|" + gameInstIdTemp);
    };
    jDocument.bind("gameChangeListener", gameChangeListener);
    sendDeclinedToServer = function(e, gameSeatId, gameId) {
      $('#accept_decline_' + gameId).text('');
      $('#accept_decline_' + gameId).css('cursor', 'default');
      $('#accept_decline_' + gameId).html(utils.getMiniLoaderHTML());
      return msgManager.sendUPC(UPC.SEND_SERVERMODULE_MESSAGE, config.unionGameServerId, "REQ", "C|DG", "GSID|" + gameSeatId);
    };
    jDocument.bind("sendDeclinedToServer", sendDeclinedToServer);
    sendUpcMessageToServer = function(event, upcFunctionCode, p2, p3, p4, p5, p6) {
      var argArr, i, _i, _ref;
      if (config.isDevEnvironment) {
        utils.log("sending upc message [upcFunctionCode:" + upcFunctionCode + ",p2:" + p2 + ",p3:" + p3 + ",p4:" + p4 + ",p5:" + p5 + ",p6:" + p6 + "]");
      }
      argArr = [];
      for (i = _i = 0, _ref = arguments.length; 0 <= _ref ? _i < _ref : _i > _ref; i = 0 <= _ref ? ++_i : --_i) {
        if (i === 0) {
          continue;
        } else {
          if (arguments[i]) {
            argArr.push(arguments[i]);
          } else {
            argArr.push('');
          }
        }
      }
      utils.log(argArr);
      if (upcFunctionCode) {
        return msgManager.sendUPC.apply(msgManager, argArr);
      }
    };
    onLogoutResult = function(clientUnionId, userId) {
      if (parseInt(unionClientId) === parseInt(clientUnionId)) {
        return window.location = "http://" + document.domain + baseUrl + "/site/closesession";
      }
    };
    addZzListeners = function() {
      var msgEvt;
      for (msgEvt in zzListeners) {
        if (msgManager && msgManager.addMessageListener) {
          if (zzListeners[msgEvt] === "messageListener") {
            msgManager.addMessageListener(UPC[msgEvt], zzListeners[msgEvt], this, gameInstId);
          } else {
            msgManager.addMessageListener(UPC[msgEvt], zzListeners[msgEvt], this);
          }
        }
      }
      return true;
    };
    zzListeners = {
      JOINED_ROOM: joinedRoomListener,
      CLIENT_ADDED_TO_ROOM: clientAddedListener,
      CLIENT_REMOVED_FROM_ROOM: clientRemovedListener,
      ROOM_SNAPSHOT: roomSnapshotListener,
      CLIENT_SNAPSHOT: clientSnapshotListener,
      ROOM_ATTR_UPDATE: roomAttrUpdateListener,
      CLIENT_ATTR_UPDATE: clientAttrUpdateListener,
      LOGGED_IN: onLoginResult,
      LOGGED_OFF: onLogoutResult,
      RECEIVE_MESSAGE: messageListener
    };
    if (version.version) {
      $("#active-screen").append("<div style=\"position:absolute;bottom:5px;left:5px;z-index:999;padding:5px;border-radius:4px;background:rgba(4,4,4,0.8)\">Version : " + version.version + " </div>");
    }
    return zzUnionConnection;
  })();
});
