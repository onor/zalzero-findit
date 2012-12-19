// Generated by CoffeeScript 1.4.0

define(['./envConfig'], function(envConfig) {
  return {
    unionConnection: {
      url: envConfig.unionConnection.url,
      port: envConfig.unionConnection.port
    },
    unionGameServerId: "zalerioGameServer",
    userLevelImg: ["carauselbelts_otherplayers_white_belt.png", "carauselbelts_otherplayers_yellow_belt.png", "carauselbelts_otherplayers_orange_belt.png", "carauselbelts_otherplayers_green_belt.png", "carauselbelts_otherplayers_blue_belt.png", "carauselbelts_otherplayers_purple_belt.png", "carauselbelts_otherplayers_red_belt.png", "carauselbelts_otherplayers_brown_belt.png", "carauselbelts_otherplayers_black_belt.png"],
    userLevelImgBig: ["carauselbelts_main_player_white_belt.png", "carauselbelts_main_player_yellow_belt.png", "carauselbelts_main_player_orange_belt.png", "carauselbelts_main_player_green_belt.png", "carauselbelts_main_player_blue_belt.png", "carauselbelts_main_player_purple_belt.png", "carauselbelts_main_player_red_belt.png", "carauselbelts_main_player_brown_belt.png", "carauselbelts_main_player_black_belt.png"],
    isDevEnvironment: true
  };
});
