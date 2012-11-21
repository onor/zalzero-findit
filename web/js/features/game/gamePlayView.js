// Generated by CoffeeScript 1.3.3

define(['drag-drop'], function(dragdrop) {
  var gameView;
  gameView = (function() {

    function gameView() {
      this.tilesIdxVOs = {};
      this.tileClassOverload = {
        BASE_TILE_CLASS: {
          N: "box-blank box-black ui-droppable",
          Z: "box-blankZoom box-blackZoom"
        },
        OTHER_TURN: {
          N: "box-previousRoundOtherPlayer",
          Z: "box-previousRoundOtherPlayerZoom"
        },
        PRV_CURRPLYR_CORRECT_TILE: {
          N: "box-previousRoundCurrentPlayerCorrect",
          Z: "box-previousRoundCurrentPlayerCorrectZoom"
        },
        PRV_CURRPLYR_INCORRECT_TILE: {
          N: "box-previousRoundCurrentPlayerIncorrect",
          Z: "box-previousRoundCurrentPlayerIncorrectZoom"
        },
        CURRENT_CORRECT_TILE: {
          N: "box-currentRoundCorrect",
          Z: "box-currentRoundCorrectZoom"
        },
        CURRENT_INCORRECT_TILE: {
          N: "box-currentRoundIncorrect",
          Z: "box-currentRoundIncorrectZoom"
        },
        CURR_PLYR_FIG_COMPLETE: {
          N: "box-dizitCompleted",
          Z: "box-dizitCompletedZoom"
        },
        CURR_PLYR_NEWBET: {
          N: "box-newBet",
          Z: "box-newBetZoom"
        },
        JOKER_BET: {
          N: "joker",
          Z: "jokerZoom"
        },
        SUPPER_JOKER_BET: {
          N: "superJoker",
          Z: "superJokerZoom"
        }
      };
    }

    gameView.prototype.getNotPlayedRoundClass = function(el) {
      return el.className = "notPlayedRound";
    };

    gameView.prototype.getDoneRoundClass = function(el) {
      return el.className = "doneRound";
    };

    gameView.prototype.getCurrentRoundClass = function(el) {
      return el.className = "currentRound";
    };

    gameView.prototype.getCurrentFinalRoundClass = function(el) {
      return el.className = "currentFinalRound";
    };

    gameView.prototype.getFinalRoundClass = function(el) {
      return el.className = "finalRound";
    };

    gameView.prototype.getBetsAlreadyPlacedClass = function(el) {
      return el.className = "betsAlreadyPlaced";
    };

    gameView.prototype.getUsedDraggableBetsClass = function(el) {
      return el.className = "usedDraggableBets";
    };

    gameView.prototype.getDraggableBetsClass = function(el) {
      return el.className = "draggableBets ui-draggable";
    };

    gameView.prototype.setBetDonePlayButtonEl = function(el) {
      el.href = "#Already Placed Bets";
      el.className = 'bet_done';
      return this.disablePlayBoutton(el);
    };

    gameView.prototype.setInnerHTML = function(el, val) {
      return el.innerHTML = val;
    };

    gameView.prototype.disablePlayBoutton = function(el) {
      return el.parentNode.setAttribute("class", "bottomHUDbuttons-play-gray");
    };

    gameView.prototype.enablePlayBoutton = function(el) {
      el.href = "#Place Bets";
      el.className = '';
      return el.parentNode.setAttribute("class", "");
    };

    gameView.prototype.getPlayButtonEl = function() {
      return document.getElementById("placeBetOnServer");
    };

    gameView.prototype.getGameRoundulElem = function() {
      return document.getElementById("gameScore-round");
    };

    gameView.prototype.getGameBetPanelEl = function() {
      return document.getElementById("gameBetPanel");
    };

    gameView.prototype.getSpaceClass = function(divAnc) {
      return divAnc.className = "nbrs";
    };

    gameView.prototype.getShowOnMouseOverEl = function() {
      return jQuery("#showOnMouseOver");
    };

    gameView.prototype.addMoveClass = function(el) {
      return el.className += " moving";
    };

    gameView.prototype.addBoxDropHoverClass = function(el) {
      return el.className += " box-drophover";
    };

    gameView.prototype.setGameDisable = function() {
      jQuery(".draggableBets").attr("draggable", "false");
      jQuery(".resignPopup").hide();
      return jQuery("#gameBetPanel").hide();
    };

    gameView.prototype.removeStatusPopup = function() {
      jQuery(".status_show_popup").remove();
      return jQuery(".gdWrapper").remove();
    };

    gameView.prototype.showBetsPanel = function() {
      return jQuery("#gameBetPanel").show();
    };

    gameView.prototype.markMyTurnTiles = function(correctFlag) {
      var tempTileElem, _results;
      _results = [];
      tempTileElem = document.getElementById("boardTile-" + tileId);
      if (correctFlag === 0 || correctFlag === 1) {
        if (correctFlag === 1) {
          tempTileElem.className = tempTileElem.className + " box-MyTurn";
          return _results.push(tempTileElem.innerHTML = "N");
        } else {
          tempTileElem.className = tempTileElem.className + " box-MyWorngTurn";
          return _results.push(tempTileElem.innerHTML = "X");
        }
      } else {
        return _results.push(void 0);
      }
    };

    gameView.prototype.showBetPlacedBy = function(boardVOs, usersObject, boardVOCodes, tileIdx) {
      var currentFigId, imgSrc, jTileHoverDiv, jTileHoverDivContent, playerSeatId, playersObjs, strOut;
      jTileHoverDiv = this.getShowOnMouseOverEl();
      $(".roundForBidsCount").remove();
      strOut = '';
      playersObjs = boardVOs[tileIdx][boardVOCodes.PLAYER_INFO_OBJ];
      for (playerSeatId in playersObjs) {
        if (usersObject.PLRS[playerSeatId] != null) {
          currentFigId = boardVOs[tileIdx][boardVOCodes.FIGURE_ID];
          if ((usersObject.PLRS[playerSeatId] != null) && (usersObject.PLRS[playerSeatId].PFB != null)) {
            imgSrc = "https://graph.facebook.com/" + usersObject.PLRS[playerSeatId].PFB + "/picture";
          } else {
            imgSrc = '';
          }
          jTileHoverDivContent = jQuery(jTileHoverDiv.find(".roundForBidsCountUl")[0]);
          if (typeof currentFigId !== "undefined") {
            if (currentFigId.indexOf("_NUMERIC_12") !== -1 || currentFigId.indexOf("_NUMERIC_13") !== -1 || currentFigId.indexOf("_NUMERIC_14") !== -1 || currentFigId.indexOf("_NUMERIC_15") !== -1 || currentFigId.indexOf("_NUMERIC_16") !== -1 || currentFigId.indexOf("_NUMERIC_17") !== -1) {
              strOut += "<li><img src='" + baseUrl + "/images/zalerio_1.2/3.ingame_board/board/player_countindicator_joker.png' alt='Joker' ></li>";
            } else {
              if (currentFigId.indexOf("_NUMERIC_11") !== -1) {
                strOut += "<li><img src='" + baseUrl + "/images/zalerio_1.2/3.ingame_board/board/player_countindicator_Superjoker.png' alt='Supper Joker' ></li>";
              }
            }
          }
          strOut += "<li><img src='" + imgSrc + "' alt='" + usersObject.PLRS[playerSeatId].PFB + "' ></li>";
        }
      }
      jTileHoverDivContent.html(strOut);
      return true;
    };

    gameView.prototype.setCustomDragImage = function(e) {
      var dragIcon;
      dragIcon = document.createElement("img");
      dragIcon.src = baseUrl + "/images/zalerio_1.2/3.ingame_board/stand/stand_tile_pickup.png";
      dragIcon.width = 45;
      return e.dataTransfer.setDragImage(dragIcon, 35, 30);
    };

    gameView.prototype.createliElem = function() {
      return document.createElement("li");
    };

    gameView.prototype.createAHrefElem = function() {
      return document.createElement("a");
    };

    gameView.prototype.setFinalInnerHTML = function(el) {
      return el.innerHTML = "Final";
    };

    gameView.prototype.parseCoordsAsNum = function(coordX, coordY, board_X) {
      return (coordY * board_X) + coordX;
    };

    gameView.prototype.getGameWall = function(board_X, board_Y) {
      var csBlankTileClassName, elBr, gameWallDiv, gameWallTileDiv, i, j, tileIdx;
      gameWallDiv = document.getElementById("gamewall");
      gameWallDiv.setAttribute("dropzone", "move s:text/x-betiddata");
      gameWallDiv.innerHTML = "";
      i = 0;
      while ((0 <= board_Y ? i < board_Y : i > board_Y)) {
        j = 0;
        while ((0 <= board_X ? j < board_X : j > board_X)) {
          csBlankTileClassName = "box-blank box-black ui-droppable";
          gameWallTileDiv = document.createElement("div");
          dragdrop.addDrop(gameWallTileDiv);
          gameWallTileDiv.className = csBlankTileClassName;
          tileIdx = this.parseCoordsAsNum(j, i, board_X);
          gameWallTileDiv.id = "boardTile-" + tileIdx;
          gameWallTileDiv.setAttribute("tileIdx", tileIdx);
          if (j === (board_X - 1)) {
            gameWallTileDiv.className = csBlankTileClassName + " boardRowLastTile";
          }
          this.tilesIdxVOs[tileIdx] = gameWallTileDiv;
          gameWallDiv.appendChild(gameWallTileDiv);
          if (0 <= board_X) {
            j++;
          } else {
            j--;
          }
        }
        if (0 <= board_Y) {
          i++;
        } else {
          i--;
        }
      }
      elBr = document.createElement("br");
      elBr.setAttribute("clear", "all");
      gameWallDiv.appendChild(elBr);
      return gameWallDiv;
    };

    gameView.prototype.getTilesIdxVOs = function() {
      return this.tilesIdxVOs;
    };

    return gameView;

  })();
  return new gameView;
});
