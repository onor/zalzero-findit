create table roles(
	role_id varchar(100) primary key,
	role_name varchar(20) not null,
        create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
        update_time TIMESTAMP WITH TIME ZONE DEFAULT now()
);

insert into roles (role_id,role_name) values('1','admin'),('2','customer'),('3','guest');

CREATE TABLE ZzUser (
    user_id varchar(100) primary key,
    user_name varchar(50) not null,
    user_email varchar(150) not null unique,
    user_fbId varchar(150) unique ,
    user_password varchar(150) not null,
    user_handle varchar(150) not null,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    user_role_id varchar(100) not null,
    foreign key (user_role_id) references roles(role_id)
);

insert into ZzUser(user_id,user_email,user_password,user_name,user_handle,user_role_id) values
    ('1','rshekhar.in@gmail.com',MD5('shekhar'),'Ravi Shekhar','rshekhar.in@gmail.com','1'),
    ('2','r84singh@gmail.com',MD5('rajesh'),'Rajesh Singh','r84singh@gmail.com','1'),
    ('3','piyush.shah@gmail.com',MD5('Cytzmlk1'),'Piyush Shah','piyush.shah@gmail.com','1');

CREATE TABLE ZzProduct(
    product_id varchar(100) primary key,
    product_sku varchar(150),
    product_name varchar(150) not null,
    product_brand varchar(150) not null,
    product_shortdesc varchar(200),
    product_imageName varchar(200),
    product_desc varchar(500),
    product_price decimal(19,2),
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE ZzGame(
    game_id varchar(100) primary key,
    game_name varchar(150) not null,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE ZzGameInstTempl(
    gameinsttempl_id varchar(100) primary key,
    gameinsttempl_game_id varchar(100) not null,
    gameinsttempl_game_desc varchar(300) not null,
    gameinst_paramNum1 int,
    gameinst_paramNum2 int,
    gameinst_paramNum3 int,
    gameinst_paramNum4 int,
    gameinst_paramNum5 int,
    gameinst_paramNum6 int,
    gameinst_paramNum7 int,
    gameinst_paramNum8 int,
    gameinst_paramNum9 int,
    gameinst_paramNum10 int,
    gameinst_paramDate1 TIMESTAMP WITH TIME ZONE,
    gameinst_paramDate2 TIMESTAMP WITH TIME ZONE,
    gameinst_paramDate3 TIMESTAMP WITH TIME ZONE,
    gameinst_paramDate4 TIMESTAMP WITH TIME ZONE,
    gameinst_paramDate5 TIMESTAMP WITH TIME ZONE,
    gameinst_paramVar1 varchar(150),
    gameinst_paramVar2 varchar(150),
    gameinst_paramVar3 varchar(150),
    gameinst_paramVar4 varchar(150),
    gameinst_paramVar5 varchar(150),
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (gameinsttempl_game_id) REFERENCES ZzGame(game_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ZzGameInst(
    gameinst_id varchar(100) primary key,
    gameinst_game_id varchar(100) not null,
    gameinst_product_id varchar(100) not null,
    gameinst_gameinsttempl_id varchar(100) not null,
    gameinst_starttime TIMESTAMP WITH TIME ZONE,
    gameinst_endtime TIMESTAMP WITH TIME ZONE,
    gameinst_entrytime TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    gameinst_capacity int default 6,
    gameinst_threshold int default 0,
    gameinst_paramNum1 int,
    gameinst_paramNum2 int,
    gameinst_paramNum3 int,
    gameinst_paramNum4 int,
    gameinst_paramNum5 int,
    gameinst_paramNum6 int,
    gameinst_paramNum7 int,
    gameinst_paramNum8 int,
    gameinst_paramNum9 int,
    gameinst_paramNum10 int,
    gameinst_paramDate1 TIMESTAMP WITH TIME ZONE,
    gameinst_paramDate2 TIMESTAMP WITH TIME ZONE,
    gameinst_paramDate3 TIMESTAMP WITH TIME ZONE,
    gameinst_paramDate4 TIMESTAMP WITH TIME ZONE,
    gameinst_paramDate5 TIMESTAMP WITH TIME ZONE,
    gameinst_paramVar1 varchar(150),
    gameinst_paramVar2 varchar(150),
    gameinst_paramVar3 varchar(150),
    gameinst_paramVar4 varchar(150),
    gameinst_paramVar5 varchar(150),
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (gameinst_game_id) REFERENCES ZzGame(game_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (gameinst_product_id) REFERENCES ZzProduct(product_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (gameinst_gameinsttempl_id) REFERENCES ZzGameInstTempl(gameinsttempl_id) ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TABLE ZzGameSeat(
    gameseat_id varchar(100) primary key,
    gameseat_user_id varchar(100) not null,
    gameseat_gameinst_id varchar(100) not null,
    gameseat_createdat TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (gameseat_user_id) REFERENCES ZzUser(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (gameseat_gameinst_id) REFERENCES ZzGameInst(gameinst_id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE ZzZlroFigGroup(
    zlfiggroup_id varchar(100) primary key,
    zlfiggroup_name varchar(150) not null,
    zlfiggroup_nooffigrequired int default 0,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE ZzZlroFig(
    zlfig_id varchar(100) primary key,
    zlfig_name varchar(150) ,
    zlfig_required smallint default 0,
    zlfig_remark varchar(150),
    zlfig_points int default 0,
    zlfig_zlfiggroup_id varchar(100) not null,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (zlfig_zlfiggroup_id) REFERENCES ZzZlroFigGroup(zlfiggroup_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ZzZlroFigInst(
    zlfiginst_id varchar(100) primary key,
    zlfiginst_zlfig_id varchar(100) not null,
    zlfiginst_valid smallint default 1,
    zlfiginst_noofcolumns int default 0,
    zlfiginst_noofrows int default 0,
    zlfiginst_orientation char default 'H',
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (zlfiginst_zlfig_id) REFERENCES ZzZlroFig(zlfig_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ZzZlroFigCoord(
    zlfigcoord_id varchar(100) primary key,
    zlfigcoord_zlfig_id varchar(100) not null,
    zlfigcoord_posx int not null,
    zlfigcoord_posy int not null,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (zlfigcoord_zlfig_id) REFERENCES ZzZlroFig(zlfig_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ZzZlroFigInstCoord(
    zlfiginstcoord_id varchar(100) primary key,
    zlfiginstcoord_zlfiginst_id varchar(100) not null,
    zlfiginstcoord_posx int not null,
    zlfiginstcoord_posy int not null,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (zlfiginstcoord_zlfiginst_id) REFERENCES ZzZlroFigInst(zlfiginst_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ZzZlroGameInstFig(
    zlgameinstfig_id varchar(100) primary key,
    zlgameinstfig_zlfiginst_id varchar(100) not null,
    zlgameinstfig_gameinst_id varchar(100) not null,
    zlgameinstfig_posleft int not null,
    zlgameinstfig_postop int not null,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (zlgameinstfig_zlfiginst_id) REFERENCES ZzZlroFigInst(zlfiginst_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (zlgameinstfig_gameinst_id) REFERENCES ZzGameInst(gameinst_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ZzZlroGameRound(
    zlgameround_id varchar(100) primary key,
    zlgameround_gameinst_id varchar(100) not null,
    zlgameround_roundname varchar(20),
    zlgameround_timestart TIMESTAMP WITH TIME ZONE,
    zlgameround_timeend TIMESTAMP WITH TIME ZONE,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (zlgameround_gameinst_id) REFERENCES ZzGameInst(gameinst_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ZzZlroGameScore(
    zlgamescore_id varchar(100) primary key,
    zlgamescore_zlgameround_id varchar(100) not null,
    zlgamescore_gameseat_id varchar(100) not null,
    zlgamescore_scored int default 0 not null,
    create_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (zlgamescore_zlgameround_id) REFERENCES ZzZlroGameRound(zlgameround_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (zlgamescore_gameseat_id) REFERENCES ZzGameSeat(gameseat_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ZzZlroGameBet(
    zlgamebet_id varchar(100) primary key,
    zlgamebet_zlgameround_id varchar(100) not null,
    zlgamebet_gameseat_id varchar(100) not null,
    zlgamebet_bettime TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    zlgamebet_betcoord int not null,
    create_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    FOREIGN KEY (zlgamebet_zlgameround_id) REFERENCES ZzZlroGameRound(zlgameround_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (zlgamebet_gameseat_id) REFERENCES ZzGameSeat(gameseat_id) ON DELETE CASCADE ON UPDATE CASCADE
);


create table authitem(
   name varchar(64) not null,
   type integer not null,
   description text,
   bizrule text,
   data text,
   create_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   update_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   primary key (name)
);

create table authitemchild(
    parent varchar(64) not null,
    child varchar(64) not null,
    create_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    primary key (parent,child),
    foreign key (parent) references authitem (name) on delete cascade on update cascade,
    foreign key (child) references authitem (name) on delete cascade on update cascade
);

create table authassignment(
    itemname varchar(64) not null,
    userid varchar(64) not null,
    bizrule text,
    data text,
    create_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    update_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    primary key (itemname,userid),
    foreign key (itemname) references authitem (name) on delete cascade on update cascade
);

create table ZzZlrofriends(
    user_id varchar(64) not null,
    friend_id varchar(64) not null,
    primary key (user_id,friend_id),
    foreign key (user_id) references ZzUser (user_id) on delete cascade on update cascade   
);
