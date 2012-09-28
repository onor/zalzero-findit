// Generated by CoffeeScript 1.3.3

define(["../helper/utils"], function(utils) {
  var resignPopUP, resignme, sendResignToServer;
  resignPopUP = function() {
    return jQuery("#bottomHUDbuttons-more").click(function() {
      var currentUserObj, i, seatId, totalPlayer, usersObjectAP;
      totalPlayer = 0;
      usersObjectAP = jQuery.parseJSON(zzGlobals.roomVars.AP);
      for (i in usersObjectAP) {
        usersObjectAP[i] = jQuery.parseJSON(usersObjectAP[i]);
        usersObjectAP[i].PLRS = jQuery.parseJSON(usersObjectAP[i].PLRS);
        for (seatId in usersObjectAP[i].PLRS) {
          usersObjectAP[i].PLRS[seatId] = jQuery.parseJSON(usersObjectAP[i].PLRS[seatId]);
          if (usersObjectAP[i].PLRS[seatId].GSS === 2 || usersObjectAP[i].PLRS[seatId].GSS === 1) {
            ++totalPlayer;
          }
        }
        break;
      }
      currentUserObj = jQuery.parseJSON(zzGlobals.clientVars.UINFO);
      if (totalPlayer > 1 && parseInt(zzGlobals.roomVars.FR) !== 1 && parseInt(currentUserObj.PRE) !== 1) {
        return jQuery(".resignPopup").show();
      }
    });
  };
  resignPopUP();
  sendResignToServer = function() {
    jQuery(".draggableBets").attr("draggable", "false");
    jQuery(".resignPopup").hide();
    jQuery("#gameBetPanel").hide();
    return jDocument.trigger(zzEvents.SEND_UPC_MESSAGE, [UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|RG"]);
  };
  resignme = document.getElementById("resignme");
  return utils.addEventHandler(resignme, "click", sendResignToServer, false);
});
