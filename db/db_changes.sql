ALTER TABLE zzgameusersummary
   ADD COLUMN current_gameinst_id character varying;

ALTER TABLE zzuser ADD COLUMN zzuser_status character varying(150);

ALTER TABLE zzinvitefriends ADD COLUMN zzinvitefriends_status character varying(150);

ALTER TABLE zzinvitefriends DROP COLUMN gameid;

ALTER TABLE zzgameseat ADD COLUMN zzgameseat_status character varying(150);

ALTER TABLE zzgameseat DROP COLUMN is_resigned;

ALTER TABLE zzgameinst ADD COLUMN zzgameinst_status character varying(150) default 'active';

ALTER TABLE zzgameinst ADD COLUMN gameinst_created_by character varying(100);

ALTER TABLE zzgameinst  ADD CONSTRAINT zzgame_created_by FOREIGN KEY (gameinst_created_by) REFERENCES zzuser (user_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE zzgameinst DROP COLUMN  game_complete;


ALTER TABLE zzinvitefriends ADD CONSTRAINT id PRIMARY KEY (id);

ALTER TABLE zzinvitefriends add column user_id  character varying(150);

## Added by Snehesh on August 14th 2012 ##
ALTER TABLE zzgameseat
   ADD COLUMN gameseat_score integer;
COMMENT ON COLUMN zzgameseat.gameseat_score IS 'Field used to store the Game Seat Score';

ALTER TABLE zzgameseat ALTER COLUMN gameseat_score SET DEFAULT 0;

## ADDED by Rajesh on Aug 21 for zzinvite table

CREATE SEQUENCE zzinvitefriends_seq


ALTER TABLE zzinvitefriends
ALTER COLUMN id
SET DEFAULT NEXTVAL('zzinvitefriends_seq');

### Added by rajes on Aug 23 for storing the users status time with respect to game

ALTER TABLE zzgameseat ADD COLUMN gameseat_status_update_time timestamp with time zone;
ALTER TABLE zzgameseat ALTER COLUMN gameseat_status_update_time SET DEFAULT now(); 
