// Generated by CoffeeScript 1.3.3

define(["../../helper/confirmBox", "../../helper/utils", "./myLevel", "../../config/globals", "gamePlayView", "messageListener"], function(confirmBox, utils, myLevel, globals, gamePlayView, messageListenerObj) {
  var ZalerioGame, bets, boardVo, currentGameUsersData, placeBetsToServer, rematchCall, responseVo, sendOriginalFigsRequest, sendPlaceBetRequest, setPlayersInfo, usersRecordCall;
  bets = {};
  boardVo = {};
  responseVo = {};
  window.currentBets = {};
  window.currentBetsIdx = {};
  ZalerioGame = (function() {
    var betChange, betChangeCode, betsPanelIndexVO, boardVOCodes, boardVOs, board_X, board_Y, coordCodes, currPlayerFigVOs, docElems, drawBetPanel, drawGameBoard, drawResponseTiles, figureDetailsVO, flag_roundDrawn, flag_zoomTrue, getTileClass, handleDragStart, handleDragStartWithinBoard, initBoard, initRoundBets, internalDNDType, parseCoord, parseRounds, parseToGameBoard, reDrawBetsPanel, refreshGameBoard, refreshRoundsPanel, resetDropZoneOnGameBoard, roundBets, roundVOs, roundVOsIdx, sendPlaceBetToServer, tilesIdxVOs, updateBoardVars, updateFigureDetails, zalerioMapType, _this;
    ZalerioGame = function() {};
    _this = this;
    docElems = {};
    tilesIdxVOs = {};
    betsPanelIndexVO = {};
    window.currentRoundBidPlaced = -1;
    internalDNDType = "text/x-betiddata";
    flag_zoomTrue = false;
    currPlayerFigVOs = {};
    window.playerBetTiles = {};
    boardVOCodes = {
      TILE_COUNT: "BC",
      PLAYER_INFO_OBJ: "PR",
      FIGURE_ID: "CF",
      BET_WINNER: "BW"
    };
    betChangeCode = {
      PLAYER_SEAT_ID: "PS",
      TILE_COUNT: "BC",
      BET_OBJ: "PB",
      ROUND_ID: "PR"
    };
    window.betChangeVOs = {};
    boardVOs = {};
    flag_roundDrawn = false;
    coordCodes = {
      COORD_COUNT: "BC",
      COORD_NUM: "CN",
      COORD_X: "CX",
      COORD_Y: "CY"
    };
    zalerioMapType = {
      ORIG_MAP: {
        code: "OF",
        className: "box-RevealingNumber"
      }
    };
    roundVOsIdx = {};
    board_X = null;
    board_Y = null;
    roundBets = null;
    roundVOs = {};
    figureDetailsVO = {};
    initBoard = function() {
      var boardDimension;
      utils.log("BoardXY : " + zzGlobals.roomVars[zzGlobals.roomCodes.BOARD_XY]);
      boardDimension = zzGlobals.roomVars[zzGlobals.roomCodes.BOARD_XY].split(":");
      board_X = boardDimension[0];
      board_Y = boardDimension[1];
      return drawGameBoard();
    };
    initRoundBets = function() {
      utils.log("Round Bets : " + zzGlobals.roomVars[zzGlobals.roomCodes.ROUND_NOOFBETS]);
      roundBets = zzGlobals.roomVars[zzGlobals.roomCodes.ROUND_NOOFBETS];
      return reDrawBetsPanel();
    };
    parseRounds = function(el, val) {
      var i, obj;
      utils.log("parse Ar data : " + zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ALLROUNDS]);
      obj = jQuery.parseJSON(val);
      roundVOs = {};
      for (i in obj) {
        roundVOs[i] = jQuery.parseJSON(obj[i]);
      }
      return refreshRoundsPanel();
    };
    refreshRoundsPanel = function() {
      var el, lastEl, lastRoundId, roundEndTimeTTL, roundId;
      drawRoundsPanel();
      lastRoundId = null;
      lastEl = null;
      for (roundId in roundVOs) {
        el = roundVOsIdx[roundId];
        roundEndTimeTTL = parseInt(roundVOs[roundId]["EM"]);
        if (roundEndTimeTTL > 0) {
          gamePlayView.getNotPlayedRoundClass(el);
        } else {
          gamePlayView.getDoneRoundClass(el);
        }
        if (roundId === zzGlobals.roomVars[zzGlobals.roomCodes.CURRENTROUND]) {
          gamePlayView.getCurrentRoundClass(el);
        }
        lastEl = el;
        lastRoundId = roundId;
      }
      if (lastEl && lastRoundId) {
        if (lastRoundId === zzGlobals.roomVars[zzGlobals.roomCodes.CURRENTROUND]) {
          return gamePlayView.getCurrentFinalRoundClass(el);
        } else {
          return gamePlayView.getFinalRoundClass(el);
        }
      }
    };
    window.drawRoundsPanel = function(elementDiv) {
      var aHrefElem, aHrefElemClone, cnt, gameRoundulElem, liElem, roundId;
      if (typeof elementDiv === 'undefined') {
        gameRoundulElem = gamePlayView.getGameRoundulElem();
        roundVOsIdx = {};
      } else {
        gameRoundulElem = elementDiv;
        window.roundVOsIdxRightHUD = {};
      }
      gameRoundulElem.innerHTML = "";
      cnt = 0;
      aHrefElemClone = null;
      for (roundId in roundVOs) {
        cnt++;
        liElem = gamePlayView.createliElem();
        aHrefElem = gamePlayView.createAHrefElem();
        aHrefElem.href = "#";
        gamePlayView.getNotPlayedRoundClass(aHrefElem);
        gamePlayView.setInnerHTML(aHrefElem, roundVOs[roundId]["RN"]);
        utils.log("roundVo : ", roundId);
        liElem.appendChild(aHrefElem);
        if (typeof elementDiv === 'undefined') {
          roundVOsIdx[roundId] = aHrefElem;
        } else {
          roundVOsIdxRightHUD[cnt] = aHrefElem;
        }
        gameRoundulElem.appendChild(liElem);
        aHrefElemClone = aHrefElem;
      }
      if (cnt > 0) {
        gamePlayView.setFinalInnerHTML(aHrefElemClone);
        return flag_roundDrawn = true;
      }
    };
    reDrawBetsPanel = function() {
      var count, currentBetId, el, flag_alreadyUsed, i, k, playButtonEl, usedBetId, _results;
      try {
        if (tutorial === true) {
          return;
        }
      } catch (_error) {}
      playButtonEl = gamePlayView.getPlayButtonEl();
      if (currentRoundBidPlaced > 0) {
        i = 0;
        while ((0 <= roundBets ? i < roundBets : i > roundBets)) {
          currentBetId = "bet_" + i;
          el = betsPanelIndexVO[currentBetId];
          gamePlayView.getBetsAlreadyPlacedClass(el);
          el.dragBet = 0;
          if (el.draggable != null) {
            el.draggable = false;
          }
          utils.removeEventHandler(el, "dragstart", handleDragStart, false);
          if (0 <= roundBets) {
            i++;
          } else {
            i--;
          }
        }
        gamePlayView.setBetDonePlayButtonEl(playButtonEl);
        return utils.removeEventHandler(playButtonEl, "click", sendPlaceBetRequest, true);
      } else {
        count = 0;
        for (k in window.currentBets) {
          count++;
        }
        if (count < 9) {
          gamePlayView.disablePlayBoutton(playButtonEl);
        } else {
          gamePlayView.enablePlayBoutton(playButtonEl);
        }
        if (!flag_roundBetsDrawn) {
          drawBetPanel();
        }
        _results = [];
        i = 0;
        while ((0 <= roundBets ? i < roundBets : i > roundBets)) {
          currentBetId = "bet_" + i;
          el = betsPanelIndexVO[currentBetId];
          flag_alreadyUsed = false;
          for (k in window.currentBets) {
            if (!__hasProp_.call(window.currentBets, k)) {
              continue;
            }
            usedBetId = window.currentBets[k];
            if (usedBetId === currentBetId) {
              flag_alreadyUsed = true;
              break;
            }
          }
          if (flag_alreadyUsed) {
            gamePlayView.getUsedDraggableBetsClass(el);
            el.dragBet = 0;
            if (el.draggable != null) {
              el.draggable = false;
            }
            utils.removeEventHandler(el, "dragstart", handleDragStart, false);
          } else {
            gamePlayView.getDraggableBetsClass(el);
            el.dragBet = 1;
            if (el.draggable != null) {
              el.draggable = true;
            }
            utils.addEventHandler(el, "dragstart", handleDragStart, false);
          }
          gamePlayView.enablePlayBoutton(playButtonEl);
          _results.push(utils.addEventHandler(playButtonEl, "click", sendPlaceBetToServer, true));
          if (0 <= roundBets) {
            i++;
          } else {
            i--;
          }
        }
        return _results;
      }
    };
    drawBetPanel = function() {
      var betsPanel, currentBetId, divAnc, divNbrPanel, flag_roundBetsDrawn, i, _results;
      betsPanelIndexVO = {};
      betsPanel = gamePlayView.getGameBetPanelEl();
      betsPanel.innerHTML = "";
      _results = [];
      i = 0;
      while ((0 <= roundBets ? i < roundBets : i > roundBets)) {
        currentBetId = "bet_" + i;
        divNbrPanel = gamePlayView.createliElem();
        gamePlayView.getDraggableBetsClass(divNbrPanel);
        divNbrPanel.id = currentBetId;
        if (divNbrPanel.draggable != null) {
          divNbrPanel.draggable = true;
        }
        utils.addEventHandler(divNbrPanel, "dragstart", handleDragStart, false);
        divAnc = gamePlayView.createAHrefElem();
        gamePlayView.getSpaceClass(divAnc);
        divAnc.id = "new-" + i;
        bets[i] = divAnc.id;
        divNbrPanel.appendChild(divAnc);
        betsPanel.appendChild(divNbrPanel);
        betsPanelIndexVO[currentBetId] = divNbrPanel;
        _results.push(flag_roundBetsDrawn = true);
        if (0 <= roundBets) {
          i++;
        } else {
          i--;
        }
      }
      return _results;
    };
    handleDragStart = function(e) {
      utils.log(e.target.id);
      gamePlayView.setCustomDragImage(e);
      e.dataTransfer.setData(internalDNDType, this.id);
      gamePlayView.addMoveClass(e.target);
      utils.log(e.target + " : " + e.target.className);
      return utils.log("drag started!");
    };
    handleDragStartWithinBoard = function(e) {
      var betId;
      betId = this.getAttribute("placedBetId");
      gamePlayView.setCustomDragImage(e);
      e.dataTransfer.setData(internalDNDType, betId);
      gamePlayView.addMoveClass(e.target);
      utils.log(e.target, e.target.className);
      return utils.log("drag started!");
    };
    drawGameBoard = function() {
      var gameWallDiv;
      gameWallDiv = gamePlayView.getGameWall(board_X, board_Y);
      return tilesIdxVOs = gamePlayView.getTilesIdxVOs();
    };
    getTileClass = function(tileClassOverLoadObj) {
      if (flag_zoomTrue) {
        if (tileClassOverLoadObj) {
          if (tileClassOverLoadObj.N) {
            if (tileClassOverLoadObj.Z) {
              return " " + tileClassOverLoadObj.N + " " + tileClassOverLoadObj.Z;
            } else {
              return " " + tileClassOverLoadObj.N + " ";
            }
          }
        }
      } else {
        if (tileClassOverLoadObj ? tileClassOverLoadObj.N : void 0) {
          return " " + tileClassOverLoadObj.N + " ";
        }
      }
      return " ";
    };
    refreshGameBoard = function() {
      var csBlankTileClassName, currentEl, currentFigId, currentSeatId, currentTileClass, currentTilePriority, currentTileVal, dropEnable, dropEnableVal, flag, lastRound, oldDraggableState, playerSeatId, playersObjs, tileIdx;
      csBlankTileClassName = getTileClass(gamePlayView.tileClassOverload.BASE_TILE_CLASS);
      currentTileClass = "";
      currentTileVal = "";
      currentEl = null;
      currentSeatId = zzGlobals.clientVars[zzGlobals.clientCodes.USER_SEAT_ID];
      currentTilePriority = 0;
      for (tileIdx in tilesIdxVOs) {
        flag = false;
        if (!__hasProp_.call(tilesIdxVOs, tileIdx)) {
          continue;
        }
        currentEl = tilesIdxVOs[tileIdx];
        currentTilePriority = 0;
        currentTileClass = csBlankTileClassName;
        dropEnable = true;
        currentEl.draggable = false;
        currentEl.dragBet = 0;
        currentTileVal = "";
        oldDraggableState = currentEl.draggable;
        if (boardVOs[tileIdx] != null) {
          if (boardVOs[tileIdx][boardVOCodes.FIGURE_ID]) {
            currentFigId = boardVOs[tileIdx][boardVOCodes.FIGURE_ID];
            if (currentFigId.indexOf("_NUMERIC_12") !== -1 || currentFigId.indexOf("_NUMERIC_13") !== -1 || currentFigId.indexOf("_NUMERIC_14") !== -1 || currentFigId.indexOf("_NUMERIC_15") !== -1 || currentFigId.indexOf("_NUMERIC_16") !== -1 || currentFigId.indexOf("_NUMERIC_17") !== -1) {
              dropEnable = false;
              currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.JOKER_BET);
              currentTileVal = "";
            } else if (currentFigId.indexOf("_NUMERIC_11") !== -1) {
              dropEnable = false;
              currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.SUPPER_JOKER_BET);
              currentTileVal = "";
            }
          }
          if (!boardVOs[tileIdx][boardVOCodes.FIGURE_ID]) {
            currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.OTHER_TURN);
            currentTileVal = "";
          }
          playersObjs = boardVOs[tileIdx][boardVOCodes.PLAYER_INFO_OBJ];
          for (playerSeatId in playersObjs) {
            if (playerSeatId === currentSeatId) {
              dropEnable = false;
              currentFigId = boardVOs[tileIdx][boardVOCodes.FIGURE_ID];
              if (currentFigId) {
                currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.PRV_CURRPLYR_CORRECT_TILE);
                utils.log("figureDetailsVO", currentFigId);
                if (currPlayerFigVOs[currentFigId] === figureDetailsVO[currentFigId]) {
                  currentTilePriority = 10;
                  currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.CURR_PLYR_FIG_COMPLETE);
                  if (currentFigId.indexOf("_NUMERIC_12") !== -1 || currentFigId.indexOf("_NUMERIC_13") !== -1 || currentFigId.indexOf("_NUMERIC_14") !== -1 || currentFigId.indexOf("_NUMERIC_15") !== -1 || currentFigId.indexOf("_NUMERIC_16") !== -1 || currentFigId.indexOf("_NUMERIC_17") !== -1) {
                    currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.JOKER_BET);
                    flag = true;
                  } else if (currentFigId.indexOf("_NUMERIC_11") !== -1) {
                    currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.SUPPER_JOKER_BET);
                    flag = true;
                  }
                }
                if (flag) {
                  currentTileVal = "";
                } else {
                  currentTileVal = boardVOs[tileIdx][boardVOCodes.TILE_COUNT];
                }
              } else {
                currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.PRV_CURRPLYR_INCORRECT_TILE);
                currentTileVal = " ";
              }
              break;
            }
          }
        }
        lastRound = false;
        if (zzGlobals.roomVars.FR === "1") {
          lastRound = true;
        }
        if (!lastRound) {
          if ((window.currentBets[tileIdx] != null) && window.currentBets[tileIdx] !== null) {
            dropEnable = false;
            currentEl.draggable = true;
            currentEl.dragBet = 1;
            currentEl.setAttribute("placedBetId", window.currentBets[tileIdx]);
            currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.CURR_PLYR_NEWBET);
            utils.addEventHandler(currentEl, "dragstart", handleDragStartWithinBoard, false);
            currentTileVal = "";
          }
          if (currentTilePriority < 10 && (betChangeVOs[tileIdx] != null)) {
            if (parseInt(betChangeVOs[tileIdx]) === 1) {
              currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.CURR_PLYR_NEWBET);
              currentTileVal = "";
            } else {
              currentTileClass = csBlankTileClassName + getTileClass(gamePlayView.tileClassOverload.CURR_PLYR_NEWBET);
              currentTileVal = "";
            }
          }
        }
        if ((parseInt(tileIdx) % board_X) === 0) {
          currentTileClass += " boardRowLastTile ";
        }
        dropEnableVal = null;
        if (currentEl.id === "boardTile-35" && isDevEnvironment) {
          utils.log("[dropEnable:", dropEnable, ",currentEl:", currentEl.getAttribute("droppable"));
        }
        switch (currentEl.getAttribute("droppable")) {
          case "-1":
            if (dropEnable === true) {
              dropEnableVal = "1";
            }
            break;
          case "0":
            if (dropEnable === true) {
              dropEnableVal = "1";
            }
            break;
          case "1":
            if (dropEnable === false) {
              dropEnableVal = "0";
            }
            break;
          case "2":
            if (dropEnable === false) {
              dropEnableVal = "0";
            }
        }
        if (currentEl.getAttribute("droppable") == null) {
          currentEl.setAttribute("droppable", "1");
        }
        if (dropEnableVal !== null) {
          currentEl.setAttribute("droppable", dropEnableVal);
        }
        currentEl.className = currentTileClass;
        currentEl.innerHTML = currentTileVal;
      }
      return resetDropZoneOnGameBoard();
    };
    resetDropZoneOnGameBoard = function() {
      var currentEl, tileIdx, _results;
      _results = [];
      for (tileIdx in tilesIdxVOs) {
        if (!__hasProp_.call(tilesIdxVOs, tileIdx)) {
          continue;
        }
        currentEl = tilesIdxVOs[tileIdx];
        switch (currentEl.getAttribute("droppable")) {
          case "1":
            _results.push(currentEl.setAttribute("droppable", "2"));
            break;
          case "0":
            _results.push(currentEl.setAttribute("droppable", "-1"));
            break;
          default:
            _results.push(void 0);
        }
      }
      return _results;
    };
    window.handleDropNew = function(e, ui) {
      var betId, betTileIdx, betd;
      sound.playTitleDropSound();
      if (e.target != null) {
        utils.removeClassName(e.target, "box-drophover");
      }
      if (ui.draggable.attr('placedbetid')) {
        betId = ui.draggable.attr('placedbetid');
        ui.draggable.removeAttr('placedbetid');
      } else {
        betId = ui.draggable.attr('id');
      }
      if ((betId != null) && betId !== "") {
        if (e.target.getAttribute("droppable") === "2") {
          if (!window.currentBets[e.target.getAttribute("tileidx")]) {
            window.currentBetsIdx[betId] = e.target.getAttribute("tileidx");
            window.currentBets = {};
            for (betd in window.currentBetsIdx) {
              if (!__hasProp_.call(window.currentBetsIdx, betd)) {
                continue;
              }
              betTileIdx = window.currentBetsIdx[betd];
              window.currentBets[betTileIdx] = betd;
            }
            refreshGameBoard();
            reDrawBetsPanel();
            return true;
          }
        }
      }
      return true;
    };
    sendPlaceBetToServer = function() {
      var betCtr, betId, betPanelId, betStr, betTileId, playButtonEl;
      utils.log("bet Validation before sen  ding the request t  o server");
      if (tutorial === true) {
        return;
      }
      playButtonEl = gamePlayView.getPlayButtonEl();
      if (playButtonEl.className === "bet_done") {
        confirmBox("Not so fast.... lets wait for your friends to play their turn");
        return;
      }
      betStr = "";
      betCtr = 0;
      sound.playPlayButtonSound();
      for (betPanelId in window.currentBetsIdx) {
        if (!__hasProp_.call(window.currentBetsIdx, betPanelId)) {
          continue;
        }
        betTileId = window.currentBetsIdx[betPanelId];
        betId = betTileId.replace(/\bboardTile-\b/, "");
        betStr += (betStr === "" ? betId : ":" + betId);
        ++betCtr;
        utils.log("bets[" + betCtr + "] : " + betId);
      }
      if (betStr === "") {
        utils.log("No bets placed!");
        confirmBox("No bets placed!");
        return false;
      } else if (betCtr !== 9) {
        utils.log("Bets count is less then 9!");
        confirmBox("Not so fast... please place all of your 9 tiles!");
        return false;
      } else {
        utils.log("every thing is fine's end the bets to the server");
        $('.box-newBet').draggable('disable');
        placeBetsToServer(betStr);
      }
      return false;
    };
    parseCoord = function(coordNum, boardX, boardY) {
      var coordX, coordY;
      boardX = boardX || board_X;
      boardY = boardY || board_Y;
      coordX = coordNum % board_X;
      coordY = coordNum / board_X;
      return {
        x: coordX,
        y: coordY
      };
    };
    parseToGameBoard = function(mapType, mapData) {
      var ctr, curCoordId, curCoordObj;
      if ((mapType != null) && (mapType.code != null) && (mapType.className != null)) {
        ctr = 0;
        for (curCoordId in mapData) {
          curCoordObj = mapData[curCoordId];
          jQuery("#boardTile-" + (parseInt(curCoordId))).addClass(mapType.className);
        }
      }
      return true;
    };
    betChange = function() {
      reDrawBetsPanel();
      return refreshGameBoard();
    };
    jDocument.bind(zzEvents.CLIENT_BETS_PLACED, betChange);
    drawResponseTiles = function(responseObj) {
      var correctFlag, tileId, _results;
      utils.log("responseObj in drawResponseTiles(): " + responseObj);
      _results = [];
      for (tileId in responseObj) {
        correctFlag = responseObj[tileId];
        _results.push(gamePlayView.markMyTurnTiles(correctFlag));
      }
      return _results;
    };
    updateBoardVars = function(evt, val) {
      var boardObj, boardObjs, boardStr, i, noOfTiles, playerBetTiles, playerTiles, seatId;
      currPlayerFigVOs = {};
      playerBetTiles = {};
      boardVOs = {};
      boardObjs = jQuery.parseJSON(val);
      for (i in boardObjs) {
        boardStr = boardObjs[i];
        boardObj = jQuery.parseJSON(boardStr);
        if (boardObj["PR"] != null) {
          boardObj["PR"] = jQuery.parseJSON(boardObj["PR"]);
          for (seatId in boardObj["PR"]) {
            if (boardObj["CF"] != null) {
              playerTiles = {};
              if (playerBetTiles[seatId]) {
                playerTiles = playerBetTiles[seatId];
              }
              playerTiles[i] = 1;
              playerBetTiles[seatId] = playerTiles;
              if (zzGlobals.clientVars[zzGlobals.clientCodes.USER_SEAT_ID] === seatId) {
                noOfTiles = 0;
                if (currPlayerFigVOs[boardObj["CF"]] != null) {
                  noOfTiles = currPlayerFigVOs[boardObj["CF"]];
                }
                currPlayerFigVOs[boardObj["CF"]] = ++noOfTiles;
              }
            }
          }
        }
        boardVOs[i] = boardObj;
      }
      return refreshGameBoard();
    };
    updateFigureDetails = function(evt, val) {
      figureDetailsVO = {};
      if (val != null) {
        return figureDetailsVO = jQuery.parseJSON(val);
      }
    };
    jQuery(function() {
      var isMousingOver, jTileHoverDiv;
      jTileHoverDiv = gamePlayView.getShowOnMouseOverEl();
      utils.log("jTileHoverDiv : " + jTileHoverDiv);
      isMousingOver = false;
      jQuery("#gamewall").delegate(".box-previousRoundOtherPlayer,.box-previousRoundCurrentPlayerIncorrect,.box-previousRoundCurrentPlayerCorrect,.box-dizitCompleted,.joker,.superJoker", "mouseover mouseout", function(e) {
        var elLeft, elTop, leftMajor, noOfCols, noOfRows, tileIdx, tileNo, topMajor, usersObject;
        usersObject = zzGlobals.dataObjVars.AP;
        if (e.type === "mouseover" && !isMousingOver) {
          tileIdx = this.getAttribute("tileIdx");
          utils.log("offset :" + this.offsetLeft + " : " + this.offsetTop);
          if (boardVOs[tileIdx] != null) {
            gamePlayView.showBetPlacedBy(boardVOs, usersObject, boardVOCodes, tileIdx);
            tileNo = parseInt(tileIdx);
            noOfRows = tileNo / board_X;
            noOfCols = tileNo % board_Y;
            leftMajor = (noOfRows > board_Y / 2 ? false : true);
            topMajor = (noOfCols > board_X / 2 ? false : true);
            elLeft = e.pageX + 10;
            elTop = e.pageY;
            if (this.offsetLeft > 300) {
              elLeft = elLeft - jTileHoverDiv.width() - 10;
            }
            if (this.offsetTop > 400) {
              elTop = elTop - jTileHoverDiv.height();
            }
            jTileHoverDiv.show().offset({
              top: elTop,
              left: elLeft
            });
          }
          isMousingOver = true;
          return utils.log("enlarged");
        } else if (e.type === "mouseout") {
          jTileHoverDiv.hide();
          isMousingOver = false;
          return utils.log("resetting");
        }
      });
      jDocument.bind("client:" + zzGlobals.clientCodes.UINFO, usersRecordCall);
      jDocument.bind("client:" + zzGlobals.clientCodes.ALL_PAST_GAME, usersRecordCall);
      jDocument.bind("client:" + zzGlobals.msgCodes.RIGHT_HUD, usersRecordCall);
      jDocument.bind("room:" + zzGlobals.roomCodes.FIGURE_DETAILS, updateFigureDetails);
      jDocument.bind("room:" + zzGlobals.roomCodes.BOARD_XY, initBoard);
      jDocument.bind("room:" + zzGlobals.roomCodes.ROUND_NOOFBETS, initRoundBets);
      jDocument.bind("room:" + zzGlobals.roomCodes.ROOM_ALLROUNDS, parseRounds);
      jDocument.bind("room:" + zzGlobals.roomCodes.BOARDVARS, updateBoardVars);
      jDocument.bind("room:" + zzGlobals.roomCodes.CURRENTROUND, refreshRoundsPanel);
      return jDocument.bind("room:" + zzGlobals.roomCodes.ALL_PLAYER_INFO, setPlayersInfo);
    });
    return ZalerioGame;
  }).call(this);
  usersRecordCall = function() {
    var gameIdArray, i, scoreArray, seatId, usersInfo, usersObjectAPG, usersObjectUINFO;
    if (typeof zzGlobals.clientVars.UINFO !== 'undefined' && zzGlobals.clientVars.UINFO !== 'USERINFO') {
      usersObjectUINFO = jQuery.parseJSON(zzGlobals.clientVars.UINFO);
    }
    if (typeof zzGlobals.clientVars.APG !== 'undefined' && zzGlobals.clientVars.APG !== 'ALL_PAST_GAME') {
      usersObjectAPG = jQuery.parseJSON(zzGlobals.clientVars.APG);
      scoreArray = [];
      gameIdArray = [];
      for (i in usersObjectAPG) {
        usersObjectAPG[i] = jQuery.parseJSON(usersObjectAPG[i]);
        usersObjectAPG[i].PLRS = jQuery.parseJSON(usersObjectAPG[i].PLRS);
        scoreArray[i] = parseInt(usersObjectAPG[i].EDL);
        gameIdArray.push(i);
        for (seatId in usersObjectAPG[i].PLRS) {
          usersObjectAPG[i].PLRS[seatId] = jQuery.parseJSON(usersObjectAPG[i].PLRS[seatId]);
        }
      }
      gameIdArray.sort(function(x, y) {
        return scoreArray[y] - scoreArray[x];
      });
    }
    usersInfo = {
      RH: zzGlobals.msgVars.RH,
      APG: usersObjectAPG,
      APG_SORT: gameIdArray,
      UINFO: usersObjectUINFO
    };
    return myLevel(usersInfo);
  };
  currentGameUsersData = {};
  setPlayersInfo = function() {
    var i, scoreArray, seatId, seatIdArray, usersObject, x;
    usersObject = jQuery.parseJSON(zzGlobals.roomVars.AP);
    for (i in usersObject) {
      usersObject[i] = jQuery.parseJSON(usersObject[i]);
      usersObject[i].PLRS = jQuery.parseJSON(usersObject[i].PLRS);
      scoreArray = [];
      seatIdArray = [];
      for (seatId in usersObject[i].PLRS) {
        usersObject[i].PLRS[seatId] = jQuery.parseJSON(usersObject[i].PLRS[seatId]);
        scoreArray[seatId] = parseInt(usersObject[i].PLRS[seatId].PSC);
        seatIdArray.push(seatId);
      }
      seatIdArray.sort(function(x, y) {
        return scoreArray[y] - scoreArray[x];
      });
      usersObject[i].PLSC = {};
      for (x in seatIdArray) {
        usersObject[i].PLSC[x] = seatIdArray[x];
      }
    }
    zzGlobals.dataObjVars.AP = usersObject[i];
    return jDocument.trigger("dataObj:" + zzGlobals.dataObjCodes.ALL_PLAYER_INFO, zzGlobals.dataObjVars.AP);
  };
  if (document.domain === "localhost" || document.domain === "zl.mobicules.com") {
    utils.addEventHandler(document.getElementById("bottomHUDbuttons-more"), "click", sendOriginalFigsRequest, false);
    window.sendOriginalFigsRequest = sendOriginalFigsRequest;
  }
  sendOriginalFigsRequest = function() {
    return jDocument.trigger(zzEvents.SEND_UPC_MESSAGE, [UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|OF"]);
  };
  rematchCall = function(e, rematchPlayerFBID) {
    var gameId, plrs, seatID, _results;
    if (e.preventDefault) {
      e.preventDefault();
    }
    sound.playOtherButtonSound();
    if (typeof rematchPlayerFBID === "undefined") {
      plrs = zzGlobals.dataObjVars.AP.PLRS;
      _results = [];
      gameId = gameInstId;
      for (seatID in plrs) {
        _results.push(plrs[seatID].PFB);
      }
    } else {
      _results = [];
      _results.push(rematchPlayerFBID);
    }
    InviteFriends(_results, 'Rematch', gameId);
    jQuery('.zalerio_popup').css('display', 'none');
  };
  utils.addEventHandler(document.getElementById('rematch'), 'click', rematchCall, false);
  placeBetsToServer = function(betStr) {
    return jDocument.trigger(zzEvents.SEND_UPC_MESSAGE, [UPC.SEND_ROOMMODULE_MESSAGE, zzGlobals.roomVars[zzGlobals.roomCodes.ROOM_ID], "RQ", "C|PB", "BI|" + betStr]);
  };
  return sendPlaceBetRequest = function() {
    var bet, betStr;
    utils.log("bet Validation before sending the request t  o server");
    betStr = "";
    for (bet in bets) {
      betStr += (betStr === "" ? bets[bet] : ":" + bets[bet]);
      utils.log("bets[" + bet + "] : " + bets[bet]);
    }
    utils.log("[betStr: " + betStr + "]");
    if (betStr === "") {
      utils.log("No bets placed!");
      messagePopup("No bets placed!");
      return false;
    } else {
      utils.log("every thing is fine end the bets to the server");
      placeBetsToServer(betStr);
    }
    return false;
  };
});
