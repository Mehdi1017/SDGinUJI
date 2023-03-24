CREATE TYPE stat_enum AS ENUM ('Approved', 'Rejected', 'Pending','Ended');
CREATE TYPE sector_enum AS ENUM ('Governance', 'Education', 'Research');
CREATE TYPE gender_enum AS ENUM ('Male', 'Female', 'Other');
CREATE TYPE type_enum AS ENUM ('PDI', 'PAS', 'Student', 'Others');

CREATE TABLE UJI_participant(
mail 	      VARCHAR(50),
name_mem 	VARCHAR(50) NOT NULL,
usr_type 	type_enum NOT NULL,
gender 	gender_enum NOT NULL,
phone       VARCHAR (15) NOT NULL,
CONSTRAINT cp_uji_participant PRIMARY KEY (mail)
);

CREATE TABLE ODS(
name_ods       VARCHAR(50),
relevance      INTEGER NOT NULL,
axis           sector_enum NOT NULL,
description    VARCHAR(500) NOT NULL,
CONSTRAINT cp_ods PRIMARY KEY (name_ods)
);

CREATE TABLE Initiative (
name_ini       VARCHAR(50) NOT NULL,
description    VARCHAR(500) NOT NULL,
startDate      DATE NOT NULL,
endDate        DATE,
stat           stat_enum NOT NULL,
lastModified   DATE NOT NULL,
progress       DECIMAL(3,2) NOT NULL,
mail           VARCHAR(50),
name_ods       VARCHAR(50),
CONSTRAINT cp_initiative PRIMARY KEY (name_ini),

CONSTRAINT ca_initiative_mail FOREIGN KEY (mail) REFERENCES UJI_participant(mail) ON DELETE RESTRICT ON UPDATE CASCADE,

CONSTRAINT ca_initiative_ods FOREIGN KEY (name_ods) REFERENCES ODS(name_ods) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Subscription (
mail           VARCHAR(50) NOT NULL,
id_sub         SERIAL,
name_ods       VARCHAR(50) NOT NULL,
initialDate    DATE NOT NULL,
endDate        DATE,
CONSTRAINT cp_subscription PRIMARY KEY (id_sub),

CONSTRAINT ca_subscription_mail FOREIGN KEY (mail) REFERENCES UJI_participant(mail) ON DELETE RESTRICT ON UPDATE CASCADE,

CONSTRAINT ca_initiative_ods FOREIGN KEY (name_ods) REFERENCES ODS(name_ods) ON DELETE RESTRICT ON UPDATE CASCADE

);

CREATE TABLE Target (
name_ods        VARCHAR(50),
name_targ       VARCHAR(50),
description     VARCHAR(500),
CONSTRAINT cp_target PRIMARY KEY (name_ods, name_targ),

CONSTRAINT ca_target_ods FOREIGN KEY (name_ods) REFERENCES ODS(name_ods) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Action (
name_act       VARCHAR(50),
name_ini       VARCHAR(50),
name_ods       VARCHAR(50) NOT NULL,
name_targ      VARCHAR(50) NOT NULL,
creation_date  DATE NOT NULL,
end_ate        DATE,
description    VARCHAR(500) NOT NULL,
progress       DECIMAL(3,2) NOT NULL,

CONSTRAINT cp_action PRIMARY KEY (name_act, name_ini),

CONSTRAINT ca_action_ini FOREIGN KEY (name_ini) REFERENCES Initiative(name_ini) ON DELETE RESTRICT ON UPDATE CASCADE,

CONSTRAINT ca_action_targ FOREIGN KEY (name_targ, name_ods) REFERENCES Target(name_targ, name_ods) ON DELETE RESTRICT ON UPDATE CASCADE

);

CREATE TABLE Action_participation (
name_act         VARCHAR(50),
name_ini         VARCHAR(50),
mail             VARCHAR(50),
stat             stat_enum NOT NULL,
startDate        DATE NOT NULL,
endDate          DATE,
commentary       VARCHAR(200) NOT NULL,


CONSTRAINT cp_action_part PRIMARY KEY (name_act, name_ini, mail),

CONSTRAINT ca_action_mail FOREIGN KEY (mail) REFERENCES UJI_participant(mail) ON DELETE RESTRICT ON UPDATE CASCADE, 
CONSTRAINT ca_action_act FOREIGN KEY (name_act,name_ini) REFERENCES Action(name_act,name_ini) ON DELETE RESTRICT ON UPDATE CASCADE

);

CREATE TABLE Initiative_participation (
mail             VARCHAR(50),
name_ini         VARCHAR(50),
request_message  VARCHAR(200) NOT NULL,
creation_date    DATE NOT NULL,
stat             stat_enum NOT NULL,
startDate        DATE,
endDate          DATE,

CONSTRAINT cp_ini_par PRIMARY KEY (mail, name_ini),

CONSTRAINT ca_ini_par_mail FOREIGN KEY (mail) REFERENCES UJI_participant(mail) ON DELETE RESTRICT ON UPDATE CASCADE, 

CONSTRAINT ca_ini_par_name_ini FOREIGN KEY (name_ini) REFERENCES Initiative(name_ini) ON DELETE RESTRICT ON UPDATE CASCADE

);

