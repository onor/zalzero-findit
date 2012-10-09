// Generated by CoffeeScript 1.3.3

define(["../helper/sound"], function(sound) {
  var closeInvits, showInviteStatus;
  closeInvits = function() {
    return jDocument.trigger(zzEvents.SEND_UPC_MESSAGE, [UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|CI"]);
  };
  showInviteStatus = function() {
    var gameObjPLRS, invitedImgs, popupDivBase, rejected, remindUsersData, seatid, totalResponse, usersInfoObject;
    if (parseInt(zzGlobals.dataObjVars.AP.GCB) !== parseInt(zzGlobals.currentUserDBId)) {
      return;
    }
    usersInfoObject = zzGlobals.dataObjVars.AP;
    gameObjPLRS = usersInfoObject.PLRS;
    totalResponse = 0;
    invitedImgs = 0;
    popupDivBase = $("<div class=\"status_show_popup zalerio_popup\" id=\"invitestatus\" style=\"display:block\" >\n	<div id=\"score_friendpopup\" class=\"outer_base\">\n		<a class=\"positionAbsolute\" id=\"close\"></a>\n		<div class=\"invite_status_base\" style=\"padding-top:32px\">\n			<div class=\"topUserInfo\"></div>\n			<div class=\"statusWrapper\">\n				<div class=\"acceptBlock\"><span>Accepted</span><div class=\"imgWrapper\"></div></div>\n				<div class=\"declineBlock\"><span>Declined</span><div class=\"imgWrapper\"></div></div>\n				<div class=\"inviteBlock\"><span>Not responded yet ...</span><div class=\"imgWrapper\"></div></div>\n			</div>\n			<div class=\"buttonWrapper\">\n				<div class=\"leftButtonWrapper\">\n					<div class=\"leftButton\">Close Invitations</div>\n					<div class=\"textLeftButton\">Game will kick-off with all friends that have accepted your game invitation so far</div>\n				</div>\n				<div class=\"rightButtonWrapper\">\n					<div class=\"rightButton\">Send Reminder</div>\n					<div class=\"textRightButton\">Friends who did not respond yet will get a gentle reminder to join your game...</div>\n				</div>\n			</div>\n		</div>\n	</div>\n</div>");
    $('.positionAbsolute', popupDivBase).click(function() {
      sound.playCloseButtonSound();
      jQuery('.status_show_popup').remove();
      return false;
    });
    $('.leftButton', popupDivBase).click(function() {
      sound.playCloseButtonSound();
      return closeInvits();
    });
    rejected = 0;
    remindUsersData = {};
    for (seatid in gameObjPLRS) {
      remindUsersData[gameObjPLRS[seatid].PFB] = {
        GSS: gameObjPLRS[seatid].GSS,
        CRS: gameObjPLRS[seatid].CRS
      };
      totalResponse++;
      if (parseInt(gameObjPLRS[seatid].GSS) === 1) {
        totalResponse--;
        invitedImgs++;
        $('.inviteBlock .imgWrapper', popupDivBase).append("<img src=\"https://graph.facebook.com/" + gameObjPLRS[seatid].PFB + "/picture\" />");
      } else if (parseInt(gameObjPLRS[seatid].GSS) === 2) {
        $('.acceptBlock .imgWrapper', popupDivBase).append("<img src=\"https://graph.facebook.com/" + gameObjPLRS[seatid].PFB + "/picture\" />");
      } else if (parseInt(gameObjPLRS[seatid].GSS) === 3) {
        $('.declineBlock .imgWrapper', popupDivBase).append("<img src=\"https://graph.facebook.com/" + gameObjPLRS[seatid].PFB + "/picture\" />");
      }
    }
    $('.imgWrapper:not(:has(img))', popupDivBase).parent().remove();
    $('.rightButton', popupDivBase).click({
      gameId: gameInstId,
      user: remindUsersData
    }, function(e) {
      sound.playOtherButtonSound();
      return remindUser(e.data.gameId, e.data.user);
    });
    if (!(totalResponse > usersInfoObject.TP / 2)) {
      return;
    }
    if (invitedImgs === 0) {
      return;
    }
    if (zzGlobals.inviteStatus !== null && typeof zzGlobals.inviteStatus !== "undefined") {
      if (!(totalResponse > zzGlobals.inviteStatus)) {
        return;
      }
    }
    zzGlobals.inviteStatus = totalResponse;
    jQuery('.status_show_popup').remove();
    jQuery("body").append(popupDivBase);
    return true;
  };
  jDocument.bind("dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, showInviteStatus);
  return true;
});
