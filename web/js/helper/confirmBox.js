// Generated by CoffeeScript 1.3.3

define(['./sound'], function(sound) {
  var removeMessagePopup;
  removeMessagePopup = function() {
    sound.playCloseButtonSound();
    return $('.msg-outer-div').remove();
  };
  return window.messagePopup = function(message, callback, callback_param) {
    var msgCancelDiv, popUp;
    removeMessagePopup();
    sound.playPopupApperenceSound();
    popUp = $("<div class=\"msg-outer-div bounceIn animated\">\n	  					<div class=\"msg-inner-div\"><div class=\"msgbox-msg\"><p>" + message + "</p></div><div class=\"msgbox-ok\">ok</div></div>\n</div>");
    if (typeof callback === 'undefined') {
      $('.msgbox-ok', popUp).on('click', function(e) {
        return removeMessagePopup();
      });
    } else {
      msgCancelDiv = $("<div class=\"msgbox-cancel\">cancel</div>");
      $('.msgbox-ok', popUp).addClass("msgbox-cancel");
      msgCancelDiv.on('click', function(e) {
        return removeMessagePopup();
      });
      $('.msgbox-ok', popUp).on('click', function(e) {
        callback(callback_param);
        return removeMessagePopup();
      });
    }
    $('.msgbox-ok', popUp).after(msgCancelDiv);
    return $('#active-screen').append(popUp);
  };
});
