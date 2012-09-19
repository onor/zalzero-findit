
INSERT INTO ZzGame (game_id,game_name) VALUES
('ZALERIO_1','Zalerio1');


INSERT INTO ZzZlroFigGroup (zlfiggroup_id ,zlfiggroup_name,zlfiggroup_nooffigrequired) VALUES
('NUMBERS_10','Numbers 0-9',2);

INSERT INTO ZzGameInstTempl
(gameinsttempl_id,gameinsttempl_game_id,gameinsttempl_game_desc, gameinst_paramNum1,gameinst_paramNum2,gameinst_paramNum3,gameinst_paramNum4,gameinst_paramNum5,gameinst_paramVar1)
VALUES
('ZalerioTempl1','ZALERIO_1','Board:22x22,Bets:9,Rounds:6,Figures:12',22,22,9,7,12,'NUMBERS_10');

INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_1','Numeric 1',0,100,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_1_1','NUMERIC_1',0,0),
('NUMERIC_1_2','NUMERIC_1',1,0),
('NUMERIC_1_3','NUMERIC_1',1,-1),
('NUMERIC_1_4','NUMERIC_1',1,-2),
('NUMERIC_1_5','NUMERIC_1',1,-3),
('NUMERIC_1_6','NUMERIC_1',1,-4);

INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_2','Numeric 2',0,100,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_2_1','NUMERIC_2',0,0),
('NUMERIC_2_2','NUMERIC_2',1,0),
('NUMERIC_2_3','NUMERIC_2',2,0),
('NUMERIC_2_4','NUMERIC_2',2,-1),
('NUMERIC_2_5','NUMERIC_2',0,-2),
('NUMERIC_2_6','NUMERIC_2',1,-2),
('NUMERIC_2_7','NUMERIC_2',2,-2),
('NUMERIC_2_8','NUMERIC_2',0,-3),
('NUMERIC_2_9','NUMERIC_2',0,-4),
('NUMERIC_2_10','NUMERIC_2',1,-4),
('NUMERIC_2_11','NUMERIC_2',2,-4);

INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_3','Numeric 3',0,100,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_3_1','NUMERIC_3',0,0),
('NUMERIC_3_2','NUMERIC_3',1,0),
('NUMERIC_3_3','NUMERIC_3',2,0),
('NUMERIC_3_4','NUMERIC_3',2,-1),
('NUMERIC_3_5','NUMERIC_3',0,-2),
('NUMERIC_3_6','NUMERIC_3',1,-2),
('NUMERIC_3_7','NUMERIC_3',2,-2),
('NUMERIC_3_8','NUMERIC_3',2,-3),
('NUMERIC_3_9','NUMERIC_3',0,-4),
('NUMERIC_3_10','NUMERIC_3',1,-4),
('NUMERIC_3_11','NUMERIC_3',2,-4);

INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_4','Numeric 4',0,100,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_4_1','NUMERIC_4',0,0),
('NUMERIC_4_2','NUMERIC_4',0,-1),
('NUMERIC_4_3','NUMERIC_4',0,-2),
('NUMERIC_4_4','NUMERIC_4',1,-2),
('NUMERIC_4_5','NUMERIC_4',1,-3),
('NUMERIC_4_6','NUMERIC_4',1,-4),
('NUMERIC_4_7','NUMERIC_4',2,-2);

INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_5','Numeric 5',0,100,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_5_1','NUMERIC_5',0,0),
('NUMERIC_5_2','NUMERIC_5',1,0),
('NUMERIC_5_3','NUMERIC_5',2,0),
('NUMERIC_5_4','NUMERIC_5',0,-1),
('NUMERIC_5_5','NUMERIC_5',0,-2),
('NUMERIC_5_6','NUMERIC_5',1,-2),
('NUMERIC_5_7','NUMERIC_5',2,-2),
('NUMERIC_5_8','NUMERIC_5',2,-3),
('NUMERIC_5_9','NUMERIC_5',0,-4),
('NUMERIC_5_10','NUMERIC_5',1,-4),
('NUMERIC_5_11','NUMERIC_5',2,-4);

INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_6','Numeric 6',0,100,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_6_1','NUMERIC_6',0,0),
('NUMERIC_6_2','NUMERIC_6',1,0),
('NUMERIC_6_3','NUMERIC_6',2,0),
('NUMERIC_6_4','NUMERIC_6',0,-1),
('NUMERIC_6_5','NUMERIC_6',0,-2),
('NUMERIC_6_6','NUMERIC_6',1,-2),
('NUMERIC_6_7','NUMERIC_6',2,-2),
('NUMERIC_6_8','NUMERIC_6',0,-3),
('NUMERIC_6_9','NUMERIC_6',2,-3),
('NUMERIC_6_10','NUMERIC_6',0,-4),
('NUMERIC_6_11','NUMERIC_6',1,-4),
('NUMERIC_6_12','NUMERIC_6',2,-4);

INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_7','Numeric 7',0,100,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_7_1','NUMERIC_7',0,0),
('NUMERIC_7_2','NUMERIC_7',1,0),
('NUMERIC_7_3','NUMERIC_7',2,0),
('NUMERIC_7_4','NUMERIC_7',2,-1),
('NUMERIC_7_5','NUMERIC_7',1,-2),
('NUMERIC_7_6','NUMERIC_7',2,-2),
('NUMERIC_7_7','NUMERIC_7',2,-3),
('NUMERIC_7_8','NUMERIC_7',2,-4);

INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_8','Numeric 8',0,100,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_8_1','NUMERIC_8',0,0),
('NUMERIC_8_2','NUMERIC_8',1,0),
('NUMERIC_8_3','NUMERIC_8',2,0),
('NUMERIC_8_4','NUMERIC_8',0,-1),
('NUMERIC_8_5','NUMERIC_8',2,-1),
('NUMERIC_8_6','NUMERIC_8',0,-2),
('NUMERIC_8_7','NUMERIC_8',1,-2),
('NUMERIC_8_8','NUMERIC_8',2,-2),
('NUMERIC_8_9','NUMERIC_8',0,-3),
('NUMERIC_8_10','NUMERIC_8',2,-3),
('NUMERIC_8_11','NUMERIC_8',0,-4),
('NUMERIC_8_12','NUMERIC_8',1,-4),
('NUMERIC_8_13','NUMERIC_8',2,-4);


INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_9','Numeric 9',0,100,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_9_1','NUMERIC_9',0,0),
('NUMERIC_9_2','NUMERIC_9',1,0),
('NUMERIC_9_3','NUMERIC_9',2,0),
('NUMERIC_9_4','NUMERIC_9',0,-1),
('NUMERIC_9_5','NUMERIC_9',2,-1),
('NUMERIC_9_6','NUMERIC_9',0,-2),
('NUMERIC_9_7','NUMERIC_9',1,-2),
('NUMERIC_9_8','NUMERIC_9',2,-2),
('NUMERIC_9_9','NUMERIC_9',2,-3),
('NUMERIC_9_10','NUMERIC_9',0,-4),
('NUMERIC_9_11','NUMERIC_9',1,-4),
('NUMERIC_9_12','NUMERIC_9',2,-4);

INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_11','Numeric 0',0,77,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_11_1','NUMERIC_11',0,0);




INSERT INTO ZzZlroFig (zlfig_id,zlfig_name,zlfig_required,zlfig_points,zlfig_zlfiggroup_id) VALUES
('NUMERIC_10','Numeric 0',1,200,'NUMBERS_10');

INSERT INTO ZzZlroFigCoord (zlfigcoord_id,zlfigcoord_zlfig_id,zlfigcoord_posx,zlfigcoord_posy) VALUES
('NUMERIC_10_1','NUMERIC_10',0,0),
('NUMERIC_10_2','NUMERIC_10',1,0),
('NUMERIC_10_3','NUMERIC_10',2,0),
('NUMERIC_10_4','NUMERIC_10',0,-1),
('NUMERIC_10_5','NUMERIC_10',2,-1),
('NUMERIC_10_6','NUMERIC_10',0,-2),
('NUMERIC_10_7','NUMERIC_10',2,-2),
('NUMERIC_10_8','NUMERIC_10',0,-3),
('NUMERIC_10_9','NUMERIC_10',2,-3),
('NUMERIC_10_10','NUMERIC_10',0,-4),
('NUMERIC_10_11','NUMERIC_10',1,-4),
('NUMERIC_10_12','NUMERIC_10',2,-4);


