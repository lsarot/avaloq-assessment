--drop table if exists simulation;
--drop table if exists sums;


create table simulation(
    time_stamp numeric,
    rolls numeric,
    sides numeric,
    dice numeric,
    constraint pk_sim primary key (time_stamp)
);

create table roll(
    time_stamp numeric,
    roll_sum numeric,
    qty numeric,
    constraint pk_sums primary key (time_stamp,roll_sum),
    constraint fk_simts foreign key (time_stamp) references simulation (time_stamp)
);


commit;