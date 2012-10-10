// Generated by CoffeeScript 1.3.3
var acceptInvitationAdd, drawRoundsPanel, flag_roundBetsDrawn, flag_roundDrawn, isDevEnvironment, popupMSG, rematchCall, remindUserAdd, resignStatus, roundVOsIdxRightHUD, showRoundScorePopup, usersRecord, __hasProp_, _currentRoundStatus;

popupMSG = {
  remindSucess: function(userName) {
    return "You have sent reminder successfuly";
  },
  acceptInvite: 'Would you like to start playing',
  declineInvite: function() {
    return "You have declined successfuly";
  },
  gameCreate: 'Challenge has been sent. Would you like to start playing.'
};

__hasProp_ = Object.prototype.hasOwnProperty;

rematchCall = function() {};

usersRecord = function() {};

resignStatus = 0;

flag_roundBetsDrawn = false;

drawRoundsPanel = function() {};

showRoundScorePopup = function() {};

_currentRoundStatus = -1;

roundVOsIdxRightHUD = {};

flag_roundDrawn = false;

acceptInvitationAdd = function(div, gameIdToAdd) {
  return div.on('click', function(e) {
    return acceptInvitation(gameIdToAdd);
  });
};

remindUserAdd = function(gameLinkID, remindUsersLeft, x, fbUser, div) {
  return div.onclick = function() {
    return remindUser(gameLinkID, remindUsersLeft, x, fbUser);
  };
};

if (typeof valDevEnvironment !== "undefined") {
  isDevEnvironment = valDevEnvironment;
} else {
  isDevEnvironment = true;
}

