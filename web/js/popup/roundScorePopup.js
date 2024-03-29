// Generated by CoffeeScript 1.4.0

define([], function() {
  var showRoundScorePopup;
  window._currentRoundStatus = -1;
  showRoundScorePopup = function() {
    var index, rankHtml, roundNo, scorePopup, scorePopuptopthree, seatId, selfHtml, userCount, usersObject;
    roundNo = parseInt(zzGlobals.roomVars.CR.split("_")[1], 10);
    if (roundNo === 0 || zzGlobals.roomVars.FR === "1" || roundNo === window._currentRoundStatus) {
      return;
    }
    window._currentRoundStatus = roundNo;
    usersObject = zzGlobals.dataObjVars.AP;
    scorePopup = $(" <div class=\"roundresult\"><div class=\"content_r bounceIn animated\"><h2 class=\"round_no_r\">Round " + (zzGlobals.roomVars.CR.split("_")[1]) + "</h2></div></div> ");
    scorePopuptopthree = $("<div class=\"top_three_r\"> </div>");
    rankHtml = "";
    selfHtml = "";
    userCount = 0;
    for (index in usersObject.PLSC) {
      seatId = usersObject.PLSC[index];
      if (parseInt(usersObject.PLRS[seatId].GSS) !== 2 || usersObject.PLRS[seatId].PRE === 1) {
        continue;
      }
      userCount++;
      if (userCount <= 3) {
        rankHtml += "<div class='score'><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + ("/picture' /><div class='points " + (usersObject.PLRS[seatId].PSC < 0 ? "minus" : "plus") + "'>") + usersObject.PLRS[seatId].PSC + "</div></div>";
      }
      if (zzGlobals.currentUserDBId === usersObject.PLRS[seatId].UI) {
        selfHtml = $(" <div class=\"score self\"><img src=\"https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + "/picture\" /><div class=\"points " + (usersObject.PLRS[seatId].PSC < 0 ? "minus" : "plus") + "\">" + usersObject.PLRS[seatId].PSC + "</div></div> ");
      }
    }
    $(".roundresult").remove();
    scorePopuptopthree.append(rankHtml);
    if (selfHtml !== "") {
      $(".content_r", scorePopup).append(selfHtml);
      $(".content_r", scorePopup).append(scorePopuptopthree);
      $("#active-screen").append(scorePopup);
      setTimeout((function() {
        return $(".roundresult").remove();
      }), 5000);
      return $(".roundresult").click(function() {
        return $(this).remove();
      });
    }
  };
  jDocument.bind("dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, showRoundScorePopup);
  return true;
});
