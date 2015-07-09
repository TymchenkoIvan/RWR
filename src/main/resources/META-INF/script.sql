create database if not exists RWR;

use RWR;

create table candidates ( 
id int (10) AUTO_INCREMENT, 
first_name varchar(20) NOT NULL,
last_name varchar(20) NOT NULL,
create_date DATE NOT NULL, 
interview_date DATE NOT NULL, 
PRIMARY KEY (id) 
);

create table contacts ( 
id int (10) AUTO_INCREMENT, 
id_candidate int (10) NOT NULL,
description varchar(20) NOT NULL,
contact varchar(20) NOT NULL, 
PRIMARY KEY (id),
FOREIGN KEY (id_candidate) REFERENCES candidates (id)
);

create table skills ( 
id int (10) AUTO_INCREMENT, 
id_candidate int (10) NOT NULL,
description varchar(20) NOT NULL, 
rate int(20) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (id_candidate) REFERENCES candidates (id)
);

commit;