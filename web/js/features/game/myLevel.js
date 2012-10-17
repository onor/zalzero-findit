// Generated by CoffeeScript 1.3.3

define(['../../helper/utils'], function(utils) {
  var GB_UINFO, usersRecord;
  GB_UINFO = {};
  GB_UINFO.PFN = "";
  return usersRecord = function(gameRecords) {
    var APG, FBids, UINFO, cl, cl_up, cls, cm_fbids, cr_belt_html, gameId, gssCount, i, index, is_apg, low_next_game_arr, low_next_won_arr, message, need_game_next, need_won_next, next_belt_html, next_cls, next_level, rematchButton, seatID, status, top_level, urDiv, user_level, vertical_text;
    $(".Mylevel", "#rip_active_rh").remove();
    try {
      message = gameRecords.RH;
      for (gameId in message) {
        urDiv = $("<div class=\"userArea Mylevel\" id=\"myLevel_active_" + gameId + "\" ><div class=\"imgCon\"></div></div>");
        if (message[gameId].CR) {
          urDiv.append("<div class=\"round_no\">" + message[gameId].CR + "</div>");
        }
        for (index in message[gameId].PLSC) {
          seatID = message[gameId].PLSC[index];
          if (message[gameId].PLRS[seatID].GSS === 5 || message[gameId].PLRS[seatID].GSS === 3) {
            continue;
          }
          if (gameRecords.UINFO.UI === message[gameId].PLRS[seatID].UI) {
            urDiv.append("<div class=\"rank_active\" >" + (utils.playerRank(message[gameId].PLRS[seatID].PR)) + "</div>");
          }
          status = (message[gameId].PLRS[seatID].PON === 1 ? "online" : "offline");
          $('.imgCon', urDiv).append("<div class=\"imageWrapper\" id=\"myLevel_active_Images" + gameId + "\"><img src=\"https://graph.facebook.com/" + message[gameId].PLRS[seatID].PFB + "/picture\" alt=\"" + message[gameId].PLRS[seatID].PFN + "\" class=\"" + status + "\" id=\"myLevel_active_who_am_i_" + seatID + "\" /></div>");
        }
        $("#rip_active_rh").append(urDiv);
      }
      $(".ap_games").remove();
    } catch (_error) {}
    try {
      message = gameRecords.APG;
      for (gameId in message) {
        urDiv = $("<div class=\"userArea Mylevel\" id=\"myLevel_apg_" + gameId + "\" ></div>");
        urDiv.append("<div class=\"end_date\">" + message[gameId].ED + "</div>");
        rematchButton = "<div class=\"msgbox-ok\">Rematch</div>";
        FBids = [];
        for (index in message[gameId].PLRS) {
          if (message[gameId].PLRS[index].GSS === 5 || message[gameId].PLRS[index].GSS === 3) {
            continue;
          }
          FBids.push(message[gameId].PLRS[index].PFB);
          if (gameRecords.UINFO.UI === message[gameId].PLRS[index].UI) {
            if (message[gameId].PLRS[index].PRE !== 1) {
              urDiv.append(rematchButton);
            }
            urDiv.append("<div class=\"point\">Points- " + message[gameId].PLRS[index].PSC + " </div>");
            urDiv.append("<div class=\"rank " + (message[gameId].PLRS[index].PR === "1" ? 'winner' : void 0) + "\" >" + (utils.playerRank(message[gameId].PLRS[index].PR)) + "</div>");
          }
          status = (message[gameId].PLRS[index].PON === 1 ? "online" : "offline");
          if (message[gameId].PLRS[index].PFB) {
            urDiv.append("<div class=\"imageWrapper\" id=\"myLevel_apg_Images" + gameId + "\"><img src=\"https://graph.facebook.com/" + message[gameId].PLRS[index].PFB + "/picture\" alt=\"" + message[gameId].PLRS[index].PFN + "\" class=\"" + status + "\" id=\"myLevel_apg_who_am_i_" + index + "\" /></div>");
          }
        }
        $('.msgbox-ok', urDiv).click({
          ids: FBids,
          gameOption: "Rematch",
          gameId: gameId
        }, function(e) {
          return rematchPastGames(e.data.ids, e.data.gameOption, e.data.gameId);
        });
        if ($('.imageWrapper', urDiv).length !== 0) {
          $("#rip_won_apg").append(urDiv);
        }
      }
    } catch (_error) {}
    $(function() {
      return $(".scroll-pane").jScrollPane({
        showArrows: true,
        autoReinitialise: true
      });
    });
    try {
      is_apg = true;
      if (gameRecords.APG === "ALL_PAST_GAME" || gameRecords.APG === "" || !(gameRecords.APG != null) || typeof gameRecords.APG === "undefined") {
        APG = {};
        is_apg = false;
      } else {
        APG = gameRecords.APG;
      }
      if (typeof gameRecords.UINFO === "undefined") {
        return;
      }
      UINFO = gameRecords.UINFO;
      GB_UINFO = UINFO;
      if (typeof UINFO.PL === "undefined" || parseInt(UINFO.PL) === 0) {
        UINFO.PL = 1;
      }
      UINFO.user_pic = "https://graph.facebook.com/" + UINFO.PFB + "/picture?type=square";
      cm_fbids = "";
      gssCount = 0;
    } catch (err) {

    }
    try {
      user_level = UINFO.PL;
      cls = belt_array[user_level].toLowerCase();
      top_level = sizeOfObj(belt_array);
      if (user_level < top_level) {
        next_level = user_level + 1;
      } else {
        next_level = user_level;
      }
      next_cls = belt_array[next_level].toLowerCase();
      low_next_won_arr = total_won_array[next_level - 1].split("-");
      need_won_next = low_next_won_arr[0] - UINFO.GW;
      low_next_game_arr = total_games_array[next_level - 1].split("-");
      need_game_next = low_next_game_arr[0] - UINFO.GP;
      cr_belt_html = "<li>You played " + UINFO.GP + " game";
      if (UINFO.GP > 0) {
        cr_belt_html += "s";
      }
      cr_belt_html += "</li><li>You won " + UINFO.GW + "</li>";
      next_belt_html = "";
      if (need_game_next > 0 && need_game_next > need_won_next) {
        next_belt_html += "<li>" + need_game_next + " more game";
        if (need_game_next > 1) {
          next_belt_html += "s";
        }
        next_belt_html += "</li>";
      }
      if (need_won_next > 0) {
        next_belt_html += "<li>" + need_won_next + " more to win</li>";
      }
      $("#next_belt_ul").html(next_belt_html);
      $("#current_belt_ul").html(cr_belt_html);
      $("#current_belt_h4").html(capFirst(cls));
      $("#next_belt_h4").html(capFirst(next_cls));
      $("#belt-info").attr("class", "belt-info " + cls);
      $("#belt-info").css("background", "url(" + baseUrl + "/images/zalerio_1.2/5.all_popup/mystats/mylevel/girl/girl_in" + cls + "/girl_" + cls + "_1.png) no-repeat -20px 33px");
      $("#belt_text").html(capFirst(cls));
      if (user_level === next_level) {
        $("#belt-info .next-belt").hide();
        $("#belt-info .dummy_div").show();
      } else {
        $("#belt-info .next-belt").show();
        $("#belt-info .dummy_div").hide();
      }
      vertical_text = "";
      i = top_level;
      while (i > 0) {
        cl = belt_array[i].toLowerCase();
        cl_up = capFirst(cl);
        if (user_level === i) {
          vertical_text += "<li class=\"active\"><img src=\"../images/zalerio_1.2/5.all_popup/mystats/mylevel/belts/belts_highlighted/" + cl + ".png\" alt=\"Black belt\"><a class=\"" + cl + "\" >{cl_up}</a></li>";
        } else {
          vertical_text += "<li><img src=\"../images/zalerio_1.2/5.all_popup/mystats/mylevel/belts/belts_ideal/" + cl + ".png\" alt=\"Black belt\"><a class=\"" + cl + "\" >" + cl_up + "</a></li>";
        }
        i--;
      }
      $("#vertical_belt_conent").html(vertical_text);
      $("#mylevel-check").show();
    } catch (_error) {}
    try {
      $("#stat_image_main").attr("src", UINFO.user_pic);
      $("#stat_image_tri_level").attr("src", baseUrl + "/images/zalerio_1.2/5.all_popup/mystats/mystatsCommon/stats_main_player_belts/" + userLevelImgBig[UINFO.PL - 1]);
      $("#popup-stats .div_top .div_img #stat_userName").html(UINFO.PDN);
      $("#popup-stats .div_top #joined_time").html(" " + UINFO.JD);
      return $("#user-level-span").removeClass().addClass(capFirst(cls.toLowerCase()) + "Belt").html(capFirst(cls.toLowerCase()) + " Belt");
    } catch (_error) {}
  };
});
