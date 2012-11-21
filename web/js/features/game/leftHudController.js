// Generated by CoffeeScript 1.3.3

define(["../../config/config", "../../helper/notifications"], function(config, notifications) {
  var updateLeftHud, updatePlayerPlate;
  updatePlayerPlate = function() {
    var currentUserObj;
    currentUserObj = jQuery.parseJSON(zzGlobals.clientVars.UINFO);
    $("#currentUserName").text(currentUserObj.PDN);
    if (typeof currentUserObj.PL !== "undefined") {
      if (parseInt(currentUserObj.PL) === 0) {
        currentUserObj.PL = 1;
      }
      $("#currentUserAreaImg .userlevelbelt").remove();
      return $("#currentUserAreaImg").prepend("<img class=\"userlevelbelt\" src=\"" + baseUrl + "/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/" + config.userLevelImgBig[parseInt(currentUserObj.PL) - 1] + "\" />");
    }
  };
  updateLeftHud = function() {
    var fbUser, infoPlate, plrs, remind_user, seatID, userList, userPlayStatusClassName, userPlayStatusText;
    plrs = zzGlobals.dataObjVars.AP.PLRS;
    $("#gameInfo-game-players").empty();
    userList = $("<div class=\"background\"></div>");
    for (seatID in plrs) {
      if (zzGlobals.currentUserDBId === plrs[seatID].UI || plrs[seatID].GSS !== 1 && plrs[seatID].GSS !== 2) {
        continue;
      }
      remind_user = '';
      userPlayStatusText = "not played yet";
      userPlayStatusClassName = "userPlayStatus";
      if (plrs[seatID].CRS === 0) {
        userPlayStatusClassName = "userPlayStatus";
        remind_user = "<div class=\"reminder\" title=\"Remind\" alt=\"Remind\" ></div>";
      } else if (plrs[seatID].CRS === 5) {
        userPlayStatusText = '...playing now';
        userPlayStatusClassName = "userPlayStatus green";
      } else if (plrs[seatID].CRS === 9) {
        userPlayStatusText = "finished round";
        userPlayStatusClassName = "userPlayStatus green";
      }
      if (plrs[seatID].GSS === 1) {
        userPlayStatusClassName = "userPlayStatus red";
        userPlayStatusText = "not accepted yet";
        remind_user = "<div class=\"reminder\" title=\"Remind\" alt=\"Remind\"></div>";
      }
      fbUser = {};
      fbUser[plrs[seatID].PFB] = {
        GSS: plrs[seatID].GSS,
        CRS: plrs[seatID].CRS
      };
      infoPlate = $(" <div class=\"infoPlate\">\n									" + remind_user + "\n									<div class=\"userAreaImg\" id=\"\">\n											<img class=\"userlevelbelt\" src=\"" + baseUrl + "/images/zalerio_1.2/4.ingame_ui/carauselbelts_main_player/" + config.userLevelImgBig[parseInt(plrs[seatID].PL) - 1] + "\" />\n			                                <img class=\"backendImage " + (plrs[seatID].PON !== 1 ? "offline" : void 0) + "\" src=\"https://graph.facebook.com/" + plrs[seatID].PFB + "/picture\" />\n			                        </div>\n									<div class=\"userinfo\">\n										<div class=\"username\">" + plrs[seatID].PDN + "</div>\n										<div class=\"lastPlayed\">" + (plrs[seatID].PLP ? plrs[seatID].PLP : '') + "</div>\n										<div class=\"" + userPlayStatusClassName + "\">" + userPlayStatusText + "</div>\n									</div>\n</div>");
      $('.reminder', infoPlate).click({
        gameId: gameInstId,
        seatID: seatID,
        user: fbUser
      }, function(e) {
        return notifications.remindUsers(e.data.gameId, e.data.user, e.data.seatID, e.data.user);
      });
      userList.append(infoPlate);
    }
    return $("#gameInfo-game-players").append(userList);
  };
  jDocument.bind("dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, updateLeftHud);
  jDocument.bind("client:" + zzGlobals.clientCodes.USERINFO, updatePlayerPlate);
  return true;
});