jQuery(function() {
  var ZalerioUserComponent, addEventHandler, closeInvits, jDocument, noUserExist, objSize, oloUserComponent, pad2, printOnConsole, redirectToPlayNow, removeClassName, removeEventHandler, roomID, unionClientId;
  jDocument = jQuery(document);
  unionClientId = null;
  roomID = null;
  pad2 = function(number) {
    var _ref;
    _ref = void 0;
    return ((_ref = number < 10) != null ? _ref : {
      0: ""
    }) + number;
  };
  oloUserComponent = function() {
    var DEFAULT_PLAYER_IMG_URL, MAX_PLAYERS_IN_A_GAME, PLAYER_OFFLINE_IMG_URL, PLAYER_ONLINE_IMG_URL, activateUserPanelJQuery, currentNoOfUsers, docElems, drawUserPanel, flag_drawUserPanel, pushToUserVO, refreshUserPanel, removeFromUserVO, renderUserVOToUserPanel, showAllUsers, updateUserIndex, updateUserIndexForPartyId, updateUserIndexForPartyIdViaOfflinePlayers, userDivCodes, userStatusCurrent, userVOsDivIndex, userVOsLen;
    flag_drawUserPanel = false;
    docElems = {};
    DEFAULT_PLAYER_IMG_URL = "";
    PLAYER_ONLINE_IMG_URL = "";
    PLAYER_OFFLINE_IMG_URL = "";
    showAllUsers = "ALL";
    userStatusCurrent = 10;
    userVOsLen = 0;
    userVOsDivIndex = {};
    MAX_PLAYERS_IN_A_GAME = 100;
    currentNoOfUsers = 0;
    userDivCodes = {
      USER_IMAGE: "userImg",
      USER_DISPLAY_NAME: "userDisplayNameSpan",
      USER_SCORE: "userScoreSpanValue",
      USER_RANK: "userRankSpan",
      USER_LI: "userLi",
      USER_ONLINE_STATUS: "userOnlineStatusDiv"
    };
    updateUserIndex = function() {
      var i;
      zzGlobals.userVOsIndex = {};
      for (i in zzGlobals.userVOs) {
        zzGlobals.userVOsIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_UNION_ID]] = zzGlobals.userVOs[i];
      }
      return updateUserIndexForPartyId();
    };
    updateUserIndexForPartyId = function() {
      var i, _results;
      _results = [];
      for (i in zzGlobals.userVOs) {
        if (!zzGlobals.userVOsPartyIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_PARTY_ID]]) {
          _results.push(zzGlobals.userVOsPartyIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_PARTY_ID]] = zzGlobals.userVOs[i]);
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    };
    updateUserIndexForPartyIdViaOfflinePlayers = function() {
      var i, _results;
      _results = [];
      for (i in zzGlobals.offlinePlayers) {
        if (!zzGlobals.userVOsPartyIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_PARTY_ID]]) {
          _results.push(zzGlobals.userVOsPartyIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_PARTY_ID]] = zzGlobals.offlinePlayers[i]);
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    };
    refreshUserPanel = function() {
      var i, offlineUserVO, userCtr, userVO;
      userCtr = 0;
      for (i in zzGlobals.userVOs) {
        if (zzGlobals.userVOs.hasOwnProperty(i)) {
          if ((showAllUsers !== "FB") || (showAllUsers === "FB" && zzGlobals.userFBVOs[i])) {
            userVO = zzGlobals.userVOs[i];
            renderUserVOToUserPanel(userCtr++, userVO);
          }
        }
      }
      if ((typeof getGameInstWithFriends !== "undefined" && getGameInstWithFriends !== null) && !zzGlobals.offlinePlayers && getGameInstWithFriends(zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID])) {
        zzGlobals.offlinePlayers = getGameInstWithFriends(zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID]);
        updateUserIndexForPartyIdViaOfflinePlayers();
        changeWinner();
      }
      for (i in zzGlobals.offlinePlayers) {
        if (zzGlobals.offlinePlayers.hasOwnProperty(i)) {
          offlineUserVO = zzGlobals.offlinePlayers[i];
          if (showAllUsers === "FB") {
            if (!userFBVOs[i]) {
              renderUserVOToUserPanel(userCtr++, offlineUserVO, true);
            }
          } else {
            if (!zzGlobals.userVOs[i]) {
              renderUserVOToUserPanel(userCtr++, offlineUserVO, true);
            }
          }
        }
      }
      currentNoOfUsers = userCtr;
      while (userCtr < MAX_PLAYERS_IN_A_GAME) {
        if (userVOsDivIndex && userVOsDivIndex[userCtr] && userVOsDivIndex[userCtr][userDivCodes.USER_DIV]) {
          userVOsDivIndex[userCtr][userDivCodes.USER_DIV].style.display = "none";
        }
        userCtr++;
      }
      return activateUserPanelJQuery(currentNoOfUsers);
    };
    activateUserPanelJQuery = function(currentNoOfUsers) {
      zzGlobals.roomVars[zzGlobals.roomCodes.USER_TOTAL] = currentNoOfUsers;
      return jDocument.trigger("room:" + zzGlobals.roomCodes.USER_TOTAL, currentNoOfUsers);
    };
    renderUserVOToUserPanel = function(idx, userVO, userOffline) {
      userOffline = userOffline || false;
      if (userVO && userVO[zzGlobals.clientCodes.USER_ID]) {
        userVOsDivIndex[idx][userDivCodes.USER_LI].style.display = "block";
        if (userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME]) {
          userVOsDivIndex[idx][userDivCodes.USER_DISPLAY_NAME].innerHTML = userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME];
        } else {
          userVOsDivIndex[idx][userDivCodes.USER_DISPLAY_NAME].innerHTML = "-";
        }
        if (userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID] && userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID].length > 2) {
          userVOsDivIndex[idx][userDivCodes.USER_IMAGE].src = "https://graph.facebook.com/" + userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID] + "/picture";
        } else {
          userVOsDivIndex[idx][userDivCodes.USER_IMAGE].src = DEFAULT_PLAYER_IMG_URL;
        }
        if (userOffline) {
          return userVOsDivIndex[idx][userDivCodes.USER_ONLINE_STATUS].className = "user-offline";
        } else {
          return userVOsDivIndex[idx][userDivCodes.USER_ONLINE_STATUS].className = "user-online";
        }
      }
    };
    drawUserPanel = function() {
      var i, playerListUl, userDisplayNameDiv, userImg, userImgDiv, userLi, userOnlineStatusDiv, userVODivIndexObj, userVOIndex;
      userVOIndex = {};
      playerListUl = document.getElementById("nowPlayers");
      playerListUl.innerHML = "";
      i = 0;
      while ((0 <= MAX_PLAYERS_IN_A_GAME ? i < MAX_PLAYERS_IN_A_GAME : i > MAX_PLAYERS_IN_A_GAME)) {
        userLi = document.createElement("li");
        userLi.style.display = "none";
        userImgDiv = document.createElement("div");
        userImgDiv.className = "imgOfWn";
        userImg = document.createElement("img");
        userImg.src = DEFAULT_PLAYER_IMG_URL;
        userImgDiv.appendChild(userImg);
        userOnlineStatusDiv = document.createElement("div");
        userOnlineStatusDiv.className = "offline";
        userImgDiv.appendChild(userOnlineStatusDiv);
        userLi.appendChild(userImgDiv);
        userDisplayNameDiv = document.createElement("div");
        userDisplayNameDiv.className = "uName";
        userLi.appendChild(userDisplayNameDiv);
        playerListUl.appendChild(userLi);
        userVODivIndexObj = {};
        userVODivIndexObj[userDivCodes.USER_LI] = userLi;
        userVODivIndexObj[userDivCodes.USER_IMAGE] = userImg;
        userVODivIndexObj[userDivCodes.USER_DISPLAY_NAME] = userDisplayNameDiv;
        userVODivIndexObj[userDivCodes.USER_ONLINE_STATUS] = userOnlineStatusDiv;
        userVOsDivIndex[i] = userVODivIndexObj;
        if (0 <= MAX_PLAYERS_IN_A_GAME) {
          i++;
        } else {
          i--;
        }
      }
      flag_drawUserPanel = true;
      return refreshUserPanel();
    };
    pushToUserVO = function(event, userVO, flag_updateUserIdx) {
      if (userVO && userVO[zzGlobals.clientCodes.USER_ID]) {
        zzGlobals.userVOs[userVO[zzGlobals.clientCodes.USER_ID]] = userVO;
        updateUserIndex();
      }
      if (flag_drawUserPanel) {
        return refreshUserPanel();
      }
    };
    removeFromUserVO = function(event, clientID) {
      var clientName;
      clientName = void 0;
      if (clientID && zzGlobals.userVOsIndex[clientID]) {
        clientName = zzGlobals.userVOsIndex[clientID][zzGlobals.clientCodes.USER_DISPLAY_NAME];
        delete zzGlobals.userVOs[zzGlobals.userVOsIndex[clientID][zzGlobals.clientCodes.USER_ID]];
        jDocument.trigger(zzEvents.CLIENT_LEFT, [clientID, clientName]);
        return updateUserIndex();
      }
    };
    return jQuery(function() {
      jDocument.bind(zzEvents.JOINED_ROOM, drawUserPanel);
      jDocument.bind(zzEvents.RECEIVE_USERVO, pushToUserVO);
      return jDocument.bind(zzEvents.REMOVE_USERVO, removeFromUserVO);
    });
  };
  redirectToPlayNow = function(gameInstId) {
    var urlToRe, _this;
    _this = this;
    urlToRe = ofbizUrl + "playnowGame?gameInstId=" + gameInstId;
    return jQuery.ajax(function() {
      return {
        url: urlToRe,
        success: function(data) {
          return jQuery("#gameScreenPanel").html(data);
        }
      };
    });
  };
  jQuery(function() {
    jDocument.bind("room:ZS", printOnConsole);
    jDocument.bind("room:AR", printOnConsole);
    return jDocument.bind("room:FR", noUserExist);
  });
  noUserExist = function() {
    if (zzGlobals.roomVars.FR === "2") {
      messagePopup("Game cancel!!! <br/>  Insufficient Users in Game");
      return jQuery("#gameBetPanel").hide();
    }
  };
  printOnConsole = function(obj, val) {
    var i, roundObj, roundObjs, roundStr, roundVOs;
    if ((obj != null) && (obj.type != null) && (val != null)) {
      if (isDevEnvironment) {
        console.log(obj.type, " : ", val);
      }
      if (obj.type === "room:AR") {
        roundVOs = {};
        roundObjs = jQuery.parseJSON(val);
        for (i in roundObjs) {
          roundStr = roundObjs[i];
          roundObj = jQuery.parseJSON(roundStr);
          roundVOs[i] = roundObj;
        }
        if (isDevEnvironment) {
          console.log("RoundVOs :", roundVOs);
        }
      }
      if (obj.type === "room:BV") {
        roundVOs = {};
        roundObjs = jQuery.parseJSON(val);
        for (i in roundObjs) {
          roundStr = roundObjs[i];
          roundObj = jQuery.parseJSON(roundStr);
          if (roundObj["PR"] != null) {
            roundObj["PR"] = jQuery.parseJSON(roundObj["PR"]);
          }
          roundVOs[i] = roundObj;
        }
        if (isDevEnvironment) {
          return console.log("TilesVOs :", roundVOs);
        }
      }
    }
  };
  addEventHandler = function(node, evtType, func, isCapture) {
    if (window && window.addEventListener) {
      return node.addEventListener(evtType, func, isCapture);
    } else {
      return node.attachEvent("on" + evtType, func);
    }
  };
  removeEventHandler = function(node, evtType, func, isCapture) {
    if (window && window.removeEventListener) {
      return node.removeEventListener(evtType, func, isCapture);
    } else {
      return node.detachEvent("on" + evtType, func);
    }
  };
  removeClassName = function(node, cls) {
    var reg;
    reg = void 0;
    if ((node != null) && node.className) {
      reg = new RegExp("(\\s|^)" + cls + "(\\s|$)");
      return node.className = node.className.replace(reg, " ");
    }
  };
  ZalerioUserComponent = (function() {
    var DEFAULT_PLAYER_IMG_URL, MAX_PLAYERS_IN_A_GAME, PLAYER_OFFLINE_IMG_URL, PLAYER_ONLINE_IMG_URL, currentNoOfUsers, docElems, docElemsCodes, drawUserPanel, flag_drawUserPanel, onClickRedrict, pushToUserVO, refreshUserPanel, removeFromUserVO, renderUserVOToUserPanel, showAllUsers, updateTotalSeats, updateUserIndex, updateUserIndexForPartyId, updateUserIndexForPartyIdViaOfflinePlayers, updateUserIndexForSeatId, updateUserIndexForSeatIdViaOfflinePlayers, updateZalerioScores, userDivCodes, userPanelMov, userScoreArr, userScoreObj, userStatusCurrent, userVOsDivIndex, userVOsLen;
    ZalerioUserComponent = function() {};
    onClickRedrict = function(userRecodeDivValue, gameID) {
      var game;
      game = void 0;
      game = gameID;
      return window.location = "http://" + document.domain + baseUrl + "/gameinst/play?gameinst_id=" + game;
    };
    flag_drawUserPanel = false;
    docElems = {};
    userPanelMov = null;
    docElemsCodes = {
      LEFT_HUD_YOUR_TURN: "leftHudYourTurn",
      LEFT_HUD_MY_TURN: "leftHudMyTurn"
    };
    DEFAULT_PLAYER_IMG_URL = "";
    PLAYER_ONLINE_IMG_URL = "";
    PLAYER_OFFLINE_IMG_URL = "";
    showAllUsers = "ALL";
    userStatusCurrent = 10;
    userVOsLen = 0;
    userVOsDivIndex = {};
    MAX_PLAYERS_IN_A_GAME = 0;
    userScoreObj = {};
    userScoreArr = [];
    currentNoOfUsers = 0;
    userDivCodes = {
      USER_PIC_TOP_HUD_LI: "userPicTopHUDLi",
      USER_PIC_TOP_HUD_IMG: "userPicTopHUDImg",
      USER_PIC_TOP_HUD_NAME: "userPicTopHUDName",
      USER_PIC_TOP_HUD_LAST_PLAY: "userPicTopHUDLastPlay",
      USER_PIC_TOP_HUD_PLAY_STATUS: "userPicTopHUDPlayStatus",
      USER_PIC_TOP_HUD_REMIND: "userPicTopHUDRemind",
      USER_TOP_HUD_TR: "userTopHUDTr",
      USER_TOP_HUD_SERAIL: "userTopHUDSerial",
      USER_TOP_HUD_NAME: "userTopHUDName",
      USER_TOP_HUD_SCORE: "userTopHUDScore",
      USER_TOP_HUD_ONLINE: "userTopHUDOnline",
      USER_TOP_HUD_BETSTATUS: "userTopHUDBetstatus",
      USER_LEFT_HUDMT_LI: "userLeftHUDMTLi",
      USER_LEFT_HUDMT_IMG: "userLeftHUDMTImg",
      USER_LEFT_HUDMT_NAME: "userLeftHUDMTName",
      USER_LEFT_HUDMT_BET_MSG: "userLeftHUDMTBetMsg",
      USER_LEFT_HUDMT_LASTBET_MSG: "userLeftHUDMTLastbetMsg",
      USER_LEFT_HUDYT_LI: "userLeftHUDYTLi",
      USER_LEFT_HUDYT_IMG: "userLeftHUDYTImg",
      USER_LEFT_HUDYT_NAME: "userLeftHUDYTName",
      USER_LEFT_HUDYT_BET_MSG: "userLeftHUDYTBetMsg",
      USER_LEFT_HUDYT_LASTBET_MSG: "userLeftHUDYTLastbetMsg"
    };
    updateUserIndex = function() {
      var i;
      zzGlobals.userVOsIndex = {};
      for (i in zzGlobals.userVOs) {
        zzGlobals.userVOsIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_UNION_ID]] = zzGlobals.userVOs[i];
      }
      updateUserIndexForPartyId();
      return updateUserIndexForSeatId();
    };
    updateUserIndexForPartyId = function() {
      var i, _results;
      i = void 0;
      _results = void 0;
      _results = [];
      for (i in zzGlobals.userVOs) {
        if (!zzGlobals.userVOsPartyIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_PARTY_ID]]) {
          _results.push(zzGlobals.userVOsPartyIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_PARTY_ID]] = zzGlobals.userVOs[i]);
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    };
    updateUserIndexForSeatId = function() {
      var i, _results;
      _results = [];
      for (i in zzGlobals.userVOs) {
        if (!zzGlobals.userVOsSeatIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_SEAT_ID]]) {
          _results.push(zzGlobals.userVOsSeatIndex[zzGlobals.userVOs[i][zzGlobals.clientCodes.USER_SEAT_ID]] = zzGlobals.userVOs[i]);
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    };
    updateUserIndexForPartyIdViaOfflinePlayers = function() {
      var i, _results;
      _results = [];
      for (i in zzGlobals.offlinePlayers) {
        if (!zzGlobals.userVOsPartyIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_PARTY_ID]]) {
          _results.push(zzGlobals.userVOsPartyIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_PARTY_ID]] = zzGlobals.offlinePlayers[i]);
        }
      }
      return _results;
    };
    updateUserIndexForSeatIdViaOfflinePlayers = function() {
      var i, _results;
      i = void 0;
      _results = void 0;
      _results = [];
      for (i in zzGlobals.offlinePlayers) {
        if (!zzGlobals.userVOsSeatIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_SEAT_ID]]) {
          _results.push(zzGlobals.userVOsSeatIndex[zzGlobals.offlinePlayers[i][zzGlobals.clientCodes.USER_SEAT_ID]] = zzGlobals.offlinePlayers[i]);
        } else {
          _results.push(void 0);
        }
      }
      return _results;
    };
    refreshUserPanel = function() {
      var arrLen, userCtr, userPos, userVO;
      arrLen = void 0;
      userCtr = void 0;
      userPos = void 0;
      userVO = void 0;
      userCtr = 0;
      if (isDevEnvironment) {
        console.log("refreshUserPanel : ", userScoreArr);
      }
      arrLen = userScoreArr.length;
      userPos = 0;
      while ((0 <= arrLen ? userPos < arrLen : userPos > arrLen)) {
        userVO = zzGlobals.userVOsSeatIndex[userScoreArr[userPos]];
        if (isDevEnvironment) {
          console.log(userVO);
        }
        if (userVO) {
          renderUserVOToUserPanel(userCtr++, userVO, null, userPos + 1);
        }
        if (0 <= arrLen) {
          userPos++;
        } else {
          userPos--;
        }
      }
      if ((typeof getGameInstWithFriends !== "undefined" && getGameInstWithFriends !== null) && !zzGlobals.offlinePlayers && getGameInstWithFriends(zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID])) {
        zzGlobals.offlinePlayers = getGameInstWithFriends(zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID]);
        updateUserIndexForPartyIdViaOfflinePlayers();
        return updateUserIndexForSeatIdViaOfflinePlayers();
      }
    };
    renderUserVOToUserPanel = function(idx, userVO, userOffline, userSerial) {
      var userDisplayName, userFacebookImgUrl;
      userDisplayName = void 0;
      userFacebookImgUrl = void 0;
      userOffline = userOffline || false;
      if (userVOsDivIndex[idx] && userVO && userVO[zzGlobals.clientCodes.USER_ID]) {
        userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_TR].style.visibility = "visible";
        userDisplayName = "-";
        if (userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME] != null) {
          userDisplayName = userVO[zzGlobals.clientCodes.USER_DISPLAY_NAME];
        }
        userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_NAME].innerHTML = userDisplayName;
        userFacebookImgUrl = DEFAULT_PLAYER_IMG_URL;
        if ((userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID] != null) && userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID].length > 2) {
          userFacebookImgUrl = "https://graph.facebook.com/" + userVO[zzGlobals.clientCodes.USER_FACEBOOK_ID] + "/picture";
        }
        userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_SERAIL].innerHTML = userSerial + ".";
        if (userVO[zzGlobals.clientCodes.USER_SEAT_ID]) {
          userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_SCORE].innerHTML = userScoreObj[userVO[zzGlobals.clientCodes.USER_SEAT_ID]];
        } else {
          userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_SCORE].innerHTML = 0;
        }
        if (zzGlobals.roomVars.PP.indexOf(userVO[zzGlobals.clientCodes.USER_SEAT_ID]) !== -1) {
          $(userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_BETSTATUS]).removeClass("arrowclass");
        } else {
          $(userVOsDivIndex[idx][userDivCodes.USER_TOP_HUD_BETSTATUS]).addClass("arrowclass");
        }
        if (isDevEnvironment) {
          return console.log(userScoreObj);
        }
      }
    };
    drawUserPanel = function() {
      var i, playerListTopHUDTbl, s, scoreArray, seatId, seatIdArray, userObject, userTopHUDBetsPlacedTd, userTopHUDNameTd, userTopHUDScoreTd, userTopHUDSerialNumTd, userTopHUDTr, usersInfoObject, usersInfoObjectx, usersObject, usersScoreObject, x, y;
      if (isDevEnvironment) {
        console.log("drawUserPanel");
      }
      playerListTopHUDTbl = document.getElementById("userScoreHUDMain");
      playerListTopHUDTbl.innerHTML = "";
      usersObject = jQuery.parseJSON(zzGlobals.roomVars.AP);
      if (isDevEnvironment) {
        console.log("Score Board left (zzGlobals.roomVars.AP)", usersObject);
      }
      for (i in usersObject) {
        usersInfoObject = jQuery.parseJSON(usersObject[i]);
      }
      i = 0;
      usersInfoObject = jQuery.parseJSON(usersInfoObject.PLRS);
      scoreArray = [];
      seatIdArray = [];
      usersInfoObjectx = [];
      for (seatId in usersInfoObject) {
        usersInfoObjectx[seatId] = jQuery.parseJSON(usersInfoObject[seatId]);
        scoreArray[seatId] = parseInt(usersInfoObjectx[seatId].PSC);
        seatIdArray.push(seatId);
      }
      seatIdArray.sort(function(x, y) {
        return scoreArray[y] - scoreArray[x];
      });
      for (y in seatIdArray) {
        x = seatIdArray[y];
        userObject = jQuery.parseJSON(usersInfoObject[x]);
        if (userObject.GSS === 3 || userObject.GSS === 5) {
          continue;
        }
        if (isDevEnvironment) {
          console.log("drawing for user #", i + 1);
        }
        userTopHUDTr = document.createElement("tr");
        userTopHUDTr.className = "userScoreHUD_player";
        userTopHUDBetsPlacedTd = document.createElement("td");
        userTopHUDBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo arrowclass";
        userTopHUDBetsPlacedTd.innerHTML = "";
        if (userObject.CRS === 9) {
          userTopHUDBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo";
        }
        userTopHUDTr.appendChild(userTopHUDBetsPlacedTd);
        userTopHUDSerialNumTd = document.createElement("td");
        userTopHUDSerialNumTd.className = "userScoreHUD_playerSerial";
        userTopHUDSerialNumTd.innerHTML = ++i;
        userTopHUDTr.appendChild(userTopHUDSerialNumTd);
        userTopHUDNameTd = document.createElement("td");
        userTopHUDNameTd.className = "userScoreHUD_playerName";
        userTopHUDNameTd.innerHTML = "";
        if (userObject.PDN) {
          if (parseInt(userObject.PRE) !== 1) {
            if (parseInt(userObject.PON) === 0) {
              userTopHUDTr.className = "userScoreHUD_player GRAY";
            }
            userTopHUDNameTd.innerHTML = userObject.PDN;
          } else {
            if (parseInt(userObject.UI) === parseInt(zzGlobals.currentUserDBId)) {
              jQuery("#gameBetPanel").hide();
              jQuery(".draggableBets").attr("draggable", "false");
            }
            s = document.createElement("s");
            s.innerHTML = userObject.PDN;
            userTopHUDNameTd.appendChild(s);
            userTopHUDBetsPlacedTd.className = "userScoreHUD_playerBetPlacedNo";
          }
        }
        userTopHUDTr.appendChild(userTopHUDNameTd);
        userTopHUDScoreTd = document.createElement("td");
        userTopHUDScoreTd.className = "userScoreHUD_playerScore";
        if (userObject.PSC) {
          userTopHUDScoreTd.innerHTML = userObject.PSC;
        } else {
          try {
            usersScoreObject = jQuery.parseJSON(zzGlobals.roomVars.ZS);
            userTopHUDScoreTd.innerHTML = typeof usersScoreObject[x] !== "undefined" ? usersScoreObject[x] : 0;
          } catch (_error) {}
        }
        userTopHUDTr.appendChild(userTopHUDScoreTd);
        playerListTopHUDTbl.appendChild(userTopHUDTr);
      }
      flag_drawUserPanel = true;
      if (isDevEnvironment) {
        console.log("Draw score Panel Bord", userObject);
      }
      return refreshUserPanel();
    };
    pushToUserVO = function(event, userVO, flag_updateUserIdx) {
      if ((userVO != null) && (userVO[zzGlobals.clientCodes.USER_ID] != null)) {
        zzGlobals.userVOs[userVO[zzGlobals.clientCodes.USER_ID]] = userVO;
        updateUserIndex();
      }
      if (flag_drawUserPanel != null) {
        return refreshUserPanel();
      }
    };
    removeFromUserVO = function(event, clientID) {
      var clientName;
      clientName = void 0;
      if ((clientID != null) && (zzGlobals.userVOsIndex[clientID] != null)) {
        clientName = zzGlobals.userVOsIndex[clientID][zzGlobals.clientCodes.USER_DISPLAY_NAME];
        delete zzGlobals.userVOs[zzGlobals.userVOsIndex[clientID][zzGlobals.clientCodes.USER_ID]];
        jDocument.trigger(zzEvents.CLIENT_LEFT, [clientID, clientName]);
        return updateUserIndex();
      }
    };
    updateTotalSeats = function(evt, val) {
      MAX_PLAYERS_IN_A_GAME = parseInt(val);
      if (isDevEnvironment) {
        console.log("Max Seats in game :  ", val);
      }
      return drawUserPanel();
    };
    updateZalerioScores = function(evt, val) {
      var userSeatId, _this;
      _this = this;
      userScoreObj = jQuery.parseJSON(val);
      userScoreArr = [];
      for (userSeatId in userScoreObj) {
        userScoreArr.push(userSeatId);
      }
      userScoreArr.sort(function(x, y) {
        return userScoreObj[y] - userScoreObj[x];
      });
      return refreshUserPanel();
    };
    jQuery(function() {
      var _this;
      _this = this;
      jDocument.bind(zzEvents.RECEIVE_USERVO, pushToUserVO);
      jDocument.bind(zzEvents.REMOVE_USERVO, removeFromUserVO);
      jDocument.bind("room:" + zzGlobals.roomCodes.ZALERIO_SCORES, updateZalerioScores);
      jDocument.bind("room:" + zzGlobals.roomCodes.PLAYER_BETS_PLACE, renderUserVOToUserPanel);
      return jDocument.bind("room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, drawUserPanel);
    });
    return ZalerioUserComponent;
  })();
  objSize = function(obj) {
    var key, size;
    size = 0;
    for (key in obj) {
      if (obj.hasOwnProperty(key)) {
        size++;
      }
    }
    return size;
  };
  //jDocument.trigger(zzEvents.SEND_UPC_MESSAGE, [UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|RG"]);
  closeInvits = function() {
    //return jDocument.trigger(zzEvents.SEND_UPC_MESSAGE, [UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|CI"]);
  };
  return oloUserComponent();
});
