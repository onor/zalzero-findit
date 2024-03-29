// Generated by CoffeeScript 1.4.0

define([], function() {
  var showFinalScore;
  showFinalScore = function() {
    var disableRematch, ii, index, rankHtml, rankHtmlArray, seatId, usersObject;
    try {
      if (zzGlobals.roomVars.FR === "1") {
        jQuery("#gameBetPanel").hide();
        jQuery(".resignPopup").hide();
        ii = 0;
        rankHtmlArray = [];
        rankHtml = "";
        disableRematch = false;
        usersObject = zzGlobals.dataObjVars.AP;
        for (index in usersObject.PLSC) {
          seatId = usersObject.PLSC[index];
          if (parseInt(usersObject.PLRS[seatId].PRE) === 1 || parseInt(usersObject.PLRS[seatId].GSS) !== 2) {
            if (parseInt(zzGlobals.currentUserDBId) === parseInt(usersObject.PLRS[seatId].UI)) {
              disableRematch = true;
            }
            continue;
          }
          if (ii === 0) {
            rankHtmlArray.push("<div  id='score" + ++ii + "'><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + "/picture' /><div class='name'>" + usersObject.PLRS[seatId].PDN + "</div><div class='score'>" + usersObject.PLRS[seatId].PSC + "</div></div>");
          } else {
            rankHtml += "<div class='score_rep' id='score" + ++ii + "'><div class='rank'>" + ii + "nd</div><img src='https://graph.facebook.com/" + usersObject.PLRS[seatId].PFB + "/picture' /><div class='name'>" + usersObject.PLRS[seatId].PDN + "</div><div class='score'>" + usersObject.PLRS[seatId].PSC + "</div></div>";
          }
        }
        if (ii === 0) {
          return;
        }
        if (ii < 2 || disableRematch || resignStatus === 1) {
          jQuery("#rematch").hide();
          jQuery(".dismiss").css({
            marginTop: '1px',
            marginLeft: '50px'
          });
        } else {
          jQuery("#rematch").show();
          jQuery(".dismiss").css({
            marginTop: '',
            marginLeft: ''
          });
        }
        jQuery("#topScore_div").html(rankHtmlArray[0]);
        jQuery("#bottomScore_div").html(rankHtml);
        return jQuery(".score_show_popup").css("display", "block");
      }
    } catch (_error) {}
  };
  return jDocument.bind("dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, showFinalScore);
});
